package cn.edu.sdu.uims.def;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.flex.FHashMap;

public class UCommandTemplate extends UTemplate{
	public String method;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public FHashMap getParas() {
		return paras;
	}
	public void setParas(FHashMap paras) {
		this.paras = paras;
	}
	public String service;
	public FHashMap paras = null;
	
}
