package cn.edu.sdu.uims.def.dataexport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.sdu.spring_util.ApplicationContextHandle;


public class DataExportTemplateToXLS extends DataExportTemplate {
	protected String ruleBean;
	protected String fileName;
	protected List<DataExportItemTemplate> itemList;
	protected String formClassName;
	private int rowNum;


	public String getFormClassName() {
		return formClassName;
	}

	public void setFormClassName(String formClassName) {
		this.formClassName = formClassName;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public List<DataExportItemTemplate> getItemList() {
		return itemList;
	}

	public void setItemList(List<DataExportItemTemplate> itemList) {
		this.itemList = itemList;
	}
	public String getRuleBean() {
		return ruleBean;
	}


	public void setRuleBean(String ruleBean) {
		this.ruleBean = ruleBean;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileSuffix(){
		return ".xls";
	}
	
	public void getAttribute(Element e) {
		super.getAttribute(e);
		ruleBean = e.attributeValue("ruleBean");
		fileName = e.attributeValue("fileName");
		formClassName = e.attributeValue("formClassName");
		String str = e.attributeValue("rowNum");
		if(str == null)
			rowNum = 1;
		else
			rowNum = new Integer(str);
		DataExportItemTemplate item;
		itemList = null;
		Iterator it1;
		Element e1;
		it1 = e.elementIterator("item");
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			if (itemList == null)
				itemList = new ArrayList();
			item = new DataExportItemTemplate();
			item.getAttribute(e1);
			itemList.add(item);
		}
	}
	public List<String> getClauseList(){
		return null;
	}
	public String getSQLString(){
		return null;
	}
	public DataExportToXLSProcessorI getDataExportToXLSProcessor(){
		if(ruleBean == null || ruleBean.length() == 0)
			return null;
		return (DataExportToXLSProcessorI)ApplicationContextHandle.getBean(ruleBean);
	}
	public HashMap<String,Method> getMethodMap(){
		return null;
	}
	public void resetChangePi(){
		
	}
	public void resetComputePi(){
	}
}
