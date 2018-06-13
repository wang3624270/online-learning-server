package cn.edu.sdu.homework.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.homework.model.ElearningHomeworkAnswer;

public interface ElearningHomeworkAnswerDao extends GenericServiceI<ElearningHomeworkAnswer>{
	
	public List getAnswerListByHomeworkId(Integer homeworkId);
	public ElearningHomeworkAnswer getAnswerByHomeworkIdAndStuId(Integer homeworkId,Integer stuId);
	//public ElearningHomeworkAnswer submitHomeworkAnswer(ElearningHomeworkAnswer homeworkAnswer);
}
