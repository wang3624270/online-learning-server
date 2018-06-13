package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;

import cn.edu.sdu.uims.graph.model.GElementHistogram;
import cn.edu.sdu.uims.trans.UFRect;

public class XmlElementDataProcessorHistogram extends XmlElementDataProcessor {
	public void getAttributeFromData( double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double dx1, dy1, x1, y1, w, h;
		GElementHistogram ge= (GElementHistogram)gElement;
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
		ge.xLabel = (xmlElement.attributeValue("xlabel") == null ? "" : xmlElement
				.attributeValue("xlabel"));
		ge.yLabel = (xmlElement.attributeValue("ylabel") == null ? "" : xmlElement
				.attributeValue("ylabel"));
		ge.orientation = (xmlElement.attributeValue("orientation") == null ? "vertical"
				: xmlElement.attributeValue("orientation"));
		ge.box = new UFRect(x1, y1, w, h);
	}
	public void setAttributeValueToData(HashMap map) {
		Attribute attribute = null;
		UFRect boxRect = (UFRect) map.get("box");
		String str = null;
		Double x1, y1, w, h;
		super.setAttributeValueToData( map);
		if (boxRect != null) {
			x1 = boxRect.x;
			y1 = boxRect.y;
			w = boxRect.w;
			h = boxRect.h;
			attribute = xmlElement.attribute("x");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("x", x1.toString());
			attribute = null;

			attribute = xmlElement.attribute("y");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("y", y1.toString());
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

		str = (String) map.get("label");
		if (str != null) {
			attribute = xmlElement.attribute("label");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("label", str);
			attribute = null;
		}

		str = (String) map.get("xlabel");
		if (str != null) {
			attribute = xmlElement.attribute("xlabel");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("xlabel", str);
			attribute = null;
		}

		str = (String) map.get("ylabel");
		if (str != null) {
			attribute = xmlElement.attribute("ylabel");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("ylabel", str);
			attribute = null;
		}

		str = (String) map.get("orientation");
		if (str != null) {
			attribute = xmlElement.attribute("orientation");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("orientation", str);
			attribute = null;
		}

	}


}
