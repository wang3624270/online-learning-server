package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.service.UFactory;

public class UPageRowTemplate extends UBaseTemplate{

	protected List <UBaseTemplate>componentTemplates = null;
	
	protected HashMap<String,UPageElementTemplate> elementsMap = null;
	
	protected HashMap <String,String>nameTemplateMap = null;
	
	
	protected int cols;

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public List<UBaseTemplate> getComponentTemplates() {
		return componentTemplates;
	}

	public void setComponentTemplates(List componentTemplates) {
		this.componentTemplates = componentTemplates;
	}

	public HashMap<String, String> getNameTemplateMap() {
		return nameTemplateMap;
	}

	public void setNameTemplateMap(HashMap<String, String> nameTemplateMap) {
		this.nameTemplateMap = nameTemplateMap;
	}

	public UPageRowTemplate(){
		componentTemplates = new ArrayList<UBaseTemplate>();
		this.elementsMap = new HashMap<String,UPageElementTemplate>();
		nameTemplateMap = new HashMap<String,String>();
	}
	
	/**
	 * 将控件加入rowTemplate，同时记录相应的控件的templateName和name的映射关系
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	public void addPageElementTemplateConfig(UBaseTemplate element){
		this.componentTemplates.add(element);
		if(element instanceof UPageElementTemplate){
			UPageElementTemplate e = (UPageElementTemplate)element;
			if(this.elementsMap.get(e.getName()) == null){
				this.elementsMap.put(e.getName(), e);
			}
			if(e.getTemplateName() != null){
				if(this.nameTemplateMap.get(e.getTemplateName()) != null){
					this.nameTemplateMap.put(e.getName(), e.getTemplateName());
				}
			}
			if (e.getType() != null) {
				HashMap<String,String> tmpMap = (HashMap<String,String>) UFactory.getModelSession().getConstantsMap().get("componentType");
				String tmpStr = (String) tmpMap.get(e.getType());
				if (tmpStr != null)
					e.typeInt = Integer.parseInt(tmpStr);
			}
		}
	}
}
