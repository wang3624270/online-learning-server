package org.octopus.reportdog.module;

import java.io.Serializable;
import java.util.HashMap;

public class SourceMappingConfig implements Serializable {

	private String handler="defaultMidHandler";
	
	private String id;

	protected HashMap properties = new HashMap();

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public HashMap getProperties() {
		return (this.properties);
	}

	public void addProperty(String name, Object midDataMappingState) {
		properties.put(name, midDataMappingState);

	}

	public Object getProperty(String name) {
		return properties.get(name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
