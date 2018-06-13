package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class UNameValueForm extends UForm {
	private String name;
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
