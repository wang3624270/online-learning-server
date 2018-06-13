package cn.edu.sdu.uims.component;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JSplitPane;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.constant.UConstants;

public class USplitPane extends JSplitPane {
	private ArrayList<UComponentI> comList= new ArrayList<UComponentI>();
	public USplitPane() {
		super();
		// TODO Auto-generated constructor stub
		init();
	}

	public USplitPane(int newOrientation, boolean newContinuousLayout,
			Component newLeftComponent, Component newRightComponent) {
		super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
		// TODO Auto-generated constructor stub
	}

	public USplitPane(int newOrientation, boolean newContinuousLayout) {
		super(newOrientation, newContinuousLayout);
		// TODO Auto-generated constructor stub
	}

	public USplitPane(int newOrientation, Component newLeftComponent,
			Component newRightComponent) {
		super(newOrientation, newLeftComponent, newRightComponent);
		// TODO Auto-generated constructor stub
	}

	public USplitPane(int newOrientation) {
		super(newOrientation);
		// TODO Auto-generated constructor stub
	}
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	public void init(){
		setDividerSize(UConstants.SPLIT_WIDTH);
		setOneTouchExpandable(true);
	}
	public void addUComponent(UComponentI com){
		comList.add(com);
	}
	public void ResetComponentsMinSize(){
		return;
/*		
		UComponentI com;
		URect rect;
		Dimension size;
		for(int i = 0; i < 1;i++) {
			com = (UComponentI)comList.get(i);
			rect = com.getBoundRect();
			size = new Dimension(rect.x+rect.w, rect.y + rect.h);
			com.getAWTComponent().setMinimumSize(size);
		}
*/		
	}
}
