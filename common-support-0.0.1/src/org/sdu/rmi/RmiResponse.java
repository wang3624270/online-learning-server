package org.sdu.rmi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class RmiResponse implements Serializable {

	private static final long serialVersionUID = 5816730550412765773L;

	private HashMap hData;

	private String errorMsg;

	public RmiResponse() {
		errorMsg = null;
		hData = new HashMap();
	}

	@SuppressWarnings("unchecked")
	public void add(String key, Object value) {
		hData.put(key, value);
	}

	public Object get(boolean success) {
		return hData.get(success);
	}

	public Object get(String success) {
		return hData.get(success);
	}

	public Set keySet() {
		return hData.keySet();
	}

	public void clear() {
		hData.clear();
		errorMsg = null;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public HashMap gethData() {
		return hData;
	}

	public void sethData(HashMap hData) {
		this.hData = hData;
	}

}
