package cn.edu.sdu.uims.component.groupcomponent;

import java.util.ArrayList;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentMuti extends UGroupComponent {
	protected int currentIndex = -1;
	protected ArrayList<UPageComponentDescription> comList = new ArrayList<UPageComponentDescription>();

	public void initTitle(UPageComponentDescription des) {

	}

	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		UPageComponentDescription comDescription = new UPageComponentDescription();
		comList.add(comDescription);
		comDescription.com = com;
		comDescription.name = com.getComponentName();
		comDescription.title = elementTemplate.text;
		comDescription.enTitle = elementTemplate.enLabel;
		comDescription.iconName = elementTemplate.iconName;
		comDescription.visible = this.groupElementTemplate.visible;
		initTitle(comDescription);
	}

	public int getVisableNum() {
		int num = 0;
		UPageComponentDescription comDescription;
		for (int i = 0; i < comList.size(); i++) {
			comDescription = (UPageComponentDescription) comList.get(i);
			if (comDescription.visible) {
				num++;
			}
		}
		return num;
	}

	public UPanelI getCurrentDisplayPagePanel() {
		UComponentI com = getCurrentDisplayPageCommonent();
		if (com != null && com instanceof UPanelI) {
			return (UPanelI) com;
		} else {
			return null;
		}

		
	}
	public UComponentI getCurrentDisplayPageCommonent() {
		UComponentI com;
		if (currentIndex < 0)
			return null;
		UPageComponentDescription comDescription;
		comDescription = (UPageComponentDescription) comList.get(currentIndex);
		com = (UComponentI) comDescription.com;
		return com;
	}

	public void setCurrentSelectPanelByCom(UComponentI com) {
		UPageComponentDescription de;
		for (int i = 0; i < comList.size(); i++) {
			de = comList.get(i);
			if (de.com == com) {
				currentIndex = i;
				return;
			}
		}
	}

	public UPageComponentDescription getPageComponentDescriptionByName(
			String name) {
		UPageComponentDescription dc = null;
		for (int i = 0; i < comList.size(); i++) {
			dc = (UPageComponentDescription) comList.get(i);
			if (dc.name.equals(name)) {
				return dc;
			}
		}
		return null;
	}

	public void setSubComponentVisible(String name, boolean visible) {
		UPageComponentDescription dc = getPageComponentDescriptionByName(name);
		if (dc != null) {
			dc.visible = visible;
			addComponentAfter();
		}
	}
	public void setSubComponentVisibleAttribute(String name, boolean visible) {
		UPageComponentDescription dc = getPageComponentDescriptionByName(name);
		if (dc != null) {
			dc.visible = visible;
		}
	}

	public void removeComponent() {

	}

	public void reLayoutComponents() {
		removeComponent();
		addComponentAfter();
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
}
