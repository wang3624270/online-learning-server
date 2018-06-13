package org.starfish.pageside_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.starfish.configure_model.SFModel;
import org.starfish.permi_dimension.PermissionDim;

public class PageAccess extends SFModel {

	private HashMap<String, Component> comMap;
	private String currentPagePath;
	private String id;

	public PageAccess(SFModel parent) {
		super(parent);
		comMap = new HashMap<String, Component>();

	}

	public void parse() {
		List list = currentElement.elements();
		int i;
		Element e;
		for (i = 0; i < list.size(); i++) {
			e = (Element) list.get(i);
			if (e.getName().equals("component")) {
				Component com = new Component(this);
				com.currentElement = e;
				com.parse();
				comMap.put(com.getId(), com);
			}
		}
	}

	public boolean checkUserPermission(String componentId,
			PermissionDim permissionDim) {
		Component com = comMap.get(componentId);
		if (com == null)
			return true;
		return com.checkPermission(permissionDim);

	}

	public List<Component> getComponents() {
		Set set = comMap.keySet();
		Iterator it = set.iterator();
		List<Component> list = new ArrayList<Component>();
		while (it.hasNext()) {
			list.add(comMap.get(it.next()));
		}

		return list;

	}

	public Component getComponent(String componentId) {
		Set set = comMap.keySet();
		Iterator it = set.iterator();
		Component com = null;
		while (it.hasNext()) {
			com = comMap.get(it.next());
		}
		return com;
	}

	public void parseChildren() {

	}

	public String getCurrentPagePath() {
		return currentPagePath;
	}

	public void setCurrentPagePath(String currentPagePath) {
		this.currentPagePath = currentPagePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}