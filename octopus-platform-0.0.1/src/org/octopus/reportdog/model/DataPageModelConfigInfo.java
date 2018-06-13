package org.octopus.reportdog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DataModelConfigInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "data_page_model_config_info")
public class DataPageModelConfigInfo implements java.io.Serializable {

	// Fields

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer modelId;
	private String modelName;
	private String modelConfig;
	private Date timestamp;
	private String modelDes;
	private String modifyer;

	// Constructors

	/** default constructor */
	public DataPageModelConfigInfo() {
	}

	/** minimal constructor */
	public DataPageModelConfigInfo(String modelName) {
		this.modelName = modelName;
	}

	/** full constructor */
	public DataPageModelConfigInfo(String modelName, String modelConfig,
			Date timestamp) {
		this.modelName = modelName;
		this.modelConfig = modelConfig;
		this.timestamp = timestamp;
	}

	// Property accessors

	public Integer getModelId() {
		return this.modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelConfig() {
		return this.modelConfig;
	}

	public void setModelConfig(String modelConfig) {
		this.modelConfig = modelConfig;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getModelDes() {
		return modelDes;
	}

	public void setModelDes(String modelDes) {
		this.modelDes = modelDes;
	}

	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

}