package org.octopus.common_business.data_dictionary.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DataDictionary implements java.io.Serializable, Comparable {

	private Integer dataItemId;

	private DataDictionary baseDataDictionary;

	private String dataItemNum;

	private String dataItemName;
	private String dataItemEngName;

	private String dataItem;

	private Integer isLeaf;

	private String isVisual;// 判断该数据字典项是否可见，该项为空或者1时，表示显示，该项为0时，该项不显示

	private Integer orderNum;

	private String remark;

	private Set baseDataDictionaries = new LinkedHashSet();
	private List<DataDictionary> childDataDictionaryList;

	public DataDictionary() { 
		childDataDictionaryList = new ArrayList<DataDictionary>();
	}

	public Integer getDataItemId() {
		return this.dataItemId;
	}

	public void setDataItemId(Integer dataItemId) {
		this.dataItemId = dataItemId;
	}

	public DataDictionary getBaseDataDictionary() {
		return this.baseDataDictionary;
	}

	public void setBaseDataDictionary(DataDictionary baseDataDictionary) {
		this.baseDataDictionary = baseDataDictionary;
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

	public void setDataItemName(String dataItemName) {
		this.dataItemName = dataItemName;
	}

	public String getDataItemEngName() {
		return dataItemEngName;
	}

	public void setDataItemEngName(String dataItemEngName) {
		this.dataItemEngName = dataItemEngName;
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

	public Set getBaseDataDictionaries() {
		return this.baseDataDictionaries;
	}

	public List<DataDictionary> getOrderedchildDictionaryList() {
		if (childDataDictionaryList.size() == 0) {
			childDataDictionaryList.addAll(baseDataDictionaries);
			Collections.sort(childDataDictionaryList, new Comparator<DataDictionary>() {
				public int compare(DataDictionary arg0, DataDictionary arg1) {
					return arg0.getDataItemId() - arg1.getDataItemId();

				}
			});
		}
		return childDataDictionaryList;
	}

	public void setBaseDataDictionaries(Set baseDataDictionaries) {
		this.baseDataDictionaries = baseDataDictionaries;
		childDataDictionaryList.clear();
	}

	public String getIsVisual() {
		return isVisual;
	}

	public void setIsVisual(String isVisual) {
		this.isVisual = isVisual;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		DataDictionary t = (DataDictionary) o;
		if (orderNum == null || t.orderNum == null)
			return 0;
		else
			return orderNum.compareTo(t.orderNum);
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}