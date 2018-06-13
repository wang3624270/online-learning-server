package org.shandong_univ.tenant;

import java.util.HashMap;

public class TenantBeansWrapper {
	private String tenantIdentity;
	private HashMap<String, Object> beanMap;

	public TenantBeansWrapper() {
		beanMap = new HashMap<String, Object>();

	}

	public String getTenantIdentity() {
		return tenantIdentity;
	}

	public void setTenantIdentity(String tenantIdentity) {
		this.tenantIdentity = tenantIdentity;
	}

	public Object getBean(String beanId) {
		return beanMap.get(beanId);
	}

	public void addBean(String beanId, Object bean) {
		beanMap.put(beanId, bean);
	}

}