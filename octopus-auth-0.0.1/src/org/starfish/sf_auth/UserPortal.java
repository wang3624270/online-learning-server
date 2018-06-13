package org.starfish.sf_auth;

import java.util.ArrayList;

public class UserPortal {
	private String portalName = "";
	private String successURL = "";
	private String failURL = "";
	private String logoutURL = "";

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getSuccessURL() {
		return successURL;
	}

	public void setSuccessURL(String successURL) {
		this.successURL = successURL;
	}

	public String getFailURL() {
		return failURL;
	}

	public void setFailURL(String failURL) {
		this.failURL = failURL;
	}

	public String getLogoutURL() {
		return logoutURL;
	}

	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}

}