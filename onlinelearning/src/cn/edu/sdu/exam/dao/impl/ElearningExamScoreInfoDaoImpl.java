package cn.edu.sdu.exam.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningExamScoreInfoDao;
import cn.edu.sdu.exam.model.ElearningExamScoreInfo;

@Repository
public class ElearningExamScoreInfoDaoImpl extends GenericServiceImpl<ElearningExamScoreInfo>
implements ElearningExamScoreInfoDao {

	public ElearningExamScoreInfoDaoImpl(){
		super(ElearningExamScoreInfo.class);
		}
	
	@Override
	public ElearningExamScoreInfo getElearningExamScoreInfoByConditions(Integer stuId,Integer examId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamScoreInfo a where 1 = 1 ";
		if(stuId!=null && !stuId.equals("")){
			hql+="and a.stuId ='"+stuId+"'";
		}
		if(examId!=null && !examId.equals("")){
			hql+="and a.examId ='"+examId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningExamScoreInfo) list.get(0);
		}
	}
	
	@Override
	public List getListByConditions(Integer stuId,Integer examId){
		// TODO Auto-generated method stub
		String hql = "from ElearningExamScoreInfo a where 1 = 1 ";
		if(stuId!=null && !stuId.equals("")){
			hql+="and a.stuId ='"+stuId+"'";
		}
		if(examId!=null && !examId.equals("")){
			hql+="and a.examId ='"+examId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	
}
