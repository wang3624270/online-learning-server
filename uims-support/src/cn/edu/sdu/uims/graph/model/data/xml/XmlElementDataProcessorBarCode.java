package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;

import cn.edu.sdu.uims.graph.model.GElementBarCode;
import cn.edu.sdu.uims.trans.UFRect;

public class XmlElementDataProcessorBarCode extends XmlElementDataProcessor {
	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double dx1, dy1, w, h;
		String str;
		GElementBarCode ge = (GElementBarCode) gElement;
		super.getAttributeFromData(xpoint, ypoint, sx, sy, ra);
		ge.text = xmlElement.attributeValue("text");
		dx1 = (xmlElement.attributeValue("x") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("x")));
		dy1 = (xmlElement.attributeValue("y") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("y")));
		ge.box.x = xpoint + dx1;
		ge.box.y = ypoint + dy1;
		ge.box.w = (xmlElement.attributeValue("w") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("w")));
		ge.box.h = (xmlElement.attributeValue("h") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("h")));

	}

	/**
	 * 将类型为text的element属性值存放到xml中
	 */
	public void setAttributeValueToData(HashMap map) {
		super.setAttributeValueToData(map);
		Attribute attribute = null;
		String text = (String) map.get("text");
		if (text != null && !text.equals("")) {
			attribute = xmlElement.attribute("text");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("text", text);
		}

		UFRect rect = (UFRect) map.get("rect");
		if (rect != null) {
			double x = rect.x;
			attribute = xmlElement.attribute("x");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("x", new Double(x).toString());

			attribute = xmlElement.attribute("y");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("y", new Double(rect.y).toString());

			attribute = xmlElement.attribute("w");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("w", new Double(rect.w).toString());

			attribute = xmlElement.attribute("h");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("h", new Double(rect.h).toString());
		}

	}

}
