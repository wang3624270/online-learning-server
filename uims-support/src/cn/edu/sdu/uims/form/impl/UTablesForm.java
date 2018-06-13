package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class UTablesForm extends UForm {
	public static int ITEMS_MAX = 5;
	private Object [][]items = new Object[ITEMS_MAX][];
	public Object [] getItems(Integer index){
		if(index == null || index < 0 || index > ITEMS_MAX)
			return null;
		else
			return items[index];
	}
	public void setItems(Object []data, Integer index){
		if(index == null || index < 0 || index > ITEMS_MAX) 
			return;
		items[index] = data;
	}
}
