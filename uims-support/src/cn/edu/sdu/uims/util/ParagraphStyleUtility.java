package cn.edu.sdu.uims.util;

import java.util.ArrayList;
import java.util.List;

public class ParagraphStyleUtility {

	public final static String SPLITSTREND = "#/font#";

	public final static String SPLITSTRSTART = "#font:";

	public static List parseSyte(String paragraph, String defaultFont) {
		// String[] style = null;
		List styleList = null;
		if (paragraph != null) {
			styleList = new ArrayList();
			int index = paragraph.indexOf(SPLITSTREND);
			if (index != -1) {
				String[] strs = paragraph.split(SPLITSTREND);
				int i;
				String[] style;
				for (i = 0; i < strs.length; i++) {
					String str = strs[i];
					if (!str.equals("")) {
						index = str.indexOf(SPLITSTRSTART);
						if (index != -1) {
							String strleft = str.substring(0, index);
							if (!strleft.equals("")) {
								style = new String[2];
								style[0] = strleft;
								style[1] = defaultFont;
								styleList.add(style);
							}
							str = str.substring(index + SPLITSTRSTART.length());
							int pos = str.indexOf('#');
							style = new String[2];
							style[0] = str.substring(pos + 1, str.length());
							style[1] = str.substring(0, pos);
							styleList.add(style);
						} else {
							style = new String[2];
							style[0] = str;
							style[1] = defaultFont;
							styleList.add(style);
						}

					}
				}
			} else {
				String[] style = new String[2];
				style[0] = paragraph;
				style[1] = defaultFont;
				styleList.add(style);
			}
		}
		return styleList;
	}

	// public static String parepareString(String paragraph, String replaceStr)
	// {
	// String preparedStr = paragraph;
	// if (paragraph != null) {
	// String strs[] = paragraph.split(replaceStr);
	// }
	// return preparedStr;
	//	}

	public static void main(String args[]) {
		String str = "I love you@font:font12@I also love you@/font@";
		String defaultFont = "Font1998";
		List list = ParagraphStyleUtility.parseSyte(str, defaultFont);
		for (int i = 0; i < list.size(); i++) {
			String[] strs = (String[]) list.get(i);
			System.out.print(i + "       ");
			System.out.print(strs[0] + "      ");
			System.out.println(strs[1]);
		}
	}
}
