package org.starfish.pageside_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.starfish.configure_model.ComAccess;
import org.starfish.configure_model.SFModel;
import org.starfish.permi_dimension.PermissionDim;
import org.starfish.utils.StarfishUtils;

public class URLAccess extends ComAccess {

	private HashMap<String, Access> accessMap;

	public URLAccess(SFModel parent) {
		super(parent);
		accessMap = new HashMap<String, Access>();

	}

	public void parse() {
		List list = null;
		if (currentElement != null)
			list = currentElement.elements();
		if (list == null)
			list = new ArrayList();
		int i;
		Element e;
		for (i = 0; i < list.size(); i++) {
			e = (Element) list.get(i);
			if (e.getName().equals("access")) {
				Access acc = new Access(parent);
				acc.currentElement = e;
				acc.parse();
				accessMap.put(acc.getDim(), acc);
			}
		}

	}

	public void parseChildren() {

	}

	public void mergeAccessList(List<Access> list) {
		int i;
		for (i = 0; i < list.size(); i++) {
			Access acc = accessMap.get(list.get(i).getDim());
			if (acc == null) {
				acc = new Access(this);
				acc.setDim(list.get(i).getDim());
				accessMap.put(list.get(i).getDim(), acc);
			}
			for (String str : list.get(i).getIdentityList()) {
				if (!acc.getIdentityList().contains(str)) {

					acc.getIdentityList().add(str);

				}
			}

		}
	}

	public boolean checkUserPermission(PermissionDim permissionDim) {
		Set set = accessMap.keySet();
		Iterator it = set.iterator();
		boolean flag = false;
		while (it.hasNext()) {
			Access ac = accessMap.get(it.next());
			flag |= ac.checkUserPermission(permissionDim);
			if (flag)
				break;
		}

		return flag;

	}

	public List<Access> getAccesses() {
		List<Access> list = StarfishUtils.mapToList(accessMap);
		return list;
	}

	public void addAccess(Access ac) {
		accessMap.put(ac.getDim(), ac);
	}
}