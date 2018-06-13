package cn.edu.sdu.uims.component.groupcomponent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UElementTemplate;
import pagelayout.CellGrid;
import pagelayout.ComponentCell;

public class UGroupComponentGrid extends UGroupComponent {
	private ComponentCell cells[][];
	private double comScaleW[][], comScaleH[][];
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if(comnum == 0) {
			cells= new ComponentCell[groupElementTemplate.rowNum][groupElementTemplate.colNum];
			comScaleW= new double[groupElementTemplate.rowNum][groupElementTemplate.colNum];
			comScaleH= new double[groupElementTemplate.rowNum][groupElementTemplate.colNum];
		}
		cells[comnum/groupElementTemplate.colNum][comnum % groupElementTemplate.colNum] = new ComponentCell(com.getAWTComponent());
		comScaleW[comnum/groupElementTemplate.colNum][comnum % groupElementTemplate.colNum] = elementTemplate.weightx;
		comScaleH[comnum/groupElementTemplate.colNum][comnum % groupElementTemplate.colNum] = elementTemplate.weighty;
		comnum++;
	}	
	public void addComponentAfter() {
		CellGrid grid=
				CellGrid.createCellGrid(cells);
		grid.createLayout(container);
//		grid.setSize(cells[0][0].getComponent(), new Dimension(100,50), 2);
		int i,j;
		for(i = 0; i <groupElementTemplate.rowNum; i++) {
			for(j = 0; j < groupElementTemplate.colNum;j++) {
				if(i == 0 && j == 0)
					continue;
				grid.linkHeight(cells[0][0].getComponent(),comScaleH[i][j], cells[i][j].getComponent());				
				grid.linkWidth(cells[0][0].getComponent(),comScaleW[i][j], cells[i][j].getComponent());				
			}
		}
	}

}
