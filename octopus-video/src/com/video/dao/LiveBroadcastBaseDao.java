package com.video.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import com.video.model.AudioVideoInfo;
import com.video.model.LiveBroadcastBase;

public interface LiveBroadcastBaseDao extends GenericServiceI<LiveBroadcastBase>{
	public LiveBroadcastBase getLiveBroadcastBaseByPersonId(Integer personId);
	List getLiveBroadcastBaseInfoOptionListById(Integer id);
	public LiveBroadcastBase getLiveBroadcastBaseByLiveId(Integer liveId);
}
