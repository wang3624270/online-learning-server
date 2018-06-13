package org.shandong_univ.grapedb.derive_parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

public class DeriveGroup extends DeriveModel {
	List<DeriveItem> deriveItemList;
	public HashMap<String, DeriveItem> deriveItemMap;
	private String mainTable;

	public DeriveGroup() {
		deriveItemList = new ArrayList<DeriveItem>();
		deriveItemMap = new HashMap<String, DeriveItem>();
	}

	public void parseModel(Element element) throws Exception {
		Element root = element;
		super.parseModel(element);
		List list = root.elements();
		int i, j;
		DeriveItem tempItem;
		for (i = 0; i < list.size(); i++) {
			tempItem = new DeriveItem();
			tempItem.parseModel((Element) list.get(i));
			deriveItemList.add(tempItem);
			String[] arrs = tempItem.getOuterKey().split(",");
			for (j = 0; j < arrs.length; j++) {
				deriveItemMap.put(arrs[j], tempItem);
			}
		}
	}

	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}

}