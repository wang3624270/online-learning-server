package org.starfish.pageside_access;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import org.starfish.configure_model.SFModel;
import org.starfish.permi_dimension.PermissionDim;

public class Component extends SFModel {
	public Component(SFModel parent) {
		super(parent);
		visiableList = new ArrayList<Visiable>();
	}

	public List<Visiable> getVisiableList() {
		return visiableList;
	}

	public void setVisiableList(List<Visiable> visiableList) {
		this.visiableList = visiableList;
	}

	public Element currentElement;
	private List<Visiable> visiableList;
	private String id;
	private String description;
	private boolean isVisiable; // visibility of the component of the page;

	public boolean getIsVisiable() {
		return isVisiable;
	}

	public void setIsVisiable(boolean isVisiable) {
		this.isVisiable = isVisiable;
	}

	public void parse() {
		id = currentElement.attributeValue("id");
		description = currentElement.attributeValue("description");
		List list = currentElement.elements();
		int i;
		Element e;
		for (i = 0; i < list.size(); i++) {
			e = (Element) list.get(i);
			if (e.getName().equals("visiable")) {
				Visiable com = new Visiable(this);
				com.currentElement = e;
				visiableList.add(com);
				com.parse();
			}
		}
	}

	public boolean checkPermission(PermissionDim permissionDim) {

		int i;
		boolean flag = false;
		for (i = 0; i < visiableList.size(); i++) {
			PermissionDim p = permissionDim.getPermissionDim(visiableList
					.get(i).getDim());
			if (p == null)
				flag |= false;
			List<String> list = p.getPermiItems();
			flag |= checkIntersection(visiableList.get(i).getIdentityList(),
					list);
			if (flag)
				return flag;
		}

		return false;
	}

	private boolean checkIntersection(List<String> l1, List<String> l2) {

		for (String l2s : l2) {
			if (l1.contains(l2s)) {
				return true;
			}
		}
		return false;
	}

	public void parseChildren() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}