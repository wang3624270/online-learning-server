package cn.edu.sdu.uims.progress;


import java.util.HashMap;


public class ProgressFactory {
	static private int MAXNUMBER = 20000;
	static private int no = 0;
	static private HashMap map = new HashMap();
	public ProgressFactory() {
		super();
	}
	public static ProgressElementObject  getNewInstance(){
		ProgressElementObject obj = new ProgressElementObject(no); 
		map.put(obj.getKey(),obj);
		no++;
		if(no >MAXNUMBER)
			no = 0;
		return obj;
	}
	public static void setPos(ProgressElementObject o , int pos){
		if(o == null)
			return;
		String  key = o.getKey();
		Object obj = map.get(key);
		if(o != null){
			ProgressElementObject po = (ProgressElementObject)obj;
			po.setPos(pos);
		}
	}
	public static void setMax(ProgressElementObject o , int max){
		if(o == null)
			return;
		String  key = o.getKey();
		Object obj = map.get(key);
		if(obj != null){
			ProgressElementObject po = (ProgressElementObject)obj;
			po.setMax(max);
		}
	}
	public static void appendAddString(ProgressElementObject o , String str){
		if(o == null)
			return;
		String  key = o.getKey();
		Object obj = map.get(key);
		if(o != null){
			ProgressElementObject po = (ProgressElementObject)obj;
			po.appendAddString(str);
		}
	}
	public static String getAddString(ProgressElementObject o){
		if(o == null)
			return null;
		String  key = o.getKey();
		Object obj = map.get(key);
		if(o != null){
			ProgressElementObject po = (ProgressElementObject)obj;
			return po.getAddString();
		}
		return null;
	}

	public static void remove(ProgressElementObject o){
		map.remove(o.getKey());
	}
	
	public static ProgressElementObject getEqualObjectData(ProgressElementObject o){		
		String  key = o.getKey();
		Object obj = map.get(key);
		if(obj == null)
				return null;
		else{
			ProgressElementObject rObj = new ProgressElementObject((ProgressElementObject)obj);
			ProgressElementObject pobj = (ProgressElementObject)obj;
			pobj.clearAddString();
			return rObj;
		}
	}
}
