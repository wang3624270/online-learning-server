package org.octopus.auth.jpa_model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_group")
public class UserGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// 
	private Integer groupid;// 
	private Integer sysusrid;// 
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public Integer getSysusrid() {
		return sysusrid;
	}
	public void setSysusrid(Integer sysusrid) {
		this.sysusrid = sysusrid;
	}

	
}
