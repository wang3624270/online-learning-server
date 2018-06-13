package cn.edu.sdu.exam.dao.impl;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningPracticeStuAnswerDao;
import cn.edu.sdu.exam.model.ElearningPracticeStuAnswer;

@Repository
public class ElearningPracticeStuAnswerDaoImpl extends GenericServiceImpl<ElearningPracticeStuAnswer>
implements ElearningPracticeStuAnswerDao {

	public ElearningPracticeStuAnswerDaoImpl(){
		super(ElearningPracticeStuAnswer.class);
		}

	
}
