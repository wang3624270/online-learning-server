package cn.edu.sdu.uims.def.cell;

import java.util.HashMap;

import cn.edu.sdu.uims.base.UComponentI;
import pagelayout.Cell;
import pagelayout.CellGrid;

public class UCellStructGrid extends UCellStructMulti {
	private int colNum, rowNum;
	
	public void setRowNum(int rowNum){
		this.rowNum = rowNum;
	}
	public void setColNum(int colNum){
		this.colNum = colNum;
	}

	public Cell getCell(HashMap<String, UComponentI> map) {
		// TODO Auto-generated method stub
		int n= cellList.size();
		CellGrid grid = null;
		int rowIndices[],colIndices[];
		boolean b = (colNum * rowNum <= n);
		Cell cells[]=new Cell[n];
		rowIndices = new int[n];
		colIndices = new int[n];
		UCellStruct uc;
		for(int i=0;i<n;i++){
			uc = cellList.get(i);
			cells[i]= uc.getCell(map);
			if(b) {
				rowIndices[i] = i;
				colIndices[i] = i;				
			}else {
				rowIndices[i] = uc.getRow();
				colIndices[i] = uc.getCol();								
			}
		}
		grid=CellGrid.createCellGrid(cells,rowIndices,colIndices);
		return grid;
	}

}
