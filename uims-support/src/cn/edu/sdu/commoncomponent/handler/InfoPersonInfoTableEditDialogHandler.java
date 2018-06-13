package cn.edu.sdu.commoncomponent.handler;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.commoncomponent.form.CommonBaseDataQueryForm;
import cn.edu.sdu.commoncomponent.form.InfoPersonInfoTableItemForm;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.form.impl.UDataListForm;
import cn.edu.sdu.uims.form.impl.UTableForm;
import cn.edu.sdu.uims.handler.impl.UDialogHandler;
import cn.edu.sdu.uims.util.UimsUtils;

public class InfoPersonInfoTableEditDialogHandler extends UDialogHandler {
	public void processActionEvent(Object o , String cmd){
		ActionEvent e  = (ActionEvent)o;
		String amd = e.getActionCommand();
		if(amd .equals("compute")) {
			doAdd();
		}else 
			super.processActionEvent(o, cmd);
	}
	public void doAdd(){
		CommonBaseDataQueryForm qForm = (CommonBaseDataQueryForm)this.component.getSubComponent("queryPanel").getData();
		RmiRequest request = new RmiRequest();
		request.add("perTypeCode", qForm.getPerTypeCode());
		request.add("collegeId", qForm.getCollegeId());
		request.add("perNum", qForm.getPerNum());
		request.add("perName",qForm.getPerName());
		request.setCmd("commonBaseDataQueryRequestInfoPersonTableItemFormList");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg()!=null){
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
			return;
		}
		List list = null;
		list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
			if( list == null || list.size() == 0) {
				UimsUtils.messageBoxInfo("找不到对应的人员，不能添加！");
				return;
			}
		InfoPersonInfoTableItemForm f ;
		if(list.size() > 1) {
			UDataListForm form = new UDataListForm();
			form.setDataList(list);
			UDialogPanel dlg = (UDialogPanel) this.startDialog(
					"uimsListDataSingleSelectionDialog", form);
			if (!dlg.getReturnValue().equals(dlg.RETURN_OK))
				return;
			if (form.getValue() == null)
				return;
			f = (InfoPersonInfoTableItemForm)form.getValue();
		}else 
			f = (InfoPersonInfoTableItemForm)list.get(0);
		UTableForm form = (UTableForm) this.dataForm;
		form.addItem(f);
		this.formToComponent();
	}
	public void processMouseEvent(Object o, String cmd){
		MouseEvent e = (MouseEvent)o;
		if(!cmd.equals("clicked"))
			return;
		UTablePanel tp = (UTablePanel)this.component.getSubComponent("dataTable");
		int ids[] = tp.getSelectedIndices();
		if(ids == null || ids.length < 1)
			return;
		UTableForm tForm = (UTableForm)dataForm;
		tForm.removeObjects(ids);
		this.formToComponent();
	}
}
