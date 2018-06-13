package cn.edu.sdu.exam.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.model.ElearningPracticeInfo;

public interface ElearningPracticeInfoDao extends GenericServiceI<ElearningPracticeInfo>{

	public List getPracticeList();
}
