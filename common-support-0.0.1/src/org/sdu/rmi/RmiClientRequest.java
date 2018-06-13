package org.sdu.rmi;

import java.rmi.Naming;
import java.util.Date;
import java.util.HashMap;

public class RmiClientRequest implements ClientRequestI{
	/** Rmi服务器端接口 */
	private RmiServiceI rmiservice;
	private HashMap<String, RmiServiceI> assistSysServicMap = new HashMap<String, RmiServiceI>();
	private String appName;
	private static ClientRequestI clientRequest = new RmiClientRequest();
	/** 在客户端需要一直保存的数据 */
	private HashMap datamap = new HashMap();

	private RmiClientRequest(){
	}
	public void setRmiservice(RmiServiceI rmiservice) {
		this.rmiservice = rmiservice;
	}
	/**
	 * Rmi客户端请求对象初始化：连接服务器
	 * 
	 * @param rmipath
	 * @throws Exception
	 */
	public static void initRmiClientInLocalMode(RmiServiceI localSysService) {
		((RmiClientRequest) clientRequest).setRmiservice(localSysService);

	}

	public static ClientRequestI getClientRequest() {
		return clientRequest;
	}

	public static void setClientRequest(ClientRequestI clientRequest1) {
		clientRequest = clientRequest1;
	}

	public void init(String rmipath) throws Exception {
		rmiservice = (RmiServiceI) Naming.lookup(rmipath);
	}

	public void addSysService(String name, String rmipath) throws Exception {
		try {
			RmiServiceI service = (RmiServiceI) Naming.lookup(rmipath);
			assistSysServicMap.put(name, service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(RmiServiceI rmiservice) throws Exception {
		this.rmiservice = rmiservice;
	}

	public RmiResponse request(RmiRequest req) {
		return request(rmiservice, req);
	}

	public RmiResponse request(String name, RmiRequest req) {
		if (name == null || name.equals(appName)) {
			return request(rmiservice, req);
		}
		RmiServiceI serviceI = assistSysServicMap.get(name);
		if (serviceI == null) {
			RmiResponse respond = new RmiResponse();
			respond.setErrorMsg("系统通信错误，没有配置相应的服务器");
			return respond;
		} else
			return request(serviceI, req);
	}

	public RmiResponse request(RmiServiceI service, RmiRequest req) {
		RmiResponse respond  = null;
		if(service == null) 
			return new RmiResponse();
		try {
			Date ds, de;
			ds = new Date();
			respond = service.requestProcesser(req);
		} catch (Exception e) {
			e.printStackTrace();
			respond  = new RmiResponse();
			respond.setErrorMsg("系统内部错误，请重试");
		}
		return respond;
	}

	@SuppressWarnings("unchecked")
	public void addData(String key, Object value) {
		datamap.put(key, value);
	}

	public Object getData(String key) {
		return datamap.get(key);
	}
	@Override
	public void setAppName(String appName) {
		// TODO Auto-generated method stub
		this.appName = appName;
	}

}
