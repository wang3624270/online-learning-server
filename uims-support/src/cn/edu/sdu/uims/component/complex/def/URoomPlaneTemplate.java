package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class URoomPlaneTemplate extends UElementTemplate {
	public boolean displayBuildingName = false;
	public boolean buildingCanSelect = false;
	public String title;
	public String bedLabel;
	public String perLabel;
	public int roomColumn = 6;

	public void getSelfAttribute(Element e1) {
		String str;
		str = e1.attributeValue("displayBuildingName");
		if (str != null && str.equals("true"))
			displayBuildingName = true;
		else
			displayBuildingName =false;
		str = e1.attributeValue("buildingCanSelect");
		if (str != null && str.equals("true"))
			buildingCanSelect = true;
		else
			buildingCanSelect =false;
		title = e1.attributeValue("title");
		bedLabel = e1.attributeValue("bedLabel");
		if(bedLabel == null)
			bedLabel = "床位数";
		perLabel = e1.attributeValue("perLabel");
		if(perLabel == null)
			perLabel = "人数";
		str = e1.attributeValue("roomColumn");
		if(str != null)
			roomColumn = new Integer(str);
	}

}
