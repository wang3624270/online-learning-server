package cn.edu.sdu.uims.def;

import java.util.HashMap;

import org.dom4j.Element;

public class UTextFieldQueryTemplate extends UTextFieldTemplate {
	public String beanName;
	public String dataClassName;
	public String errPromptMsg;
	public int valueActionNum = 0;
	public String panelTemplateName;
	public boolean isNotDigit = true;
	public boolean keyAction = false;
	public void getSelfAttribute(Element e1){
		String str;
		super.getSelfAttribute(e1);
		panelTemplateName=e1.attributeValue("panelTemplateName");
		beanName = e1.attributeValue("beanName");
		dataClassName = e1.attributeValue("dataClassName");
		errPromptMsg = e1.attributeValue("errPromptMsg");
		str = e1.attributeValue("valueActionNum");
		if(str == null) {
			valueActionNum = -1;
		}else 
			valueActionNum = Integer.parseInt(str);
		str = e1.attributeValue("isNotDigit");
		if(str != null && str.equals("false")) {
			isNotDigit = false;
		}else 
			isNotDigit = true;
		str = e1.attributeValue("keyAction");
		if(str != null && str.equals("true")) {
			keyAction = true;
		}else 
			keyAction = false;
		
	}
	public void setExtendAttributeByParasMap(HashMap<String, String>map){
		super.setExtendAttributeByParasMap(map);
		String str = map.get("valueActionNum");
		if(str != null)
			valueActionNum = Integer.parseInt(str);
		str =  map.get("isNotDigit");
		if(str != null && str.equals("false")) {
			isNotDigit = false;
		}else 
			isNotDigit = true;
		beanName = map.get("beanName");
		dataClassName = map.get("dataClassName");
		errPromptMsg = map.get("errPromptMsg");
	}

}
