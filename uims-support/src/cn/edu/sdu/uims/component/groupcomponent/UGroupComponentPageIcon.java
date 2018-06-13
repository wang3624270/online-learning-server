package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Component;
import java.util.List;

import cn.edu.sdu.uims.component.USplitPane;

public class UGroupComponentPageIcon extends UGroupComponentPage {
	protected USplitPane splitPane = null;
	private UIconControlPanel iconPanel;
	private int currentDispMode = 1;
	public static final int DISP_MODE_ICON_ONLY = 1;
	public static final int DISP_MODE_ICON_PANEL = 2;
	
	public Component getContentPanel(){
		return splitPane;
	}

	public void initControlPanel() {
		splitPane = new USplitPane(USplitPane.HORIZONTAL_SPLIT);
		iconPanel = new UIconControlPanel(this);
		iconPanel.setSize(1000, 1000);
		splitPane.setLeftComponent(iconPanel);	
		splitPane.setDividerLocation(groupElementTemplate.divw);
		splitPane.setDividerSize(groupElementTemplate.divsw);
	}
	
	public void exchangeDispMode(){
		if(currentDispMode == DISP_MODE_ICON_ONLY)
			currentDispMode = DISP_MODE_ICON_PANEL;
		else
			currentDispMode = DISP_MODE_ICON_ONLY;			
		if(currentDispMode == DISP_MODE_ICON_ONLY)
			splitPane.setRightComponent(null);	
		else{
			int w = splitPane.getLastDividerLocation();
			if(w < groupElementTemplate.divw)
				w = groupElementTemplate.divw;
			splitPane.setDividerLocation(w);
			splitPane.setRightComponent(contentPanel);	
		}
	}
	public void getInitControlData(){
		iconPanel.initIconData(comList);
	}
	
	public int getCurrentDispMode() {
		return currentDispMode;
	}

	public void setCurrentDispMode(int currentDispMode) {
		this.currentDispMode = currentDispMode;
	}
	@Override
	public void setPageIconDisplayInfo(List<String> infoList) {
		// TODO Auto-generated method stub
		iconPanel.setPageIconDisplayInfo(infoList);
	}

}
