package cn.edu.sdu.uims.comparator;

import java.util.Comparator;

import cn.edu.sdu.common.form.ListOptionInfo;

public class ComparatorListOptionInfoByValue implements Comparator<Object> {

	private static ComparatorListOptionInfoByValue instance = new ComparatorListOptionInfoByValue();
	private ComparatorListOptionInfoByValue(){
		
	}
	public static ComparatorListOptionInfoByValue getInstance(){
		return instance;
	}
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		ListOptionInfo l1 = (ListOptionInfo)o1;
		ListOptionInfo l2 = (ListOptionInfo)o2;
		
		if (l1.getValue().compareTo(l2.getValue()) < 0) {
			return -1;
		}
		if (l1.getValue().compareTo(l2.getValue()) > 0) {
			return 1;
		}
		return 0;
	}

}
