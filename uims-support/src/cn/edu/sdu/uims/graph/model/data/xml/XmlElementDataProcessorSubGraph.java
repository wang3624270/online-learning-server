package cn.edu.sdu.uims.graph.model.data.xml;

import cn.edu.sdu.uims.graph.model.GElementSubGraph;

public class XmlElementDataProcessorSubGraph extends XmlElementDataProcessor {
	public void getAttributeFromData( double xpoint, double ypoint, double sx,
			double sy, double ra) {
		double sxo = 0, syo = 0;
		String sstr;
		
		GElementSubGraph ge = (GElementSubGraph)gElement;
		super.getAttributeFromData(xpoint, ypoint,  sx,sy,ra); 
		ge.subGraphName = xmlElement.attributeValue("subName");
		
		sstr = xmlElement.attributeValue("xo");
		if (sstr != null)
			sxo = Double.parseDouble(sstr);
		sstr = xmlElement.attributeValue("yo");
		if (sstr != null)
			syo = Double.parseDouble(sstr);
		ge.po.x = xpoint + sxo;
		ge.po.y = ypoint + syo;
		sstr = xmlElement.attributeValue("sx");
		if (sstr != null)
			sx = Double.parseDouble(sstr);
		sstr = xmlElement.attributeValue("sy");
		if (sstr != null)
			sy = Double.parseDouble(sstr);
		sstr = xmlElement.attributeValue("ra");
		if (sstr != null)
			ra = Double.parseDouble(sstr);
		sstr = xmlElement.attributeValue("dataDisp");
		if(sstr == null || sstr.equals("true")){
			ge.dataDisp = true;
		}
		else 
			ge.dataDisp = false;
	}

}
