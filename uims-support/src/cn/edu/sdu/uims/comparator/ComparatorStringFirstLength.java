package cn.edu.sdu.uims.comparator;

import java.util.Comparator;

public class ComparatorStringFirstLength implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		String str1 = (String)o1;
		String str2 = (String)o2;
		if(str1.length() < str2.length())
			return -1;
		else if(str1.length() >str2.length())
			return 1;
		else 
			return str1.compareTo(str2);
	}


}
