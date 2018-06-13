package cn.edu.sdu.uims.service;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;

import org.dom4j.Element;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import com.itextpdf.awt.DefaultFontMapper;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UPointCursor;
import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.base.UUIStyle;
import cn.edu.sdu.uims.component.menu.UMenuInfo;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UButtonTemplate;
import cn.edu.sdu.uims.def.UDictionaryMappingTemplate;
import cn.edu.sdu.uims.def.UFilterTemplate;
import cn.edu.sdu.uims.def.UMenuItemTemplate;
import cn.edu.sdu.uims.def.UMenuTemplate;
import cn.edu.sdu.uims.def.UPromptTemplate;
import cn.edu.sdu.uims.def.dataexport.DataExportTemplate;
import cn.edu.sdu.uims.def.dataimport.DataImportTemplate;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.def.UClientFrameTemplate;
import cn.edu.sdu.uims.graph.model.GraphModel2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.view.UBasicStroke;
import cn.edu.sdu.uims.print.photo.PhotoLayoutTemplate;
import cn.edu.sdu.uims.util.UimsUtils;



public class UModelSession implements UModelSessionI {

	protected UFont defaultFont = new UFont("宋体", Font.PLAIN, 12,null);

	protected UColor defaultColor = new UColor(0, 0, 0);

	protected UColor defaultBgColor = new UColor(255, 255, 255);

	protected UBorder defaultStrokeBorder = null;

	protected UBorder defaultBoundBorder = null;

	protected UPointCursor defaultPointCursor = null;

	protected UUIStyle defaultUIStyle = null;

	protected HashMap<String, GraphModelI> graphModelMap = new HashMap<String, GraphModelI>();

	public UModelSession() {
		defaultFont = new UFont("宋体", Font.PLAIN, 12,null);
		defaultFont.getObject();
		defaultColor = new UColor(0, 0, 0);
		defaultColor.getObject();
		defaultBgColor = new UColor(255, 255, 255);
		defaultBgColor.getObject();

	}

	public UColor getDefaultColor() {
		return defaultColor;
	}

	public UColor getDefaultBgColor() {
		return defaultBgColor;
	}

	public UFont getDefaultFont() {
		return defaultFont;
	}

	public UBorder newBorder(String type, int layout, int width, float[] dash,
			String colorName) {
		UBorder border = new UBorder();
		border.width = width;
		border.layout = layout;
		border.dash = dash;
		border.colorName = colorName;
		if (type == null || type.equals("stroke")) {
			border.obj = new UBasicStroke(width, UBasicStroke.CAP_ROUND,
					UBasicStroke.JOIN_ROUND, 10, dash, 0);
		} else {
			UColor color = getColorByName(border.colorName);
			border.obj = BorderFactory.createLineBorder(color.color, width);
		}
		return border;
	}

	public UBorder getDefaultStrokeBorder() {
		if (defaultStrokeBorder == null) {
			defaultStrokeBorder = newBorder("stroke",
					UConstants.BORDER_LAYOUT_ALL, 1, null, null);
		}
		return defaultStrokeBorder;
	}

	public UBorder getDefaultBoundBorder() {
		if (defaultBoundBorder == null) {
			defaultBoundBorder = newBorder("bound",
					UConstants.BORDER_LAYOUT_ALL, 1, null, null);
		}
		return defaultBoundBorder;
	}

	public UBorder getBorderByName(String name) {
		return null;
	}

	public UPointCursor getDefaultPointCursor() {
		if (defaultPointCursor == null) {
			defaultPointCursor = new UPointCursor();
		}
		return defaultPointCursor;
	}

	public UUIStyle getDefaultUIStyle() {
		if (defaultUIStyle == null) {
			defaultUIStyle = new UUIStyle();
		}
		return defaultUIStyle;
	}

	public UColor getColorByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDefaultClassByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDefaultClassByTypeIntKey(String type) {
		return null;
	}

	public UFont getFontByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public UFilterTemplate getFilterTemplateByName(String name) {
		return null;
	}

