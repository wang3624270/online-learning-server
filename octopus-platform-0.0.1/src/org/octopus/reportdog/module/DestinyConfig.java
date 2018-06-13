package org.octopus.reportdog.module;

import java.util.HashMap;

public class DestinyConfig {

	private String name;

	private String handler;

	protected HashMap properties = new HashMap();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
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

}
