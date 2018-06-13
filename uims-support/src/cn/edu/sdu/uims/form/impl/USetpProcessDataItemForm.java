package cn.edu.sdu.uims.form.impl;

import java.util.List;

import cn.edu.sdu.common.form.UForm;


public class USetpProcessDataItemForm extends UForm {
	private int setCount;//目前根据不同服务器先写死 
	private int start =0;
	private List dataList;
	
	public int getSetCount() {
		return setCount;
	}
	public void setSetCount(int setCount) {
		this.setCount = setCount;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
}
