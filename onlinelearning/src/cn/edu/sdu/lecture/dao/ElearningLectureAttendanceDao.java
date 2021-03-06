package cn.edu.sdu.lecture.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.lecture.model.ElearningLectureAttendance;
import cn.edu.sdu.lecture.model.ElearningLectureEntry;

public interface ElearningLectureAttendanceDao extends GenericServiceI<ElearningLectureAttendance> {

	public ElearningLectureAttendance getAttendanceByConditions(Integer lectureId,Integer entryId);

	public List getAttendanceListByConditions(Integer lectureId,Integer personId,Integer entryId,String attendance);
}
