package org.octopus.reportdog.module.impl;

import java.util.HashMap;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.module.SourceDataConfig;
import org.octopus.reportdog.module.SourceMappingConfig;
import org.octopus.reportdog.module.SourceModuleConfig;

public class SourceModuleConfigImpl implements SourceModuleConfig {

	private String name;
	private HashMap sourceConfigs;
	private HashMap dataConfigs;

	public SourceModuleConfigImpl() {
		super();
		this.sourceConfigs = new HashMap();
		this.dataConfigs = new HashMap();
	}

	public SourceModuleConfigImpl(String name) {
		super();
		this.setName(name);
		this.sourceConfigs = new HashMap();
		this.dataConfigs = new HashMap();
	}

	public void addSourceDataConfig(SourceDataConfig sourceDataConfig) {
		// TODO Auto-generated method stub
		this.dataConfigs.put(sourceDataConfig.getName(), sourceDataConfig);
	}

	public void addSourceMappingConfig(SourceMappingConfig sourceMappingConfig) {
		// TODO Auto-generated method stub
		this.sourceConfigs.put(ReportdogConstants.SOURCE_DATA_MAPPING_KEY,
				sourceMappingConfig);

	}

	public SourceDataConfig findSourceDataConfig(String name) {
		// TODO Auto-generated method stub
		return (SourceDataConfig) this.dataConfigs.get(name);
	}

	public SourceMappingConfig findSourceMappingConfig(String path) {
		// TODO Auto-generated method stub
		return (SourceMappingConfig) this.sourceConfigs.get(path);
	}

	public HashMap getSourceConfigs() {
		// TODO Auto-generated method stub
		return sourceConfigs;
	}

	public HashMap getDataConfigs() {
		// TODO Auto-generated method stub
		return dataConfigs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
