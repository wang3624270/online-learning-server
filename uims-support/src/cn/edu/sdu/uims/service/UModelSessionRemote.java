package cn.edu.sdu.uims.service;

import java.util.HashMap;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UPointCursor;
import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.def.UDictionaryMappingTemplate;
import cn.edu.sdu.uims.def.UFilterTemplate;
import cn.edu.sdu.uims.def.UPromptTemplate;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.def.UClientFrameTemplate;
import cn.edu.sdu.uims.util.UimsUtils;

public class UModelSessionRemote extends UModelSession {

	// 从数据库里面取出来的menuList，现在暂时直接通过外面传进来，以后需要改成接口形式
	private HashMap<String, UColor> colorMap = new HashMap<String, UColor>();
	private HashMap<String, UFont> fontMap = new HashMap<String, UFont>();
	private HashMap<String, UBorder> borderMap = new HashMap<String, UBorder>();
	private HashMap<String, UFilterTemplate> filterTemplateMap = new HashMap<String, UFilterTemplate>();
	private HashMap<String, String> defaultClassMap = new HashMap<String, String>();
	private HashMap<String, UPointCursor> pointCursorMap = new HashMap<String, UPointCursor>();
	private HashMap<String, UStroke> strokeMap = new HashMap<String, UStroke>();
	private HashMap<String, HashMap> typeValueMap = new HashMap<String, HashMap>();
	private HashMap<String, UParagraphTemplate> paragraphTemplateMap = new HashMap<String, UParagraphTemplate>();
	private HashMap<String, UPaperTemplate> paperTemplateMap = new HashMap<String, UPaperTemplate>();
	private HashMap<String, UPromptTemplate> promptTemplateMap = new HashMap<String, UPromptTemplate>();
	private HashMap<String, String> defaultClassMapIntKey = new HashMap<String, String>();

	public UColor getColorByName(String name) {
		UColor color = colorMap.get(name);
		if (color != null)
			return color;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getColorByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return defaultColor;
		color = (UColor) respond.get(RmiKeyConstants.KEY_OBJECT);
		color.getObject();
		colorMap.put(name, color);
		return color;
	}

	public UFont getFontByName(String name) {
		UFont font = fontMap.get(name);
		if (font != null)
			return font;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getFontByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return defaultFont;
		font = (UFont) respond.get(RmiKeyConstants.KEY_OBJECT);
		font.getObject();
		fontMap.put(name, font);
		return font;
	}

	public UBorder getBorderByName(String name) {
		UBorder border = borderMap.get(name);
		if (border != null)
			return border;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getBorderByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return this.defaultBoundBorder;
		border = (UBorder) respond.get(RmiKeyConstants.KEY_OBJECT);
		border.getObject();
		borderMap.put(name, border);
		return border;
	}

	public UFilterTemplate getFilterTemplateByName(String name) {
		UFilterTemplate filter = filterTemplateMap.get(name);
		if (filter != null)
			return filter;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getFilterTemplateByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		filter = (UFilterTemplate) respond.get(RmiKeyConstants.KEY_OBJECT);
		filterTemplateMap.put(name, filter);
		return filter;
	}

