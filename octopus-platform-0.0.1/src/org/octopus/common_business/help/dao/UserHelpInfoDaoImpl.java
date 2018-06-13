package org.octopus.common_business.help.dao;

import java.util.HashMap;
import java.util.List;

import org.octopus.common_business.help.model.UserHelpInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class UserHelpInfoDaoImpl extends GenericServiceImpl<UserHelpInfo> implements UserHelpInfoDao{
	public UserHelpInfoDaoImpl() {
		super(UserHelpInfo.class);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		String hql = " from UserHelpInfo ";
		return this.queryForList(hql);
	}

	@Override
	public HashMap<String, UserHelpInfo> getUserHelpInfoMap() {
		// TODO Auto-generated method stub
		String hql = " from UserHelpInfo ";
		List list =  this.queryForList(hql);
		HashMap<String, UserHelpInfo> map = new HashMap<String, UserHelpInfo>();
		if(list == null || list.size() == 0)
			return map;
		UserHelpInfo u;
		for(int i = 0; i < list.size();i++) {
			u = (UserHelpInfo)list.get(i);
			map.put(u.getHelpNum(), u);
		}
		return map;
	}
}
