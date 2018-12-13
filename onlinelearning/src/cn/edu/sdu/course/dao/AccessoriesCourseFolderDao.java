package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;

public interface AccessoriesCourseFolderDao extends GenericServiceI<AccessoriesCourseFolder>{
	
	public AccessoriesCourseFolder getFolferByCourseIdAndTaskId(Integer courseId,Integer taskId);
	public List getFolferTreeById(Integer id);
	public AccessoriesCourseFolder getFolderById(Integer id);
	public List getFolferListByCourseId(Integer courseId);
	public List getFolferListByTaskId(Integer taskId);
	public List getAllListByCourseId(Integer courseId);
}
