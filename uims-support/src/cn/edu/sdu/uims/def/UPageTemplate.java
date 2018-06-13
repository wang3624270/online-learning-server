package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.service.UModelSessionServer;

public class UPageTemplate extends UBaseTemplate {

	protected String name;
	
	/**该页面属于哪个模块*/
	protected String module = "" ;
	
	protected UCssTemplate cssTemPlate;
	
	protected UJsTemplate jsTemplate;
	
	protected String formShortName = "" ;//对应于struts里面formBean 的名字
	
	protected boolean validate = false;
	
	protected String requestCmd = "" ;//请求命令
	
	/** 存放pageTemplate内的所有组件元素*/
	protected List<UBaseTemplate>  pageComponentsList = null;
	
	/**只存放pageTemplate内的elementTemplate*/
	protected HashMap<String,UPageElementTemplate>pageElementsMap = null;
	
	/**存放pageTemplate内元素的id映射关系*/
	protected HashMap <String,String>nameTemplateMap = null;
	
	protected UPageConfigModule moduleConfig = null;//暂时先放上
	
	protected String formAction;
	
	protected UModelSessionServer modelSession;
	
	protected Object pageFormData;
	
	public Object getPageFormData() {
		return pageFormData;
	}
	public void setPageFormData(Object pageFormData) {
		this.pageFormData = pageFormData;
	}
	public String getFormAction() {
		return formAction;
	}
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<UBaseTemplate> getComponentTemplates() {
		if(this.pageComponentsList == null)
			this.pageComponentsList = new ArrayList<UBaseTemplate>();
		return pageComponentsList;
	}

	public void setComponentTemplates(List<UBaseTemplate> componentTemplates) {
		this.pageComponentsList = componentTemplates;
	}

	public HashMap<String, String> getNameTemplateMap() {
		return nameTemplateMap;
	}

	public void setNameTemplateMap(HashMap<String, String> nameTemplateMap) {
		this.nameTemplateMap = nameTemplateMap;
	}

	public  UPageTemplate(){
		modelSession = (UModelSessionServer)UFactory.getModelSession();
		if(this.pageComponentsList == null)
			this.pageComponentsList = new ArrayList<UBaseTemplate>();
		this.nameTemplateMap = new HashMap<String,String>();
		if(this.pageElementsMap == null)
			this.pageElementsMap = new HashMap<String,UPageElementTemplate>();
	}
	
	/**加入element元素*/
	public void addPageElement(UPageElementTemplate element){
		if(this.pageElementsMap == null)
			this.pageElementsMap = new HashMap<String,UPageElementTemplate>();
		if(this.pageElementsMap.get(element.getName()) == null){
			this.pageElementsMap.put(element.getName(), element);
		}
		
		if(element.getId() != null){
			if(this.nameTemplateMap.get(element.getId()) == null)
				this.nameTemplateMap.put(element.getId(), element.getName());
		}
	}
	/**
	 * 将控件加入rowTemplate，同时记录相应的控件的templateName和name的映射关系
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	public void addComponentTemplateConfig(UBaseTemplate element){
		this.pageComponentsList.add(element);
		if(element instanceof UPageElementTemplate){
			UPageElementTemplate e = (UPageElementTemplate)element;
			if (e.getType() != null) {
				HashMap<String,String> tmpMap = (HashMap<String,String>) modelSession.getConstantsMap().get("componentType");
				String tmpStr = (String) tmpMap.get(e.getType());
				if (tmpStr != null)
					e.setTypeInt(Integer.parseInt(tmpStr));
			}
			if(e.getId() != null){
				if(this.nameTemplateMap.get(e.getId()) == null)
					if(e.getTypeInt() == UConstants.COMPONENT_TABLE_DATA_FORM){
						this.nameTemplateMap.put(e.getId(), e.getTemplateName());
					}
					else{
						this.nameTemplateMap.put(e.getId(), e.getName());
					}
			}
			if(this.pageElementsMap.get(e.getName()) == null){
				this.pageElementsMap.put(e.getName(), e);
			}
		}
	}
	protected  void setModuleConfig(UPageConfigModule moduleConfig){
		this.moduleConfig = moduleConfig;
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public UCssTemplate getCssTemPlate() {
		return cssTemPlate;
	}
	public void setCssTemPlate(UCssTemplate cssTemPlate) {
		this.cssTemPlate = cssTemPlate;
	}
	public UJsTemplate getJsTemplate() {
		return jsTemplate;
	}
	public void setJsTemplate(UJsTemplate jsTemplate) {
		this.jsTemplate = jsTemplate;
	}
	public String getFormShortName() {
		return formShortName;
	}
	public void setFormShortName(String formShortName) {
		this.formShortName = formShortName;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getRequestCmd() {
		return requestCmd;
	}
	public void setRequestCmd(String requestCmd) {
		this.requestCmd = requestCmd;
	}
	public UPageConfigModule getModuleConfig() {
		return moduleConfig;
	}

	public HashMap<String, UPageElementTemplate> getComponentsMap() {
		return pageElementsMap;
	}

	public void setComponentsMap(HashMap<String, UPageElementTemplate> componentsMap) {
		this.pageElementsMap = componentsMap;
	}
	
	
}
