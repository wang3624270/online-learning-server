package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.service.UFactory;

public class UPageGroupTemplate extends UBaseTemplate{

	protected String colSpan;
	protected String rowSpan;
	protected List<UPageElementTemplate> componentList;
	protected UFactory factory;
	protected String componentSpace;
	public String getComponentSpace() {
		return componentSpace;
	}
	public void setComponentSpace(String componentSpace) {
		this.componentSpace = componentSpace;
	}
	public UPageGroupTemplate(){
		this.componentList = new ArrayList<UPageElementTemplate>();
	}
	public void addComponentToGroup(UPageElementTemplate e){
		componentList.add(e);
		if (e.getType() != null) {
			HashMap<String,String> tmpMap = (HashMap<String,String>) UFactory.getModelSession().getConstantsMap().get("componentType");
			String tmpStr = (String) tmpMap.get(e.getType());
			if (tmpStr != null)
				e.typeInt = Integer.parseInt(tmpStr);
		}
	}
	public String getColSpan() {
		return colSpan;
	}
	public void setColSpan(String colSpan) {
		this.colSpan = colSpan;
	}
	public String getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(String rowSpan) {
		this.rowSpan = rowSpan;
	}
	public List<UPageElementTemplate> getComponentList() {
		return componentList;
	}
	public void setComponentList(List<UPageElementTemplate> componentList) {
		this.componentList = componentList;
	}
}
