package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UPageImageViewTemplate extends UElementTemplate {
	public boolean isPages = true;
	public  int colNum = 10;
	public int rowNum = 1;
	public int imgW = 100;
	public int imgH = 160;
	public int textW = 0;
	public int textH = 60;
	public int DW  = 20;
	public int DH = 20;
	public int B = 5;
	public String labelDrawClass;
	public void getSelfAttribute(Element e1){
		String str;		
		labelDrawClass = e1.attributeValue("labelDrawClass");
		str = e1.attributeValue("colNum");
		if(str != null && !str.equals("")) {
			colNum= Integer.parseInt(str);
		}
		str = e1.attributeValue("rowNum");
		if(str != null && !str.equals("")) {
			rowNum= Integer.parseInt(str);
		}
		str = e1.attributeValue("imgW");
		if(str != null && !str.equals("")) {
			imgW= Integer.parseInt(str);
		}
		str = e1.attributeValue("imgH");
		if(str != null && !str.equals("")) {
			imgH= Integer.parseInt(str);
		}
		str = e1.attributeValue("textW");
		if(str != null && !str.equals("")) {
			textW= Integer.parseInt(str);
		}
		str = e1.attributeValue("textH");
		if(str != null && !str.equals("")) {
			textH= Integer.parseInt(str);
		}
		str = e1.attributeValue("DW");
		if(str != null && !str.equals("")) {
			DW= Integer.parseInt(str);
		}
		str = e1.attributeValue("DH");
		if(str != null && !str.equals("")) {
			DH= Integer.parseInt(str);
		}
		str = e1.attributeValue("B");
		if(str != null && !str.equals("")) {
			B= Integer.parseInt(str);
		}
		str = e1.attributeValue("isPages");
		if(str != null && str.equals("false")) {
			isPages = false;
		}
		
		 
	}
}
