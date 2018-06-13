package com.video.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "live_hub")
public class LiveHub {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String hubName;
	private Integer hubType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHubName() {
		return hubName;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public Integer getHubType() {
		return hubType;
	}
	public void setHubType(Integer hubType) {
		this.hubType = hubType;
	}
	
	
}
