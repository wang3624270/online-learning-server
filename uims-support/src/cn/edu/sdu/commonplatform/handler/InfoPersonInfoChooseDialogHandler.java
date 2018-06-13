package cn.edu.sdu.commonplatform.handler;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.InfoPersonInfoForm;
import cn.edu.sdu.common.form.USFormI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.form.impl.UTableForm;
import cn.edu.sdu.uims.handler.impl.UDialogHandler;
import cn.edu.sdu.uims.util.UimsUtils;

public class InfoPersonInfoChooseDialogHandler extends UDialogHandler {

	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent) o;
		String command = e.getActionCommand();
		if(command.equals("query")) {
			doQuery();
		}else if(command.equals("selectAll")) {
			selectAll(true);
		}else if(command.equals("selectAllNot")) {
			selectAll(false);
		}else 
			super.processActionEvent(o, cmd);
	}
	public void doQuery(){
		InfoPersonInfoForm f = (InfoPersonInfoForm)this.component.getSubComponent("queryPanel").getData();
		RmiRequest request = new RmiRequest();
		request.add("perNum", f.getPerNum());
		request.add("perName", f.getPerName());
		request.add("mobilePhone", f.getMobilePhone());
		request.setCmd("commonplatformGetInfoPersonInfoformListUserChoose");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		UTableForm tForm = (UTableForm)dataForm;
		tForm.setItemsByList(list);
		this.formToComponent();
	}
	public void selectAll(boolean b) {
		UTablePanel tp = (UTablePanel) this.component.getSubComponent("personTable");
		tp.doSelectAll("select", b);
	}
	public void processMouseEvent(Object obj, String cmd){
		if(!cmd.equals("clicked"))
			return;
		MouseEvent e = (MouseEvent) obj;
		UTablePanel tp = (UTablePanel)e.getSource();
		int [] ins = tp.getSelectedIndices();
		if(ins == null || ins.length == 0)
			return;
		UTableForm tForm = (UTableForm)dataForm;
		Object items[] = tForm.getItems();
		if(items == null || items.length <= ins[0])
			return;
		USFormI f = (USFormI)items[ins[0]];
		f.setSelect(true);
		UDialogPanel dlg = (UDialogPanel)component;
		dlg.doOk();
	}
}
