package com.video.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import com.video.dao.AudioVideoInfoDao;
import com.video.model.AudioVideoInfo;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.video.form.VideoQueryForm;

@Repository
public class AudioVideoInfoDaoImpl extends GenericServiceImpl<AudioVideoInfo> implements AudioVideoInfoDao {

	public AudioVideoInfoDaoImpl(){
		super(AudioVideoInfo.class);
	}

	@Override
	public List getAudioVideoInfoOptionListByQueryForm(VideoQueryForm qForm) {
		// TODO Auto-generated method stub
		String hql = " select id, title from AudioVideoInfo where 1=1";
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
	public List getVideoNumByCreateTime(String time) {
		// TODO Auto-generated method stub
		String sql = "from AudioVideoInfo a where 1 = 1 and a.videoNum like '%" + time + "%' order by a.videoNum";
		List list = this.queryForList(sql);
		if(list == null || list.size() == 0) {
			return null;
		}else {
			return list;
		}
	}

}
