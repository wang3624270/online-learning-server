package cn.edu.sdu.exam.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.model.ElearningExamStuAnswer;

public interface ElearningExamStuAnswerDao extends GenericServiceI<ElearningExamStuAnswer>{

	public ElearningExamStuAnswer getElearningExamStuAnswerByQuestionId(Integer examId,Integer questionId,Integer stuId);
	public List getListByConditions(Integer examId,Integer questionId,Integer stuId);
}
