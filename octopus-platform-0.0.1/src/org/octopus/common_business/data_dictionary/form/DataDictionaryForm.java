package org.octopus.common_business.data_dictionary.form;

import java.util.HashMap;

public class DataDictionaryForm {
	private Integer dataItemId;

	private String dataItemNum;

	private String dataItemName;

	private String dataItemEngName;

	private String dataItem;

	private Integer isLeaf;
	private String isVisual;
	private Integer orderNum; 

	private String remark;

	private String fatherNum;

	private HashMap childMap;

	public HashMap getChildMap() {
		return childMap;
	}

	public void setChildMap(HashMap childMap) {
		this.childMap = childMap;
	}

	public String getFatherNum() {
		return fatherNum;
	}

	public void setFatherNum(String fatherNum) {
		this.fatherNum = fatherNum;
	}

	public Integer getDataItemId() {
		return this.dataItemId;
	}

	public void setDataItemId(Integer dataItemId) {
		this.dataItemId = dataItemId;
	}

	public String getDataItemNum() {
		return this.dataItemNum;
	}

	public void setDataItemNum(String dataItemNum) {
		this.dataItemNum = dataItemNum;
	}

	public String getDataItemName() {
		return this.dataItemName;
	}

	public String getDataItemEngName() {
		return dataItemEngName;
	}

	public void setDataItemEngName(String dataItemEngName) {
		this.dataItemEngName = dataItemEngName;
	}

	public void setDataItemName(String dataItemName) {
		this.dataItemName = dataItemName;
	}

	public String getDataItem() {
		return this.dataItem;
	}

	public void setDataItem(String dataItem) {
		this.dataItem = dataItem;
	}

	public Integer getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsVisual() {
		return isVisual;
	}

	public void setIsVisual(String isVisual) {
		this.isVisual = isVisual;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
}
