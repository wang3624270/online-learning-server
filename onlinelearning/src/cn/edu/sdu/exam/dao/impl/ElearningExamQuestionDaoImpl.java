package cn.edu.sdu.exam.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningExamQuestionDao;
import cn.edu.sdu.exam.model.ElearningExamQuestion;

@Repository
public class ElearningExamQuestionDaoImpl extends GenericServiceImpl<ElearningExamQuestion>
implements ElearningExamQuestionDao{

	public ElearningExamQuestionDaoImpl(){
		super(ElearningExamQuestion.class);
		}
	
	@Override
	public List getQuestionListByQuestionType(String questionType) {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamQuestion a where 1 = 1 ";
		if(questionType!=null && !questionType.equals("")){
			hql+="and a.questionType='"+questionType+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return  list;
		}
	}
	
	@Override
	public List getQuestionListOrderByQuestionType() {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamQuestion a where 1 = 1 ";
		hql+="order by a.questionType asc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return  list;
		}
	}
	
	@Override
	public List getQuestionListByConditions(String question,String questionType) {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamQuestion a where 1 = 1 ";
		if(question!=null && !question.equals("")){
			hql+="and a.question like '%"+question+"%'";
		}
		if(questionType!=null && !questionType.equals("")){
			hql+="and a.questionType='"+questionType+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return  list;
		}
	}

	
}
