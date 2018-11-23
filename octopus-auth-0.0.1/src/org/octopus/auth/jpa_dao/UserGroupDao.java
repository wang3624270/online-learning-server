package org.octopus.auth.jpa_dao;

import java.util.List;

import org.octopus.auth.jpa_model.UserGroup;
import org.octopus.spring_utils.jpa.GenericServiceI;



public interface UserGroupDao extends GenericServiceI<UserGroup>{

	public UserGroup getRelationBySys(Integer sysusrid);
	public UserGroup getUserGroup(Integer sysusrid, Integer groupId);
	public List getListByConditionds(Integer sysusrid, Integer groupid);
	
}
