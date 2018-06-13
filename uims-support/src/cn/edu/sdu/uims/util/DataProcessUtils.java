package cn.edu.sdu.uims.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.sdu.common.util.DateTimeTool;

public class DataProcessUtils {
	
	public static Object[] addItem(Object[] items, Object item) {
		if(items == null || items.length == 0)
			return new Object[]{item};
		Object retItem[] = new Object[items.length+1];
		for(int i =0;i < items.length;i++)
			retItem[i] = items[i];
		retItem[items.length]= item;
		return retItem;
	}
	public static Object[] insertItems(Object[] items, List list) {
		if(list == null ||list.size() == 0)
			return items;
		Object retItems[];
		List<Object> tempList;
		int count = 0;
		int i, j;
		boolean b;
		Object o;
		if (items != null) {
			tempList = new ArrayList<Object>(items.length + list.size());
			count = items.length;
			for (i = 0; i < count; i++) {
				tempList.add(items[i]);
			}
			for (i = 0; i < list.size(); i++) {
				b = false;
				j = 0;
				o = list.get(i);
				while (!b && j < items.length) {
					if (o.equals(items[j])) {
						b = true;
					}
					j++;
				}
				if (!b) {
					tempList.add(o);
				}
			}
		} else {
			tempList = list;
		}
		retItems = new Object[tempList.size()];
		for (i = 0; i < tempList.size(); i++) {
			retItems[i] = tempList.get(i);
		}
		return retItems;
	}

	public static Object[] removeObjects(Object items[], int indexs[]) {
		if (indexs == null || indexs.length == 0)
			return items;
		if (items == null || items.length == 0)
			return null;
		int i, j;
		boolean b;
		List list = new ArrayList(items.length);
		for (i = 0; i < items.length; i++) {
			b = false;
			j = 0;
			while (!b && j < indexs.length) {
				if (i == indexs[j])
					b = true;
				else
					j++;
			}
			if (!b) {
				list.add(items[i]);
			}
		}
		if(list.size() == 0)
			return null;
		items = new Object[list.size()];
		for (i = 0; i < list.size(); i++) {
			items[i] = list.get(i);
		}
		return items;
	}
	public static Object[] removeObject(Object items[], Object obj) {
		if (obj == null )
			return items;
		if (items == null || items.length == 0)
			return null;
		int i;
		boolean b;
		List list = new ArrayList(items.length);
		for (i = 0; i < items.length; i++) {
			if (obj != items[i])
				list.add(items[i]);
		}
		if(list.size() == 0)
			return null;
		items = new Object[list.size()];
		for (i = 0; i < list.size(); i++) {
			items[i] = list.get(i);
		}
		return items;
	}

	public static Object changeStringToObject(Class type, String str) {
		if (str == null || str.length() == 0)
			return null;
		try {
			if (type == Integer.class) {
				return new Integer(str);
			} else if (type == Float.class) {
				return new Float(str);
			} else if (type == Double.class) {
				return new Double(str);
			} else if (type == Boolean.class) {
				if (str.equals("Y") || str.equals("y") || str.equals("是")
						|| str.equals("true"))
					return true;
				else
					return false;
			} else if (type == Date.class) {
				return DateTimeTool.formatDateTime(str, "yyyy-MM-dd HH:mm:ss");
			} else if (type == String.class) {
				return new String(str);
			} else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

}
