package org.sdu.rmi;

public interface ClientRequestI {
	void init(String rmipath) throws Exception;
	void init(RmiServiceI rmiservice) throws Exception;
	void addSysService(String name, String rmipath) throws Exception;
	RmiResponse request(RmiRequest req);
	RmiResponse request(String name, RmiRequest req);
	void addData(String key, Object value);
	public Object getData(String key);
	public void setAppName(String appName);

}
