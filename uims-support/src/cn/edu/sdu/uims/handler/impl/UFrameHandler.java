package cn.edu.sdu.uims.handler.impl;

import cn.edu.sdu.uims.frame.UClientFrame;
import cn.edu.sdu.uims.handler.UFrameHandleI;

public class UFrameHandler extends UFormHandler implements UFrameHandleI {
	protected UClientFrame clientFrame ;
	public void setClientFrame(UClientFrame frame){
		clientFrame = frame;
	}

	public void processActionEvent(Object o, String cmd) {
	}

	public void processMouseEvent(Object o, String cmd) {
		System.out.println("mouse");
	}

	public void updateModelTree() {
		// TODO Auto-generated method stub
		
	}

}
