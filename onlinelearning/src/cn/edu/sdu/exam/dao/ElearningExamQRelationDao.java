package cn.edu.sdu.exam.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.model.ElearningExamQRelation;

public interface ElearningExamQRelationDao extends GenericServiceI<ElearningExamQRelation>{

	public List getQuestionListByExamId(Integer examId);
	public List  getRelationListByQuestionId(Integer questionId);
	
}
