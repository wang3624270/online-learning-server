package org.octopus.auth;

import java.util.HashMap;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.starfish.StarfishPageConfigManager;
import org.starfish.constants.StarfishConstants;
import org.starfish.pageside_access.PageAccess;
import org.starfish.permi_dimension.PermissionDim;

 

public class PermissionHelper_starfish {
	private static final ThreadLocal<HashMap<String, Boolean>> nepenthesPermiInThread = new ThreadLocal<HashMap<String, Boolean>>();

	public boolean isVisiable() {
		HashMap<String, Boolean> map = nepenthesPermiInThread.get();
		if (map == null) {
			map = new HashMap<String, Boolean>();
			nepenthesPermiInThread.set(map);
		}
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		Object ob = ((HttpServletRequest) context.getRequest())
				.getAttribute("currentRenderCom");
		if (ob == null)
			return true;
		if (map.get(ob.toString()) != null) {
			return map.get(ob.toString());
		}

		HttpServletRequest re = ((HttpServletRequest) context.getRequest());
		PageAccess pageAccess = StarfishPageConfigManager.checkPageSF(re
				.getServletPath());

		HttpSession session = ((HttpServletRequest) context.getRequest())
				.getSession();

		PermissionDim permissionDim = (PermissionDim) session
				.getAttribute(StarfishConstants.userPermission);
		map.put(ob.toString(),
				pageAccess.checkUserPermission(ob.toString(), permissionDim));
		return map.get(ob.toString());

	}
}