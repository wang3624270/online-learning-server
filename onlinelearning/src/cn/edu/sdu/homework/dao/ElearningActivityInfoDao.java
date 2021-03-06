package cn.edu.sdu.homework.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.homework.model.ElearningActivityInfo;

public interface ElearningActivityInfoDao extends GenericServiceI<ElearningActivityInfo>{


	public List getActivitylistByConditions(String activityName,String taskName);
	public List getListByTaskId(Integer taskId);

}