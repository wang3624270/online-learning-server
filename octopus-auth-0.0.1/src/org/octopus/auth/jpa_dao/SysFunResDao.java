package org.octopus.auth.jpa_dao;

import java.util.List;

import org.octopus.auth.jpa_model.SysFunRes;
import org.octopus.spring_utils.jpa.GenericServiceI;


public interface SysFunResDao extends GenericServiceI<SysFunRes>{
	
	public List getMenuListByCondition(String name,String path);
}
