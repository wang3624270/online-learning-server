package cn.edu.sdu.uims.form.impl;

import java.util.HashMap;

import cn.edu.sdu.common.form.UForm;

public class CommonQueryBaseForm extends UForm {
	private HashMap<String, String>attributeDataMap = new HashMap<String,String>();
	private HashMap<String, String[]>attributeDatasMap = new HashMap<String,String[]>();
	public HashMap<String, String> getAttributeDataMap() {
		return attributeDataMap;
	}
	public void setAttributeData(String key, String value) {
		attributeDataMap.put(key, value);
	}
	public String getAttributeData(String key) {
		return attributeDataMap.get(key);
	}
	public void setAttributeDatas(String key, String value[]) {
		attributeDatasMap.put(key, value);
	}
	public String[] getAttributeDatas(String key) {
		return attributeDatasMap.get(key);
	}
	public void setAttributeDataMap(HashMap<String, String> attributeDataMap) {
		this.attributeDataMap = attributeDataMap;
	}
	
	public void setAttributeDatasMap(HashMap<String, String[]> attributeDataMap) {
		this.attributeDatasMap = attributeDataMap;
	}
	public HashMap<String, String[]> getAttributeDatasMap() {
		return attributeDatasMap;
	}
	public HashMap getParameters() {
		return parameters;
	}

	public void setParameters(HashMap parameters) {
		this.parameters = parameters;
	}
	private HashMap parameters;


}
