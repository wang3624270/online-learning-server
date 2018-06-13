package cn.edu.sdu.uims.graph.model.data;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.uims.graph.model.GElement;

public interface ElementDataProcessorI {
	public void setElement(Element e);

	public void setGElement(GElement ge);

	public void getAttributeFromData();

	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra);

	public void setAttributeValueToData(HashMap map);

	public GElement getGElement();

	public Element getElement();


	public void deleteNoteElement(GElement e);

	public List getNoteListFromXml(GElement ge,Element element_model);
}
