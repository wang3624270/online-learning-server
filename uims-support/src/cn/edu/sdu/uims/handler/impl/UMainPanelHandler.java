package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;

import cn.edu.sdu.uims.frame.UClientFrame;

public class UMainPanelHandler extends UFormHandler {
	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent)o;
		String acm = e.getActionCommand();
		UClientFrame.getFrame().enterWorkBenchByButton(acm);
	}
}
