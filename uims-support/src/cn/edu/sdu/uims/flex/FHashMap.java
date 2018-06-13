package cn.edu.sdu.uims.flex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FHashMap implements  Serializable{
	public List list = new ArrayList();

	public FHashMap() {
	}

	public int size() {
		return list.size();
	}

	public Object getData(String key) {

		int i;

		FNameObjectPar obj;
		for (i = 0; i < list.size(); i++) {
			obj = (FNameObjectPar) list.get(i);
			if (obj.name.equals(key)) {
				return obj.ob;
			}
		}
		return null;
	}

	public void putData(String key1, Object o) {
		int i;

		FNameObjectPar obj;
		for (i = 0; i < list.size(); i++) {
			obj = (FNameObjectPar) list.get(i);
			if (obj.name.equals(key1)) {
				obj.ob = o;
				return;
			}
		}
		obj = new FNameObjectPar();
		obj.name = key1;
		obj.ob = o;
		list.add(obj);
	}
	public FNameObjectPar getNameObject(int i) {
		return (FNameObjectPar)list.get(i);
	}
}