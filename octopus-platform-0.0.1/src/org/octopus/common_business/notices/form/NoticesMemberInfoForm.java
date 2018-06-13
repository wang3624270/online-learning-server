package org.octopus.common_business.notices.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import cn.edu.sdu.common.form.PersonSetI;
import cn.edu.sdu.common.form.UForm;

public class NoticesMemberInfoForm extends UForm implements PersonSetI {
	
	private Integer noticeid;
	private NoticesInfoForm noticesInfoForm;
	private Integer personid;
	private String perName;
	private String haveSeen;
	private Date seenTime;
	
	public Integer getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}
	public NoticesInfoForm getNoticesInfoForm() {
		return noticesInfoForm;
	}
	public void setNoticesInfoForm(NoticesInfoForm noticesInfoForm) {
		this.noticesInfoForm = noticesInfoForm;
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
	public String getPerName() {
		return perName;
	}
	@Override
	public void setPersonId(Integer personId) {
		// TODO Auto-generated method stub
		personid = personId;
	}
	@Override
	public void setPerNum(String perNum) {
		// TODO Auto-generated method stub
		perName = perNum;
	}
	@Override
	public void setPerName(String perName) {
		// TODO Auto-generated method stub
		
	}
}

