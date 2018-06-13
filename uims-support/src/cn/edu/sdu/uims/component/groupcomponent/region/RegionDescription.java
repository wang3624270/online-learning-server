package cn.edu.sdu.uims.component.groupcomponent.region;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UComponentI;

public class RegionDescription {
	protected Container container;
	protected List comList = new ArrayList();
	protected JPanel panel;
	protected int innerHeight = 0, innerWidth = 0;

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public List getComList() {
		return comList;
	}

	public void setComList(List comList) {
		this.comList = comList;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public void addCom(UComponentI com) {
		comList.add(com);
	}

	public void initContents() {

	}

	public void addToContainer() {

	}

	public int getHeight() {
		return innerHeight;
	}

	public int getWidth() {
		return innerWidth;
	}

}
