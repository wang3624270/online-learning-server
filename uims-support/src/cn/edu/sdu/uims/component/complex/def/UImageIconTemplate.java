package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UImageIconTemplate extends UElementTemplate {
	public int colNum = -1, rowNum = -1;
	public int imageWidth = 150;
	public int imageHeight = 210;
	public int titileHeight = 25;
	public int intervalW  = 10;
	public int IntervalH = 10 ;
	public String titleLayout ="south"; 
	public int blankWidth = 10;
	public void getSelfAttribute(Element e1){
		String str;		
		str = e1.attributeValue("colNum");
		if(str != null && !str.equals("")) {
			colNum= Integer.parseInt(str);
		}
		str = e1.attributeValue("rowNum");
		if(str != null && !str.equals("")) {
			rowNum= Integer.parseInt(str);
		}
		str = e1.attributeValue("imageWidth");
		if(str != null && !str.equals("")) {
			imageWidth= Integer.parseInt(str);
		}
		str = e1.attributeValue("imageHeight");
		if(str != null && !str.equals("")) {
			imageHeight= Integer.parseInt(str);
		}
		str = e1.attributeValue("titileHeight");
		if(str != null && !str.equals("")) {
			titileHeight= Integer.parseInt(str);
		}
		str = e1.attributeValue("intervalW");
		if(str != null && !str.equals("")) {
			intervalW= Integer.parseInt(str);
		}
		str = e1.attributeValue("intervalW");
		if(str != null && !str.equals("")) {
			intervalW= Integer.parseInt(str);
		}
		str = e1.attributeValue("blankWidth");
		if(str != null && !str.equals("")) {
			blankWidth= Integer.parseInt(str);
		}
		str = e1.attributeValue("titleLayout");
		if(str!= null ){
			if( str.equals("null"))
				titleLayout = null;
			else
				titleLayout = str;
		}else
			titleLayout = "south";
	}	
	
}
