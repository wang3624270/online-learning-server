package org.sdu.rmi.parse;

public class Cmd extends RmiModel {
	private String key;
	private String beanId;
	private String method;
	private String description;
	private String authToken = null;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void parse() {
		key = this.currentElement.attributeValue("key");
		beanId = this.currentElement.attributeValue("beanId");
		method = this.currentElement.attributeValue("method");
		description = this.currentElement.attributeValue("description");
		setAuthToken(this.currentElement.attributeValue("authToken"));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}