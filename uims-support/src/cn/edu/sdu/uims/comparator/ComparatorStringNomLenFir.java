package cn.edu.sdu.uims.comparator;

import java.util.Comparator;

public class ComparatorStringNomLenFir implements Comparator<Object>{
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		String str1 = (String)o1;
		String str2 = (String)o2;
		
		for(int i=0;i<str1.length();i++){
			if(i==str1.length()&&i<str2.length())
				return -1;
			else if(i==str2.length()&&i<str1.length())
				return 1;
			char ch1 = str1.charAt(i);
			char ch2 = str2.charAt(i);
			if(ch1>='0'&&ch2<='9'){
				int i1 = getNumber(str1.substring(i));
				int i2 = getNumber(str2.substring(i));
				if(i1==i2)
					continue;
				else
					return i1-i2;
			}else if(ch1!=ch2){
				return ch1-ch2;
			}
		}
		
		return 0;
	}
	
	private int getNumber(String str){
		int num = Integer.MAX_VALUE;
		int bits = 0;
		
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)>='0'&&str.charAt(i)<='9')
				bits ++;
			else
				break;
		}
		if(bits>0)
			num = Integer.parseInt(str.substring(0,bits));
		
		return num;
	}
}
