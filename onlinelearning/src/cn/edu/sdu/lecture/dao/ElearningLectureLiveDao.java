package cn.edu.sdu.lecture.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.lecture.model.ElearningLectureAttendance;
import cn.edu.sdu.lecture.model.ElearningLectureLive;

public interface ElearningLectureLiveDao extends GenericServiceI<ElearningLectureLive>{

	public ElearningLectureLive getLiveByConditions(Integer lectureId);
}
