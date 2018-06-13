package cn.edu.sdu.uims.def.cell;

import java.util.HashMap;

import cn.edu.sdu.uims.base.UComponentI;
import pagelayout.Cell;
import pagelayout.Row;

public class UCellStructRow extends UCellStructMulti {

	public Cell getCell(HashMap<String, UComponentI> map) {
		// TODO Auto-generated method stub
		Row row = new Row();
		String name;
		UComponentI com;
		for(int i = 0; i < cellList.size();i++) {
				row.newColumn(Cell.CENTER,Cell.NO_ALIGNMENT,cellList.get(i).getCell(map));				
		}
		return row;
	}

}
