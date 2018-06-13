package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.course.model.ElearningCourseSection;

public interface ElearningCourseSectionDao extends GenericServiceI<ElearningCourseSection>{

	public List getSectionListByTaskId(Integer taskId);
}
