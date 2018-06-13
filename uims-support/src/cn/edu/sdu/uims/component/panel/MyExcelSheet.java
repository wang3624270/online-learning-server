package cn.edu.sdu.uims.component.panel;

import java.util.HashMap;

public class MyExcelSheet {
	private HashMap colMap;
	//数据
	private Object  [][]data;
	
	public HashMap getColMap() {
		return colMap;
	}
	public void setColMap(HashMap colMap) {
		this.colMap = colMap;
	}
	public Object[][] getData() {
		return data;
	}
	public void setData(Object[][] data) {
		this.data = data;
	}

}
