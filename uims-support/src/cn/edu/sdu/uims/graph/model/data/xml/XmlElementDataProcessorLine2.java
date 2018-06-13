package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;

import cn.edu.sdu.uims.graph.model.GElementLine2;
import cn.edu.sdu.uims.trans.UFPoint;

public class XmlElementDataProcessorLine2 extends XmlElementDataProcessor {
	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double dx1, dy1, dx2, dy2;
		GElementLine2 ge = (GElementLine2) gElement;
		super.getAttributeFromData(xpoint, ypoint, sx, sy, ra);
		dx1 = (xmlElement.attributeValue("x1") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("x1")));
		dy1 = (xmlElement.attributeValue("y1") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("y1")));
		dx2 = (xmlElement.attributeValue("x2") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("x2")));
		dy2 = (xmlElement.attributeValue("y2") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("y2")));
		ge.p1.x = xpoint + dx1;
		ge.p1.y = ypoint + dy1;
		ge.p2.x = xpoint + dx2;
		ge.p2.y = ypoint + dy2;
	}

	public void setAttributeValueToData(HashMap map) {
		Attribute attribute = null;
		super.setAttributeValueToData(map);
		Double dx1, dy1, dx2, dy2;
		UFPoint point = null;
		point = (UFPoint) map.get("p1");
		if (point != null) {
			dx1 = point.x;
			attribute = xmlElement.attribute("x1");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("x1", dx1.toString());
			attribute = null;

			dy1 = point.y;
			attribute = xmlElement.attribute("y1");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("y1", dy1.toString());
			attribute = null;
		}

		point = (UFPoint) map.get("p2");
		if (point != null) {
			dx2 = point.x;
			attribute = xmlElement.attribute("x2");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("x2", dx2.toString());
			attribute = null;

			dy2 = point.y;
			attribute = xmlElement.attribute("y2");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("y2", dy2.toString());
			attribute = null;
		}
	}

}
