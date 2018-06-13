package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.event.GroupComponentEvent;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.frame.UClientFrame;

public class UComponentSelectWorkbenchHandler extends FrameWorkbenchBaseHandler {

	public void processGroupComponentEvent(Object o, String cmd) {
		GroupComponentEvent ge = (GroupComponentEvent)o;
		UComponentI com = (UComponentI)ge.getSelectCom();
		if(com == null)
			return;
		UElementTemplate e = com.getElementTemplate();
		if(e == null)
			return;
		String queryName = e.queryName;
		if(queryName == null)
			return;
		UPanelI p = UClientFrame.getFrame().getClientToolPanel();
		if(p == null)
			return ;
		com = p.getSubComponent("queryPanel");
		if(com == null || !(com instanceof UPanelI))
			return ;
		p = (UPanelI)com;
		p.setCurrentPagePanelByComponentName(queryName);
		
	}
	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent)o;
		String acm =e.getActionCommand();
		if(acm.equals("query")|| acm.equals("compute") || acm.equals("export")
				|| acm.equals("button0")|| acm.equals("button1")|| acm.equals("button2")) {
			UFormHandler f = (UFormHandler)this.component.getCurrentDisplayPagePanel().getHandler();
			f.processActionEvent(o, cmd);
		}
	}
	
}
