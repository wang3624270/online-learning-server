package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UWebAppTemplate extends UElementTemplate {
	public boolean isUserNavigation = true;
	public boolean isClientUser = true; 
	public boolean isMenuCommand = false;
	public String webServer =  null;
	public String  defaultUrl = null;
	public String requestCmd = null;
	
	public void getSelfAttribute(Element e1){
		String str;
		webServer = e1.attributeValue("webServer");
		defaultUrl = e1.attributeValue("defaultUrl");
		str = e1.attributeValue("isUserNavigation");
		if(str != null && str.equals("false"))
			isUserNavigation = false;
		str = e1.attributeValue("isClientUser");
		if(str != null && str.equals("false"))
			isClientUser = false;
		str = e1.attributeValue("isMenuCommand");
		if(str != null && str.equals("true"))
			isMenuCommand = true;
		requestCmd = e1.attributeValue("requestCmd");
	}

}
