package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.frame.UClientFrame;

public class UFrameToolPaneMultiQuerylHandler extends UFormHandler {
	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent)o;
		String amd = e.getActionCommand();
		int in0 = amd.indexOf("[");
		int in1 = amd.indexOf("]");
		if(in0 > 0 && in1 > 0) {
			String comName = amd.substring(in0+1,in1);
			UPanelI qp= (UPanelI)component.getSubComponent("queryPanel");
			qp.setCurrentPagePanelByComponentName(comName);
			amd = amd.substring(0,in0);
			e = new ActionEvent(e.getSource(),e.getID(),amd);
			
		}
		UClientFrame.getFrame().porcessActionPerformed(e);
	}

	public void initQueryPanelAddedData(String queryName, String comName,
			Object[] a) {
		UComponentI sub = component.getSubComponent("queryPanel")
				.getSubComponent(queryName).getSubComponent(comName);
		if (sub != null) {
			FilterI filter = sub.getFilter();
			if (filter != null) {
				filter.setAddedData(a);
			}
			sub.updateAddedDatas();
		}
	}

}
