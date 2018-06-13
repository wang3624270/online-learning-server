package cn.edu.sdu.uims.def;

import org.dom4j.Element;

public class UButtonTemplate extends UElementTemplate {
	public String cmd;
	public String content;
	public String enContent;
	public String selectedIconName;
	public String pressedIconName;
	public String userTaskName;
	public String methodName;
	public String uiTemplateName;
	public String urlLink;
	public String target;
	public int groupHeight;
	public boolean groupOpened;
	public String getUrlLink() {
		return urlLink;
	}
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getEnContent() {
		return enContent;
	}
	public void setEnContent(String enContent) {
		this.enContent = enContent;
	}
	public String getSelectedIconName() {
		return selectedIconName;
	}
	public void setSelectedIconName(String selectedIconName) {
		this.selectedIconName = selectedIconName;
	}
	public String getPressedIconName() {
		return pressedIconName;
	}
	public void setPressedIconName(String pressedIconName) {
		this.pressedIconName = pressedIconName;
	}
	public String getUserTaskName() {
		return userTaskName;
	}
	public void setUserTaskName(String userTaskName) {
		this.userTaskName = userTaskName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getUiTemplateName() {
		return uiTemplateName;
	}
	public void setUiTemplateName(String uiTemplateName) {
		this.uiTemplateName = uiTemplateName;
	}
	public void getSelfAttribute(Element e1){
		name = e1.attributeValue("name");
		content = e1.attributeValue("content");
		enContent = e1.attributeValue("enContinue");
		cmd = e1.attributeValue("cmd");
		if(cmd == null){
			cmd = name;
		}
		selectedIconName = e1.attributeValue("selectedIcon");
		pressedIconName = e1.attributeValue("pressedIcon");
		userTaskName = e1.attributeValue("userTaskName");
		methodName = e1.attributeValue("methodName");
		uiTemplateName = e1.attributeValue("uiTemplateName");
		urlLink = e1.attributeValue("urlLink");
		String str = e1.attributeValue("groupHeight");
		if(str != null && str.length() != 0) {
			groupHeight = Integer.parseInt(str);
		}
		str = e1.attributeValue("groupOpened");
		if(str != null && str.equals("true")){
			groupOpened = true;
		}else {
			groupOpened = false;
		}
	}	
}
