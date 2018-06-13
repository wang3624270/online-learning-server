package com.video.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import com.video.model.AudioVideoInfo;

import cn.edu.sdu.video.form.VideoQueryForm;

public interface AudioVideoInfoDao extends GenericServiceI<AudioVideoInfo> {
	List getAudioVideoInfoOptionListByQueryForm(VideoQueryForm qForm);
	//AudioVideoInfo getVideoNumByCreateTime(String time);
	List getVideoNumByCreateTime(String time);
}
