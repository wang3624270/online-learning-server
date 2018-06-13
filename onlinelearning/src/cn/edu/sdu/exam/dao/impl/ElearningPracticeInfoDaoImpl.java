package cn.edu.sdu.exam.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningPracticeInfoDao;
import cn.edu.sdu.exam.model.ElearningPracticeInfo;

@Repository
public class ElearningPracticeInfoDaoImpl extends GenericServiceImpl<ElearningPracticeInfo>
implements ElearningPracticeInfoDao{

	public ElearningPracticeInfoDaoImpl(){
		super(ElearningPracticeInfo.class);
		}
	
	@Override
	public List getPracticeList() {
		// TODO Auto-generated method stub
		String hql = "from ElearningPracticeInfo a where 1 = 1 ";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}

	
}
