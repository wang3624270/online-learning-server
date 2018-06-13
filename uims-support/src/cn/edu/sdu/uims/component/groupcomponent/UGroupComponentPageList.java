package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.sdu.uims.component.USplitPane;
import cn.edu.sdu.uims.component.event.GroupComponentEvent;

public class UGroupComponentPageList  extends UGroupComponentPage implements ListSelectionListener{
	protected USplitPane splitPane = null;
	private JList menuList;
	
	public Component getContentPanel(){
		return splitPane;
	}

	public void getInitControlData(){
		menuList.setListData(comList.toArray());
	}

	public void initControlPanel() {
		splitPane = new USplitPane(USplitPane.HORIZONTAL_SPLIT);
		menuList = new JList();
		menuList.addListSelectionListener(this);
		splitPane.setLeftComponent(menuList);	
		splitPane.setDividerLocation(groupElementTemplate.divw);
		splitPane.setDividerSize(groupElementTemplate.divsw);
		splitPane.setRightComponent(contentPanel);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		int index[] = menuList.getSelectedIndices();
		if(index != null && index.length != 0) {

			setCurrentPagePanel(index[0]);
			if(parent != null && parent.getEventAdaptor() != null) {
				UPageComponentDescription comDescription = (UPageComponentDescription) comList.get(index[0]);				
				GroupComponentEvent ge =new GroupComponentEvent(this,index[0],comDescription.com);
				parent.getEventAdaptor().componentSelection(ge);
			}
		}
	}

}