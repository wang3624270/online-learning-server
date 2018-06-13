package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.form.UFormIdI;
import cn.edu.sdu.common.form.UFormModifyStatusI;

public class UMIForm extends UForm implements UFormIdI, UFormModifyStatusI {
	protected boolean isModify = false;
	protected Integer id;

	public boolean isModify() {
		return isModify;
	}

	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}
	
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

}
