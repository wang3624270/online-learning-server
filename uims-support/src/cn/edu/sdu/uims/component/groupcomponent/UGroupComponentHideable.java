package cn.edu.sdu.uims.component.groupcomponent;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.panel.HideableGroupPanel;
import cn.edu.sdu.uims.component.panel.HideablePanel;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentHideable extends UGroupComponent {
	private HideableGroupPanel groupPanel;
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			groupPanel = new HideableGroupPanel();
		}
		UElementTemplate ele = (UElementTemplate) com.getTemplate();
		groupPanel.addHideablePanel(new HideablePanel(ele.text,
				(JPanel) com.getAWTComponent()));
	}

}
