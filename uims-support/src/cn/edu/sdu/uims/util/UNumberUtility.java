package cn.edu.sdu.uims.util;

public class UNumberUtility {

	public UNumberUtility() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static boolean isIntValue(String str) {
		int length = str.length();
		if (length == 0)
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
				return false;
			}
		}
		return true;

	}

	public static boolean isFloatValue(String str) {
		int length = str.length();
		boolean flag = true;
		int count = 0;
		if (length == 0 || str.charAt(0) == '.'
				|| str.charAt(length - 1) == '.')
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9')
					&& str.charAt(i) != '.') {
				flag = false;
				break;
			}

			if (str.charAt(i) == '.')
				count++;
		}
		if (!flag || count > 1)
			return false;
		return true;

	}
}
