package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.USplitPane;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentVDiv extends UGropuComponentDiv {

	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(new BorderLayout());
			splitPane = new USplitPane(USplitPane.VERTICAL_SPLIT);
			splitPane.setDividerLocation(groupElementTemplate.divh);
			splitPane.setDividerSize(groupElementTemplate.divsw);
			splitPane.setTopComponent(com.getAWTComponent());
			splitPane.addUComponent(com);
			comnum++;
		} else {
			splitPane.setBottomComponent(com.getAWTComponent());
			splitPane.addUComponent(com);
			container.add(splitPane, BorderLayout.CENTER);
			comnum++;
		}
	}
}
