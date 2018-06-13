package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import cn.edu.sdu.uims.graph.model.GElement;
import cn.edu.sdu.uims.graph.model.Graph2D;
import cn.edu.sdu.uims.graph.model.data.ElementDataProcessorI;
import cn.edu.sdu.uims.graph.model.data.Graph2DDataProcessor;

public class XmlGraph2DDataProcessor extends Graph2DDataProcessor {
	protected Element xmlElement;

	public void setElement(Element e) {
		// TODO Auto-generated method stub
		xmlElement = e;
	}

	public Element getElement() {
		// TODO Auto-generated method stub
		return xmlElement;
	}

	public void getElementAddedAttributeFormClassName() {
		Graph2D ge = (Graph2D) gElement;
		if (xmlElement != null) {
			String elementAddedFormClassName = xmlElement
					.attributeValue("elementAddedFormClassName");
			if (elementAddedFormClassName != null) {
				ge.setElementAddedFormClassName(elementAddedFormClassName);
			}
		}
	}

	public void setElementAddedAttributeFormClassName() {
		Graph2D ge = (Graph2D) gElement;
		if (ge.elementAddedFormClassName != null) {
			Attribute attribute = xmlElement
					.attribute("elementAddedFormClassName");
			if (attribute != null) {
				xmlElement.remove(attribute);
			}
			xmlElement.addAttribute("elementAddedFormClassName",
					ge.elementAddedFormClassName);
		}
	}

	public void setObject() {
		Graph2D graph2D = (Graph2D) gElement;
		graph2D.setName(xmlElement.attributeValue("name"));
		List elementList = getElementListFormXML(graph2D, xmlElement);
		graph2D.setElementList(elementList);
		graph2D.makeAddedData();
	}

