package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;

import cn.edu.sdu.uims.graph.model.GraphLayer;
import cn.edu.sdu.uims.graph.model.GraphModel2D;

public class XmlElementDataProcessorLayer extends XmlElementDataProcessor {

	public void getAttributeFromData() {
		getAttributeFromData(0, 0, 1, 1, 0);
	}

	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double x, y, w, h, dpi;
		boolean display;
		String graph2dName;
		super.getAttributeFromData(xpoint, ypoint, sx, sy, ra);
		GraphLayer graphlayer = (GraphLayer) gElement;
		x = xmlElement.attribute("x") == null ? 0 : Double.parseDouble(xmlElement
				.attributeValue("x"));
		y = xmlElement.attribute("y") == null ? 0 : Double.parseDouble(xmlElement
				.attributeValue("y"));
		w = xmlElement.attribute("w") == null ? 595.27559f : Double
				.parseDouble(xmlElement.attributeValue("w"));
		h = xmlElement.attributeValue("h") == null ? 841.88976f : Double
				.parseDouble(xmlElement.attributeValue("h"));
		dpi = xmlElement.attributeValue("dpi") == null ? 72 : Double
				.parseDouble(xmlElement.attributeValue("dpi"));
		display = xmlElement.attributeValue("disp") == null ? true : Boolean
				.parseBoolean(xmlElement.attributeValue("disp"));
		graph2dName = xmlElement.attributeValue("graph2dName") == null ? null
				: xmlElement.attributeValue("graph2dName");
		Object obj = gElement.parent;
		if (obj != null && obj instanceof GraphModel2D) {
			GraphModel2D gm2d = (GraphModel2D) obj;
			graphlayer.setX(x);
			graphlayer.setY(y);
			graphlayer.setW(w);
			graphlayer.setH(h);
			graphlayer.setDpi(dpi);
			graphlayer.setVisible(display);
			graphlayer.setGraph2DName(graph2dName);
		}

	}

	public void setAttributeValueToData(HashMap map) {
		super.setAttributeValueToData(map);
		Attribute attribute = null;
		GraphLayer graphLayer = (GraphLayer) gElement;
		if (graphLayer != null) {
			attribute = xmlElement.attribute("x");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("x", new Double(graphLayer.getX())
					.toString());
			attribute = null;

			attribute = xmlElement.attribute("y");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("y", new Double(graphLayer.getY())
					.toString());
			attribute = null;

			attribute = xmlElement.attribute("w");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("w", new Double(graphLayer.getW())
					.toString());
			attribute = null;

			attribute = xmlElement.attribute("h");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("h", new Double(graphLayer.getH())
					.toString());
			attribute = null;

			attribute = xmlElement.attribute("dpi");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("dpi", new Double(graphLayer.getDpi())
					.toString());
			attribute = null;

			attribute = xmlElement.attribute("disp");
			if (attribute != null)
				xmlElement.remove(attribute);
			xmlElement.addAttribute("disp", new Boolean(graphLayer.isVisible())
					.toString());
			attribute = null;

			if (attribute != null) {
				attribute = xmlElement.attribute("graph2dName");
				if (attribute != null)
					xmlElement.remove(attribute);
				xmlElement.addAttribute("graph2dName", graphLayer.getGraph2DName());
				attribute = null;
			}

		}
	}
}
