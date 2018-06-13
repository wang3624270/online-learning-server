package org.sdu.rmi;

import java.util.HashMap;

public class RmiSession {

	private HashMap<String, Object> paraMap;
	private String sessionId;

	public RmiSession() {
		paraMap = new HashMap<String, Object>();

	}

	public Object getAttribute(String key) {
		return paraMap.get(key);

	}

	public void setAttribute(String key, Object value) {
		paraMap.put(key, value);

	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}