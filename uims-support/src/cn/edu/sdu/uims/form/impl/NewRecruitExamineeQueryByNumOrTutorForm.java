package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class NewRecruitExamineeQueryByNumOrTutorForm extends UForm {
	private String order;
	private String major;
	private Integer tutor;
	private String exameeType;
	private Integer personId;
	public String getOrder() {
		return order;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Integer getTutor() {
		return tutor;
	}
	public void setTutor(Integer tutor) {
		this.tutor = tutor;
	}
	public String getExameeType() {
		return exameeType;
	}
	public void setExameeType(String exameeType) {
		this.exameeType = exameeType;
	}
}
