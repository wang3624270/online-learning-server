package cn.edu.sdu.commonplatform.handler;

import java.awt.event.ActionEvent;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.util.Base64;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.form.impl.PasswdForm;
import cn.edu.sdu.uims.handler.impl.UDialogHandler;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UserChangePasswordHandler extends UDialogHandler {
	
	public void doOkCommand(){
		this.componentToForm();
		PasswdForm form = (PasswdForm) dataForm;
		if(form.getEnterOldPwd() == null || form.getEnterOldPwd().length() == 0 || form.getEnterOldPwd().trim().length() == 0) {
			UimsUtils.messageBoxInfoByPromptName("非法旧密码");
			return ;
		}
		if(form.getEnterNewPwd() == null || form.getEnterNewPwd().length() == 0 || form.getEnterNewPwd().trim().length() == 0) {
			UimsUtils.messageBoxInfoByPromptName("请输入新密码");
			return ;
		}
		if(UFactory.getModelSession().getEnvironmentTemplate().checkPassword ) {
			String tpw = new String (Base64.decode(form.getEnterNewPwd().getBytes()));
			if(UimsUtils.validateWeakPassword(tpw) == UimsUtils.WEAKPAS_YES) {
				UimsUtils.messageBoxInfoByPromptName("密码为弱密码");
				return ;
			}
		}
		if(form.getEnterRepeatNewPwd() == null || form.getEnterRepeatNewPwd().length() == 0 || form.getEnterRepeatNewPwd().trim().length() == 0) {
			UimsUtils.messageBoxInfoByPromptName("没有重复新密码");
			return ;
		}
		if(form.getEnterNewPwd().trim().equals(form.getEnterOldPwd().trim())) {
			UimsUtils.messageBoxInfoByPromptName("新密码和旧密码相同");
			return ;
		}
		if(!form.getEnterNewPwd().trim().equals(form.getEnterRepeatNewPwd().trim())) {
			UimsUtils.messageBoxInfoByPromptName("新密码和确认密码不相同");
			return ;
		}
		RmiRequest request = new RmiRequest();
		request.add("oldPassword", form.getEnterOldPwd());
		request.add("newPassword", form.getEnterNewPwd());
		request.setCmd("changePasswordBase64");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() == null) {
			UimsUtils.messageBoxInfoByPromptName("修改成功");				
		} else {
			UimsUtils.messageBoxError(respond.getErrorMsg());
		}
	}
	public void processActionEvent(Object obj, String cmd){
		ActionEvent e = (ActionEvent) obj;
		String command = e.getActionCommand();
		if(command.equals("ok")) {
			doOkCommand();
		}else {
			UDialogPanel dlg = (UDialogPanel)component;
			if (command.equals("okCommand")) {
				doOkCommand();
				dlg.doOk();
			} else if (command.equals("cancelCommand")){
				dlg.doCancel();
			}else {
				dlg.doUserAction(command);
			}

		}
	}
}