	public List getElementListFormXML(Graph2D graph2D, Element graph_model) {
		GElement gElement = null;
		ElementDataProcessorI gdProcessorI = null;

		Element column, row, element;
		double space = 0, dx = 0, dy = 0;
		Integer count = 1;
		double xpoint = 0, ypoint = 0, xinitial = 0, sxinitial, syinitial, rainitial;
		double sx = sxinitial = 1, sy = syinitial = 1, ra = rainitial = 0, dsx = 0, dsy = 0, dra = 0;
		ArrayList elementList = new ArrayList();
		Iterator it3 = graph_model.elementIterator("element");
		while (it3.hasNext()) {
			element = (Element) it3.next();
			gElement = newGraphElement(element);
			if (gElement != null) {
				gdProcessorI = this.getElementDataProcessor(gElement);
				gdProcessorI.setElement(element);
				gdProcessorI.getAttributeFromData(0, 0, 1, 1, 0);
				if (gdProcessorI instanceof XmlElementDataProcessor) {
					XmlElementDataProcessor xmlgdProcessorI = (XmlElementDataProcessor) gdProcessorI;
					xmlgdProcessorI.getAddedAtributeFormValue();
				}
				String str = element.attributeValue("index");
				if (str != null) {
					gElement.index = Integer.parseInt(str);
				} else {
					gElement.index = -1;
				}
				gElement.init();
				List noteList = gdProcessorI.getNoteListFromXml(gElement,
						element); // 获得element中的Note列表
				gElement.setListNode(noteList);
				elementList.add(gElement);
			}
		}
		it3 = graph_model.elementIterator("row");
		/** 表明采用的行是循环模式 */
		while (it3.hasNext()) {
			int index = 0;
			row = (Element) it3.next();
			if (row.attributeValue("count") != null
					&& !row.attributeValue("count").equals("")) {
				count = Integer.parseInt(row.attributeValue("count"));
			}
			if (row.attributeValue("space") != null
					&& !row.attributeValue("space").equals("")) {
				space = Double.parseDouble(row.attributeValue("space"));
			}
			if (row.attributeValue("xpoint") != null
					&& !row.attributeValue("xpoint").equals("")) {
				xpoint = xinitial = Double.parseDouble((row
						.attributeValue("xpoint")));
			}

			if (row.attributeValue("ypoint") != null
					&& !row.attributeValue("ypoint").equals("")) {
				ypoint = Double.parseDouble(row.attributeValue("ypoint"));
			}
			for (int n1 = 0; n1 < count; n1++) {
				Iterator it5 = row.elementIterator("column");
				while (it5.hasNext()) {
					column = (Element) it5.next();
					int columncount = 0;
					if (column.attributeValue("count") != null
							&& !column.attributeValue("count").equals("")) {
						columncount = Integer.parseInt(column
								.attributeValue("count"));
					}
					for (int n2 = 0; n2 < columncount; n2++) {
						/** *计算中心点的偏移量* */
						dx = 0;
						dy = 0;
						dsx = 0;
						dsy = 0;
						dra = 0;
						if (column.attributeValue("dx") != null
								&& !column.attributeValue("dx").equals("")) {
							dx = Double.parseDouble(column.attributeValue("dx"));
						}
						if (column.attributeValue("dy") != null
								&& !column.attributeValue("dy").equals("")) {
							dy = Double.parseDouble(column.attributeValue("dy"));
						}
						if (column.attributeValue("dsx") != null
								&& !column.attributeValue("dsx").equals("")) {
							dsx = Double
									.parseDouble(column.attributeValue("dsx"));
						}
						if (column.attributeValue("dsy") != null
								&& !column.attributeValue("dsy").equals("")) {
							dsy = Double
									.parseDouble(column.attributeValue("dsy"));
						}
						if (column.attributeValue("dra") != null
								&& !column.attributeValue("dra").equals("")) {
							dra = Double
									.parseDouble(column.attributeValue("dra"));
						}
						sx += dsx;
						sy += dsy;
						ra += dra;
						xpoint = sx * (new Double(Math.cos(ra))) * xpoint + dx;
						ypoint = sy * (new Double(Math.cos(ra))) * ypoint + dy;

						Iterator it7 = column.elementIterator("element");
						while (it7.hasNext()) {
							element = (Element) it7.next();
							gElement = newGraphElement(element);
							if (gElement != null) {
								gdProcessorI = this
										.getElementDataProcessor(gElement);
								gdProcessorI.setElement(element);
								gdProcessorI.getAttributeFromData(xpoint,
										ypoint, sx, sy, ra);
								if (gdProcessorI instanceof XmlElementDataProcessor) {
									XmlElementDataProcessor xmlgdProcessorI = (XmlElementDataProcessor) gdProcessorI;
									xmlgdProcessorI.getAddedAtributeFormValue();
								}
								String str = element.attributeValue("index");
								if (str != null) {
									if (str.equals("=iterator")) {
										gElement.index = index;
									} else {
										gElement.index = Integer.parseInt(str);
									}
								} else {
									gElement.index = -1;
								}
								List noteList = gdProcessorI
										.getNoteListFromXml(gElement, element); // 获得element中的Note列表
								gElement.setListNode(noteList);
								gElement.init();
								elementList.add(gElement);
								if(gElement.name !=null) {
									gElement.name = gElement.name + "_" + n1 + "_" + n2;
								}
							}
							index++;
						}
					}
				}
				xpoint = xinitial;
				ypoint += space;
				sx = sxinitial;
				sy = syinitial;
				ra = rainitial;
			}
		}
		it3 = graph_model.elementIterator("array");
		/** 表明采用的行是循环模式 */
		Element ea;
		String tempStr;
		while (it3.hasNext()) {
			int index = 0;
			int rowCount, colCount;
			ea = (Element) it3.next();
			tempStr = ea.attributeValue("xpoint");
			if (tempStr == null)
				xpoint = 0;
			else
				xpoint = Double.parseDouble(tempStr);
			tempStr = ea.attributeValue("ypoint");
			if (tempStr == null)
				ypoint = 0;
			else
				ypoint = Double.parseDouble(tempStr);
			tempStr = ea.attributeValue("rowCount");
			if (tempStr == null)
				rowCount = 1;
			else
				rowCount = Integer.parseInt(tempStr);
			tempStr = ea.attributeValue("colCount");
			if (tempStr == null)
				colCount = 1;
			else
				colCount = Integer.parseInt(tempStr);
			tempStr = ea.attributeValue("dx");
			if (tempStr == null)
				dx = 0;
			else
				dx = Double.parseDouble(tempStr);
			tempStr = ea.attributeValue("dy");
			if (tempStr == null)
				dy = 0;
			else
				dy = Double.parseDouble(tempStr);
			int k1, k2;
			Iterator it7 = ea.elementIterator("element");
			while (it7.hasNext()) {
				element = (Element) it7.next();
				for (k1 = 0; k1 < rowCount; k1++) {
					for (k2 = 0; k2 < colCount; k2++) {
						gElement = newGraphElement(element);
						if (gElement != null) {
							gdProcessorI = this
									.getElementDataProcessor(gElement);
							gdProcessorI.setElement(element);
							gdProcessorI.getAttributeFromData(xpoint + k2 * dx,
									ypoint + k1 * dy, 1.0f, 1.0f, 0f);
							if (gdProcessorI instanceof XmlElementDataProcessor) {
								XmlElementDataProcessor xmlgdProcessorI = (XmlElementDataProcessor) gdProcessorI;
								xmlgdProcessorI.getAddedAtributeFormValue();
							}
							String str = element.attributeValue("index");
							if (str != null) {
								if (str.equals("=iterator")) {
									gElement.index = k1 * colCount + k2;
								} else {
									gElement.index = Integer.parseInt(str);
								}
							} else {
								gElement.index = -1;
							}
							List noteList = gdProcessorI.getNoteListFromXml(
									gElement, element); // 获得element中的Note列表
							gElement.setListNode(noteList);
							gElement.init();
							elementList.add(gElement);
							if(gElement.name !=null) {
								gElement.name = gElement.name + "_" + k1 + "_" + k2;
							}
						}
					}
				}
			}
		}
		return elementList;
	}

