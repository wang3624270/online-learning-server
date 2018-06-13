package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class QueryByNumOrIdCardForm extends UForm {
	private String num;
	private String idCard;
	private Integer id;
	private String picPath;
	private String exameeType;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public void setExameeType(String exameeType) {
		this.exameeType = exameeType;
	}
	public String getExameeType() {
		return exameeType;
	}
}
