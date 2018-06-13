package cn.edu.sdu.exam.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.exam.dao.ElearningExamOptionDao;
import cn.edu.sdu.exam.model.ElearningExamOption;

@Repository
public class ElearningExamOptionDaoImpl extends GenericServiceImpl<ElearningExamOption>
implements ElearningExamOptionDao{
	
	public ElearningExamOptionDaoImpl(){
		super(ElearningExamOption.class);
		}


	@Override
	public List getOptionListByQuestionId(Integer questionId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamOption a where 1 = 1 ";
		if(questionId!=null && !questionId.equals("")){
			hql+="and a.questionId='"+questionId+"'";
		}
		hql+="order by a.number asc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
}
