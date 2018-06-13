package cn.edu.sdu.uims.component.workbench;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UNameObjectPar;
import cn.edu.sdu.uims.base.UPanelI;

public class UWorkbenchPage extends UWorkbench {

	private int currSelectIndex = 0;
	private CardLayout cl;
	public void init() {
		initComponents();
		addComponmentsToContainer();
	}
	
	public void addComponmentsToContainer() {
		cl = new CardLayout();
		container.setLayout(cl);
		currSelectIndex = 0;
	}
	void setCenter(JPanel inPanel) {
		Dimension outPanelSize;
		outPanelSize = container.getSize();
		double outWidth = outPanelSize.getWidth();
		double outHeight = outPanelSize.getHeight();
		inPanel.setBounds((int) 0, (int) 0, // 
				(int) outWidth, (int) outHeight - 30);
		inPanel.updateUI();
	}
	
	protected void addPanel(String name, String tabName, UPanelI inPanel) {
		// ImagePanel panel = new ImagePanel();
		int isExit = isPanelExist(name);
		if (isExit == -1) {
			panelList.add(new UNameObjectPar(name, inPanel));
			JPanel p = (JPanel) inPanel.getAWTComponent();
			ImagePanel panel = new ImagePanel();
			panel.setLayout(new BorderLayout());
			panel.add(p);
			setCenter(p);
			container.add(panel, name);
			cl.show(container, name);
			if(panelList.size() == 1) {
				container.updateUI();
			}
			currSelectIndex = panelList.size()-1;
		}
	}
	public void removeAllPanel() {
		super.removeAllPanel();
		if(container !=null)
			container.removeAll();
	}
	public int isPanelExist(String panelName) {
		int i = -1;
		JPanel p = null;
		int size = panelList.size();
		for (i = 0; i < size; i++) {
			UNameObjectPar par = (UNameObjectPar) panelList.get(i);
			String name = par.getName();
			if (name.equals(panelName)) {
				cl.show(container, name);
				currSelectIndex = i;
				break;
			}
		}
		if (i == size)
			return -1;
		return i;
	}
	public UPanelI getCurrentPanel() {
		if(currSelectIndex < 0 || panelList == null || panelList.size() == 0 || currSelectIndex >= panelList.size())
			return null;
		else 
			return (UPanelI) ((UNameObjectPar) panelList.get(this.currSelectIndex)).getObject();
	}

}
