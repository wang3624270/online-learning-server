package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;

import cn.edu.sdu.uims.component.button.ui.UBasicToggleButtonUI;

public class UGroupComponentPageLabel extends UGroupComponentPage {
	private JPanel ptitlePanel;
	private ProcessActionEventor paEventor;

	public String getMenuLayoutMode(String mode) {
		if(mode.equals("top"))
			return  BorderLayout.NORTH;
		else if(mode.equals("bottom")) {
			return  BorderLayout.SOUTH;			
		}
		else if(mode.equals("left")) {
			return  BorderLayout.WEST;			
		}
		else if(mode.equals("right")) {
			return  BorderLayout.EAST;			
		}
		return null;
	}

	public void addComponentBefore() {
		Dimension dimension= new Dimension(1,1);
		ptitlePanel = new JPanel();
		contentPanel = new JPanel();
		paEventor = new ProcessActionEventor();
		container.setLayout(new BorderLayout());
		String mode = getMenuLayoutMode(groupElementTemplate.dispTitleMode);
		if(mode !=null)
			container.add(ptitlePanel,mode);
		setTitlePanelToPanel();
		container.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setPreferredSize(dimension);
		cl = new CardLayout();
		contentPanel.setLayout(cl);
	}
	public String changeMenuDispTitle(String title){
		return title;
	}
	public void initTitle(UPageComponentDescription comDescription){
		comDescription.bt  = new JToggleButton(changeMenuDispTitle(comDescription.title), false);
		comDescription.bt.setUI(new UBasicToggleButtonUI());
		comDescription.bt.setActionCommand(comDescription.name);
		comDescription.bt.addActionListener(this.paEventor);
	}

	public void setCurrentPagePanel(int index) {
		UPageComponentDescription comDescription;
		if (currentIndex == index)
			return;
		if (currentIndex >= 0) {
			comDescription = (UPageComponentDescription)comList.get(currentIndex);
			comDescription.bt.setSelected(false);
		}
		comDescription = (UPageComponentDescription)comList.get(index);
		comDescription.bt.setSelected(true);
		cl.show(contentPanel, comDescription.name);
		currentIndex = index;
		if(changeListener != null) {
			ChangeEvent event = new ChangeEvent(comDescription.com);
			changeListener.stateChanged(event);
		}
	}
	public GridLayout getGridLayout(int num) {
		return new GridLayout(1, num);
	}
	public void addComponentAfter() {
		int i;
		ptitlePanel.removeAll();
		contentPanel.removeAll();
		currentIndex = -1;
		int num = getVisableNum();
		GridLayout gl = getGridLayout(num);
		ptitlePanel.setLayout(gl);
		int index = 0-1;
		UPageComponentDescription comDescription;
		for (i = 0; i < comList.size(); i++) {
			comDescription = (UPageComponentDescription)comList.get(i);
			if(comDescription.visible){
				if(index < 0) {
					index = i;
				}
				ptitlePanel.add(comDescription.bt);
				contentPanel.add(comDescription.com.getAWTComponent(), comDescription.name);
			}
		}
		if(index >= 0)
			setCurrentPagePanel(index);
	}

	public class ProcessActionEventor implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd = e.getActionCommand();
			UPageComponentDescription comDescription;
			for (int i = 0; i < comList.size(); i++) {
				comDescription = (UPageComponentDescription)comList.get(i);
				if (comDescription.name.equals(cmd)) {
					setCurrentPagePanel(i);
					break;
				}
			}
		}
	}
	public void removeComponent(){
		ptitlePanel.removeAll();
		contentPanel.removeAll();
	}
	public void componentRepaint(){
		ptitlePanel.updateUI();
	}
}
