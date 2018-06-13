package org.starfish.configure_model;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class SFModel {
	public Element currentElement;
	public SFModel parent;

	public SFModel(SFModel parent) {
	}

	public void parse() {

	}

	public void parseChildren() {

	}

	public String asXML() {
		return currentElement.asXML();
	}

	public Element getDomElement() {
		return currentElement;
	}

	public Element addChildElement(String elementName) {
		return currentElement.addElement(elementName);

	}
}