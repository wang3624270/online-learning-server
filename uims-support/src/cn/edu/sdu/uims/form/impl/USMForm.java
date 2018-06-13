package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.USFormI;

public class USMForm extends UMForm implements USFormI{
	protected Boolean select=false;

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}
}
