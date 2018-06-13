package cn.edu.sdu.uims.component.panel;

import cn.edu.sdu.uims.frame.UClientFrame;

public class UPanelTimerControlActionThread extends Thread{
	private UClientFrame clientFrame;
	private boolean state = true;

	public UPanelTimerControlActionThread(UClientFrame frame){
		state = true;
		this.clientFrame = frame;
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
				clientFrame.setPanelTimeControlActionAttribute();
				sleep(60000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
