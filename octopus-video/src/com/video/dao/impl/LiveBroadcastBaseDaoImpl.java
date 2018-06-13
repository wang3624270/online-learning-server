package com.video.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;


import com.video.dao.LiveBroadcastBaseDao;
import com.video.model.LiveBroadcastBase;

import cn.edu.sdu.common.form.ListOptionInfo;


@Repository
public class LiveBroadcastBaseDaoImpl extends GenericServiceImpl<LiveBroadcastBase> implements LiveBroadcastBaseDao{

	public LiveBroadcastBaseDaoImpl(){
		super(LiveBroadcastBase.class);
	}

	@Override
	public LiveBroadcastBase getLiveBroadcastBaseByPersonId(Integer personId) {
		// TODO Auto-generated method stub
		String hql = "from LiveBroadcastBase lb where 1=1 ";
		if(personId!=null){
			hql+="and lb.personId="+personId;
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (LiveBroadcastBase) list.get(0);
		}
	}

	@Override
	public List getLiveBroadcastBaseInfoOptionListById(Integer id) {
		// TODO Auto-generated method stub
		String hql = "select id , streamName from LiveBroadcastBase where 1 = 1 and id ="+id;
		List list = this.queryForList(hql);
		if(list == null || list.size() == 0)
			return null;
		List rList = new ArrayList();
		Object a[];
		for(int i = 0; i < list.size(); i++) {
			a = (Object[])list.get(i);
			rList.add(new ListOptionInfo(a[0].toString(), a[0].toString()+"-"+a[1].toString()));
		}
		return rList;
	}

	@Override
	public LiveBroadcastBase getLiveBroadcastBaseByLiveId(Integer liveId) {
		// TODO Auto-generated method stub
		String sql = "from LiveBroadcastBase where 1 = 1 and id = "+liveId;
		List list = this.queryForList(sql);
		if(list == null || list.size() ==0) {
			return null;
		}else {
			return (LiveBroadcastBase)list.get(0);
		}
	}
}
