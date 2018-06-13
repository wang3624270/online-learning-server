package org.octopus.common_business.notices.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "notices_info")
public class NoticesInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String title;
	private String contents;
	@NotNull
	private String createTime;
	@NotNull
	private Integer createrid;
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

}
