package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.form.UFormIdI;

public class UIdForm extends UForm implements UFormIdI{
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
