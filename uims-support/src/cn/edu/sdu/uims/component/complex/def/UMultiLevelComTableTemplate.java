package cn.edu.sdu.uims.component.complex.def;

import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UMultiLevelComTableTemplate extends UElementTemplate {
	public  int boundw = 2;
	public int comUnitWidth=150,comUnitHeight=30;
	public List<UMultiLevelComColumnTemplate> colList;
	
	public void getSelfAttribute(Element e1){
		String str;		
		str = e1.attributeValue("comUnitWidth");
		if(str != null && !str.equals("")) {
			comUnitWidth= Integer.parseInt(str);
		}
		str = e1.attributeValue("comUnitHeight");
		if(str != null && !str.equals("")) {
			comUnitHeight= Integer.parseInt(str);
		}
		str = e1.attributeValue("boundw");
		if(str != null && !str.equals("")) {
			boundw= Integer.parseInt(str);
		}
	}
}
