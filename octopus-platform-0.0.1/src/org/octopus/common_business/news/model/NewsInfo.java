package org.octopus.common_business.news.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "news_info")
public class NewsInfo {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;//主键
	
		private Integer createrId;//创建者
		@NotNull
		private String title;//题目
		private String newsType;//新闻类型 文学生活馆：数据字典 XWLXM 
		private Integer readCount;//阅读次数
		private String brief;//摘要
		private String newsNum;//编号
		private Integer attachId;//附件
		@NotNull
		private Date createTime;
		private Date modifyTime;
		private Integer isVisable;//是否可用
		private Integer orderNum;//次序
		private String URL;//超链
		
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
		public Integer getAttachId() {
			return attachId;
		}
		public void setAttachId(Integer attachId) {
			this.attachId = attachId;
		}
		public Integer getOrderNum() {
			return orderNum;
		}
		public void setOrderNum(Integer orderNum) {
			this.orderNum = orderNum;
		}
		public Integer getCreaterId() {
			return createrId;
		}
		public void setCreaterId(Integer createrId) {
			this.createrId = createrId;
		}
		public String getNewsNum() {
			return newsNum;
		}
		public void setNewsNum(String newsNum) {
			this.newsNum = newsNum;
		}
		public String getURL() {
			return URL;
		}
		public void setURL(String uRL) {
			URL = uRL;
		}
		
}
