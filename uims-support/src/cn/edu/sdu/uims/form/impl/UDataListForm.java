package cn.edu.sdu.uims.form.impl;

import java.util.List;

import cn.edu.sdu.common.form.UForm;


public class UDataListForm extends UForm {
	private Object value;
	private List dataList;

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
