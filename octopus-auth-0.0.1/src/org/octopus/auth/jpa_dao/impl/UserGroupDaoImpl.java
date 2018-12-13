package org.octopus.auth.jpa_dao.impl;

import java.util.List;

import org.octopus.auth.jpa_dao.UserGroupDao;
import org.octopus.auth.jpa_model.UserGroup;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;



@Repository
public class UserGroupDaoImpl extends GenericServiceImpl<UserGroup>
implements UserGroupDao {

	public UserGroupDaoImpl(){
	    super(UserGroup.class);
	}

	@Override
	public UserGroup getRelationBySys(Integer sysusrid) {
		// TODO Auto-generated method stub
		String sql = "from UserGroup where sysusrid = " + sysusrid;
		List list = this.queryForList(sql);

		if (list != null && list.size() > 0) {
			UserGroup user = (UserGroup) list.get(0);
			return user;
		} else {
			return null;
		}
	}

	@Override
	public UserGroup getUserGroup(Integer sysusrid, Integer groupid) {
		// TODO Auto-generated method stub
		String hql = "from UserGroup a where 1=1";
		if(sysusrid!=null && !sysusrid.equals("")){
			hql+="and a.sysusrid='"+sysusrid+"'";
		}
		if(groupid!=null && !groupid.equals("")){
			hql+="and a.groupid='"+groupid+"'";
		}
		List list = this.queryForList(hql);
		if (list != null && list.size() > 0) {
			UserGroup user = (UserGroup) list.get(0);
			return user;
		} else {
			return null;
		}
	}

	@Override
	public List getListByConditionds(Integer sysusrid, Integer groupid) {
		// TODO Auto-generated method stub
		String hql = "from UserGroup a where 1=1";
		if(sysusrid!=null && !sysusrid.equals("")){
			hql+="and a.sysusrid='"+sysusrid+"'";
		}
		if(groupid!=null && !groupid.equals("")){
			hql+="and a.groupid='"+groupid+"'";
		}
		List list = this.queryForList(hql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

}
