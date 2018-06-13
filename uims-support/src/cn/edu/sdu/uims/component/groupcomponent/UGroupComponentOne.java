package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentOne extends UGroupComponent {
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(new BorderLayout());
			container.add(com.getAWTComponent(), BorderLayout.CENTER);
			comnum++;
		}
	}

}
