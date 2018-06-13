package cn.edu.sdu.uims.component.panel;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UCanPopupPanelComponentI;

public class UPopupPanel extends UFPanel {
	protected UCanPopupPanelComponentI owner;
	
	public UPopupPanel() {
		container = new JPanel();
	}
	public JPanel getContainer(){
		return container;
	}
	public UCanPopupPanelComponentI getOwner() {
		return owner;
	}

	public void setOwner(UCanPopupPanelComponentI owner) {
		this.owner = owner;
	}



	public void setCanPopupPanelComponent(UCanPopupPanelComponentI owner) {
		this.owner = owner;
	}

	@Override
	public void closePopUpPanel(int returnPanel) {
		// TODO Auto-generated method stub
		if (returnPanel == 0 )
			return;
		this.doBeforePanelClosed();
		owner.closePopupPanel(returnPanel);
	}

}
