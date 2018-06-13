package org.octopus.reportdog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DataPanelModelConfigInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "data_panel_model_config_info")
public class DataPanelModelConfigInfo  {

	// Fields

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer modelId;
	private String modelName;
	private String modelConfig;
	private Date timestamp;

	// Constructors

	/** default constructor */
	public DataPanelModelConfigInfo() {
	}

	/** minimal constructor */
	public DataPanelModelConfigInfo(String modelName) {
		this.modelName = modelName;
	}

	/** full constructor */
	public DataPanelModelConfigInfo(String modelName, String modelConfig,
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

}