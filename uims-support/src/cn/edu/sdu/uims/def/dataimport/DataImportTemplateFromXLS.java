package cn.edu.sdu.uims.def.dataimport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;


public class DataImportTemplateFromXLS extends DataImportTemplate {
	private String cmd;
	private String formClassName;
	private String primaryKey;
	private String label;
	private String templateFileName;
	private int rowNum;
	private String ruleBean;

	private String source;
	private String target;
	private String init;
	private String map;
	private String dataMap;
	private String dataValue;

	public String getRuleBean() {
		return ruleBean;
	}

	public void setRuleBean(String ruleBean) {
		this.ruleBean = ruleBean;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	private List<ItemTemplate> itemList;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getFormClassName() {
		return formClassName;
	}

	public void setFormClassName(String formClassName) {
		this.formClassName = formClassName;
	}

	public List<ItemTemplate> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemTemplate> itemList) {
		this.itemList = itemList;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getDataMap() {
		return dataMap;
	}

	public void setDataMap(String dataMap) {
		this.dataMap = dataMap;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	
	public  ItemTemplate getItemTemplate(String type) {
		if (type.equals("date") || type.equals("Date")) {
			return null;
		}
		String className = null;
		if (type.startsWith("common")) {
			className = "cn.edu.sdu.commoncomponent.bs.ItemTemplate"
					+ type.substring(0, 1).toUpperCase()
					+ type.substring(1, type.length());

		} else {
			className = "cn.edu.sdu.bsuims.def.item.ItemTemplate";
			if (type.length() > 0) {
				className += type.substring(0, 1).toUpperCase()
						+ type.substring(1, type.length());
			}
		}
		if (className == null)
			return null;
		try {
			return (ItemTemplate) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public void getAttribute(Element e) {
		super.getAttribute(e);
		Iterator it1;
		Element e1;
		ItemTemplate item;
		String type;
		cmd = e.attributeValue("cmd");
		ruleBean = e.attributeValue("ruleBean");
		formClassName = e.attributeValue("formClassName");
		it1 = e.elementIterator("item");
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			type = e1.attributeValue("type");
			if (type == null)
				type = "label";
			item = getItemTemplate(type);
			item.getAttribute(e1);
			item.setType(type);
			item.setSource(e1.attributeValue("source"));
			item.setTarget(e1.attributeValue("target"));
			item.setInit(e1.attributeValue("init"));
			item.setMap(e1.attributeValue("map"));
			item.setDataMap(e1.attributeValue("dataMap"));
			item.setDataValue((e1.attributeValue("dataValue")));
			if (itemList == null)
				itemList = new ArrayList<ItemTemplate>();
			itemList.add(item);
		}
		primaryKey = e.attributeValue("primaryKey");
		if (primaryKey == null || primaryKey.length() == 0)
			primaryKey = itemList.get(0).getName();
		label = e.attributeValue("label");
		templateFileName = e.attributeValue("templateFileName");
		String str = e.attributeValue("rowNum");
		if (str == null)
			rowNum = 1;
		else
			rowNum = new Integer(str);
	}

}
