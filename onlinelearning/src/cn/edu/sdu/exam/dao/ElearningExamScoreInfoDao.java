package cn.edu.sdu.exam.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.model.ElearningExamScoreInfo;

public interface ElearningExamScoreInfoDao extends GenericServiceI<ElearningExamScoreInfo>{

	public ElearningExamScoreInfo getElearningExamScoreInfoByConditions(Integer stuId,Integer examId);
	public List getListByConditions(Integer stuId,Integer examId);
}
