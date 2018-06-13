package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Component;
import java.awt.Dimension;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UElementTemplate;
import pagelayout.Cell;
import pagelayout.Column;
import pagelayout.ComponentCell;

public class UGroupComponentColumn extends UGroupComponent {
	private Column column = null;
	private Component firstCom;
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			column = new Column();
			firstCom = com.getAWTComponent();
			com.getAWTComponent().setPreferredSize(new Dimension(elementTemplate.w,elementTemplate.h));
		}
		column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(com.getAWTComponent()));	
		if(comnum > 0) {
			column.linkHeight(firstCom,1.0, com.getAWTComponent());			
			column.linkWidth(firstCom,1.0, com.getAWTComponent());			
		}
		comnum++;
	}
	public void addComponentAfter() {
		column.createLayout(container);
	}
	
}
