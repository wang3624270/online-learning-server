package org.octopus.auth.jpa_dao;

import java.util.List;

import org.octopus.auth.jpa_model.GroupFunres;
import org.octopus.spring_utils.jpa.GenericServiceI;



public interface GroupFunresDao extends GenericServiceI<GroupFunres>{

	public List getIdListByGroupId(Integer groupId);

	public List getListByResId(Integer resid);

	public List getListByConditions(Integer groupId,Integer resid);
}

