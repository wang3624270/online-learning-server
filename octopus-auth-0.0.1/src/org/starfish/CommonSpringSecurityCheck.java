package org.starfish;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.starfish.constants.StarfishConstants;
import org.starfish.pageside_access.URLAccess;
import org.starfish.permi_dimension.PermissionDim;

 
public class CommonSpringSecurityCheck {

	public boolean checkURLAuth(ServletRequest servletRequest) {
		HttpSession se = ((HttpServletRequest) servletRequest).getSession();
		PermissionDim p = (PermissionDim) se
				.getAttribute(StarfishConstants.userPermission);
		String url = ((HttpServletRequest) servletRequest).getServletPath()
				.toString();

		url = url.substring(1);
		URLAccess urlAccess = URLSupportManager.urlAccessMap.get(url);
		if (urlAccess == null)
			return true;
		return urlAccess.checkUserPermission(p);

	}

}