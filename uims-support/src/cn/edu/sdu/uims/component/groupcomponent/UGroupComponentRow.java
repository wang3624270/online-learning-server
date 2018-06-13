package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Component;
import java.awt.Dimension;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UElementTemplate;
import pagelayout.Cell;
import pagelayout.ComponentCell;
import pagelayout.Row;

public class UGroupComponentRow extends UGroupComponent {
	private Row row = null;
	private Component firstCom;
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			row = new Row();
			firstCom = com.getAWTComponent();
		}
		com.getAWTComponent().setPreferredSize(new Dimension(elementTemplate.w,elementTemplate.h));
		row.newColumn(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(com.getAWTComponent()));	
		if(comnum > 0) {
			row.linkWidth(firstCom,1.0, com.getAWTComponent());			
		}
		comnum++;
	}
	public void addComponentAfter() {
		row.createLayout(container);
	}

}
