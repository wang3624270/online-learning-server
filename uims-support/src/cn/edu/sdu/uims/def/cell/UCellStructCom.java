package cn.edu.sdu.uims.def.cell;

import java.util.HashMap;

import cn.edu.sdu.uims.base.UComponentI;
import pagelayout.ComponentCell;

public class UCellStructCom extends UCellStruct {
	private String comName;

	@Override
	public void setComName(String name) {
		// TODO Auto-generated method stub
		this.comName = name;
	}


	@Override
	public String getComName() {
		// TODO Auto-generated method stub
		return comName;
	}


	@Override
	public ComponentCell getCell(HashMap<String, UComponentI> map) {
		// TODO Auto-generated method stub
		return new ComponentCell(map.get(comName).getAWTComponent());
	}
	
}
