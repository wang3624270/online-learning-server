package com.video.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;


import com.video.dao.LiveHubDao;
import com.video.model.LiveHub;



@Repository
public class LiveHubDaoImpl extends GenericServiceImpl<LiveHub> implements LiveHubDao{

	public LiveHubDaoImpl(){
		super(LiveHub.class);
	}

	@Override
	public LiveHub getHubNameByHubType(Integer hubType) {
		// TODO Auto-generated method stub
		String hql = "from LiveHub la where 1=1 ";
		if(hubType!=null){
			hql+="and la.hubType="+hubType;
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (LiveHub) list.get(0);
		}
	}
}
