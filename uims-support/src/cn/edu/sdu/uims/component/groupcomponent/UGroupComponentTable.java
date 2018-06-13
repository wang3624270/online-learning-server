package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;


public class UGroupComponentTable extends UGroupComponentMuti {
	private JTabbedPane tabbedPane;

	public void addComponentBefore() {
		container.setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		container.setPreferredSize(new Dimension(1,1));
		container.add(tabbedPane);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Component com = tabbedPane.getSelectedComponent();
				UComponentI comI = getUComponentI(com);
				if(comI != null) {
					if(comI instanceof UPanelI) {
						UPanelI p = (UPanelI) comI;
						p.refreshComponentData();
					}
					setCurrentSelectPanelByCom(comI);
					if(changeListener != null) {
						ChangeEvent event = new ChangeEvent(comI);
						changeListener.stateChanged(event);					
					}
				}
			}
		});
	}

	public UComponentI getUComponentI(Component component){
		UPageComponentDescription comDescription;
		for(int i = 0; i < comList.size();i++){
			comDescription = (UPageComponentDescription)comList.get(i);
			if(comDescription.com.getAWTComponent() == component){
				return comDescription.com;
			}
		}
		return null;
	}
	public void addComponentAfter() {
		int i;
		currentIndex = -1;
		JToggleButton bt;
		int num = getVisableNum();
		int index = 0-1;
		UPageComponentDescription comDescription;
		for (i = 0; i < comList.size(); i++) {
			comDescription = (UPageComponentDescription)comList.get(i);
			if(comDescription.visible){
				if(index < 0) {
					index = i;
				}
				tabbedPane.addTab(comDescription.toString(), null, comDescription.com.getAWTComponent());
			}
		}
		if(index >= 0)
			setCurrentPagePanel(index);
	}
	public void setCurrentPagePanel(int index) {
		UPageComponentDescription comDescription;
		if (currentIndex == index)
			return;
		comDescription = (UPageComponentDescription)comList.get(index);
		tabbedPane.setSelectedComponent(comDescription.com.getAWTComponent());
		currentIndex = index;
		if(changeListener != null) {
			ChangeEvent event = new ChangeEvent(comDescription.com);
			changeListener.stateChanged(event);
		}
	}
	
	public void removeComponent(){
		tabbedPane.removeAll();
	}
	public void componentRepaint(){
		tabbedPane.removeAll();
		addComponentAfter();
	}

}
