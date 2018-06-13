package org.sdu.rmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserTokenClientSide extends ReturnToClientStruct  {
	private String loginName;
	private Integer personId;
	private String sessionId;
	private List<String> roleList;
	private Map dataMap;
	
	public Map getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public UserTokenClientSide() {
		roleList = new ArrayList<String>();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public List<String> getRoleList() {
		return this.roleList;
	}

	public void setRoles(List<String> list) {
		roleList.clear();
		roleList.addAll(list);
	}
}