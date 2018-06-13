package cn.edu.sdu.uims.def;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.flex.FHashMap;

public class UMenuPanelMapTemplate extends UTemplate {
	public String templateName;
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public UTemplate getOutDefineTemplate() {
		return outDefineTemplate;
	}
	public void setOutDefineTemplate(UTemplate outDefineTemplate) {
		this.outDefineTemplate = outDefineTemplate;
	}
	public FHashMap getParameterMap() {
		return parameterMap;
	}
	public void setParameterMap(FHashMap parameterMap) {
		this.parameterMap = parameterMap;
	}
	public FHashMap getReplaceMap() {
		return replaceMap;
	}
	public void setReplaceMap(FHashMap replaceMap) {
		this.replaceMap = replaceMap;
	}
	public UTemplate outDefineTemplate = null;
	public FHashMap parameterMap = new FHashMap();
	public FHashMap replaceMap = new FHashMap();
	
}
