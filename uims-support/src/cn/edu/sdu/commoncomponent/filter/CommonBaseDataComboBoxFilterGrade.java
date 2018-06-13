package cn.edu.sdu.commoncomponent.filter;

import java.util.List;

import cn.edu.sdu.commoncomponent.util.UCommonQueryUtils;
import cn.edu.sdu.uims.filter.UFilter;

public class CommonBaseDataComboBoxFilterGrade extends UFilter{
	public void init(String parameter) {
		List list = UCommonQueryUtils.getGradeList();
		if(list != null)
			arrayObject = list.toArray();
	}

}
