package org.starfish.pageside_access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;
import org.starfish.configure_model.SFModel;

public class Visiable extends SFModel {

	private String dim;
	private String identity;
	private List<String> identityList;

	public Visiable(SFModel parent) {
		super(parent);
	}

	public void parse() {
		dim = currentElement.attributeValue("dim");
		identity = currentElement.attributeValue("identity");
		identityList = Arrays.asList(identity.split(","));
	}

	public void parseChildren() {

	}

	public String getDim() {
		return dim;
	}

	public void setDim(String dim) {
		this.dim = dim;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void updateIdentity() {
		currentElement.addAttribute("identity", this.identity);

	}

	public List<String> getIdentityList() {
		return identityList;
	}

	public void setIdentityList(List<String> identityList) {
		this.identityList = identityList;
	}
}