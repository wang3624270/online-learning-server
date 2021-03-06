package cn.edu.sdu.exam.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningExamStuAnswerDao;
import cn.edu.sdu.exam.model.ElearningExamStuAnswer;

@Repository
public class ElearningExamStuAnswerDaoImpl extends GenericServiceImpl<ElearningExamStuAnswer>
implements ElearningExamStuAnswerDao {
	
	public ElearningExamStuAnswerDaoImpl(){
		super(ElearningExamStuAnswer.class);
		}

	@Override
	public ElearningExamStuAnswer getElearningExamStuAnswerByQuestionId(Integer examId,Integer questionId,Integer stuId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamStuAnswer a where 1 = 1 ";
		if(examId!=null && !examId.equals("")){
			hql+="and a.examId='"+examId+"'";
		}
		if(questionId!=null && !questionId.equals("")){
			hql+="and a.questionId='"+questionId+"'";
		}
		if(stuId!=null && !stuId.equals("")){
			hql+="and a.stuId='"+stuId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningExamStuAnswer) list.get(0);
		}
	}
	
	@Override
	public List getListByConditions(Integer examId,Integer questionId,Integer stuId ){
		// TODO Auto-generated method stub
		String hql = "from ElearningExamStuAnswer a where 1 = 1 ";
		if(examId!=null && !examId.equals("")){
			hql+="and a.examId='"+examId+"'";
		}
		if(questionId!=null && !questionId.equals("")){
			hql+="and a.questionId='"+questionId+"'";
		}
		if(stuId!=null && !stuId.equals("")){
			hql+="and a.stuId='"+stuId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
}
