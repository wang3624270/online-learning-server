package org.octopus.reportdog.module;

import java.util.HashMap;

public interface SourceModuleConfig {
	
	public void addSourceMappingConfig(SourceMappingConfig sourceMappingConfig);

	public SourceMappingConfig findSourceMappingConfig(String path);

	public void addSourceDataConfig(SourceDataConfig sourceDataConfig);

	public SourceDataConfig findSourceDataConfig(String name);
	
	public HashMap getSourceConfigs();
	
	public HashMap getDataConfigs();
}
