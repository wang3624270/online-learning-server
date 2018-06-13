package cn.edu.sdu.uims.def;

import org.dom4j.Element;

public class UComboBoxPanelTemplate extends UElementTemplate {	

	public String panelTemplateName;
	public void getSelfAttribute(Element e1) {
		panelTemplateName = e1.attributeValue("panelTemplateName");
	}
}
