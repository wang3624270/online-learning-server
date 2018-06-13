package org.octopus.common_business.accessories.model;

import java.util.Date;

/**
 * AccessoriesInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AccessoriesInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer oldId;
	private String accName;
	private String accUrl;
	private String accType;
	private Integer accUploader;
	private Date uploadTime;
	private String remark;
	private String fileType;
	private Integer directoryId;
	private Long fileSize;
	// Constructors

	/** default constructor */
	public AccessoriesInfo() {
	}

	/** full constructor */
	public AccessoriesInfo(String accName, String accUrl, String accType,
			Integer accUploader, Date uploadTime, String remark, Integer oldId,Integer directoryId,Long fileSize) {
		this.accName = accName;
		this.accUrl = accUrl;
		this.accType = accType;
		this.accUploader = accUploader;
		this.uploadTime = uploadTime;
		this.remark = remark;
		this.oldId = oldId;
		this.directoryId = directoryId;
		this.fileSize = fileSize;
		
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccName() {
		return this.accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccUrl() {
		return this.accUrl;
	}

	public void setAccUrl(String accUrl) {
		this.accUrl = accUrl;
	}

	public String getAccType() {
		return this.accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public Integer getAccUploader() {
		return this.accUploader;
	}

	public void setAccUploader(Integer accUploader) {
		this.accUploader = accUploader;
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getOldId() {
		return oldId;
	}

	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}

	public Integer getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(Integer directoryId) {
		this.directoryId = directoryId;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
}