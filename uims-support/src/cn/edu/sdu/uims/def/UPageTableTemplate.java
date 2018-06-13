package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UPageTableTemplate extends UBaseTemplate{

	protected List<UPageRowTemplate> rowTemplates = null;
	protected List<UPageElementTemplate> elementsList = null;
	protected HashMap<String,UPageElementTemplate> elementsMap = null;
	protected int maxCols;
	protected int currentPage;
	protected int maxItems;
	protected int totalPage;
	protected int style;
	protected String width ;

	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public int getMaxCols() {
		return maxCols;
	}
	public void setMaxCols(int maxCols) {
		this.maxCols = maxCols;
	}
	public UPageTableTemplate(){
		rowTemplates = new ArrayList<UPageRowTemplate>();
		if(this.elementsList == null){
			this.elementsList = new ArrayList<UPageElementTemplate>();
		}
		if(this.elementsMap == null){
			this.elementsMap = new HashMap<String,UPageElementTemplate>();
		}
	}
	public void addElementToTable(UPageElementTemplate element){
		if(this.elementsList == null){
			this.elementsList = new ArrayList<UPageElementTemplate>();
		}
		if(this.elementsMap == null){
			this.elementsMap = new HashMap<String,UPageElementTemplate>();
		}
		this.elementsList.add(element);
		if(this.elementsMap.get(element.getName()) == null){
			this.elementsMap.put(element.getName(), element);
		}
	}
	public void addPageRowTemplateConfig(UPageRowTemplate rowTemplate){
		this.rowTemplates.add(rowTemplate);
		if(rowTemplate.getCols()>this.maxCols)
			this.maxCols = rowTemplate.getCols();
	}
	public List<UPageRowTemplate> getRowTemplates() {
		return rowTemplates;
	}
	public void setRowTemplates(List<UPageRowTemplate> rowTemplates) {
		this.rowTemplates = rowTemplates;
	}

	
}
