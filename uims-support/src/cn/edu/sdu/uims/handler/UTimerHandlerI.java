package cn.edu.sdu.uims.handler;

import java.awt.Image;
import java.awt.TrayIcon;

public interface UTimerHandlerI extends Runnable{
	
	public void initAddedData();
	
	public void registerTrayIcon(TrayIcon trayIcon,Image trayImage);

	public void setTrayFlag(Boolean flag);
	
	public void setMessageState(Boolean flag);
}
