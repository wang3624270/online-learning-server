package cn.edu.sdu.uims.util;

import java.util.List;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.filter.FilterI;

public class UCommonMethodS {
	public static void initAddedData(UPanelI component, String name, List list) {
		UComponentI sub = component.getSubComponent(name);
		if (sub != null) {
			FilterI filter = sub.getFilter();
			if (filter != null) {
				filter.setAddedData(list);
			}
			sub.updateAddedDatas();
		}
	}

}
