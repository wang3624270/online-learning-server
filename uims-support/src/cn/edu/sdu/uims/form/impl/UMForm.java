package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.form.UFormModifyStatusI;

public class UMForm extends UForm implements UFormModifyStatusI{
	protected boolean isModify = false;

	public boolean isModify() {
		return isModify;
	}

	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}
}
