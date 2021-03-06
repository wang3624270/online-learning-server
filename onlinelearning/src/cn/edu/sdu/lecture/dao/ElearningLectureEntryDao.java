package cn.edu.sdu.lecture.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningCourseSection;
import cn.edu.sdu.lecture.model.ElearningLecture;
import cn.edu.sdu.lecture.model.ElearningLectureEntry;

public interface ElearningLectureEntryDao extends GenericServiceI<ElearningLectureEntry>{
	
	public List getEntryListByConditions(Integer lectureId,Integer personId);
	public List getEntriedListByConditions(String theme, String lectureType,String state,Integer personId);
}
