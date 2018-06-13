package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class UserVisitAppInfoForm extends UForm {
	private Integer infoId;
	private String loginName;
	private String appName;
	private String appLoginName;
	private String parameter;
	private String jarUrl;
	private String localDir;
	public Integer getInfoId() {
		return infoId;
	}
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppLoginName() {
		return appLoginName;
	}
	public void setAppLoginName(String appLoginName) {
		this.appLoginName = appLoginName;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getJarUrl() {
		return jarUrl;
	}
	public void setJarUrl(String jarUrl) {
		this.jarUrl = jarUrl;
	}
	public String getLocalDir() {
		return localDir;
	}
	public void setLocalDir(String localDir) {
		this.localDir = localDir;
	}
	
}
