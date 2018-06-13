package cn.edu.sdu.uims.def.cell;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.base.UComponentI;
import pagelayout.Cell;


public class UCellStructMulti extends UCellStruct{
	protected List<UCellStruct> cellList;

	@Override
	public void setCellList(List<UCellStruct> cellList) {
		// TODO Auto-generated method stub
		this.cellList = cellList;
	}


	@Override
	public List<UCellStruct> getCellList() {
		// TODO Auto-generated method stub
		return cellList;
	}

	@Override
	public Cell getCell(HashMap<String, UComponentI> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
