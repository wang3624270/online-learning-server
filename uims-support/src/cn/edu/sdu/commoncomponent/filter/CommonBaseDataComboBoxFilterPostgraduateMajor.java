package cn.edu.sdu.commoncomponent.filter;

import java.util.List;

import cn.edu.sdu.commoncomponent.util.CommonQueryClientUtils;
import cn.edu.sdu.uims.filter.UFilter;

public class CommonBaseDataComboBoxFilterPostgraduateMajor extends UFilter {
	public void init(String parameter) {
		List list = CommonQueryClientUtils.getInstance().getPostgraduateMajorOptionList();
		if(list != null)
			arrayObject = list.toArray();
	}

}
