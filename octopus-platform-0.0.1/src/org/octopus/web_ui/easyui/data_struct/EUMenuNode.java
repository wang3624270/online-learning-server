package org.octopus.web_ui.easyui.data_struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EUMenuNode {
	private String id;
	private String text;
	private String state;
	private String name;
	private List<EUMenuNode> children;
	private HashMap<String, String> attributes;

	public EUMenuNode() {
		children = new ArrayList<EUMenuNode>();
		attributes = new HashMap<String, String>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<EUMenuNode> getChildren() {
		return children;
	}

	public void setChildren(List<EUMenuNode> children) {
		this.children = children;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}