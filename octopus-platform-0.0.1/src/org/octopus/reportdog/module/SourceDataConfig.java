package org.octopus.reportdog.module;

import java.io.Serializable;
import java.util.HashMap;

public class SourceDataConfig implements Serializable {

	private String name;
	private String id;

	private String handler = "";
	private String provider = "";
	private String method;
	private String type = "local";
	private int isGlobalDataConfig = 0;// 是否是全局数据块（0不是，1是）
	protected HashMap properties = new HashMap();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.id = this.name;
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

	public void setIsGlobalDataConfig(int isGlobalDataConfig) {
		this.isGlobalDataConfig = isGlobalDataConfig;
	}

	public int getIsGlobalDataConfig() {
		return isGlobalDataConfig;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void setId(String id) {
		this.id = id;
		this.name = this.id;
	}

	public String getId() {
		return id;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProvider() {
		return provider;
	}

	public void setType(String type) {
		this.type = type;
		if (type.equals("local"))
			this.isGlobalDataConfig = 0;
		else
			this.isGlobalDataConfig = 1;
	}

	public String getType() {
		return type;
	}

}
