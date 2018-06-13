package cn.edu.sdu.uims.graph.model.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.uims.graph.model.GElement;

public class ElementDataProcessor implements ElementDataProcessorI, Serializable {
	protected GElement gElement;

	public void getAttributeFromData() {

	}

	public void getAttributeFromData(double xpoint, double ypoint, double sx,
			double sy, double ra) {

	}

	public void setElement(Element e) {
		// TODO Auto-generated method stub

	}

	public void setGElement(GElement ge) {
		// TODO Auto-generated method stub
		gElement = ge;
	}

	public void setAttributeValueToData(HashMap map) {
		// TODO Auto-generated method stub

	}

	public Element getElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public GElement getGElement() {
		// TODO Auto-generated method stub
		return gElement;
	}

	public void deleteNoteElement(GElement e) {
		// TODO Auto-generated method stub

	}


	@Override
	public List getNoteListFromXml(GElement ge, Element elementModel) {
		// TODO Auto-generated method stub
		return null;
	}

}
