package org.octopus.reportdog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DataSourceConfigInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "data_source_config_info")
public class DataSourceConfigInfo  {

	// Fields

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sourceId;
	private String sourceName;
	private String dataSource;
	private Date timestamp;

	// Constructors

	/** default constructor */
	public DataSourceConfigInfo() {
	}

	/** minimal constructor */
	public DataSourceConfigInfo(String sourceName) {
		this.sourceName = sourceName;
	}

	/** full constructor */
	public DataSourceConfigInfo(String sourceName, String dataSource,
			Date timestamp) {
		this.sourceName = sourceName;
		this.dataSource = dataSource;
		this.timestamp = timestamp;
	}

	// Property accessors

	public Integer getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return this.sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}