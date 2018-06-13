package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UTreeListTemplate extends UElementTemplate {
	public static int DATA_DISPLAY_MODE_TREE = 1;
	public static int DATA_DISPLAY_MODE_LIST = 2;
	public static int DATA_DISPLAY_MODE_BOOT = 0;
	public int dispModel = 0;
	public String treeTitle = "分层";
	public String listTitle ="列表";
	public int treeLevel = -1;
	public void getSelfAttribute(Element e1){
		String str;
		name = e1.attributeValue("name");
		str =  e1.attributeValue("treeTitle");
		if(str != null && str.length() != 0)
			treeTitle = str;
		str =  e1.attributeValue("listTitle");
		if(str != null && str.length() != 0)
			listTitle = str;
		str =  e1.attributeValue("dispModel");
		dispModel = 0;
		if(str != null && str.length() != 0){
			if(str.equals("tree")) {
				dispModel = 1;
			}else if(str.equals("list")) {
				dispModel = 2;
			}
		}
		str =  e1.attributeValue("treeLevel");
		treeLevel = -1;
		if(str != null && str.length() != 0) {
			treeLevel = Integer.parseInt(str);
		}
	}	
}
