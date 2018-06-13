package cn.edu.sdu.uims.handler;

import cn.edu.sdu.uims.frame.UClientFrame;

public interface UFrameHandleI extends UFormHandlerI {
	public void setClientFrame(UClientFrame frame);
	
	public void updateModelTree();
}
