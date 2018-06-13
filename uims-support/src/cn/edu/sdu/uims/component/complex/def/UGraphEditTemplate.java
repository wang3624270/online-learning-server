package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UGraphEditTemplate extends UElementTemplate {
	public String commandBarName;
	public String toolBarName;
	public String infoPanelName;
	public String attributePanelName;
	public String userTaskGroupName;
	public String defaultUserTaskName;
	public String defaultMethodName;
	public void getSelfAttribute(Element e1){
		commandBarName = e1.attributeValue("commandBarName");
		toolBarName = e1.attributeValue("toolBarName");
		infoPanelName = e1.attributeValue("infoPanelName");
		attributePanelName = e1.attributeValue("attributePanelName");
		userTaskGroupName = e1.attributeValue("userTaskGroupName");
		defaultUserTaskName = e1.attributeValue("defaultUserTaskName");
		defaultMethodName = e1.attributeValue("defaultMethodName");
	}	
}
