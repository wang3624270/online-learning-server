package cn.edu.sdu.homework.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesCourseFolderDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.homework.dao.ElearningHomeworkAnswerDao;
import cn.edu.sdu.homework.model.ElearningHomeworkAnswer;

@Repository
public class ElearningHomeworkAnswerDaoImpl extends GenericServiceImpl<ElearningHomeworkAnswer>
implements ElearningHomeworkAnswerDao{

	public ElearningHomeworkAnswerDaoImpl(){
		super(ElearningHomeworkAnswer.class);
	}

	@Override
	public List getAnswerListByHomeworkId(Integer homeworkId){
		// TODO Auto-generated method stub
		String hql = "from ElearningHomeworkAnswer a where 1 = 1 ";
		if(homeworkId!=null && !homeworkId.equals("")){
			hql+="and a.homeworkId='"+homeworkId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public ElearningHomeworkAnswer getAnswerByHomeworkIdAndStuId(Integer homeworkId,Integer stuId){
		// TODO Auto-generated method stub
		String hql = "from ElearningHomeworkAnswer a where 1 = 1 ";
		if(homeworkId!=null && !homeworkId.equals("")){
			hql+="and a.homeworkId='"+homeworkId+"'";
		}
		if(stuId!=null && !stuId.equals("")){
			hql+="and a.stuId='"+stuId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningHomeworkAnswer) list.get(0);
		}
	}
	
}
