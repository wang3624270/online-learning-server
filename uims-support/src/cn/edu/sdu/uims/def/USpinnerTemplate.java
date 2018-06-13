package cn.edu.sdu.uims.def;

import org.dom4j.Element;

public class USpinnerTemplate extends UElementTemplate {
	public int value = 0;
	public int minimum = -100;
	public int maximum = 100;
	public int stepSize = 1;	
	public String listStr;
	
	public void getSelfAttribute(Element e1){
		String str;
		str = e1.attributeValue("value");
		if(str != null)
			value = Integer.parseInt(str);
		str = e1.attributeValue("minimum");
		if(str != null)
			minimum = Integer.parseInt(str);
		str = e1.attributeValue("maximum");
		if(str != null)
			maximum = Integer.parseInt(str);
		str = e1.attributeValue("stepSize");
		if(str != null)
			stepSize = Integer.parseInt(str);
		listStr = e1.attributeValue("listStr");
	}

}
