package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningTaskFinish;

public interface ElearningTaskFinishDao extends GenericServiceI<ElearningTaskFinish>{

	public ElearningTaskFinish getElearningTaskFinish(Integer taskId,Integer courseId,Integer sectionId,Integer personId,String isFinish);
	public List getListByConditions(Integer taskId,Integer courseId,Integer sectionId,Integer personId,String isFinish);
}
