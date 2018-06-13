package org.sdu.rmi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class RmiRequest implements Serializable {

	private static final long serialVersionUID = -6500368164264172203L;

	private String cmd;

	private HashMap hData;

	public RmiRequest() {
		hData = new HashMap();
		cmd = "";
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String sCmd) {
		this.cmd = sCmd;
	}

	public void add(String key, Object value) {
		hData.put(key, value);
	}

	public Object get(String key) {
		return hData.get(key);
	}

	public Set keySet() {
		return hData.keySet();
	}

	public void clear() {
		hData.clear();
		cmd = "";
	}

	public void putAll(HashMap map) {
		hData.putAll(map);
	}

	public void remove(String key) {
		// TODO Auto-generated method stub
		hData.remove(key);
	}
}
