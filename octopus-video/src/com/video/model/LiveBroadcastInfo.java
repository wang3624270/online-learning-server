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
@Table(name = "live_broadcast_info")
public class LiveBroadcastInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer hubId;
	private String title;
	private String brief;
	private String longbrief;
	private Integer attachId;
	private String pushurl;
	private String playurl;
	private Integer liveState;
	private Integer liveId;
	//private String streamName;
	private Integer readCount;
	private Date createtime;
	private Date startTime;
	private Date endTime;
	private Integer isDisabled;
	private Date disabledTime;
	//private Integer isVisable;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getLiveId() {
		return liveId;
	}
	public void setLiveId(Integer liveId) {
		this.liveId = liveId;
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
	/*public String getStreamName() {
		return streamName;
	}
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}*/

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
	public Integer getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}
	public Date getDisabledTime() {
		return disabledTime;
	}
	public void setDisabledTime(Date disabledTime) {
		this.disabledTime = disabledTime;
	}
	

}
