package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class UListSelectForm extends UForm {
	private Object items[];
	private Boolean selected[];
	public Object[] getItems() {
		return items;
	}
	public void setItems(Object[] items) {
		this.items = items;
	}
	public Boolean[] getSelected() {
		return selected;
	}
	public void setSelected(Boolean[] selected) {
		this.selected = selected;
	}
	public boolean isSelected(int index){
		if( index < 0 || index >= selected.length)
			return false;
		else 
			return selected[index];
	}
	public void setSelected(int index, boolean b){
		if( index < 0 || index >= selected.length)
			return ;
		else 
			selected[index] = b;
	}
}
