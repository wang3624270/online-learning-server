package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;

import cn.edu.sdu.uims.trans.UFPoint;

public class XmlElementDataProcessorNote extends XmlElementDataProcessor {

	public void getAttributeFromData() {
		getAttributeFromData(0, 0, 1, 1, 0);
	}

	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
	}

	public void setAttributeValueToData(HashMap map) {
		super.setAttributeValueToData(map);
		Attribute attribute = null;
		UFPoint point = (UFPoint) map.get("point");
		if (point != null) {
			Double dx = point.x;
			Double dy = point.y;
			attribute = xmlElement.attribute("x");
			if (attribute != null)
				xmlElement.remove(attribute);

			xmlElement.addAttribute("x", dx.toString());
			attribute = null;

			attribute = xmlElement.attribute("y");
			if (attribute != null)
				xmlElement.remove(attribute);

			xmlElement.addAttribute("y", dy.toString());
			attribute = null;
		}

		String imageName = (String) map.get("imageName");
		if (imageName != null) {
			attribute = xmlElement.attribute("imageName");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("imageName", imageName);
			attribute = null;
		}
	}
}
