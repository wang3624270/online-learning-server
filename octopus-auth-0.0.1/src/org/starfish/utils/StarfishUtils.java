package org.starfish.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StarfishUtils {

	public static <T> List<T> mapToList(HashMap<String, T> map) {
		Set set = map.keySet();
		Iterator it = set.iterator();
		List<T> list = new ArrayList<T>();
		while (it.hasNext()) {
			list.add(map.get(it.next()));
		}
		return list;
	}
}