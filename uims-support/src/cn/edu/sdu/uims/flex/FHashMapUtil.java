package cn.edu.sdu.uims.flex;

import java.util.HashMap;

public class FHashMapUtil {
	public static HashMap fHashMapToHashMap(FHashMap fMap){
		if(fMap == null || fMap.size() == 0)
			return null;
		HashMap map = new HashMap();
		FNameObjectPar no;
		for(int i = 0; i < fMap.size();i++) {
			no = fMap.getNameObject(i);
			if(no.getObject() instanceof FHashMap)
				map.put(no.getName(), fHashMapToHashMap((FHashMap)no.getObject()));
			else 
				map.put(no.getName(), no.getObject());
		}
		return map;
	}
}
