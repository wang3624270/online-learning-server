package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UWebViewTemplate extends UElementTemplate {
	public String url; 
	public void getSelfAttribute(Element e1){
		url = e1.attributeValue("url");
	}
}
