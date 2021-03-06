package cn.edu.sdu.lecture.dao.impl;

import java.util.List;

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

	@Override
	public ElearningLectureLive getLiveByConditions(Integer lectureId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningLectureLive a where 1=1";
		if(lectureId!=null && !lectureId.equals("")){
			hql+="and a.lectureId="+lectureId;
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningLectureLive) list.get(0);
		}
	}
}
