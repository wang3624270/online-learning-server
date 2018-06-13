package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;

import cn.edu.sdu.uims.graph.model.GElementPie;
import cn.edu.sdu.uims.trans.UFRect;

public class XmlElementDataProcessorPie extends XmlElementDataProcessor {
	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double dx1, dy1, x1, y1, w, h;
		GElementPie ge = (GElementPie)gElement;
		super.getAttributeFromData(xpoint, ypoint,  sx,sy,ra); 
		dx1 = (xmlElement.attributeValue("x") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("x")));
		dy1 = (xmlElement.attributeValue("y") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("y")));
		x1 = xpoint + dx1;
		y1 = ypoint + dy1;
		w = (xmlElement.attributeValue("w") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("w")));
		h = (xmlElement.attributeValue("h") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("h")));
		ge.label = (xmlElement.attributeValue("label") == null ? "" : xmlElement
				.attributeValue("label"));
		ge.box = new UFRect(x1, y1, w, h);
	}
	public void setAttributeValueToData( HashMap map) {
		super.setAttributeValueToData(map);
		Attribute attribute = null;
		UFRect rect = (UFRect) map.get("box");
		double xpoint = (Double) map.get("xpoint");
		double ypoint = (Double) map.get("ypoint");
		Double dx1, dy1, w, h;
		if (rect != null) {
			dx1 = rect.x - xpoint;
			dy1 = rect.y - ypoint;
			w = rect.w;
			h = rect.h;
			attribute = xmlElement.attribute("x");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("x", dx1.toString());
			attribute = null;

			attribute = xmlElement.attribute("y");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("y", dy1.toString());
			attribute = null;

			attribute = xmlElement.attribute("w");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("w", w.toString());
			attribute = null;

			attribute = xmlElement.attribute("h");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("h", h.toString());
			attribute = null;
		}

		String str = (String) map.get("label");
		if (str != null) {
			attribute = xmlElement.attribute("label");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("label", str);
			attribute = null;
		}

	}

}
