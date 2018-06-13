package cn.edu.sdu.uims.service;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

import com.itextpdf.awt.DefaultFontMapper;

import cn.edu.sdu.common.pi.ModelSessionBaseI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UPointCursor;
import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.def.UButtonTemplate;
import cn.edu.sdu.uims.def.UDictionaryMappingTemplate;
import cn.edu.sdu.uims.def.UFilterTemplate;
import cn.edu.sdu.uims.def.UMenuTemplate;
import cn.edu.sdu.uims.def.UPromptTemplate;
import cn.edu.sdu.uims.def.dataexport.DataExportTemplate;
import cn.edu.sdu.uims.def.dataimport.DataImportTemplate;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.def.UClientFrameTemplate;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.print.photo.PhotoLayoutTemplate;



public interface UModelSessionI extends ModelSessionBaseI{
	Object getTemplate(String type, String name);
	Object getBsTemplate(String type, String name);

	UBorder getDefaultStrokeBorder();

	UFont getDefaultFont();

	UColor getDefaultColor();

	UFilterTemplate getFilterTemplateByName(String name);

	String getDefaultClassByType(String type);

	String getDefaultClassByTypeIntKey(String type);


	UBorder getBorderByName(String name);

	void initApplication(String path, String appxmlname, DBDataProcessorI proc);

	void initApplicationModels(String path, String appname);

	UPointCursor getPointCursorByName(String name);

	UStroke getStrokeByName(String strokeName);

	int getTypeValue(String str, String key);


	UPaperTemplate getPaperTemplateByName(String name);
	UPromptTemplate getPromptTemplateByName(String name);

	HashMap getConstantsMap();

	UButtonTemplate[] getButtons(Element tab);

	UClientFrameTemplate getClientFrameTemplate(String templateName);

	void buildTreeTemplate(List menuList, UMenuTemplate menu,String csmType);

	void buildMenuTemplate(List menuList, UMenuTemplate menu,String csmType);

	HashMap getUserTaskMap(String name);

	GraphModelI getGraphModel2DObject(String name);

	GraphModelI getCubeViewGraphModelObject(String name);


	GraphModelI newTempGraphModel2DObject();


	void sendDisplayMessageToServer(String message);

	List getTemplateNameList(String mapKeyName);


	String getItemStyleTemplate(String name);


	HashMap getJsFunctionBodyMap();

	String getJsFunctionBodyByName(String name);

	String getgetJsSegmentByName(String name);

	void resetModels();


	EnvironmentTemplate getEnvironmentTemplate();

	void setEnvironmentTemplateByName(String name);

	DefaultFontMapper getFontMapper();
	
	void setEnvironmentConfigureTemplateByName(String name);
	void initApplicationModels();
	String getSqlSegmentByName(String name);
	List getTimeControlActionListByPanelName(String name, List<String>roleList);
	PhotoLayoutTemplate getPhotoLayoutTemplate(String name);
	HashMap getPhotoLayoutTemplateMap();
	int getTypeValueByString(String name, String key);
	String getHtmlSegmentByName(String name);
	String getEnvironmentProperties(String name);
	
	UDictionaryMappingTemplate getDictionaryMappingTemplateByName(String name);
	String  getDictionaryMappingMapVlue(String mappingName, String dictName, String value);
	String getPromptInfoByName(String name, boolean englishVersion);
	HashMap getTemplateMapInstance(String key);
	HashMap getDataTestProcessTemplateMap();
	HashMap getDataTestInfoTemplateMap();
	void setClient(boolean isClient);
	
	DataExportTemplate getDataExportTemplate(String name);
	DataImportTemplate getDataImportTemplate(String name);
}