	public String getDefaultClassByType(String type) {
		String str = defaultClassMap.get(type);
		if (str != null)
			return str;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, type);
		request.setCmd("getDefaultClassByType");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		str = (String) respond.get(RmiKeyConstants.KEY_OBJECT);
		defaultClassMap.put(type, str);
		return str;
	}
	public String getDefaultClassByTypeIntKey( String type) {
		String str = defaultClassMapIntKey.get(type);
		if (str != null)
			return str;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, type);
		request.setCmd("getDefaultClassByTypeIntKey");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		str = (String) respond.get(RmiKeyConstants.KEY_OBJECT);
		defaultClassMapIntKey.put(type, str);
		return str;
	}

	public UPointCursor getPointCursorByName(String name) {
		UPointCursor pointCursor = pointCursorMap.get(name);
		if (pointCursor != null)
			return pointCursor;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getPointCursorByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		pointCursor = (UPointCursor) respond.get(RmiKeyConstants.KEY_OBJECT);
		pointCursorMap.put(name, pointCursor);
		return pointCursor;
	}

	public UStroke getStrokeByName(String strokeName) {
		UStroke stroke = strokeMap.get(strokeName);
		if (stroke != null)
			return stroke;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, strokeName);
		request.setCmd("getStrokeByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		stroke = (UStroke) respond.get(RmiKeyConstants.KEY_OBJECT);
		stroke.MakeBasicStroke();
		strokeMap.put(strokeName, stroke);
		return stroke;
	}

	public int getTypeValue(String str, String key) {
		HashMap temp = null;
		String s;
		temp = typeValueMap.get(key);
		if (temp != null) {
			s = (String) temp.get(str);
			if (s != null)
				return Integer.parseInt(s);
		}
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, str);
		request.add(RmiKeyConstants.KEY_STRING1, key);
		request.setCmd("getTypeValue");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return -1;
		Integer ii = (Integer) respond.get(RmiKeyConstants.KEY_INTEGER);
		if (temp == null) {
			temp = new HashMap<String, String>();
			typeValueMap.put(key, temp);
		}
		temp.put(str, ii.toString());
		return ii;
	}

	public UParagraphTemplate getParagraphTemplateByName(String name) {
		UParagraphTemplate template = paragraphTemplateMap.get(name);
		if (template != null)
			return template;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getParagraphTemplateByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		template = (UParagraphTemplate) respond.get(RmiKeyConstants.KEY_OBJECT);
		paragraphTemplateMap.put(name, template);
		return template;
	}

	public UPaperTemplate getPaperTemplateByName(String name) {
		UPaperTemplate template = paperTemplateMap.get(name);
		if (template != null)
			return template;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getPaperTemplateByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		template = (UPaperTemplate) respond.get(RmiKeyConstants.KEY_OBJECT);
		paperTemplateMap.put(name, template);
		return template;
	}
	public UPromptTemplate getPromptTemplateByName(String name) {
		UPromptTemplate template = promptTemplateMap.get(name);
		if (template != null)
			return template;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getPromptTemplateByName");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		template = (UPromptTemplate) respond.get(RmiKeyConstants.KEY_OBJECT);
		promptTemplateMap.put(name, template);
		return template;
	}

	public List getTemplateNameList(String key){
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, key);
		request.setCmd("getTemplateNameList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
	}
	public Object getObjectFromServer() {
		RmiRequest request = new RmiRequest();
		request.setCmd("csLogin");
		return null;
	}

	public Object getTemplate(String type, String name) {
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, type);
		request.add(RmiKeyConstants.KEY_STRING1, name);
		request.setCmd("getTemplate");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		Object o = respond.get(RmiKeyConstants.KEY_OBJECT);
		if (respond.getErrorMsg() == null) {
			return o;
		}
		return null;
	}
	public Object getBsTemplate(String type, String name) {
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, type);
		request.add(RmiKeyConstants.KEY_STRING1, name);
		request.setCmd("getBsTemplate");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		Object o = respond.get(RmiKeyConstants.KEY_OBJECT);
		if (respond.getErrorMsg() == null) {
			return o;
		}
		return null;
	}

	public UClientFrameTemplate getClientFrameTemplate(String templateName) {
		UClientFrameTemplate clientFrameTemplate = null;
		RmiRequest request = new RmiRequest();
		request.add("templateName", templateName);
		request.setCmd("getClientFrameTemplate");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		clientFrameTemplate = (UClientFrameTemplate)respond.get(RmiKeyConstants.KEY_OBJECT);
		return clientFrameTemplate;
	}
	public HashMap getUserTaskMap(String name){
		HashMap map = null;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getUserTaskMap");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		map = (HashMap)respond.get(RmiKeyConstants.KEY_OBJECT);
		return map;
	}
	public EnvironmentTemplate getEnvironmentTemplate() {
		EnvironmentTemplate environmentTemplate = null;
		RmiRequest request = new RmiRequest();
		request.setCmd("getEnvironmentTemplate");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() != null)
			return null;
		environmentTemplate = (EnvironmentTemplate)respond.get(RmiKeyConstants.KEY_OBJECT);
		return environmentTemplate;
	}

	public List getTimeControlActionListByPanelName(String name, Integer[] sysRoleIds){
		RmiRequest request = new RmiRequest();
		request.setCmd("getTimeControlActionListByPanelName");
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.add(RmiKeyConstants.KEY_ARRAY, sysRoleIds);
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		return (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
	}
	
	public String getEnvironmentProperties(String name) {
		RmiRequest request = new RmiRequest();
		request.setCmd("getEnvironmentProperties");
		request.add(RmiKeyConstants.KEY_STRING, name);
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		return (String)respond.get(RmiKeyConstants.KEY_STRING);
	}
	public UDictionaryMappingTemplate getDictionaryMappingTemplateByName(String name) {
		RmiRequest request = new RmiRequest();
		request.setCmd("getDictionaryMappingTemplateByName");
		request.add(RmiKeyConstants.KEY_STRING, name);
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		return (UDictionaryMappingTemplate)respond.get(RmiKeyConstants.KEY_FORM);
	}


}
