package cn.edu.sdu.uims.util;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;
import net.sourceforge.pinyin4j.PinyinHelper;

public class PinYinUtil {

	/**
	 * @param args
	 */
	public static List getSelectedList(List list, String in) {
		List retList;
		if (in == null || in.length() == 0)
			return list;
		retList = new ArrayList();
		String[] py;
		String word;
		Object obj;
		int len, i, j, k;
		char c;
		boolean isTrue;
		boolean isExist;
		for (i = 0; i < list.size(); i++) {
			obj = list.get(i);
			if (obj instanceof ListOptionInfo) {
				word = ((ListOptionInfo) obj).getLabel();
			} else {
				word = obj.toString();
			}
			len = in.length();
			if (len > word.length())
				continue;
			isTrue = true;
			j = 0;
			while (isTrue && j < len) {
				c = in.charAt(j);
				py = PinyinHelper.toHanyuPinyinStringArray(word.charAt(j));
				if (py == null || py.length == 0)
					isTrue = false;
				isExist = false;
				if (py != null) {
					for (k = 0; k < py.length; k++) {
						if (py[k].charAt(0) == c) {
							isExist = true;
							break;
						}
						if (!isExist)
							isTrue = false;
					}
					j++;
				}
			}
			if (!isTrue)
				continue;
			retList.add(obj);
		}
		return retList;
	}

	public static boolean isSelectShowItem(Object obj, String in) {
		if (obj == null )
			return false;
		String[] py;
		String word;
		int len, j, k;
		char c;
		boolean isTrue;
		boolean isExist;
		if (obj instanceof ListOptionInfo) {
			word = ((ListOptionInfo) obj).getLabel();
		} else {
			word = obj.toString();
		}
		len = in.length();
		if (len > word.length())
			return false;
		if(in.equals(word))
			return true;
		isTrue = true;
		j = 0;
		while (isTrue && j < len) {
			c = in.charAt(j);
			py = PinyinHelper.toHanyuPinyinStringArray(word.charAt(j));
			if (py == null || py.length == 0)
				isTrue = false;
			isExist = false;
			if (py != null) {
				for (k = 0; k < py.length; k++) {
					if (py[k].charAt(0) == c) {
						isExist = true;
						break;
					}
					if (!isExist)
						isTrue = false;
				}
				j++;
			}
		}
		return isTrue;
	}
}
