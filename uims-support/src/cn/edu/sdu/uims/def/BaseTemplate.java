package cn.edu.sdu.uims.def;

import java.io.Serializable;

import org.dom4j.Element;

import cn.edu.sdu.uims.util.UimsUtils;

public class BaseTemplate implements Cloneable, Serializable {
	protected String id;
	protected String name;
	protected String style;
	protected String className;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void getAttribute(Element e) {
		id = e.attributeValue("id");
		name = e.attributeValue("name");
		if (e.attribute("className") != null
				&& !e.attributeValue("className").equals(""))
			className = e.attributeValue("className");
		if (name == null && id != null)
			name = id;
		if (id == null && name != null)
			id = name;
		style = e.attributeValue("style");
		style = UimsUtils.getStyleValue(style);
	}
	public int geIntValueFromXml(Element e, String name,int defaultValue){
		String str = e.attributeValue(name);
		if(str == null || str.length() == 0){
			return defaultValue;
		}else {
			return Integer.parseInt(str);
		}
	}
}
