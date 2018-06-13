package org.octopus.common_business.globalinfo.dao;

import java.util.List;

import org.octopus.common_business.globalinfo.model.SystemGlobalVariable;
import org.octopus.spring_utils.jpa.GenericServiceI;

public interface SystemGlobalVariableDao extends
GenericServiceI<SystemGlobalVariable> {
	SystemGlobalVariable findSystemGlobalVariableById(Integer variableId );
	Integer savePo(SystemGlobalVariable po);
	SystemGlobalVariable findSystemGlobalVariableByVariableName(String variableName);
	List<SystemGlobalVariable> findAll();
}
