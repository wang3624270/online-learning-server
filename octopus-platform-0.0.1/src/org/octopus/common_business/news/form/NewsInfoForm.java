package org.octopus.common_business.news.form;

import java.util.Date;

import org.octopus.common_business.news.constants.NewsConstants;

import cn.edu.sdu.common.form.PersonSetI;
import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.form.UNewsFormIdI;
import cn.edu.sdu.common.util.DateTimeTool;

public class NewsInfoForm extends UForm implements UNewsFormIdI, PersonSetI {
	
	private Integer id;
	private Integer createrId;
	private String createrName;
	private String title;
	private String newsType;
	private Integer readCount;
	private String brief;
	private String newsNum;
	private Integer attachId;
	private Date createTime;
	private Integer orderNum;
	private Date modifyTime;
	private Integer isVisable;
	private String URL;
	private String img;
	private String contentUrl;
	
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}		
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getNewsNum() {
		return newsNum;
	}
	public void setNewsNum(String newsNum) {
		this.newsNum = newsNum;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(Integer isVisable) {
		this.isVisable = isVisable;
	}
	public Boolean getIsVisableBoolean() {
		if(isVisable!= null && isVisable.equals(1))
			return true;
		else
			return false;
	}
	public void setIsVisableBoolean(Boolean isVisable) {
		if(isVisable != null && isVisable.booleanValue())
			this.isVisable = 1;
		else
			this.isVisable = 0;
	}

	public String getCreateTimeStr() {
		if(createTime == null)	
			return "";
		else
			return DateTimeTool.parseDateTime(createTime, "yyyy-MM-dd HH:mm:ss");
	}
	public void setCreateTimeStr(String createTime) {
	}
	public String getModifyTimeStr() {
		if(modifyTime == null)	
			return "";
		else
			return DateTimeTool.parseDateTime(modifyTime, "yyyy-MM-dd HH:mm:ss");
	}
	
	public void setModifyTimeStr(String modifyTime) {
	}
	
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	@Override
	public void setPersonId(Integer personId) {
		// TODO Auto-generated method stub
		createrId = personId;
	}
	@Override
	public void setPerNum(String perNum) {
		// TODO Auto-generated method stub
		createrName = perNum;
	}
	@Override
	public void setPerName(String perName) {
		// TODO Auto-generated method stub
		
	}
}
