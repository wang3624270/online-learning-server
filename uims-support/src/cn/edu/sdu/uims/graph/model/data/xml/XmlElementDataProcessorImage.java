package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;

import cn.edu.sdu.uims.graph.model.GElementImage;
import cn.edu.sdu.uims.trans.UFRect;

public class XmlElementDataProcessorImage extends XmlElementDataProcessor {
	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double dx1, dy1, x1, y1, w, h;
		String str;
		GElementImage ge = (GElementImage) gElement;
		super.getAttributeFromData(xpoint, ypoint, sx, sy, ra);
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
		ge.box = new UFRect(x1, y1, w, h);
		str = xmlElement.attributeValue("pen");
		if (!"null".equals(str)) {
			ge.setBordered(true);
		} else {
			ge.setBordered(false);
		}
		String imageUrl = xmlElement.attributeValue("imageUrl");
		if (imageUrl != null) {
			ge.setImageUrl(imageUrl);
		}
	}

	public void setAttributeValueToData(HashMap map) {
		Double dx1, dy1, w, h;
		super.setAttributeValueToData(map);
		double xpoint = (Double) map.get("xpoint");
		double ypoint = (Double) map.get("ypoint");
		UFRect box = (UFRect) map.get("rect");
		String imageUrl = (String) map.get("imageUrl");
		Attribute attribute = null;
		if (box != null) {
			dx1 = box.x - xpoint;
			dy1 = box.y - ypoint;
			w = box.w;
			h = box.h;
			if (dx1 != null) {
				attribute = xmlElement.attribute("x");
				if (attribute != null) {
					xmlElement.remove(attribute);
				}
				xmlElement.addAttribute("x", dx1.toString());
				attribute = null;
			}
			if (dy1 != null) {
				attribute = xmlElement.attribute("y");
				if (attribute != null) {
					xmlElement.remove(attribute);
				}
				xmlElement.addAttribute("y", dy1.toString());
				attribute = null;
			}
			if (w != null) {
				attribute = xmlElement.attribute("w");
				if (attribute != null) {
					xmlElement.remove(attribute);
				}
				xmlElement.addAttribute("w", w.toString());
				attribute = null;
			}
			if (h != null) {
				attribute = xmlElement.attribute("h");
				if (attribute != null) {
					xmlElement.remove(attribute);
				}
				xmlElement.addAttribute("h", h.toString());
				attribute = null;
			}
		}
		if (imageUrl != null) {
			attribute = xmlElement.attribute("imageUrl");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("imageUrl", imageUrl);
			attribute = null;
		}
		if (!((GElementImage) gElement).bordered) {
			attribute = xmlElement.attribute("pen");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("pen", "null");
		}
	}

}
