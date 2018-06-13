package org.octopus.common_business.news.form;

import java.util.Date;

import cn.edu.sdu.common.form.UForm;

public class NewsInfoQueryForm extends UForm{
	private String newsType;
	private String newsNum;
	private String title;
	private Boolean isVisable = true;
	private Date startDate;
	private Date endDate;
	
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public String getNewsNum() {
		return newsNum;
	}
	public void setNewsNum(String newsNum) {
		this.newsNum = newsNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(Boolean isVisable) {
		this.isVisable = isVisable;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
