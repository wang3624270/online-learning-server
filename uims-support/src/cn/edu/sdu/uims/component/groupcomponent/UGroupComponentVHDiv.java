package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;
import java.awt.Dimension;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.USplitPane;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentVHDiv extends UGropuComponentDiv {
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(new BorderLayout());
			splitPane = new USplitPane(USplitPane.VERTICAL_SPLIT);
			container.add(splitPane, BorderLayout.CENTER);
			splitPane.setDividerLocation(groupElementTemplate.divh);
			splitPane.setDividerSize(groupElementTemplate.divsw);
			splitPane.setTopComponent(com.getAWTComponent());
			splitPane.addUComponent(com);
			comnum++;
		} else if (comnum == 1) {
			splitPane1 = new USplitPane(USplitPane.HORIZONTAL_SPLIT);
			splitPane1.setPreferredSize(new Dimension(1,1));
			splitPane.setBottomComponent(splitPane1);
			splitPane1.setDividerLocation(groupElementTemplate.divw);
			splitPane1.setDividerSize(groupElementTemplate.divsw);
			splitPane1.setLeftComponent(com.getAWTComponent());
			splitPane1.addUComponent(com);
			comnum++;
		} else {
			splitPane1.setRightComponent(com.getAWTComponent());
			splitPane1.addUComponent(com);
			comnum++;
		}
	}
}
