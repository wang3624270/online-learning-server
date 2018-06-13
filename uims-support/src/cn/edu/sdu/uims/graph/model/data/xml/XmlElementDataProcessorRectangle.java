package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import cn.edu.sdu.uims.graph.model.GElementRectangle;
import cn.edu.sdu.uims.trans.UFRect;

public class XmlElementDataProcessorRectangle extends XmlElementDataProcessor {
	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra)  {
		double x, y, w, h;
		GElementRectangle ge= (GElementRectangle)gElement;
		super.getAttributeFromData(xpoint, ypoint,  sx,sy,ra); 
		x = (xmlElement.attributeValue("x") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("x")));
		y = (xmlElement.attributeValue("y") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("y")));
		w = (xmlElement.attributeValue("w") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("w")));
		h = (xmlElement.attributeValue("h") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("h")));
		ge.box = new UFRect(xpoint+x, ypoint+y, w,h);
	}
	public void setAttributeValueToData( HashMap map) {
	}
}
