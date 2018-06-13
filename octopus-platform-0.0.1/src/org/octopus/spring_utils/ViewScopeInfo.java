package org.octopus.spring_utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ViewScopeInfo {

	private HashMap<String, HashMap<String, Object>> map = null;

	private List<String> viewCodeList = null;

	public ViewScopeInfo() {
		map = new HashMap<String, HashMap<String, Object>>();
		viewCodeList = new LinkedList<String>();
	}

	public HashMap<String, Object> getViewMap(String viewCode) {
		return map.get(viewCode);
	}

	synchronized public HashMap<String, Object> checkViewSeq(String viewCode) {
		int x = viewCodeList.indexOf(viewCode);
		if (viewCodeList.size() < ViewScopeManager.viewMaxNums) {
			if (x >= 0)
				viewCodeList.add(viewCodeList.remove(x));
			else
				map.put(viewCode, new HashMap<String, Object>());
		} else {
			String str = viewCodeList.remove(ViewScopeManager.viewMaxNums - 1);
			map.remove(str);
			viewCodeList.add(viewCode);
			map.put(viewCode, new HashMap<String, Object>());
		}
		return map.get(viewCode);

	}
}