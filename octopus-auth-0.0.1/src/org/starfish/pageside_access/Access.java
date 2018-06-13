package org.starfish.pageside_access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.starfish.configure_model.SFModel;
import org.starfish.permi_dimension.PermissionDim;

public class Access extends SFModel {
	private String dim;
	private String identity;
	private List<String> identityList;

	public Access(SFModel parent) {
		super(parent);
		identityList = new ArrayList<String>();
	}

	public void parse() {
		dim = currentElement.attributeValue("dim");
		identity = currentElement.attributeValue("identity");

		identityList.addAll(Arrays.asList(identity.split(",")));

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

	public void updateDim() {
		currentElement.addAttribute("dim", this.dim);

	}

	public void updateIdentity() {
		currentElement.addAttribute("identity", this.identity);
		identityList.clear();
		identityList.addAll(Arrays.asList(identity.split(",")));

	}

	public List<String> getIdentityList() {
		return identityList;
	}

	public void setIdentityList(List<String> identityList) {
		this.identityList = identityList;
	}

	public boolean checkUserPermission(PermissionDim permissionDim) {

		boolean flag = false;

		PermissionDim p = permissionDim.getPermissionDim(dim);
		if (p == null)
			flag = false;
		List<String> list = p.getPermiItems();
		flag = checkIntersection(identityList, list);
		if (flag)
			return flag;

		return flag;

	}

	private boolean checkIntersection(List<String> l1, List<String> l2) {

		for (String l2s : l2) {
			if (l1.contains(l2s)) {
				return true;
			}
		}
		return false;
	}

}