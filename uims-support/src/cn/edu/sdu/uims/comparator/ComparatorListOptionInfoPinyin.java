package cn.edu.sdu.uims.comparator;

import java.text.Collator;
import java.util.Comparator;

import cn.edu.sdu.common.form.ListOptionInfo;

public class ComparatorListOptionInfoPinyin implements Comparator<Object> {

	private Comparator comparator = null;
	private String type = null; // label value

	public ComparatorListOptionInfoPinyin(String labelorValue) {
		comparator = Collator.getInstance(java.util.Locale.CHINA);
		type = labelorValue;
	}

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		ListOptionInfo obj1 = (ListOptionInfo) o1;
		ListOptionInfo obj2 = (ListOptionInfo) o2;
		if ("label".equals(type))
			return comparator.compare(obj1.getLabel(), obj2.getLabel());
		else if ("value".equals(type))
			return comparator.compare(obj1.getValue(), obj2.getValue());
		else
			return comparator.compare(obj1.getValue(), obj2.getValue());
	}

}
