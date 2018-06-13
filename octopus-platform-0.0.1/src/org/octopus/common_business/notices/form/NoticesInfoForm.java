package org.octopus.common_business.notices.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import cn.edu.sdu.common.form.PersonSetI;
import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.form.UNewsFormIdI;

public class NoticesInfoForm extends UForm implements PersonSetI {

	private Integer id;
	private String title;
	private String contents;
	private String createTime;
	private Integer createrid;
	private String createrName;
	private Integer isWechatPush;
	private Integer isAppPush;
	private Integer isVisable;
	
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
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		Date date = createTime; 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String str = sdf.format(date);
		this.createTime = str;
	}
	public Integer getCreaterid() {
		return createrid;
	}
	public void setCreaterid(Integer createrid) {
		this.createrid = createrid;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public Integer getIsWechatPush() {
		return isWechatPush;
	}
	public void setIsWechatPush(Integer isWechatPush) {
		this.isWechatPush = isWechatPush;
	}
	public Integer getIsAppPush() {
		return isAppPush;
	}
	public void setIsAppPush(Integer isAppPush) {
		this.isAppPush = isAppPush;
	}
	public Integer getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	@Override
	public void setPersonId(Integer personId) {
		// TODO Auto-generated method stub
		createrid = personId;
	}
	@Override
	public void setPerNum(String perNum) {
		// TODO Auto-generated method stub
		createrName = perNum;
	}
	@Override
	public void setPerName(String perName) {
		// TODO Auto-generated method stub
		
	}
}	


