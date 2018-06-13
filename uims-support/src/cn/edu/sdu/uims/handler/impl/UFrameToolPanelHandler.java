package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.frame.UClientFrame;

public class UFrameToolPanelHandler extends UFormHandler {
	
	
	public void processActionEvent(Object o, String cmd) {
			UClientFrame.getFrame().porcessActionPerformed((ActionEvent)o);
	}
	
	public void initQueryPanelAddedData(String name, Object[] a) {
		UComponentI sub = component.getSubComponent("queryPanel").getSubComponent(name);
		if (sub != null) {
			FilterI filter = sub.getFilter();
			if (filter != null) {
				filter.setAddedData(a);
			}
			sub.updateAddedDatas();
		}
	}
}
