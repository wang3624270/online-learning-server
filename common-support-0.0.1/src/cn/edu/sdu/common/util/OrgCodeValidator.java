package cn.edu.sdu.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrgCodeValidator {

	
	final static String[] codeNo = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",  
            "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "O", "P", "Q", "R", "S",  
            "T", "U", "V", "W", "X", "Y", "Z" };  
    final static String[] staVal = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",  
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",  
            "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35" };  
    
    public static boolean cheakOrgCode(String str) {
    	Map map = new HashMap();  
        for (int i = 0; i < codeNo.length; i++) {  
            map.put(codeNo[i], staVal[i]);  
        }  
        final int[] wi = { 3, 7, 9, 10, 5, 8, 4, 2 };  
        Pattern pat = Pattern.compile("^[0-9A-Z]{8}-[0-9X]$");  
        Matcher matcher = pat.matcher(str);  
        if (!matcher.matches()) {  
            System.out.println("不符合组织机构代码形式！");  
        }  
        String[] all = str.split("-");  
        final char[] values = all[0].toCharArray();  
        int parity = 0;  
        for (int i = 0; i < values.length; i++) {  
            final String val = Character.toString(values[i]);  
            parity += wi[i] * Integer.parseInt(map.get(val).toString());  
        }  
        String cheak = (11 - parity % 11) == 10 ? "X" : Integer.toString((11 - parity % 11));  
        return cheak.equals(all[1]);  
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
