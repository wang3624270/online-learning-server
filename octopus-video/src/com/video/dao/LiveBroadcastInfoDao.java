package com.video.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import com.video.model.LiveBroadcastInfo;

import cn.edu.sdu.video.form.VideoQueryForm;

public interface LiveBroadcastInfoDao extends GenericServiceI<LiveBroadcastInfo> {
	List getLiveBroadcastInfoOptionListByQueryForm(VideoQueryForm qForm);
	List getLiveBroadcastInfoOptionList1ByQueryForm(VideoQueryForm qForm);
	List getLiveBroadcastInfoOptionList2ByQueryForm(VideoQueryForm qForm);
	public LiveBroadcastInfo getLiveBroadcastInfoByLiveId(Integer liveId);

}
