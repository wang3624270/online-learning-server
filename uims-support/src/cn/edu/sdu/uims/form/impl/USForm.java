package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.form.USFormI;

public class USForm extends UForm implements USFormI{
	protected Boolean select=false;

	public Boolean getSelect() {
		if(select == null)
			return false;
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}
}
