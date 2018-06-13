package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class TableAttributeValueForm extends UForm {
	private String title;
	private String value;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
