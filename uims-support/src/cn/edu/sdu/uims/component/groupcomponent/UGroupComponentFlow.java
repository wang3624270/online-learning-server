package cn.edu.sdu.uims.component.groupcomponent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.util.UimsUtils;

public class UGroupComponentFlow extends UGroupComponent {
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(UimsUtils.getFlowLayout(groupElementTemplate.align,(int)groupElementTemplate.divw,(int)groupElementTemplate.divh));
		}
		container.add(com.getAWTComponent());
		comnum++;
	}
}
