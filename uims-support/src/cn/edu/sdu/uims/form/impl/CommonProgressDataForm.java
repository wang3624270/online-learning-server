package cn.edu.sdu.uims.form.impl;

import java.util.HashMap;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.handler.impl.UProgressProcessorI;

public class CommonProgressDataForm extends UForm {
	private int count;
	private HashMap paraMap;
	private Object userObject;
	private UProgressProcessorI pi;

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public HashMap getParaMap() {
		return paraMap;
	}

	public void setParaMap(HashMap paraMap) {
		this.paraMap = paraMap;
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public Object getParasObject(String name){
		if(paraMap == null)
			return null;
		else
			return paraMap.get(name);
	}

	public UProgressProcessorI getPi() {
		return pi;
	}

	public void setPi(UProgressProcessorI pi) {
		this.pi = pi;
	}
	
}
