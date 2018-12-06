package cn.edu.sdu.lecture.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.lecture.dao.ElearningLectureDao;
import cn.edu.sdu.lecture.model.ElearningLecture;

@Repository
public class ElearningLectureDaoImpl extends GenericServiceImpl<ElearningLecture>
implements ElearningLectureDao {

	public ElearningLectureDaoImpl(){
		super(ElearningLecture.class);
		}

	@Override
	public List getLectureListByConditions(String theme, String lectureType) {
		// TODO Auto-generated method stub
		String hql = "from ElearningLecture a where 1 = 1";
		if(theme!=null && !theme.equals("")){
			hql+="and a.theme like '%"+theme+"%'";
		}
		if(lectureType!=null && !lectureType.equals("")){
			hql+="and a.lectureType='"+lectureType+"'";
		}
		hql+="order by a.createTime desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
}
