package cn.edu.sdu.common.form;

import java.util.Date;

public class BaseAttachmentInfoForm implements UFormI {
    private Integer attachId;
    private String attachType;
    private String docType;
    private Integer ownerId;
    private String ownerName;
    private String attachFolder;
    private String urlAddress;
    private String fileName;
    private Integer uploaderId;
    private String uploaderName;
    private Date  uploadTime;
    private Integer attachIndex;
    private Boolean isDelete;
    private String remark;
    
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
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
	public Integer getUploaderId() {
		return uploaderId;
	}
	public void setUploaderId(Integer uploaderId) {
		this.uploaderId = uploaderId;
	}
	public String getUploaderName() {
		return uploaderName;
	}
	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String toString(){
		if(remark != null && !remark.equals(""))
			return remark;
		else
			return fileName;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getAttachFolder() {
		return attachFolder;
	}
	public void setAttachFolder(String attachFolder) {
		this.attachFolder = attachFolder;
	}
	
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void getDependFieldValues() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getAttributeObject(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAttributeObject(String attributeName, Object obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getAttributeObject(String attributeName, Integer index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAttributeObject(String attributeName, Object obj, Integer index) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAttributeByParse(String value) {
		// TODO Auto-generated method stub
		
	}
}

