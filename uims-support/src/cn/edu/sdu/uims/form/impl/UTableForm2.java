package cn.edu.sdu.uims.form.impl;

import java.util.List;

import cn.edu.sdu.uims.util.DataProcessUtils;

public class UTableForm2 extends UTableForm {
	private Object []items1;

	public Object[] getItems1() {
		return items1;
	}

	public void setItems1(Object[] items1) {
		this.items1 = items1;
	}
	public void setItems1ByList(List list) {
		if(list == null || list.size()==0) 
			items1 = null;
		else {
			items1 = new Object[list.size()];
			for(int i = 0; i < items1.length;i++) {
				items1[i] = list.get(i);
			}
		}
	}
	public void addItem1(Object item){
		items1 = DataProcessUtils.addItem(items1, item);
	}

}
