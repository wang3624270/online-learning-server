package cn.edu.sdu.uims.def;

import org.dom4j.Element;

public class UGraphViewTemplate extends UElementTemplate {
	public String infoLayout = null;
	public int infoCharMax=128;
	public String infoColorName;
	public String infoFontName;
	public void getSelfAttribute(Element e1) {
		infoLayout = e1.attributeValue("infoLayout");
		String str = e1.attributeValue("infoCharMax");
		if(str != null && str.length()!=0)
			infoCharMax = Integer.parseInt(str);
		infoColorName = e1.attributeValue("infoColorName");
		infoFontName = e1.attributeValue("infoFontName");
	}
}
