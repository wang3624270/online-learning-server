package org.starfish.pageside_access.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.starfish.MenuSupportManager;
import org.starfish.URLSupportManager;
import org.starfish.configure_model.ComAccess;
import org.starfish.configure_model.SFModel;
import org.starfish.pageside_access.Access;
import org.starfish.pageside_access.URLAccess;
import org.starfish.permi_dimension.PermissionDim;

/*
 * 菜单权限
 */

public class MenuAccess extends ComAccess {
	private HashMap<String, Access> accessMap;
	private String refResType = "url";
	private String menuUrl;
	private String menuLogicId;
	private int menuDeep = 0;

	public String getMenuLogicId() {
		return menuLogicId;
	}

	public void setMenuLogicId(String menuLogicId) {
		this.menuLogicId = menuLogicId;
	}

	public MenuAccess(SFModel parent) {
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
		List<Access> list1 = new ArrayList<Access>();
		for (i = 0; i < list.size(); i++) {
			e = (Element) list.get(i);
			if (e.getName().equals("access")) {
				Access acc = new Access(parent);
				acc.currentElement = e;
				acc.parse();
				accessMap.put(acc.getDim(), acc);
				list1.add(acc);
			}
		}
		// /////////////////////////////////
		if (refResType.equals("url")) {
			if (menuUrl != null&&menuUrl.length()>0) {
				URLAccess u = URLSupportManager.getURLAccess(menuUrl);
				if (u == null) {
					u = new URLAccess(null);
					u.currentElement = this.currentElement;
					u.parse();
					URLSupportManager.setURLAccess(menuUrl, u);
				} else {
					u.mergeAccessList(list1);
				}

			} else {
				//menu的url是空，这个时候如何处理，待定
				//MenuSupportManager.setMenuAccess(menuLogicId, this);
			}
		}
	}

	public boolean checkUserPermission(PermissionDim permissionDim) {
		if (refResType.equals("url")) {
			if (this.menuUrl == null||this.menuUrl.length()==0) {
				Set set = accessMap.keySet();
				Iterator it = set.iterator();
				boolean flag = true;
				while (it.hasNext()) {
					Access ac = accessMap.get(it.next());
					flag &= ac.checkUserPermission(permissionDim);
					if (flag)
						break;
				}
				return flag;
			} else {
				URLAccess u = URLSupportManager.getURLAccess(menuUrl);
				return u.checkUserPermission(permissionDim);
			}
		} else {
			// extent
		}

		return false;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

}