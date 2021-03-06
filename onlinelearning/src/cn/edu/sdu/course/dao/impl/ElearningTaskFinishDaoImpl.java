package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.ElearningTaskFinishDao;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.ElearningSectionAcc;
import cn.edu.sdu.course.model.ElearningTaskFinish;

@Repository
public class ElearningTaskFinishDaoImpl extends GenericServiceImpl<ElearningTaskFinish>
implements ElearningTaskFinishDao {

	public ElearningTaskFinishDaoImpl(){
	super(ElearningTaskFinish.class);
	}

	@Override
	public ElearningTaskFinish getElearningTaskFinish(Integer taskId,Integer courseId,Integer sectionId,Integer personId,String isFinish) {
		// TODO Auto-generated method stub
		String hql = "from ElearningTaskFinish a where 1=1 ";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		if(courseId!=null && !courseId.equals("")){
			hql+="and a.courseId='"+courseId+"'";
		}
		if(sectionId!=null && !sectionId.equals("")){
			hql+="and a.sectionId='"+sectionId+"'";
		}
		if(personId!=null && !personId.equals("")){
			hql+="and a.stuId='"+personId+"'";
		}
		if(isFinish!=null && !isFinish.equals("")){
			hql+="and a.isFinish='"+isFinish+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningTaskFinish) list.get(0);
		}
	}
	
	@Override
	public List getListByConditions(Integer taskId,Integer courseId,Integer sectionId,Integer personId,String isFinish) {
		// TODO Auto-generated method stub
		String hql = "from ElearningTaskFinish a where 1=1 ";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		if(courseId!=null && !courseId.equals("")){
			hql+="and a.courseId='"+courseId+"'";
		}
		if(sectionId!=null && !sectionId.equals("")){
			hql+="and a.sectionId='"+sectionId+"'";
		}
		if(personId!=null && !personId.equals("")){
			hql+="and a.stuId='"+personId+"'";
		}
		if(isFinish!=null && !isFinish.equals("")){
			hql+="and a.isFinish='"+isFinish+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}


}
