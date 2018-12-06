package cn.edu.sdu.lecture.dao.impl;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.lecture.dao.ElearningLectureAttendanceDao;
import cn.edu.sdu.lecture.dao.ElearningLectureLiveDao;
import cn.edu.sdu.lecture.model.ElearningLectureAttendance;
import cn.edu.sdu.lecture.model.ElearningLectureLive;

@Repository
public class ElearningLectureLiveDaoImpl extends GenericServiceImpl<ElearningLectureLive>
implements ElearningLectureLiveDao {

	public ElearningLectureLiveDaoImpl(){
		super(ElearningLectureLive.class);
	}

}
