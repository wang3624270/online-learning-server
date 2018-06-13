package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UFormIdI;

public class UTableFormId extends UTableForm implements UFormIdI {
	private Integer id;
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

}
