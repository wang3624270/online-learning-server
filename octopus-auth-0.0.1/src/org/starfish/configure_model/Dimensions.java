package org.starfish.configure_model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.dom4j.Element;
import org.sdu.rmi.RmiSession;

public class Dimensions extends SFModel {
	List<Dimension> dimensionList;

	public Dimensions(SFModel parent) {
		super(parent);
		dimensionList = new ArrayList<Dimension>();
	}

	public void parse() {
		List<Element> list = currentElement.elements();
		int i;
		Element e;
		Dimension d;
		for (i = 0; i < list.size(); i++) {
			e = list.get(i);
			d = new Dimension(this);
			dimensionList.add(d);
			d.currentElement = e;
			d.parse();
		}

	}

	public void walkUserPermissionDims(HttpSession session) {
		for (Dimension dim : dimensionList) {
			dim.walkUserPermissionDims(session);
		}
	}
	
	public void walkUserPermissionDims(RmiSession session) {
		for (Dimension dim : dimensionList) {
			dim.walkUserPermissionDims(session);
		}
	}
	

	public void walkUserPermissionDims(Session session) {
		for (Dimension dim : dimensionList) {
			dim.walkUserPermissionDims(session);
		}
	}

	public Dimension getDimension(String dimName) {
		for (Dimension d : dimensionList) {
			if (d.getName().equals(dimName))
				return d;
		}
		return null;
	}
}