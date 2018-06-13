package com.video.model;

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

@Entity
@Table(name = "audio_video_info")
public class AudioVideoInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String title;
	private String brief;  
	private String longbrief;
	private Integer attachId;
	private String videoType;  // '视频是代码1   音频代码2',
	private String videoNum;
	private Integer authorId;
	private Date createtime;
	private Integer length;
	private String size;
	private Integer likecount;
	private Integer collectcount;
	private Integer readcount;
	private Integer sharecount;
	private Integer isVisable;
	private String isPossess;
	
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
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getLongbrief() {
		return longbrief;
	}
	public void setLongbrief(String longbrief) {
		this.longbrief = longbrief;
	}
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public String getVideoType() {
		return videoType;
	}
	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}
	public String getVideoNum() {
		return videoNum;
	}
	public void setVideoNum(String videoNum) {
		this.videoNum = videoNum;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Integer getLikecount() {
		return likecount;
	}
	public void setLikecount(Integer likecount) {
		this.likecount = likecount;
	}
	public Integer getCollectcount() {
		return collectcount;
	}
	public void setCollectcount(Integer collectcount) {
		this.collectcount = collectcount;
	}
	public Integer getReadcount() {
		return readcount;
	}
	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}
	public Integer getSharecount() {
		return sharecount;
	}
	public void setSharecount(Integer sharecount) {
		this.sharecount = sharecount;
	}
	public Integer getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(Integer isVisable) {
		this.isVisable = isVisable;
	}
	public String getIsPossess() {
		return isPossess;
	}
	public void setIsPossess(String isPossess) {
		this.isPossess = isPossess;
	}
	

}
