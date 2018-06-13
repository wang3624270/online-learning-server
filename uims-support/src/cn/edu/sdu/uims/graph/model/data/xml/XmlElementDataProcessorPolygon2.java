package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;

import cn.edu.sdu.uims.graph.model.GElementPolygon2;
import cn.edu.sdu.uims.trans.UFPoint;

public class XmlElementDataProcessorPolygon2 extends XmlElementDataProcessor{
	public void getAttributeFromData( double xpoint, double ypoint, double sx,
			double sy, double ra) {
		super.getAttributeFromData(xpoint, ypoint,  sx,sy,ra);
		GElementPolygon2 ge = (GElementPolygon2)gElement;
		ge.pList = new ArrayList();
		UFPoint fp;
		Element pElement;
		double x1, y1;
		Iterator it8 = xmlElement.elementIterator("point");
		while (it8.hasNext()) {
			pElement = (Element) it8.next();
			fp = new UFPoint();
			x1 = (pElement.attributeValue("x") == null ? 0 : Double
					.parseDouble(pElement.attributeValue("x")));
			y1 = (pElement.attributeValue("y") == null ? 0 : Double
					.parseDouble(pElement.attributeValue("y")));
			fp.x = xpoint + x1;
			fp.y = ypoint + y1;
			ge.pList.add(fp);
		}
	}
	public void setAttributeValueToData(HashMap map) {
		super.setAttributeValueToData(map);
		GElementPolygon2 ge = (GElementPolygon2)gElement;
		ge.pList = (ArrayList) map.get("pointList");
		UFPoint fp = null;
		if (ge.pList != null && ge.pList.size() > 0) {
			for (int i = 0; i < ge.pList.size(); i++) {
				fp = (UFPoint) ge.pList.get(i);
				if (fp != null) {
					Double x1 = fp.x;
					Double y1 = fp.y;
					Element pointElement = xmlElement.addElement("point");
					pointElement.addAttribute("x", x1.toString());
					pointElement.addAttribute("y", y1.toString());
				}

			}
		}
	}

}
