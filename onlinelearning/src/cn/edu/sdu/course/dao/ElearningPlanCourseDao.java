package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningPlanCourse;


public interface ElearningPlanCourseDao extends GenericServiceI<ElearningPlanCourse>{
	
	public List getCoursesListByPersonId(Integer personId);
	public List getTaskIdByStuId(Integer personId);
	public List getPlanListByTaskId(Integer taskId);
}
