package cn.edu.sdu.commoncomponent.form;

import cn.edu.sdu.common.form.UForm;

public class InfoPersonInfoTableItemForm extends UForm {
	private Integer personId;
	private String  perNum;
	private String perName;
	private String  collegeName;
	
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getPerNum() {
		return perNum;
	}
	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}
	public String getPerName() {
		return perName;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String toString (){
		return perNum+"-"+ perName+"-" +collegeName;
	}
}
