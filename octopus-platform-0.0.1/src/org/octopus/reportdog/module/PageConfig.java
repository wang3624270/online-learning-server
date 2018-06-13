package org.octopus.reportdog.module;

import java.util.HashMap;

public class PageConfig {

	public String name;

	public String handler;

	public String paper;
	public String backgroundImagePath;
	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
	}

	protected HashMap properties = new HashMap();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap getProperties() {
		return (this.properties);
	}

	public void addProperty(String name, String value) {
		properties.put(name, value);

	}

	public String getProperty(String name) {
		return (String) properties.get(name);
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	// //////////////////////////////////////////////
	public PageConfig clone() {

		PageConfig aConfig = new PageConfig();
		aConfig.name = this.name;
		aConfig.handler = this.handler;
		aConfig.paper = this.paper;
		aConfig.properties = new HashMap();
		aConfig.properties.putAll(this.properties);
		return aConfig;
	}
}
