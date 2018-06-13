package org.starfish.sf_auth.model;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import org.starfish.configure_model.SFModel;

public class SFIdentity extends SFModel {
	private List<Belong> belongList;

	public SFIdentity(SFModel parent) {
		super(parent);
		belongList = new ArrayList<Belong>();

	}

	public void parse() {
		List<Element> list = currentElement.elements();
		int i;
		Element e;
		Belong d;
		for (i = 0; i < list.size(); i++) {
			e = list.get(i);
			d = new Belong(this);
			belongList.add(d);
			d.currentElement = e;
			d.parse();
		}

	}

	public List<String> getDimPositions(String dimName) {
		List<String> list = new ArrayList<String>();
		int i;
		for (i = 0; i < belongList.size(); i++) {
			if (belongList.get(i).getDim().equals(dimName))
				list.add(belongList.get(i).getValue());

		}
		return list;
	}
}