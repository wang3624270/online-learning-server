package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class AttachmentInfoForm extends UForm {
	private Integer attachId;
	private String attachType;
	private Integer ownerId;
	private String attachFolder;
	private String remark;
	private String fileName;
	private Boolean isDelete;

	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAttachType() {
		return attachType;
	}
	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public String getAttachFolder() {
		return attachFolder;
	}
	public void setAttachFolder(String attachFolder) {
		this.attachFolder = attachFolder;
	}
}
