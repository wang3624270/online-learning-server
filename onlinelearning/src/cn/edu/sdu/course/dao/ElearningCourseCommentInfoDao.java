package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourseCommentInfo;



public interface ElearningCourseCommentInfoDao extends GenericServiceI<ElearningCourseCommentInfo>{

	public boolean CheckIsGradeResource(Integer personId,Integer taskId,Integer sectionId);
	public List<ElearningCourseCommentInfo> getElearningResourceScoreInfoById(Integer taskId,Integer sectionId);
	public List getElearningResourceScoreListByCondition(Integer taskId,Integer sectionId);
	
}
