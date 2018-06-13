package org.shandong_univ.tenant;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.shandong_univ.tenant.constants.TenantConstants;

public class TenantManager {
	public static HashMap<String, TenantBeansWrapper> tenantBeansWrapperMap = new HashMap<String, TenantBeansWrapper>();

	public static void checkRegisterTenantByPath(HttpSession session,
			String loginPortalPath) {
		if (!TenantConfigure.isMultiTenant()) {
			session.setAttribute(TenantConstants.TenantIdentity, "default");
			return;
		}
		List<String> list = TenantConfigure.getTenantList();
		int i;
		boolean flag = false;
		int c = loginPortalPath.indexOf("tenant/");
		if (c < 0) {
			session.setAttribute(TenantConstants.TenantIdentity, "default");
		} else {

			String str = loginPortalPath.substring(c + 15);

			for (i = 0; i < list.size(); i++) {

				if (str.indexOf(list.get(i)) >= 0) {
					session.setAttribute(TenantConstants.TenantIdentity,
							list.get(i));
					session.setAttribute(TenantConstants.TenantResourcePath,
							"tenant/" + list.get(i));
					flag = true;
					break;
				}
			}
		}
		if (!flag)
			session.setAttribute(TenantConstants.TenantIdentity, "default");
		session.setAttribute(
				TenantConstants.TenantDatabase,
				TenantConfigure.getTenentDatabaseList().get(
						list.indexOf(session
								.getAttribute(TenantConstants.TenantIdentity))));
	}

	public static void checkRegisterTenant(HttpSession session, String userName) {
		if (!TenantConfigure.isMultiTenant()) {
			session.setAttribute(TenantConstants.TenantIdentity, "default");
			return;
		}
		List<String> list = TenantConfigure.getTenantList();
		int i;
		boolean flag = false;
		for (i = 0; i < list.size(); i++) {
			if (userName.indexOf(list.get(i)+"/") > 0) {
				session.setAttribute(TenantConstants.TenantIdentity,
						list.get(i));
				session.setAttribute(TenantConstants.TenantResourcePath,
						"tenant/" + list.get(i));
				flag = true;
				break;

			}

		}
		if (!flag)
			session.setAttribute(TenantConstants.TenantIdentity, "default");
		session.setAttribute(
				TenantConstants.TenantDatabase,
				TenantConfigure.getTenentDatabaseList().get(
						list.indexOf(session
								.getAttribute(TenantConstants.TenantIdentity))));
	}
}