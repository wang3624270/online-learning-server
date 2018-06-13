package cn.edu.sdu.uims.def.cell;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.base.UComponentI;
import pagelayout.Cell;

public class UCellStruct implements java.io.Serializable {

	private int col,row;
	
	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setComName(String name) {
		// TODO Auto-generated method stub

	}

	public void setCellList(List<UCellStruct> cellList) {
		// TODO Auto-generated method stub

	}

	public String getComName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UCellStruct> getCellList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cell getCell(HashMap<String, UComponentI> map) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setRowNum(int rowNum){
		
	}
	public void setColNum(int colNum){
		
	}
}
