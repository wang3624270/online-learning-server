package cn.edu.sdu.exam.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningExamQRelationDao;
import cn.edu.sdu.exam.model.ElearningExamQRelation;

@Repository
public class ElearningExamQRelationDaoImpl extends GenericServiceImpl<ElearningExamQRelation>
implements ElearningExamQRelationDao{

	public ElearningExamQRelationDaoImpl(){
		super(ElearningExamQRelation.class);
		}
	
	@Override
	public List getQuestionListByExamId(Integer examId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamQRelation a where 1 = 1 ";
		if(examId!=null && !examId.equals("")){
			hql+="and a.examId='"+examId+"'";
		}
		hql+="order by a.number asc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public List getRelationListByQuestionId(Integer questionId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamQRelation a where 1 = 1 ";
		if(questionId!=null && !questionId.equals("")){
			hql+="and a.questionId='"+questionId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
}
