package cn.edu.sdu.uims.handler.impl;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.frame.UClientFrame;

public class FrameWorkbenchBaseHandler extends UToolHandler {
	public UFormI getQueryForm(){
		UPanelI p = UClientFrame.getFrame().getClientToolPanel();
		if(p == null)
			return null;
		UComponentI com = p.getSubComponent("queryPanel");
		if(com == null)
			return null;
		if(com instanceof  UPanelI) {
			UPanelI qp = (UPanelI) com;
			com = qp.getCurrentDisplayPageCommonent();
		}
		Object  o = com.getData();
		if(o == null || !(o instanceof UFormI) )
			return null;
		else
			return (UFormI)o;
	}
	public UComponentI getQueryPanel(){
		UPanelI p = UClientFrame.getFrame().getClientToolPanel();
		if(p == null)
			return null;
		UComponentI com = p.getSubComponent("queryPanel");
		if(com == null)
			return null;
		if(com instanceof  UPanelI) {
			UPanelI qp = (UPanelI) com;
			com = qp.getCurrentDisplayPageCommonent();
		}
		return com;
	}
}
