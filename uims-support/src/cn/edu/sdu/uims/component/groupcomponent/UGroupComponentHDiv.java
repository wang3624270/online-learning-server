package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.USplitPane;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentHDiv extends UGropuComponentDiv {
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(new BorderLayout());
			splitPane = new USplitPane(USplitPane.HORIZONTAL_SPLIT);
			splitPane.setDividerLocation(groupElementTemplate.divw);
			splitPane.setDividerSize(groupElementTemplate.divsw);
			splitPane.setLeftComponent(com.getAWTComponent());	
			splitPane.addUComponent(com);
//			splitPane.addPropertyChangeListener(com);
			comnum++;
		} else {
			splitPane.setRightComponent(com.getAWTComponent());				
			container.add(splitPane, BorderLayout.CENTER);
			splitPane.addUComponent(com);
//			splitPane.addPropertyChangeListener(com);
			comnum++;
		}
	}
}
