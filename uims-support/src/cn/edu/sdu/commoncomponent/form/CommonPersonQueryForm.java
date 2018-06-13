package cn.edu.sdu.commoncomponent.form;

import cn.edu.sdu.uims.form.UTextFieldDataFormI;
import cn.edu.sdu.uims.form.impl.USForm;

public class CommonPersonQueryForm extends USForm {
	private String perNum;
	private String perIdCardNum;
	private Integer personId;
	private UTextFieldDataFormI person;
	
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getPerNum() {
		if(person != null)
			return person.getDataObjectNum();
		else
			return perNum;
	}
	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}
	public String getPerIdCardNum() {
		return perIdCardNum;
	}
	public void setPerIdCardNum(String perIdCardNum) {
		this.perIdCardNum = perIdCardNum;
	}
	public UTextFieldDataFormI getPerson() {
		return person;
	}
	public void setPerson(UTextFieldDataFormI person) {
		this.person = person;
	}
	
}
