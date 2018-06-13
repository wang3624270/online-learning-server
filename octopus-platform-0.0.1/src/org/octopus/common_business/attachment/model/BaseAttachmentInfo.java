package org.octopus.common_business.attachment.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "base_attachment_info")
public class BaseAttachmentInfo {


	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer attachId;
	@NotNull
	private String attachType;
	private String docType;
	private Integer ownerId;
	private String urlAddress;
	private String fileName;
	private Integer uploader;
	private Date uploadTime;
	private String remark;
	private Integer attachIndex;
	private String permanentFileName;// 用于保存中文名的文件

	// Constructors

	public Integer getAttachIndex() {
		return attachIndex;
	}

	public void setAttachIndex(Integer attachIndex) {
		this.attachIndex = attachIndex;
	}

	public Integer getAttachId() {
		return attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getUrlAddress() {
		return urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/** default constructor */
	public BaseAttachmentInfo() {
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPermanentFileName() {
		return permanentFileName;
	}

	public void setPermanentFileName(String permanentFileName) {
		this.permanentFileName = permanentFileName;
	}
	
	public Integer getUploader() {
		return uploader;
	}

	public void setUploader(Integer uploader) {
		this.uploader = uploader;
	}

}