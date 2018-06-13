package org.starfish.login_users;

import java.util.HashMap;

import org.starfish.sf_auth.model.SFIdentity;

public class UserTokenServerSide implements UserTokenI {
	private String loginName;
	private SFIdentity sfIdentity;
	private Integer personId;
	private HashMap<String, Object> hData = new HashMap<String, Object>();
	private String session_key;
	

	public HashMap<String, Object> gethData() {
		return hData;
	}

	public void sethData(HashMap<String, Object> hData) {
		this.hData = hData;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public SFIdentity getSfIdentity() {
		return sfIdentity;
	}

	public void setSfIdentity(SFIdentity sfIdentity) {
		this.sfIdentity = sfIdentity;
	}

	@Override
	public String getUserLoginName() {
		// TODO Auto-generated method stub
		return loginName;
	}
	
	public Integer getPersonId(){
		return personId;
	}
	
	public void setPersonId(Integer personId){
		this.personId = personId;
	}
	public Object get(String key) {
		return  hData.get(key);
	}
	public Object put(String key,Object value) {
		return  hData.put(key, value);
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
}