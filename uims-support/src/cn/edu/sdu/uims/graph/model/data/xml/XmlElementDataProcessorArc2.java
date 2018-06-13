package cn.edu.sdu.uims.graph.model.data.xml;

import cn.edu.sdu.uims.graph.model.GElementArc2;

public class XmlElementDataProcessorArc2 extends XmlElementDataProcessor {
	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double dxc, dyc;
		GElementArc2 ge= (GElementArc2)gElement;
		super.getAttributeFromData(xpoint, ypoint,  sx,sy,ra); 
		dxc = (xmlElement.attributeValue("xc") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("xc")));
		dyc = (xmlElement.attributeValue("yc") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("yc")));
		ge.ps.x = (xmlElement.attributeValue("xs") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("xs")));
		ge.ps.y = (xmlElement.attributeValue("ys") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("ys")));
		ge.pe.x = (xmlElement.attributeValue("se") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("se")));
		ge.pe.y = (xmlElement.attributeValue("ye") == null ? 0 : Double
				.parseDouble(xmlElement.attributeValue("ye")));
		ge.pc.x = sx * (new Double(Math.sin(ra))) * xpoint + dxc;
		ge.pc.y = sy * (new Double(Math.cos(ra))) * ypoint + dyc;

	}

}