	public ElementDataProcessorI getElementDataProcessor(GElement gElement) {
		String str = gElement.getClass().getName();
		ElementDataProcessorI p = null;
		int index = str.indexOf("GElement");
		if (index < 0)
			return null;
		String className = "cn.edu.sdu.uims.graph.model.data.xml.XmlElementDataProcessor"
				+ str.substring(index + 8, str.length());
		try {
			p = (ElementDataProcessorI) Class.forName(className).newInstance();
			gElement.setDataProcessor(p);
			p.setGElement(gElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public GElement newGraphElement(Element element) {
		Graph2D graph2d = (Graph2D) gElement;
		GElement ge = null;
		String className = null;
		String type = null;
		type = element.attributeValue("type");
		if (type == null)
			return null;
		className = element.attributeValue("className");
		ge = graph2d.newGraphElement(type, className);
		return ge;
	}

	public GElement insertNewElement(String type, HashMap map) {
		Graph2D graph2d = (Graph2D) gElement;
		ElementDataProcessorI gdProcessorI = null;
		Element element = xmlElement.addElement("element");
		if (element == null)
			return null;
		element.addAttribute("type", type);
		GElement ge = newGraphElement(element);
		if (ge != null) {
			gdProcessorI = this.getElementDataProcessor(ge);
			gdProcessorI.setElement(element);
			gdProcessorI.setAttributeValueToData(map);
			gdProcessorI.getAttributeFromData();
			graph2d.elementList.add(ge);
		}
		return ge;
	}

	public void deleteElement(GElement e) {
		// TODO Auto-generated method stub
		xmlElement.remove(e.getDataProcessor().getElement());
		ElementDataProcessorI gdProcessorI = e.getDataProcessor();
		gdProcessorI.getAttributeFromData();
	}

	public void modifyElement(GElement ge, HashMap map) {
		// TODO Auto-generated method stub
		ElementDataProcessorI gdProcessorI = ge.getDataProcessor();
		gdProcessorI.setAttributeValueToData(map);
		gdProcessorI.getAttributeFromData();
	}

}
