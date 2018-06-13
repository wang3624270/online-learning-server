package com.video.bean.rmi;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sdu.file_util.FileUtility;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.video.dao.AudioVideoInfoDao;
import com.video.form.AudioVideoInfoForm;
import com.video.model.AudioVideoInfo;
import com.video.rule.AudioVideoInfoProcessRule;

import cn.edu.sdu.video.form.VideoQueryForm;

@Service
public class AudioVideoInfoProcessRmi {
	
	@Autowired
	private AudioVideoInfoDao audioVideoInfoDao;
	@Autowired
	private AudioVideoInfoProcessRule audioVideoInfoProcessRule;

	public AudioVideoInfoForm getAudioVideoInfoFormFromPo(AudioVideoInfo po){
		AudioVideoInfoForm f = new AudioVideoInfoForm();
		f.setId(po.getId());
		f.setTitle(po.getTitle());
		f.setBrief(po.getBrief());  
		f.setLongbrief(po.getLongbrief());
		f.setAttachId(po.getAttachId());
		f.setVideoType(po.getVideoType());  // '视频代码是 1 音频代码是2',
		f.setVideoNum(po.getVideoNum());
		f.setAuthorId(po.getAuthorId());
		f.setIsPossess(po.getIsPossess());
		f.setCreatetime(po.getCreatetime());
		f.setLength(po.getLength());
		f.setSize(po.getSize());
		f.setLikecount(po.getLikecount());
		f.setCollectcount(po.getCollectcount());
		f.setReadcount(po.getReadcount());
		f.setSharecount(po.getSharecount());
		f.setIsVisable(po.getIsVisable());
		return f;
	}
	public void setAudioVideoInfoFromForm(AudioVideoInfoForm f, AudioVideoInfo po){
		po.setTitle(f.getTitle());
		po.setBrief(f.getBrief());  
		po.setLongbrief(f.getLongbrief());
		po.setAttachId(f.getAttachId());
		po.setVideoType(f.getVideoType());  // '视频代码是1 音频代码是2',
		po.setAuthorId(1);
		Date time = f.getCreatetime();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(time);
		List list = new ArrayList();
		list = audioVideoInfoDao.getVideoNumByCreateTime(date);
		if(list == null || list.size() == 0) {
			po.setVideoNum(date+"01");
		}else {
			Integer max = list.size()-1;
			AudioVideoInfo info = (AudioVideoInfo)list.get(max);
			Integer num = Integer.valueOf(info.getVideoNum())+1;
			po.setVideoNum(String.valueOf(num));
		}
		//po.setVideoNum(time.toString());
		po.setCreatetime(f.getCreatetime());
		po.setLength(f.getLength());
		po.setSize(f.getSize());
		po.setLikecount(f.getLikecount());
		po.setIsPossess(f.getIsPossess());
		po.setCollectcount(f.getCollectcount());
		po.setReadcount(f.getReadcount());
		po.setSharecount(f.getSharecount());
		po.setIsVisable(f.getIsVisable());
	}
	
	public void getVideoNum(RmiRequest request,RmiResponse respond) {
		Date time = (Date)request.get("createtime");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(time);
		List list = new ArrayList();
		list = audioVideoInfoDao.getVideoNumByCreateTime(date);
		if(list == null || list.size() == 0) {
			respond.add("videoNum", date+"01");
		}else {
			Integer max = list.size()-1;
			AudioVideoInfo info = (AudioVideoInfo)list.get(max);
			Integer num = Integer.valueOf(info.getVideoNum())+1;
			respond.add("videoNum", String.valueOf(num));
		}
	}
	public void getAudioVideoInfoOptionListByQueryForm(RmiRequest request, RmiResponse response){
		VideoQueryForm qForm = (VideoQueryForm)request.get(RmiKeyConstants.KEY_FORM);
		response.add(RmiKeyConstants.KEY_FORMLIST, audioVideoInfoDao.getAudioVideoInfoOptionListByQueryForm(qForm));
	}
	
	public void getAudioVideoInfoFormById(RmiRequest request, RmiResponse response){
		Integer videoId = (Integer)request.get("videoId");
		AudioVideoInfo po = audioVideoInfoDao.query(videoId);
		AudioVideoInfoForm f = getAudioVideoInfoFormFromPo(po);
		response.add(RmiKeyConstants.KEY_FORM, f);
	}
	public void saveOrUpdateAudioVideoInfo(RmiRequest request, RmiResponse response){
		AudioVideoInfoForm f = (AudioVideoInfoForm)request.get(RmiKeyConstants.KEY_FORM);
		AudioVideoInfo po = null;
		if(f.getId() != null)
			po = audioVideoInfoDao.query(f.getId());
		if(po == null)
			po = new AudioVideoInfo();
		this.setAudioVideoInfoFromForm(f, po);
		if(po.getId() == null) {
			audioVideoInfoDao.save(po);
			response.add("videoId", po.getId());
		}else {
			audioVideoInfoDao.update(po);
		}
	}
	public void deleteAudioVideoInfo(RmiRequest request, RmiResponse response){
		Integer videoId = (Integer)request.get("videoId");
		if(videoId == null)
			return;
		AudioVideoInfo po = audioVideoInfoDao.query(videoId);
		audioVideoInfoDao.delete(po);
	}
	
	public void uploadAudioVideoFile(RmiRequest request,RmiResponse respond) {
		byte[] data = (byte[])request.get("data");		
		if(data == null)
			return;
		//Integer videoId = (Integer)request.get("videoId");
		InputStream in = new ByteArrayInputStream(data);
		/*String size = (String)request.get("size");
		long time = (long)request.get("time");
		Integer length = new Long(time).intValue();*/
		String videoNum = (String)request.get("videoNum");
		String path = "video/"+videoNum+"/test.mp4";
		try {
			FileUtility.uploadFile(in, path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*AudioVideoInfo info = audioVideoInfoDao.query(videoId);
		info.setLength(length);
		info.setSize(size);
		audioVideoInfoDao.update(info);*/
	}
	
	public void upLoadImageFile(RmiRequest request,RmiResponse respond) {
		byte[] data = (byte[])request.get("data");
		if(data == null)
			return;
		Integer videoId = (Integer)request.get("videoId");
		Integer attachId = (Integer)request.get("attachId");
		String videoNum = (String)request.get("videoNum");
		InputStream in = new ByteArrayInputStream(data);
		String path = "videoPhoto/"+ videoNum +"/Attach.jpg";
		attachId = audioVideoInfoProcessRule.doUpLoadImageFile(attachId, path, in);
		AudioVideoInfo info = audioVideoInfoDao.query(videoId);
		info.setAttachId(attachId);
		audioVideoInfoDao.update(info);
		respond.add("attachId", attachId);		
	}
}
