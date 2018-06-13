/**
 * 
 */
package cn.edu.sdu.uims.component.panel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UScrollPane;

/**
 * @author Administrator
 *
 */
public class HideableGroupPanel extends JPanel {

	public static final String LAYOUT_VERTICAL="vertical";
	public static final String LAYOUT_HORIZONTAL="horizontal";
	
	private  String layoutOriented;
	private  List<HideablePanel> hideablePanelList=new ArrayList<HideablePanel>(5);
	private  HideablePanel selectedPanel=null;
	private  UScrollPane scollContainer = null ;
	/**
	 * Create the panel
	 */
	public HideableGroupPanel() {
		this(null);
		//
	}
	public HideableGroupPanel(UScrollPane scollContainer) {
		super();
		this.setLayout(null);
		layoutOriented=LAYOUT_VERTICAL;
		this.scollContainer = scollContainer;
		//
	}
	public void addHideablePanel(int index,HideablePanel hideablePanel){
		hideablePanelList.add(index,hideablePanel);
		this.add(hideablePanel);
		hideablePanel.setGroupPanel(this);
		updatePanelsLayout();
	}
	public void addHideablePanel(HideablePanel hideablePanel){
		addHideablePanel(hideablePanelList.size(),hideablePanel);
	}
	public void updatePanelsLayout() {
		int x = 5;
		int y = 5;
		if (layoutOriented.equals(LAYOUT_VERTICAL)) {
			Iterator iterator = hideablePanelList.iterator();
			HideablePanel hideablePanel;
//			int index = 0;
			while (iterator.hasNext()) {
				hideablePanel = (HideablePanel) iterator.next();
				hideablePanel.setBounds(0, y, hideablePanel.getWidth(),
						hideablePanel.getHeight());				
				y+=hideablePanel.getHeight();
				if(hideablePanel.getWidth() > x )
					x = hideablePanel.getWidth();
			}
		}else{
			Iterator iterator = hideablePanelList.iterator();
			HideablePanel hideablePanel;
//			int index = 0;
			while (iterator.hasNext()) {
				hideablePanel = (HideablePanel) iterator.next();
				hideablePanel.setBounds(x, 5, hideablePanel.getWidth(),
						hideablePanel.getHeight());
				x+=hideablePanel.getWidth();
				if(hideablePanel.getHeight() > y)
					y = hideablePanel.getHeight();
			}
		}
		Dimension d = new Dimension(x+5,y+5);
		this.setPreferredSize(d);
		if(scollContainer != null)
			scollContainer.setPreferredSize(d);
	}
	/**
	 * 设置新的选中panel
	 * @param selectedPanel
	 */
	public void setPanelSelected(HideablePanel selectedPanel){
		if(this.selectedPanel!=null&&this.selectedPanel!=selectedPanel){
			this.selectedPanel.setSelectedState(false);
		}
		this.selectedPanel=selectedPanel;
	}
	/**
	 * 获取选中的可隐藏的panel
	 * @return
	 */
	public HideablePanel getSelectedPanel(){
		return this.selectedPanel;
	}
}

