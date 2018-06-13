package cn.edu.sdu.uims.handler.impl;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.UTextArea;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.form.impl.UMutiInfoForm;
import cn.edu.sdu.uims.util.UimsUtils;

public class UClientBusinessProcessDialogHandler extends UDialogHandler {
	public void start(){
		new Thread(){
			public void run(){
				process();
			}
		}.start();
	}
	public void process(){
		UMutiInfoForm f = (UMutiInfoForm)dataForm;
		if(f != null && f.getPi() != null){
			f.getPi().businessProcess(this);
		}		
	}
	public void processWindowEvent(Object o, String cmd){
		UDialogPanel dlg = (UDialogPanel)component;
		if(cmd.equals(EventConstants.CMD_CLOSING)){
			UMutiInfoForm f = (UMutiInfoForm)dataForm;
			if(!(f.isEnd())) {
				UimsUtils.messageBoxInfo("程序为执行完，不能退出");
				return;
			}
			dlg.doClose();
		}
	}
	
	public void doPanelRefreshAfterDataUpate(){
		
		UComponentI com = this.component.getSubComponent("infoArea");
		if(com == null)
			return ;
		UTextArea a = (UTextArea)com; 
		a.moveToContentLast();		
	}
	
}
