package org.octopus.common_business.notices.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notices_member_info")
public class NoticesMemberInfo {
	@Id
	private Integer noticeid;
	@NotNull
	private Integer personid;
	private String haveSeen;
	private Date seenTime;
	public Integer getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}
	public Integer getPersonid() {
		return personid;
	}
	public void setPersonid(Integer personid) {
		this.personid = personid;
	}
	public String getHaveSeen() {
		return haveSeen;
	}
	public void setHaveSeen(String haveSeen) {
		this.haveSeen = haveSeen;
	}
	public Date getSeenTime() {
		return seenTime;
	}
	public void setSeenTime(Date seenTime) {
		this.seenTime = seenTime;
	}
}
