package cn.edu.sdu.exam.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningPracticeQRelationDao;
import cn.edu.sdu.exam.model.ElearningPracticeQRelation;

@Repository
public class ElearningPracticeQRelationDaoImpl extends GenericServiceImpl<ElearningPracticeQRelation>
implements ElearningPracticeQRelationDao {

	public ElearningPracticeQRelationDaoImpl(){
		super(ElearningPracticeQRelation.class);
		}
	
	
	@Override
	public List getQuestionListByPracticeId(Integer practiceId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningPracticeQRelation a where 1 = 1 ";
		if(practiceId!=null && !practiceId.equals("")){
			hql+="and a.practiceId='"+practiceId+"'";
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
		String hql = "from ElearningPracticeQRelation a where 1 = 1 ";
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
