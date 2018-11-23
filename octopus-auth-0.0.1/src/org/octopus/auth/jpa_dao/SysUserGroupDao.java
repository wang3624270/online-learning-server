package org.octopus.auth.jpa_dao;

import java.util.List;

import org.octopus.auth.jpa_model.SysUserGroup;
import org.octopus.spring_utils.jpa.GenericServiceI;



public interface SysUserGroupDao extends GenericServiceI<SysUserGroup>{

	//public SysUserGroup getGroupBySys(Integer sysusrid);
	public List getGroupSysUserList(Integer groupId, String perNum, String perName);
	public List getGroupListByConditions(Integer groupId, String groupName);
}

