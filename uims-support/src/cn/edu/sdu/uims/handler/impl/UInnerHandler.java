package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.button.UButton;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.handler.UHandlerI;

public class UInnerHandler extends UFormHandler {
	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent)o;
		UComponentI eventSource = (UComponentI)e.getSource();
		String cmdString  = e.getActionCommand();
		if( eventSource instanceof UComboBox)
			cmdString = eventSource.getComponentName();
		if(eventSource instanceof UButton)
			this.componentToForm();
		this.sendActionEventToParent(cmdString);
	}
	public void processItemEvent(Object o, String cmd) {
		// TODO Auto-generated method stub
		UHandlerI h =  this.getParent();
		if(h != null && h instanceof UFormHandler) {
			UFormHandler fh = (UFormHandler)h;
			fh.processItemEvent(o,cmd);
		}
	}
	public void processMouseEvent(Object o, String cmd) {
		// TODO Auto-generated method stub
		UHandlerI h =  this.getParent();
		if(h != null && h instanceof UFormHandler) {
			UFormHandler fh = (UFormHandler)h;
			fh.processMouseEvent(o,cmd);
		}
	}
	@Override
	public List getInitAddedDataList(String comName) {
		// TODO Auto-generated method stub
		UHandlerI h = this.getParent();
		if(h != null)
			return h.getInitAddedDataList(comName);
		return null;
	}
	
	
	public HashMap<String, String >getBusinessParaMap(String comName){
		UHandlerI h = this.getParent();
		if(h != null)
			return h.getBusinessParaMap(comName);
		return null;
	}
	
	public void componentProcessFished(String comName){
		UHandlerI h = this.getParent();
		if(h != null)
			h.componentProcessFished(comName);
	}
}
