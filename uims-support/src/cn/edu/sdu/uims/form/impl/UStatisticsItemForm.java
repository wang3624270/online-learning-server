package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class UStatisticsItemForm extends UForm {
	private String name;
	private String label;
	private Integer count= 0 ;
	
	public UStatisticsItemForm(String name, String label){
		this.name = name;
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void addOne(){
		count = count +1;
	}
}
