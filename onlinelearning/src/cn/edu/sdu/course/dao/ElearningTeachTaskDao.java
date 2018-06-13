package cn.edu.sdu.course.dao;

import java.text.ParseException;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningTeachTask;

public interface ElearningTeachTaskDao extends GenericServiceI<ElearningTeachTask>{

	public List getAllTeachTaskList();
	public List getTeachTaskListOfNotOverDate() throws ParseException;
	public ElearningTeachTask getTeachTaskByTaskId(Integer taskId);
	public List getTeachTaskListOfNotOverDateByTaskId(Integer taskId)throws ParseException;
	public Integer getTaskIdByCourseId(Integer courseId);
}
