package cn.edu.sdu.common.form;

import java.util.Date;

public class InfoPersonInfoForm extends UForm implements USFormI,UFormModifyStatusI{
	public boolean isModify;
	public Boolean select;
	private Integer personId;
	private String perName;
	private String perNum;
	private String perTypeCode;

	private String perIdCard;
	private String genderCode;
	private Date perBirthday;
	private String mobilePhone;
	private String perAddress;
	private String perPostalCode;
	private String perTelephone;
	private String QQ;
	private String email;
	private String wechat;
	private Date createTime;
	private String remark;
	private Date modifyTime;
	private Integer modifyerId;

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getPerNum() {
		return perNum;
	}

	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}

	public String getPerIdCard() {
		return perIdCard;
	}

	public void setPerIdCard(String perIdCard) {
		this.perIdCard = perIdCard;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public Date getPerBirthday() {
		return perBirthday;
	}

	public void setPerBirthday(Date perBirthday) {
		this.perBirthday = perBirthday;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPerAddress() {
		return perAddress;
	}

	public void setPerAddress(String perAddress) {
		this.perAddress = perAddress;
	}

	public String getPerPostalCode() {
		return perPostalCode;
	}

	public void setPerPostalCode(String perPostalCode) {
		this.perPostalCode = perPostalCode;
	}

	public String getPerTelephone() {
		return perTelephone;
	}

	public void setPerTelephone(String perTelephone) {
		this.perTelephone = perTelephone;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyerId() {
		return modifyerId;
	}

	public void setModifyerId(Integer modifyerId) {
		this.modifyerId = modifyerId;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getPerTypeCode() {
		return perTypeCode;
	}

	public void setPerTypeCode(String perTypeCode) {
		this.perTypeCode = perTypeCode;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	@Override
	public boolean isModify() {
		// TODO Auto-generated method stub
		return isModify;
	}

	@Override
	public void setModify(boolean isModify) {
		// TODO Auto-generated method stub
		this.isModify = isModify;
	}

}
