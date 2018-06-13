package cn.edu.sdu.uims.form.impl;

import java.util.HashMap;

import cn.edu.sdu.common.form.UForm;

public class UMapForm extends UForm {
	private HashMap<String, Object>dataMap = new HashMap<String, Object>();

	public Object getAttributeObject(String attributeName) {
		return dataMap.get(attributeName);
	}


	@Override
	public Object getAttributeObject(String attributeName, Integer index) {
		// TODO Auto-generated method stub
		Object o = dataMap.get(attributeName);
		if(o == null || o instanceof Object[]) 
			return null;
		Object[] a = (Object [])o;
		if(index < 0 || index >= a.length)
			return null;
		return a[index];
	}

	@Override
	public void setAttributeObject(String attributeName, Object obj) {
		// TODO Auto-generated method stub
		dataMap.put(attributeName, obj);
	}

	@Override
	public void setAttributeObject(String attributeName, Object obj,
			Integer index) {
		// TODO Auto-generated method stub
		Object o = dataMap.get(attributeName);
		if(o == null || o instanceof Object[]) 
			return ;
		Object[] a = (Object [])o;
		if(index < 0 || index >= a.length)
			return ;
		a[index] = obj;
	}
	
}
