package cn.edu.sdu.exam.dao.impl;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningExamScoreInfoDao;
import cn.edu.sdu.exam.model.ElearningExamScoreInfo;

@Repository
public class ElearningExamScoreInfoDaoImpl extends GenericServiceImpl<ElearningExamScoreInfo>
implements ElearningExamScoreInfoDao {

	public ElearningExamScoreInfoDaoImpl(){
		super(ElearningExamScoreInfo.class);
		}
	
	
	
}
