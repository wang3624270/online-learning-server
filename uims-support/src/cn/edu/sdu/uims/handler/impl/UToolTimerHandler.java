package cn.edu.sdu.uims.handler.impl;

import java.awt.Image;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;

import cn.edu.sdu.uims.handler.UTimerHandlerI;

public class UToolTimerHandler extends UToolHandler implements UTimerHandlerI {

	private Image trayImage = null;
	public Boolean messageState = false;

	@Override
	public void registerTrayIcon(TrayIcon trayIcon, Image trayImage) {
		// TODO Auto-generated method stub
		this.trayIcon = trayIcon;
		this.trayImage = trayImage;
		new Thread(this).start();
		trayFlag = true;
	}

	@Override
	public void setTrayFlag(Boolean flag) {
		trayFlag = flag;
	}

	@Override
	public void setMessageState(Boolean flag) {
		messageState = flag;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while(trayFlag){
			while(messageState){
				this.trayIcon.setImage(new ImageIcon("").getImage());
				try {
					Thread.sleep(getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.trayIcon.setImage(trayImage);
				try {
					Thread.sleep(getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				
				Thread.sleep(getDataCheckDelayTime());
				String info = getNewCheckData();
				if(info != null){
				messageState = true;
				trayIcon.displayMessage("信息", info,
						TrayIcon.MessageType.INFO);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getSleepTime(){
		return 300;
	}
	public int getDataCheckDelayTime(){
		return 10000;
	}
	public String getNewCheckData(){
		return "您有新的数据";
	}
}
