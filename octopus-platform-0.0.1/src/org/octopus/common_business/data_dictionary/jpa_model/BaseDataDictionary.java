package org.octopus.common_business.data_dictionary.jpa_model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "base_data_dictionary")
public class BaseDataDictionary{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer dataItemId;
	@ManyToOne    //设置关联关系  
    @JoinColumn(name="fatherItemId")  
     private BaseDataDictionary baseDataDictionary;
	 private String dataItemEngName;
     private String dataItemNum;
     private String dataItemName;
     private String dataItem;
     private Integer isLeaf;
     private String isVisual;
     private Integer orderNum;
     private String remark;
     private Integer roleId;
     
     @OneToMany(targetEntity=BaseDataDictionary.class,cascade=CascadeType.ALL)
     @Fetch(FetchMode.JOIN)
     @JoinColumn(name="fatherItemId",updatable=false)  
     private Set baseDataDictionaries = new LinkedHashSet();
     
    /** default constructor */
    public BaseDataDictionary() {
    	
    }
    
    /** full constructor */
    public BaseDataDictionary(BaseDataDictionary baseDataDictionary, String dataItemNum, String dataItemName, String dataItemEngName,String dataItem, Integer isLeaf, String remark, LinkedHashSet baseDataDictionaries) {
        this.baseDataDictionary = baseDataDictionary;
        this.dataItemNum = dataItemNum;
        this.dataItemName = dataItemName;
        this.dataItem = dataItem;
        this.isLeaf = isLeaf;
        this.remark = remark;
        this.baseDataDictionaries = baseDataDictionaries;
    }

    public Integer getDataItemId() {
        return this.dataItemId;
    }

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setDataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
    }

    public BaseDataDictionary getBaseDataDictionary() {
        return this.baseDataDictionary;
    }
    
    public void setBaseDataDictionary(BaseDataDictionary baseDataDictionary) {
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

	public void doBeforeInsert(){};
	public void doAfterInsert(){};
	public void doBeforeUpdate(){};
	public void doAfterUpdate(){};
	public void doBeforeDelete(){};
	public void doAfterDelete(){}


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

	public String getDataItemEngName() {
		return dataItemEngName;
	}

	public void setDataItemEngName(String dataItemEngName) {
		this.dataItemEngName = dataItemEngName;
	}

	public Set getBaseDataDictionaries() {
		return baseDataDictionaries;
	}

	public void setBaseDataDictionaries(Set baseDataDictionaries) {
		this.baseDataDictionaries = baseDataDictionaries;
	}
}