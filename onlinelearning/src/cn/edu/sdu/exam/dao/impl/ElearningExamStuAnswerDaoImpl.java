package cn.edu.sdu.exam.dao.impl;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningExamStuAnswerDao;
import cn.edu.sdu.exam.model.ElearningExamStuAnswer;

@Repository
public class ElearningExamStuAnswerDaoImpl extends GenericServiceImpl<ElearningExamStuAnswer>
implements ElearningExamStuAnswerDao {
	
	public ElearningExamStuAnswerDaoImpl(){
		super(ElearningExamStuAnswer.class);
		}


}
