package org.shandong_univ.tenant;

import java.util.Map;


import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.shandong_univ.tenant.constants.TenantConstants;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
 

public class TenantScope implements Scope {

	public Object get(String name, ObjectFactory objectFactory) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		HttpSession se = ((HttpServletRequest) extContext.getRequest())
				.getSession();
		String tenant = (String) se
				.getAttribute(TenantConstants.TenantIdentity);
		if (tenant == null)
			tenant = "default";
		TenantBeansWrapper wrapper = TenantManager.tenantBeansWrapperMap
				.get(tenant);
		if (wrapper == null) {
			wrapper = new TenantBeansWrapper();
			wrapper.setTenantIdentity(tenant);
			TenantManager.tenantBeansWrapperMap.put(tenant, wrapper);
		}
		Object ob = wrapper.getBean(name);
		if (ob == null)
			ob = objectFactory.getObject();
		wrapper.addBean(name, ob);
		return ob;
	}

	public Object remove(String name) {
		return FacesContext.getCurrentInstance().getViewRoot().getViewMap()
				.remove(name);
	}

	public String getConversationId() {
		return null;
	}

	public void registerDestructionCallback(String name, Runnable callback) {
		// Not supported
	}

	public Object resolveContextualObject(String key) {
		return null;
	}
}