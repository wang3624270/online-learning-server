package com.video.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "live_broadcast_base")
public class LiveBroadcastBase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String streamName;
	private Integer personId;
	private Date createTime;
	private Integer isVisable;
	
	public Integer getId() {
		return id;
	}

	public String getStreamName() {
		return streamName;
	}
	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public Integer getIsVisable()
	{
		return this.isVisable;
	}
	public void setIsVisable(Integer isVisable)
	{
		this.isVisable=isVisable;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
