package cn.edu.sdu.uims.def;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.flex.FHashMap;

 
public class UPanelTemplate extends UTemplate{
	public int height = 0 ;//模板在整个框架中的高度，可以为百分比
	public int width = 0 ;//模板在整个框架中的宽度，可以为百分比
	public String id = "" ;
	public String scrolling = null ;

	public String src = "" ;
	
	public int dispMode;
	public int type;
	public String title = null;
	public String enTitle = null;
	public int titleHeight = 60;
	public String dataFormClassName = null;
	public String handlerClassName = null;
	public String frontHandlerClassName = null;
	public UGroupElementTemplate groupElementTemplate = null;
	public String toolActions[] = null;
	public String  paperName = null;
	public String userTaskGroupName = null;
	public String defaultUserTaskName = null;
	public String defaultMethodName = null;
	public boolean canScrolling = true;
	/** liuyang added*/
	public String closePanels[] = null;
	public boolean isOnTimer = false;
	public boolean isTimeControlAction = false;
	public String tabIconName;
	
	public int notex, notey;
	public String menu = null;
	
	public void getIntValueFromString() {
		groupElementTemplate.getIntValueFromString();
	}
	public void getStringValueFromInt() {
		groupElementTemplate.getStringValueFromInt();
	}
	
	
	public void replaceTemplateContent(String ext, FHashMap map){
		String textFieldName, templateFieldName;
		String value;
		if(map == null )
			return;
		UElementTemplate ee;
		for(int i = 0; i < groupElementTemplate.componentNum;i++) {
			ee = (UElementTemplate)groupElementTemplate.componentTemplates.get(i);
			textFieldName = ext + "." +ee.name + ".text";
			value = (String)map.getData(textFieldName);
			if(value != null){
				ee.text = value;
			}
			templateFieldName = ext+ "." +ee.name + ".templateName";
			value = (String)map.getData(templateFieldName);
			if(value != null){
				ee.templateName = value;
			}
		}
	}
}
