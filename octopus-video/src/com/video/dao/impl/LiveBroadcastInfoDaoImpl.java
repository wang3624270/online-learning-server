package com.video.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import com.video.dao.LiveBroadcastInfoDao;
import com.video.model.LiveBroadcastInfo;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.video.form.VideoQueryForm;

@Repository
public class LiveBroadcastInfoDaoImpl extends GenericServiceImpl<LiveBroadcastInfo> implements LiveBroadcastInfoDao {
	public LiveBroadcastInfoDaoImpl(){
		super(LiveBroadcastInfo.class);
	}

	@Override
	public List getLiveBroadcastInfoOptionListByQueryForm(VideoQueryForm qForm) {
		// TODO Auto-generated method stub
		String hql = " select id, title from LiveBroadcastInfo where 1=1";
		List list = this.queryForList(hql);
		if(list == null || list.size() == 0)
			return null;
		List rList = new ArrayList();
		Object a[];
		for(int i = 0; i < list.size();i++) {
			a = (Object[])list.get(i);
			rList.add(new ListOptionInfo(a[0].toString(),a[1].toString()));
		}
		return rList;
	}

	@Override
	public List getLiveBroadcastInfoOptionList1ByQueryForm(VideoQueryForm qForm) {
		// TODO Auto-generated method stub
		String sql = "from LiveBroadcastInfo live where 1 = 1 and live.liveState = "+qForm.getLiveState();
		if(!qForm.getTitle().equals("")) {
			sql += "and live.title like '%"+qForm.getTitle()+"%'";
		}
		return this.queryForList(sql);
	}

	@Override
	public List getLiveBroadcastInfoOptionList2ByQueryForm(VideoQueryForm qForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LiveBroadcastInfo getLiveBroadcastInfoByLiveId(Integer liveId) {
		// TODO Auto-generated method stub
		String sql = "from LiveBroadcastInfo where 1 = 1 and liveId = "+liveId;
		List list = this.queryForList(sql);
		if(list == null || list.size() == 0) {
			return null;
		}else {
			return (LiveBroadcastInfo)list.get(0);
		}
	}
	
	
}
