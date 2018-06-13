package cn.edu.sdu.commoncomponent.filter;

import java.util.List;

import cn.edu.sdu.commoncomponent.util.CommonQueryClientUtils;
import cn.edu.sdu.uims.filter.UFilter;

public class CommonBaseDataComboBoxFilterCultivateUnit extends UFilter {
	public void init(String parameter) {
		List list = CommonQueryClientUtils.getInstance().getCultivateUnitOptionList();
		if(list != null)
			arrayObject = list.toArray();
	}

}
