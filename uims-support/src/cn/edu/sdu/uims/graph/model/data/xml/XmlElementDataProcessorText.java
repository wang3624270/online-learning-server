package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;

import cn.edu.sdu.uims.graph.model.GElementText;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFRect;

public class XmlElementDataProcessorText extends XmlElementDataProcessor {
	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double dx1, dy1, w, h;
		String str;
		GElementText ge = (GElementText) gElement;
		super.getAttributeFromData(xpoint, ypoint, sx, sy, ra);
		ge.fontName = xmlElement.attributeValue("font");
		ge.text = xmlElement.attributeValue("text");
		dx1 = (xmlElement.attributeValue("x") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("x")));
		dy1 = (xmlElement.attributeValue("y") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("y")));
		ge.po.x = xpoint + dx1;
		ge.po.y = ypoint + dy1;
		w = (xmlElement.attributeValue("w") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("w")));
		h = (xmlElement.attributeValue("h") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("h")));

		str = xmlElement.attributeValue("rotate");
		if(str != null)
			ge.rotate = Integer.parseInt(str);
		ge.box = new UFRect(ge.po.x, ge.po.y, w, h);
		str = xmlElement.attributeValue("horizontalAlignment");
		if (str != null) {
			ge.setHorizontalAlignment(UFactory.getModelSession().getTypeValue(str,
					"alignmentType"));
		} else {
			ge.setHorizontalAlignment(UFactory.getModelSession().getTypeValue("center",
					"alignmentType"));
		}
		str = xmlElement.attributeValue("verticalAlignment");
		if (str != null) {
			ge
					.setVerticalAlignment(UFactory.getModelSession().getTypeValue(str,
							"alignmentType"));
		} else {
			ge.setVerticalAlignment(UFactory.getModelSession().getTypeValue("center",
					"alignmentType"));
		}
		str = xmlElement.attributeValue("pen");
		if (!"null".equals(str)) {
			ge.setBordered(true);
		} else {
			ge.setBordered(false);
		}
	}

	/**
	 * 将类型为text的element属性值存放到xml中
	 */
	public void setAttributeValueToData(HashMap map) {
		super.setAttributeValueToData(map);
		Attribute attribute = null;
		String font = (String) map.get("font");
		if (font != null && !font.equals("")) {
			attribute = xmlElement.attribute("font");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("font", font);
		}
		String text = (String) map.get("text");
		if (text != null && !text.equals("")) {
			attribute = xmlElement.attribute("text");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("text", text);
		}
		Integer rotate = (Integer) map.get("rotate");
		if (rotate != null && !rotate.equals(0)) {
			attribute = xmlElement.attribute("rotate");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("rotate", rotate.toString());
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

		String horizontalAlignment = (String) map.get("horizontalAlignment");
		if (horizontalAlignment != null) {
			attribute = xmlElement.attribute("horizontalAlignment");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("horizontalAlignment", horizontalAlignment);
		}

		String verticalAlignment = (String) map.get("verticalAlignment");
		if (verticalAlignment != null) {
			attribute = xmlElement.attribute("verticalAlignment");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("verticalAlignment", verticalAlignment);
		}

		Boolean pen = (Boolean) map.get("pen");
		if (pen != null && pen == false) {
			attribute = xmlElement.attribute("pen");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("pen", "null");
		}
	}

}
