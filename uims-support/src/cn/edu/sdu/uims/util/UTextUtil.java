package cn.edu.sdu.uims.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.constant.UConstants;

public class UTextUtil {
	public static Rectangle2D getFontSize(Font font, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		return font.getStringBounds("正", g2.getFontRenderContext());
	}

	public static List getRowLine(String text, Font font, Graphics g, int w,
			int firstSpace) {
		Graphics2D g2 = (Graphics2D) g;
		List list = new ArrayList();
		Rectangle2D b = getFontSize(font, g);
		double dfw = b.getWidth(), dfh = (int) (b.getHeight() + 0.5), dfrw;
		String s = null, ss = null;
		StringTokenizer st = new StringTokenizer(text, ""
				+ UConstants.RETURNCHAR);
		int len, pos, n, k;
		boolean end = false, mustChangeLine = false;
		while (st.hasMoreTokens()) {
			s = st.nextToken();
			if (w <= dfw) {
				list.add(s);
			} else {
				pos = 0;
				len = s.length();
				while (pos < len) {
					end = false;
					n = 0;
					if (list.size() > 0)
						dfrw = 0;
					else
						dfrw = firstSpace * dfw;
					while (!end && (pos + n < len)) {
						if ((s.charAt(pos + n) > 128)) {
							if (dfrw + dfw < w) {
								dfrw += dfw;
								n++;
							} else {
								end = true;
							}
						} else {
							k = 1;
							while ((pos + n + k < len)
									&& (s.charAt(pos + n + k) < 128))
								k++;
							b = font.getStringBounds(s.substring(pos + n, pos
									+ n + k), g2.getFontRenderContext());
							if (dfrw + b.getWidth() < w) {
								dfrw += b.getWidth();
								n = n + k;
							} else {
								end = true;
								if (!mustChangeLine) {
									mustChangeLine = true;
								} else {
									n = n + k;
									mustChangeLine = false;
								}
							}
						}
					}
					if (n > 0) {
						ss = s.substring(pos, pos + n);
						list.add(ss);
						pos += n;
					}
				}
			}
		}
		return list;
	}

	public static String getReplaceString(String str, HashMap map, UFormI form,
			UComponentI obj) {
		String methodName = null;
		Method method;
		String ret = null;
		if (map != null) {
			ret = (String) map.get(str);
		}
		methodName = "get" + str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
		if (ret == null && form != null) {
			try {
				method = form.getClass().getMethod(methodName);
				ret = method.invoke(form).toString();
			} catch (Exception e) {
				ret = null;
			}
		}
		if (ret == null && obj != null) {
			try {
				method = obj.getClass().getMethod(methodName);
				ret = method.invoke(obj).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (ret == "null")
			ret = "";
		return ret;
	}

	public static String replaceString(String str, HashMap map, UFormI form,
			UComponentI obj) {
		String ret = null;
		String temp = null;
		int start, end, len = str.length();
		if (str.indexOf(UConstants.MARKCHAR) < 0)
			return str;
		start = 0;
		end = -1;
		ret = "";
		while (start < len) {
			while (start < len && (str.charAt(start) != UConstants.MARKCHAR))
				start++;
			ret += str.substring(end + 1, start);
			if (start >= len)
				break;
			end = start + 1;
			//刘洋加
			if(end<len&&str.charAt(end)==UConstants.MARKCHAR){
				start=end+1;
				while (start < len && (str.charAt(start) != UConstants.MARKCHAR))
					start++;
				ret += str.substring(end, start);
				if (start >= len)
					break;
				end = start + 1;
			}
			//刘洋end
			while (end < len && (str.charAt(end) != UConstants.MARKCHAR))
				end++;
			temp = str.substring(start + 1, end);
			ret += getReplaceString(temp, map, form, obj);
			start = end + 1;
		}
		return ret;
	}

	public static String getDataString(int mode) {
		String s = null;
		int year, month, day;
		Calendar t = Calendar.getInstance();
		year = t.get(t.YEAR);
		// month = t.get(t.MONTH)+1;
		month = 6;
		day = t.get(t.DAY_OF_MONTH);
		switch (mode) {
		case UConstants.DATA_MODE_NUMBER:
			s = year + "年" + month + "月" + day + "日";
			break;
		}
		return s;
	}
}
