package com.video.bean.rmi;

import java.util.Date;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.dao.LiveBroadcastBaseDao;
import com.video.dao.LiveBroadcastInfoDao;
import com.video.dao.LiveHubDao;
import com.video.form.LiveBroadcastInfoForm;
import com.video.model.LiveBroadcastBase;
import com.video.model.LiveBroadcastInfo;
import com.video.model.LiveHub;

import cn.edu.sdu.video.form.VideoQueryForm;

@Service
public class LiveBroadcastInfoProcessRmi {
	@Autowired
	private LiveBroadcastInfoDao liveBroadcastInfoDao;
	@Autowired
	private LiveBroadcastBaseDao liveBroadcastBaseDao;
	@Autowired
	private LiveHubDao liveHubDao;

	public LiveBroadcastInfoForm getLiveBroadcastInfoFormFromPo(LiveBroadcastInfo po){
		LiveBroadcastInfoForm f = new LiveBroadcastInfoForm();
		f.setId(po.getId());
		f.setTitle(po.getTitle());
		f.setBrief(po.getBrief());
		f.setLongbrief(po.getLongbrief());
		f.setAttachId(po.getAttachId());
		f.setPushurl(po.getPushurl());
		LiveHub hub = liveHubDao.find(po.getHubId());
		f.setHubName(hub.getHubName());
		f.setPlayurl(po.getPlayurl());
		LiveBroadcastBase info = liveBroadcastBaseDao.getLiveBroadcastBaseByLiveId(po.getLiveId());
		f.setStreamName(info.getStreamName());
		f.setReadCount(po.getReadCount());
		f.setCreatetime(po.getCreatetime());
		f.setStartTime(po.getStartTime());
		f.setEndTime(po.getEndTime());
		//f.setIsVisable(po.getIsVisable());
		f.setLiveId(po.getLiveId());
		return f;
	}
	
	public void setLiveBroadcastInfoFromForm(LiveBroadcastInfoForm f, LiveBroadcastInfo po){
		po.setTitle(f.getTitle());
		po.setBrief(f.getBrief());
		po.setLongbrief(f.getLongbrief());
		po.setAttachId(f.getAttachId());
		po.setPushurl(f.getPushurl());
		po.setPlayurl(f.getPlayurl());
		//po.setStreamName(f.getStreamName());
		po.setHubId(po.getHubId());
		po.setReadCount(f.getReadCount());
		po.setCreatetime(f.getCreatetime());
		po.setStartTime(f.getStartTime());
		po.setEndTime(f.getEndTime());
		//po.setIsVisable(f.getIsVisable());
		po.setLiveId(f.getLiveId());
	}
	
	public void getLiveBroadcastInfoOptionListByQueryForm(RmiRequest request, RmiResponse respond){
		VideoQueryForm qForm = (VideoQueryForm)request.get(RmiKeyConstants.KEY_FORM);
		LiveBroadcastInfo info = new LiveBroadcastInfo();
		List list = null;
		list = liveBroadcastInfoDao.getLiveBroadcastInfoOptionList1ByQueryForm(qForm);
		if(list == null || list.size() == 0)
			return;
	    for(int i = 0; i < list.size(); i++) {
			info = (LiveBroadcastInfo)list.get(i);
			respond.add(RmiKeyConstants.KEY_FORMLIST,liveBroadcastBaseDao.getLiveBroadcastBaseInfoOptionListById(info.getLiveId()));
		}
	}
	
	public void getLiveBroadcastInfoFormById(RmiRequest request, RmiResponse response){
		Integer broadcastId = (Integer)request.get("broadcastId");
		LiveBroadcastInfo po = liveBroadcastInfoDao.query(broadcastId);
		LiveBroadcastInfoForm f = getLiveBroadcastInfoFormFromPo(po);
		response.add(RmiKeyConstants.KEY_FORM, f);
	}
	public void saveOrUpdateLiveBroadcastInfo(RmiRequest request, RmiResponse response){
		LiveBroadcastInfoForm f = (LiveBroadcastInfoForm)request.get(RmiKeyConstants.KEY_FORM);
		LiveBroadcastInfo po = null;
		if(f.getId() != null)
			po = liveBroadcastInfoDao.query(f.getId());
		if(po == null)
			po = new LiveBroadcastInfo();
		setLiveBroadcastInfoFromForm(f, po);
		if(po.getId() != null) {
			liveBroadcastInfoDao.save(po);
			response.add("broadcastId", po.getId());
		}else {
			liveBroadcastInfoDao.update(po);
		}
	}
	public void deleteLiveBroadcastInfo(RmiRequest request, RmiResponse response){
		Integer broadcastId = (Integer)request.get("broadcastId");
		if(broadcastId == null)
			return;
		LiveBroadcastInfo po = liveBroadcastInfoDao.query(broadcastId);
		liveBroadcastInfoDao.delete(po);
	}
	
	public void getBroadcastIdByLiveId(RmiRequest request, RmiResponse respond) {
		Integer liveId = (Integer)request.get("liveId");
		if(liveId == null)
			return;
		LiveBroadcastInfo po = liveBroadcastInfoDao.getLiveBroadcastInfoByLiveId(liveId);
		respond.add("broadcastId", po.getId());
	}
	
	public void setBroadcastInfoLiveState(RmiRequest request,RmiResponse respond) {
		Integer id = (Integer)request.get("id");
		Integer tag = (Integer)request.get("tag");
		if(id == null || tag == null)
			return;
		LiveBroadcastInfo po = liveBroadcastInfoDao.find(id);
		if(tag == 1) {
			po.setLiveState(1);
		}else if(tag == 2) {
			po.setLiveState(2);
		}
		liveBroadcastInfoDao.save(po);
	}

}
