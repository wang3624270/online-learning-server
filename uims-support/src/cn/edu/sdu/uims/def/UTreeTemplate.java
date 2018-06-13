package cn.edu.sdu.uims.def;

import org.dom4j.Element;

public class UTreeTemplate extends UElementTemplate {
	public boolean rootVisible = true;
	public void getSelfAttribute(Element e1){
		String str;
		name = e1.attributeValue("name");
		str = e1.attributeValue("rootVisible");
		if("false".equals(str))
			rootVisible = false;
	}

}
