package cn.edu.sdu.lecture.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;
import cn.edu.sdu.lecture.model.ElearningLecture;

public interface ElearningLectureDao extends GenericServiceI<ElearningLecture>{

	public List getLectureListByConditions(String theme,String lectureType);
}
