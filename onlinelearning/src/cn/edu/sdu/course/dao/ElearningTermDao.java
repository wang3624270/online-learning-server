package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.course.model.ElearningTerm;

public interface ElearningTermDao extends GenericServiceI<ElearningTerm>{
	public List getAllTermList();
	public ElearningTerm getTermInfoById(Integer termId);
}
