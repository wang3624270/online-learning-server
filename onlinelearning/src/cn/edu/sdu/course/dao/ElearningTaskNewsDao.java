package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningTaskNews;

public interface ElearningTaskNewsDao extends GenericServiceI<ElearningTaskNews>{

	public List getNewsListByTaskId(Integer taskId);
}
