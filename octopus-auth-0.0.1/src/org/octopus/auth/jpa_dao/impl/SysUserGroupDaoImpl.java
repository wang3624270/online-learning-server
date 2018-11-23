package org.octopus.auth.jpa_dao.impl;

import java.util.List;

import org.octopus.auth.jpa_dao.SysUserGroupDao;
import org.octopus.auth.jpa_model.SysUserGroup;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;


@Repository
public class SysUserGroupDaoImpl extends GenericServiceImpl<SysUserGroup>
implements SysUserGroupDao {

	public SysUserGroupDaoImpl(){
	super(SysUserGroup.class);
	}

	@Override
	public List getGroupSysUserList(Integer groupId, String perNum, String perName) {
		// TODO Auto-generated method stub
		String sql =" select p.perNum, p.perName, p.perAddress, p.mobilePhone, g.name, g.groupid ";
		sql += "  from UserGroup u, SysUserGroup g, SysUser s, InfoPersonInfo p where ";
		sql += " s.sysusrid = u.sysusrid and s.userid = p.personId and g.groupid = u.groupid " ;
		if( groupId != null && !groupId.equals(-1))
			sql += " and u.groupid =" + groupId;
		if( perNum != null && perNum.length() != 0 )
			sql += " and s.loginName ='" + perNum + "'";
		if( perName != null && perName.length() != 0 )
			sql += " and p.perName ='" + perName + "'";
		return this.queryForList(sql);
	}

	@Override
	public List getGroupListByConditions(Integer groupId, String groupName) {
		// TODO Auto-generated method stub
		String sql = " from SysUserGroup g where 1=1";
		if( groupId != null && !groupId.equals(""))
			sql += " and g.groupid =" + groupId;
		if( groupName != null && !groupName.equals(""))
			sql += " and g.name ='" + groupName + "'";
		return this.queryForList(sql);
	}
	
}
