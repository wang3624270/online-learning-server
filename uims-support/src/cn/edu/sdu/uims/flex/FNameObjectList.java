package cn.edu.sdu.uims.flex;

import java.util.ArrayList;


public class FNameObjectList extends ArrayList<FNameObjectPar>{
	
	public Object getObjectByName(String name){
		int i;
		FNameObjectPar obj;
		for(i = 0; i < size(); i++ ) {
			obj = this.get(i);
			if(obj.name == name)
				return obj.ob;
		}
		return null;			
	}

}
