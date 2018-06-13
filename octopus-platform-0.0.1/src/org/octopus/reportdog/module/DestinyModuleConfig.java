package org.octopus.reportdog.module;

import java.util.HashMap;

public interface DestinyModuleConfig {
	
	public void addDestinyConfig(DestinyConfig destinyConfig);
	
	public DestinyConfig findDestinyConfig(String name);
	
	public HashMap getDestinyConfigs();

}
