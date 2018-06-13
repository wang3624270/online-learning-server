package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.event.EventConstants;

public class UDialogHandler extends UFormHandler {

	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent)o;
		String command = e.getActionCommand();
		UDialogPanel dlg = (UDialogPanel)component;
		if (command.equals("okCommand")) {
			this.componentToForm();
			dlg.doOk();
		} else if (command.equals("cancelCommand")){
			dlg.doCancel();
		}else {
			dlg.doUserAction(command);
		}
	}
	public void processWindowEvent(Object o, String cmd){
		UDialogPanel dlg = (UDialogPanel)component;
		if(cmd.equals(EventConstants.CMD_CLOSING)){
			dlg.doClose();
		}
	}
	
	public void requestFirstFoucus(String name){
		UComponentI com = this.component.getSubComponent(name);
		if(com == null)
			return;
		com.requestFirstFoucus();
	}
}
