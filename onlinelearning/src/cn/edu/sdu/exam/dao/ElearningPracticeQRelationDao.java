package cn.edu.sdu.exam.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.model.ElearningPracticeQRelation;

public interface ElearningPracticeQRelationDao extends GenericServiceI<ElearningPracticeQRelation>{
	public List getQuestionListByPracticeId(Integer practiceId) ;
	public List getRelationListByQuestionId(Integer questionId);
}
