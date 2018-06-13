package org.sdu.rmi.parse;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
 

public class RmiModel {
	public Element currentElement;

	public void parse() {

	}

	public void initElement() {
	}

	public void parseChildren() {

	}

	public String asXML() {
		return currentElement.asXML();
	}

	public Element getDomElement() {
		return currentElement;
	}

	// public Element addChildElement(String elementName) {
	// return currentElement.addElement(elementName);

	// }
}