package com.video.form;

import java.util.Date;

import cn.edu.sdu.common.form.UForm;

public class LiveBroadcastInfoForm extends UForm {
	private Integer id;
	private String title;
	private String brief;
	private String longbrief;
	private Integer attachId;
	private String pushurl;
	private String playurl;
	//private Integer authorId;
	private String streamName;
	private String hubName;
	private Integer hubId;
	private Integer readCount;
	private Date createtime;
	private Date startTime;
	private Date endTime;
	//private Integer isVisable;
	private Integer liveId;
	private String authorName;
	private Integer liveState;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLiveId() {
		return liveId;
	}
	public void setLiveId(Integer liveId) {
		this.liveId = liveId;
	}
	public Integer getHubId() {
		return hubId;
	}
	public void setHubId(Integer hubId) {
		this.hubId = hubId;
	}
	public Integer getLiveState() {
		return liveState;
	}
	public void setLiveState(Integer liveState) {
		this.liveState = liveState;
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
	public String getPushurl() {
		return pushurl;
	}
	public void setPushurl(String pushurl) {
		this.pushurl = pushurl;
	}
	public String getPlayurl() {
		return playurl;
	}
	public void setPlayurl(String playurl) {
		this.playurl = playurl;
	}
	public String getStreamName() {
		return streamName;
	}
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/*public Integer getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(Integer isVisable) {
		this.isVisable = isVisable;
	}*/
	public String getHubName() {
		return hubName;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	

}
