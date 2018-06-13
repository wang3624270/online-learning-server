package org.octopus.auth.jpa_model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "info_person_info")
public class InfoPersonInfo {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	private String qq;
	private String wechat;
	private String email;
	private Date createTime;
	private String remark;
	private Date modifyTime;
	private Integer modifyerId;
	private String headimgurl;

	
	
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
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getQq() {
		return qq;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

}
