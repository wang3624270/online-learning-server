package org.starfish.sf_auth;

import java.util.ArrayList;
import java.util.List;

import org.starfish.web_security.UserPortalURLUtils;

 

public class LoginPortalsManager {

	private List<UserPortal> portals;
	private List<String> portalsList = new ArrayList<String>();

	public List<UserPortal> getPortals() {
		return portals;
	}

	public void setPortals(List<UserPortal> portals) {
		this.portals = portals;
	}

	public void init() {
		int i;
		if (portals != null)
			for (i = 0; i < portals.size(); i++)
				UserPortalURLUtils.setSuccessAndFialURLs(portals.get(i)
						.getPortalName(), portals.get(i).getSuccessURL(),portals.get(i).getLogoutURL(),
						portals.get(i).getFailURL());
	}
}