package cn.edu.sdu.uims.comparator;

import java.text.Collator;
import java.util.Comparator;

public class ComparatorStringPinyin implements Comparator<Object> {

	private Comparator comparator = null;
	public ComparatorStringPinyin(){
		comparator = Collator.getInstance(java.util.Locale.CHINA);
	
	}
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return comparator.compare(o1,o2);
	}

}
