package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class PasswdForm extends UForm {
	private String enterOldPwd;
	private String enterNewPwd;
	private String enterRepeatNewPwd;
	
	//修改用户名用
	private String enterNewLoginName;
	private String enterRepeatNewLoginName;
	
	public String getEnterNewLoginName() {
		return enterNewLoginName;
	}
	public void setEnterNewLoginName(String enterNewLoginName) {
		this.enterNewLoginName = enterNewLoginName;
	}
	public String getEnterRepeatNewLoginName() {
		return enterRepeatNewLoginName;
	}
	public void setEnterRepeatNewLoginName(String enterRepeatNewLoginName) {
		this.enterRepeatNewLoginName = enterRepeatNewLoginName;
	}
	public String getEnterNewPwd() {
		return enterNewPwd;
	}
	public void setEnterNewPwd(String enterNewPwd) {
		this.enterNewPwd = enterNewPwd;
	}
	public String getEnterOldPwd() {
		return enterOldPwd;
	}
	public void setEnterOldPwd(String enterOldPwd) {
		this.enterOldPwd = enterOldPwd;
	}
	public String getEnterRepeatNewPwd() {
		return enterRepeatNewPwd;
	}
	public void setEnterRepeatNewPwd(String enterRepeatNewPwd) {
		this.enterRepeatNewPwd = enterRepeatNewPwd;
	}
	
}