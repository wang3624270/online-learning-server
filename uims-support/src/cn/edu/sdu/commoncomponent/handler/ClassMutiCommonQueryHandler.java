package cn.edu.sdu.commoncomponent.handler;

import java.awt.event.ActionEvent;
import java.util.List;

import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.form.impl.UTableForm;

public class ClassMutiCommonQueryHandler extends ClassCommonQueryHandler {
	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent)o;
		UDialogPanel dlg = null;
		String command = e.getActionCommand();
		if(component instanceof UDialogPanel)
			dlg = (UDialogPanel)component;
		UTableForm tForm = (UTableForm)dataForm;
		if(command.equals("query")) {
			List list = getQueryClassInfoList();
			tForm.setItemsByList(list);
			this.formToComponent();
		}else if(command.equals("selectAll")){
			tForm.doSelectAll(true);
			formToComponent();
		}else if(command.equals("notAll")){
			tForm.doSelectAll(false);
			formToComponent();			
		}
		else if (command.equals("doOk")) {
			this.componentToForm();
			if(dlg != null)
				dlg.doOk();
		} else if (command.equals("doCancel")){
			if(dlg != null)
				dlg.doCancel();
		}
	}

}
