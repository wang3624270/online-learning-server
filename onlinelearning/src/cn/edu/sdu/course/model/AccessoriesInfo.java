package cn.edu.sdu.course.model;


import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.octopus.auth.jpa_model.InfoPersonInfo;

import com.sun.istack.internal.NotNull;
//auther:wqq
@Entity
@Table(name = "accessories_info")
public class AccessoriesInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;//资源Id
	private String accName;//资源名称
	private String accUrl;//资源路径
	private String accType;
	private Integer accUploader;
	private Date uploadTime;
	private String remark;
	private String fileType;//文件类型
	private Long fileSize;//文件长度
	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccUrl() {
		return accUrl;
	}
	public void setAccUrl(String accUrl) {
		this.accUrl = accUrl;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}

	public Integer getAccUploader() {
		return accUploader;
	}
	public void setAccUploader(Integer accUploader) {
		this.accUploader = accUploader;
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
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
}