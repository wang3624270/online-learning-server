package cn.edu.sdu.uims.frame.form;

import cn.edu.sdu.common.form.UForm;

public class LoginForm extends UForm {
	private String strLoginName;
	private String strPassword;
	private Boolean isSave;

	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}


	public Boolean getIsSave() {
		return isSave;
	}

	public void setIsSave(Boolean isSave) {
		this.isSave = isSave;
	}

	public String getStrLoginName() {
		return strLoginName;
	}

	public void setStrLoginName(String strLoginName) {
		this.strLoginName = strLoginName;
	}
}