	public UPaperTemplate getPaperTemplateByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	public UPromptTemplate getPromptTemplateByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public UParagraphTemplate getParagraphTemplateByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public UPointCursor getPointCursorByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public UStroke getStrokeByName(String strokeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTemplate(String type, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTypeValue(String str, String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void initApplication(String path, String appxmlname,
			DBDataProcessorI proc) {
		// TODO Auto-generated method stub
	}

	public void initApplicationModels(String path, String appname) {
		// TODO Auto-generated method stub

	}

	public UButtonTemplate[] getButtons(Element tab) {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap getConstantsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public void menuItemInfoToTemplate(UMenuInfo menuInfo,
			UMenuItemTemplate itemTemplate) {
		if (menuInfo == null || itemTemplate == null)
			return;
		itemTemplate.menuNo = menuInfo.menuNo;
		itemTemplate.content = menuInfo.menuContent;
		itemTemplate.enContent = menuInfo.menuEnContent;
		itemTemplate.name = menuInfo.menuName;
		itemTemplate.csmType = menuInfo.csmType;
		itemTemplate.cmd = menuInfo.menuCmd;
		itemTemplate.target = menuInfo.target;
		itemTemplate.handler = "";
		itemTemplate.defaultSelected = false;
	}

	public void buildMenuTemplate(List menuList, UMenuTemplate menu, String csmType) {
		if (menuList == null  || menu == null)
			return;
		
		List<UMenuItemTemplate> tempList = new ArrayList<UMenuItemTemplate>(
				menuList.size());
		UMenuInfo menuInfo = null;

		UMenuItemTemplate itemTemplate = null;
		UMenuTemplate menuTemplate = null;
		for (int i = 0; i < menuList.size(); i++) {
			menuInfo = (UMenuInfo) menuList.get(i);
			if(csmType != null && !csmType.equals(menuInfo.csmType))
				continue;
			if (menuInfo.subMenuList == null
					|| menuInfo.subMenuList.size() == 0) {
				itemTemplate = new UMenuItemTemplate();
				menuItemInfoToTemplate(menuInfo, itemTemplate);
				tempList.add(itemTemplate);
			} else {
				menuTemplate = new UMenuTemplate();
				menuItemInfoToTemplate(menuInfo, menuTemplate);
				buildMenuTemplate(menuInfo.subMenuList, menuTemplate,null);
				tempList.add(menuTemplate);
			}
		}

		menu.addItems(tempList);
	}

	public void buildTreeTemplate(List menuList, UMenuTemplate menu, String csmType) {
		if (menuList == null || menu == null)
			return;

		List<UMenuItemTemplate> tempList = new ArrayList<UMenuItemTemplate>(
				menuList.size());
		UMenuInfo menuInfo = null;

		UMenuItemTemplate itemTemplate = null;
		UMenuTemplate menuTemplate = null;
		for (int i = 0; i < menuList.size(); i++) {
			menuInfo = (UMenuInfo) menuList.get(i);
			if(csmType != null && !csmType.equals(menuInfo.csmType))
				continue;
			if (menuInfo.subMenuList == null
					|| menuInfo.subMenuList.size() == 0) {
				itemTemplate = new UMenuItemTemplate();
				menuItemInfoToTemplate(menuInfo, itemTemplate);
				tempList.add(itemTemplate);
			} else {
				menuTemplate = new UMenuTemplate();
				menuItemInfoToTemplate(menuInfo, menuTemplate);
				buildMenuTemplate(menuInfo.subMenuList, menuTemplate,null);
				tempList.add(menuTemplate);
			}
		}

		menu.addItems(tempList);
	}

	public UClientFrameTemplate getClientFrameTemplate(String templateName) {
		return null;
	}

	public HashMap getUserTaskMap(String name) {
		return null;
	}

	public GraphModelI getGraphModel2DObject(String name) {
		GraphModelI model = graphModelMap.get(name);
		Date serverTime = getServerTimestamp(name);
		if (model != null && null != serverTime
				&& serverTime.equals(model.getTimestamp()))
			return model;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getGraphModel2DObject");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		else {
			model = (GraphModelI) respond.get(RmiKeyConstants.KEY_OBJECT);
			graphModelMap.put(name, model);
			return model;
		}
	}

	public Date getServerTimestamp(String name) {
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getModelTimestampFromServer");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null)
			return null;
		else {
			return (Date) respond.get(RmiKeyConstants.KEY_DATE);
		}
	}


	public GraphModelI newTempGraphModel2DObject() {
		// TODO Auto-generated method stub
		GraphModel2D gm2d = null;
		String numString = "0001";
		String modelName = "ModelTemp" + numString;
		gm2d = new GraphModel2D();
		gm2d.setName(modelName);
		gm2d.getInitDefaultGraph();
		graphModelMap.put(modelName, gm2d);
		return gm2d;
	}


	public void sendDisplayMessageToServer(String message) {
		// TODO Auto-generated method stub
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, message);
		request.setCmd("sendDisplayMessageToServer");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);

	}

	public GraphModelI getCubeViewGraphModelObject(String name) {
		return null;
	}


	@Override
	public String getItemStyleTemplate(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List getTemplateNameList(String mapKeyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap getJsFunctionBodyMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJsFunctionBodyByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetModels() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getgetJsSegmentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public EnvironmentTemplate getEnvironmentTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEnvironmentTemplateByName(String name) {
		// TODO Auto-generated method stub

	}


	@Override
	public DefaultFontMapper getFontMapper() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setEnvironmentConfigureTemplateByName(String name) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initApplicationModels() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataExportTemplate getDataExportTemplate(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSqlSegmentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataImportTemplate getDataImportTemplate(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getBsTemplate(String type, String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List getTimeControlActionListByPanelName(String name, List<String>roleList) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public PhotoLayoutTemplate getPhotoLayoutTemplate(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap getPhotoLayoutTemplateMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTypeValueByString(String name, String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHtmlSegmentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEnvironmentProperties(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UDictionaryMappingTemplate getDictionaryMappingTemplateByName(
			String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDictionaryMappingMapVlue(String mappingName,
			String dictName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String  getPromptInfoByName(String name, boolean englishVerison) {
		UPromptTemplate template =getPromptTemplateByName(name);
		if(template == null)
			return name;
		if(!englishVerison) {
			return template.info;
		}else {
			if(template.infoEng != null && template.infoEng.length() >0)
				return template.infoEng;
			else
				return name;
		}
	}

	@Override
	public HashMap getTemplateMapInstance(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap getDataTestProcessTemplateMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap getDataTestInfoTemplateMap() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setClient(boolean isClient) {
		// TODO Auto-generated method stub
		
	}


}
