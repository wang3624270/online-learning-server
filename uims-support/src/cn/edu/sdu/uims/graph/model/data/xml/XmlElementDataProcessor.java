package cn.edu.sdu.uims.graph.model.data.xml;

import java.util.HashMap;

import org.dom4j.Attribute;
import org.dom4j.Element;

import cn.edu.sdu.uims.graph.form.AddedAttributeForm;
import cn.edu.sdu.uims.graph.model.GElement;
import cn.edu.sdu.uims.graph.model.Graph2D;
import cn.edu.sdu.uims.graph.model.data.ElementDataProcessor;

public class XmlElementDataProcessor extends ElementDataProcessor {
	protected Element xmlElement;


	public void setElement(Element e) {
		// TODO Auto-generated method stub
		xmlElement = e;
	}

	public Element getElement() {
		// TODO Auto-generated method stub
		return xmlElement;
	}

	public void getAttributeFromData() {
		getAttributeFromData(0, 0, 1, 1, 0);
	}

	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {
		gElement.name = xmlElement.attributeValue("name");
		gElement.colorName = xmlElement.attributeValue("color");
		gElement.strokeName = xmlElement.attributeValue("stroke");
		gElement.attributeName = xmlElement.attributeValue("attributeName");
	}

	public void getAddedAtributeFormValue() {
		String className = xmlElement
				.attributeValue("elementAddedFormClassName");
		if (className == null) {
			if (gElement.parent instanceof Graph2D) {
				((XmlGraph2DDataProcessor) ((Graph2D) gElement.parent)
						.getDataProcessor())
						.getElementAddedAttributeFormClassName();
				className = ((Graph2D) gElement.parent)
						.getElementAddedFormClassName();
			}
		}
		if (className != null && !className.equals("")) {
			try {
				gElement.addedAttributeForm = (AddedAttributeForm) Class
						.forName(className).newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (xmlElement.attributeValue("id") != null) {
				gElement.id = xmlElement.attributeValue("id");
				gElement.addedAttributeForm.getAddedAttributeById(Integer.parseInt(gElement.id));
			}
		} else
			gElement.addedAttributeForm = null;

	}

	public void setAddedAttributeFormValue() {
		if (gElement.addedAttributeForm != null) {
			String className = gElement.addedAttributeForm.getClass().getName();

			if (className != null
					&& !className.equalsIgnoreCase(((Graph2D) gElement.parent)
							.getElementAddedFormClassName()))
				xmlElement.addAttribute("elementAddedFormClassName", className);
			Integer addedAttributeFormId = gElement.addedAttributeForm
					.getAddedAttributeFormId();
			if (addedAttributeFormId != null) {
				xmlElement.addAttribute("id", addedAttributeFormId.toString());
			}
		}
	}

	public void setAttributeValueToData(HashMap map) {
		if (map != null) {
			String str;
			Attribute attribute = null;
			str = (String) map.get("color");
			if (str != null) {
				attribute = xmlElement.attribute("color");
				if (attribute != null) {
					xmlElement.remove(attribute);
				}
				xmlElement.addAttribute("color", str);
				attribute = null;
			}
			str = (String) map.get("stroke");
			if (str != null) {
				attribute = xmlElement.attribute("stroke");
				if (attribute != null) {
					xmlElement.remove(attribute);
				}
				xmlElement.addAttribute("stroke", str);
				attribute = null;
			}
			str = (String) map.get("attributeName");
			if (str != null) {
				attribute = xmlElement.attribute("attributeName");
				if (attribute != null) {
					xmlElement.remove(attribute);
				}
				xmlElement.addAttribute("attributeName", str);
				attribute = null;
			}
		}
	}

	/**
	 * 添加标注节点
	 */

	/**
	 * 删除标注节点
	 */
	public void deleteNoteElement(GElement e) {
		if (e.getDataProcessor() != null) {
			xmlElement.remove(e.getDataProcessor().getElement());
			gElement.listNote.remove(e);
		}
	}
}
