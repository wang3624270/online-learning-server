package cn.edu.sdu.video.handler;

import java.awt.event.ActionEvent;

import cn.edu.sdu.uims.handler.impl.UFormHandler;

public class LocalLiveBroadcastInfoManageHandler extends UFormHandler{
	
	public void processActionEvent(Object obj,String cmd) {
		ActionEvent e = (ActionEvent)obj;
		String amd = e.getActionCommand();
		if(amd.equals("doSave")) {
			doSave();
		}
	}
	
	public void doSave() {
		
	}

}
