package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UPanelI;

public class UGroupComponentPage extends UGroupComponentMuti {
	protected JPanel contentPanel;
	protected CardLayout cl;

	public void setTitlePanelToPanel() {
	}

	public Component getContentPanel() {
		return contentPanel;
	}

	public void initControlPanel() {

	}

	public void addComponentBefore() {
		Dimension dimension = new Dimension(1, 1);
		contentPanel = new JPanel();
		contentPanel.setPreferredSize(dimension);
		cl = new CardLayout();
		contentPanel.setLayout(cl);
		initControlPanel();
		container.setLayout(new BorderLayout());
		container.add(getContentPanel(), BorderLayout.CENTER);
	}

	public void getInitControlData() {

	}

	public void addComponentAfter() {
		int i;
		getInitControlData();
		contentPanel.removeAll();
		currentIndex = -1;
		int index = 0 - 1;
		UPageComponentDescription comDescription;
		for (i = 0; i < comList.size(); i++) {
			comDescription = (UPageComponentDescription) comList.get(i);
			if (comDescription.visible) {
				if (index < 0) {
					index = i;
				}
				contentPanel.add(comDescription.com.getAWTComponent(),
						comDescription.name);
			}
		}
		if (index >= 0)
			setCurrentPagePanel(index);
	}

	public void removeComponent() {
		contentPanel.removeAll();
	}

	public void setCurrentPagePanel(int index) {
		UPageComponentDescription comDescription;
		if (currentIndex == index)
			return;
		if (index >= 0) {
			comDescription = (UPageComponentDescription) comList.get(index);
			cl.show(contentPanel, comDescription.name);
			if (comDescription.com != null
					&& comDescription.com instanceof UPanelI) {
				((UPanelI) comDescription.com).refreshComponentData();
			}
		}
		currentIndex = index;

	}

	public void setCurrentPagePanelByComponentName(String comName) {
		UPageComponentDescription comDescription;
		int i;
		int index = -1;
		for (i = 0; i < comList.size(); i++) {
			comDescription = (UPageComponentDescription) comList.get(i);
			if (comDescription.visible && comDescription.name.equals(comName)) {
				index = i;
				break;
			}
		}
		if (index < 0 || currentIndex == index)
			return;
		if (index >= 0) {
			comDescription = (UPageComponentDescription) comList.get(index);
			cl.show(contentPanel, comDescription.name);
			if (comDescription.com != null
					&& comDescription.com instanceof UPanelI) {
				((UPanelI) comDescription.com).refreshComponentData();
			}
		}
		currentIndex = index;

	}

}
