package cn.edu.sdu.uims.def.dataexport;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.dom4j.Element;

public class DataExportTemplateToXLSByForm extends DataExportTemplateToXLS{
	
	transient private HashMap<String,Method>getMethodMap = null;
	
	public void getAttribute(Element e) {
		super.getAttribute(e);
	}
	public HashMap<String,Method> getMethodMap(){
		if(getMethodMap != null)
			return getMethodMap;
		getMethodMap = new HashMap<String,Method>();
		Class c = null;
		try {
			c = Class.forName(formClassName);
		} catch (Exception e) {
			return getMethodMap;
		}
		if (c == null)
			return getMethodMap;
		DataExportItemTemplate item;
		String methodName;
		Method method;;
		for (int i = 0; i < itemList.size(); i++) {
			item = itemList.get(i);
			name = item.getName();
			if (name == null || name.equals(""))
				continue;
			methodName = name.substring(0, 1).toUpperCase()
					+ name.substring(1, name.length());
			try {
				method = c.getMethod("get" + methodName);
				getMethodMap.put(item.getName(), method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getMethodMap;
	}
}
