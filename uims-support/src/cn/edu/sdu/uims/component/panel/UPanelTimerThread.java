package cn.edu.sdu.uims.component.panel;

import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.event.UTimerEvent;
import cn.edu.sdu.uims.handler.impl.UFormHandler;

public class UPanelTimerThread extends Thread {
	private UPanelI panel;
	private boolean state = true;
	private UTimerEvent timerEvent;

	public UPanelTimerThread(UPanelI panel){
		state = true;
		this.panel = panel;
		timerEvent	=new UTimerEvent(panel);
	}
	public void endTimer(){
		state = false;
		
	}
	public void startTimer(){
		state = true;
		
	}
	public void run() {

		while (state) {
			try {
					if(panel != null) {
						UFormHandler h = (UFormHandler)panel.getHandler();
						if(h != null){
							h.processTimerEvent(timerEvent, null);
						}
				}
				sleep(60000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
