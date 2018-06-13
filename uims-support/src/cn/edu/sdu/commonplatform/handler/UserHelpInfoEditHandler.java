package cn.edu.sdu.commonplatform.handler;

import java.awt.event.ActionEvent;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.form.UFormIdI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.util.UimsUtils;

public class UserHelpInfoEditHandler extends UFormHandler {

	public void initStudyLevelAddedData(List list) {
		UComponentI com = this.component.getSubComponent("queryPanel").getSubComponent("studyLevel");
		FilterI f = com.getFilter();
		f.setAddedData(list);
		com.updateAddedDatas();
	}
	public void start(){
		updateStudyLevelComboBox();
		doQuery();
	}
	public void doQuery() {
		UFormHandler sh = (UFormHandler) this.getSubComponentHandler("queryPanel");
		sh.componentToForm();
		RmiRequest request = new RmiRequest();
		RmiResponse respond;
		request.add(RmiKeyConstants.KEY_FORM, sh.getForm());
		request.setCmd("supnuevoQuerySupnuevoMerchantHelpInfo");
		respond = UimsUtils.requestProcesser(request);
		UForm fo;
		if (respond.getErrorMsg() == null) {
			fo = (UForm) respond
					.get(RmiKeyConstants.KEY_FORM);
			if (fo != null) {
				componentToForm();
				UFormHandler dataHandler = (UFormHandler) this
						.getSubComponentHandler("contentPanel");
				dataHandler.formToComponent(fo);
			}
		} else {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
		}
	}

	public void doModify() {
		this.componentToForm();
		UFormHandler dataHandler = (UFormHandler) this
				.getSubComponentHandler("contentPanel");
		UFormIdI  contentForm = (UFormIdI) dataHandler
				.getForm();
		if (contentForm.getId() == null ) {
			UimsUtils.messageBoxInfo("用户类型或者学习级别为空不能保存");
			return;
		}
		RmiRequest request = new RmiRequest();
		RmiResponse respond;
		request.add(RmiKeyConstants.KEY_FORM, contentForm);
		request.setCmd("supnuevoModifySupnuevoMerchantHelpInfo");
		respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() == null) {
			UimsUtils.messageBoxInfo("保存成功！");
		} else {
			UimsUtils.messageBoxError(respond.getErrorMsg());
		}
	}

	// 单纯地增加一条空记录
	public void doAdd() {
		UFormHandler sh = (UFormHandler) this.getSubComponentHandler("queryPanel");
		sh.componentToForm();
		RmiRequest request = new RmiRequest();
		RmiResponse respond;
		request.setCmd("supnuevoAddSupnuevoMerchantHelpInfo");
		respond = UimsUtils.requestProcesser(request);
		UForm fo;
		if (respond.getErrorMsg() == null) {
			fo = (UForm) respond
					.get(RmiKeyConstants.KEY_FORM);
			componentToForm();
			UFormHandler dataHandler = (UFormHandler) this
					.getSubComponentHandler("contentPanel");
//			formToComponent(contentPanel);
			UimsUtils.messageBoxInfo("添加成功");
			List list  = (List) respond
					.get(RmiKeyConstants.KEY_FORMLIST);
			this.initStudyLevelAddedData(list);
		} else {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
		}
	}

	public void updateStudyLevelComboBox() {
		UFormHandler sh = (UFormHandler) this.getSubComponentHandler("queryPanel");
		sh.componentToForm();
		RmiRequest request = new RmiRequest();
		RmiResponse respond;
//		request.add("merchantType", form.getMerchantType());
		request.setCmd("supnuevoGetStudyLevelListOfMerchantType");
		respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() == null) {
			List list  = (List) respond
					.get(RmiKeyConstants.KEY_FORMLIST);
			this.initStudyLevelAddedData(list);
		}
	}

	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent) o;
		String amd = e.getActionCommand();
		if (amd.equals("doQuery") || amd.equals("studyLevel") ) {
			doQuery();
		} else if (amd.equals("doModify")) {
			doModify();
		} else if (amd.equals("doAdd")) {
			doAdd();
		}else if(amd.equals("merchantType")) {
			updateStudyLevelComboBox();
		}
	}
}
