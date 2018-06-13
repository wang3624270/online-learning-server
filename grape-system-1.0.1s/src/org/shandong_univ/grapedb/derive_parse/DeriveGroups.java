package org.shandong_univ.grapedb.derive_parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

public class DeriveGroups extends DeriveModel {
	List<DeriveGroup> deriveGroupList;
	HashMap<String, DeriveGroup> deriveGroupMap;

	public DeriveGroups() {
		deriveGroupList = new ArrayList<DeriveGroup>();
		deriveGroupMap = new HashMap<String, DeriveGroup>();
	}

	public DeriveGroup getDeriveGroup(String mainTable) {
		return deriveGroupMap.get(mainTable);

	}

	public void parseModel(Element element) throws Exception {
		DeriveGroup deriveGroup;

		super.parseModel(element);
		Element root = element;
		List list = root.elements();
		int i;
		for (i = 0; i < list.size(); i++) {
			deriveGroup = new DeriveGroup();
			deriveGroup.parseModel((Element) list.get(i));
			deriveGroupList.add(deriveGroup);
			deriveGroupMap.put(deriveGroup.getMainTable(), deriveGroup);
		}

	}
}