package cn.edu.sdu.uims.def.cell;

import java.util.HashMap;

import cn.edu.sdu.uims.base.UComponentI;
import pagelayout.Cell;
import pagelayout.Column;


public class UCellStructColumn extends UCellStructMulti {
	@Override
	public Cell getCell(HashMap<String, UComponentI> map) {
		// TODO Auto-generated method stub
		Column column = new Column();
		for(int i = 0; i < cellList.size();i++) {
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,cellList.get(i).getCell(map));				
		}	
		return column;
	}

}
