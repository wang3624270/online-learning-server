package cn.edu.sdu.exam.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.model.ElearningExamQuestion;

public interface ElearningExamQuestionDao extends GenericServiceI<ElearningExamQuestion>{

	public List getQuestionListByQuestionType(String questionType);
	public List getQuestionListOrderByQuestionType();
}
