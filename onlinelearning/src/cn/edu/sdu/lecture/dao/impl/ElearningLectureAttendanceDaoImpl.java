package cn.edu.sdu.lecture.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.lecture.dao.ElearningLectureAttendanceDao;
import cn.edu.sdu.lecture.dao.ElearningLectureEntryDao;
import cn.edu.sdu.lecture.model.ElearningLectureAttendance;
import cn.edu.sdu.lecture.model.ElearningLectureEntry;

@Repository
public class ElearningLectureAttendanceDaoImpl extends GenericServiceImpl<ElearningLectureAttendance>
implements ElearningLectureAttendanceDao {

	public ElearningLectureAttendanceDaoImpl(){
		super(ElearningLectureAttendance.class);
	}

	@Override
	public ElearningLectureAttendance getAttendanceByConditions(Integer lectureId,Integer entryId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningLectureAttendance a where 1=1";
		if(lectureId!=null && !lectureId.equals("")){
			hql+="and a.lectureId="+lectureId;
		}
		if(entryId!=null && !entryId.equals("")){
			hql+="and a.entryId="+entryId;
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningLectureAttendance) list.get(0);
		}
	}
	
	@Override
	public List getAttendanceListByConditions(Integer lectureId,Integer personId,Integer entryId,String attendance) {
		// TODO Auto-generated method stub
		String hql = "from ElearningLectureAttendance a where 1=1";
		if(lectureId!=null && !lectureId.equals("")){
			hql+="and a.lectureId="+lectureId;
		}
		if(entryId!=null && !entryId.equals("")){
			hql+="and a.entryId="+entryId;
		}
		if(personId!=null && !personId.equals("")){
			hql+="and a.stuId="+personId;
		}
		if(attendance!=null && !attendance.equals("")){
			hql+="and a.attendance="+attendance;
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
}
