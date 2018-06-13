package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;

public interface ElearningCourseDao extends GenericServiceI<ElearningCourse>{

	public List getAllCoursesList();
	public List getCoursesListByPersonId(Integer personId);
	public ElearningCourse getCourseInfoByCourseId(Integer courseId);
	public List<ElearningCourse> getCourseInfoByCourseType(String courseType);
}
