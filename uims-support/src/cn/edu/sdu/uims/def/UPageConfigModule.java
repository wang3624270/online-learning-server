package cn.edu.sdu.uims.def;

import java.util.HashMap;

public class UPageConfigModule {

	protected HashMap<String,UPageTemplate> upageTemplate;
	
	protected String prefix;
	
	protected UPageTemplate currentPage;
	
	public String getPrefix() {
		return prefix;
	}
	
	public UPageTemplate getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(UPageTemplate currentPage) {
		this.currentPage = currentPage;
	}
	
	public void addElementToTable(UPageElementTemplate element){
		this.getCurrentPage().getComponentTemplates().add(element);
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public UPageConfigModule(String prefix){
		this.prefix = prefix;
		upageTemplate = new HashMap<String,UPageTemplate>();
	}
	
	public void addPageTemplateConfig(UPageTemplate template){
		template.setModuleConfig(this);
		this.upageTemplate.put(template.name, template);
	}

	public HashMap<String, UPageTemplate> getUpageTemplate() {
		return upageTemplate;
	}

	public void setUpageTemplate(HashMap<String, UPageTemplate> upageTemplate) {
		this.upageTemplate = upageTemplate;
	}
	
}
