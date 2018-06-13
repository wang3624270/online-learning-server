package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseCommentInfoDao;
import cn.edu.sdu.course.model.ElearningCourseCommentInfo;


@Repository
public class ElearningCourseCommentInfoDaoImpl extends GenericServiceImpl<ElearningCourseCommentInfo>
implements ElearningCourseCommentInfoDao{
	
	public ElearningCourseCommentInfoDaoImpl(){
		super(ElearningCourseCommentInfo.class);
	}
	public boolean CheckIsGradeResource(Integer personId,Integer taskId,Integer sectionId){
		
		String hql = "from ElearningCourseCommentInfo a where 1 = 1 ";
		
		if(personId!=null && !personId.equals("")){
			hql+="and a.personId="+personId+"";
		}
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId="+taskId+"";
		}
		if(sectionId!=null && !sectionId.equals("")){
			hql+="and a.sectionId="+sectionId+"";
		}
		List list = this.queryForList(hql);
		
		if(list == null || list.size()== 0)
			return false;
		else{
			return true;
		}
		
	}
	
	public List<ElearningCourseCommentInfo> getElearningResourceScoreInfoById(Integer taskId,Integer sectionId){
        
		String hql = "from ElearningCourseCommentInfo a where 1 = 1 ";
		
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId="+taskId+"";
		}
		if(sectionId!=null && !sectionId.equals("")){
			hql+="and a.sectionId="+sectionId+"";
		}
		List<ElearningCourseCommentInfo> list = this.queryForList(hql);
		
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	public List getElearningResourceScoreListByCondition(Integer taskId,Integer sectionId){
		String hql = "from ElearningCourseCommentInfo a where 1 = 1 ";
		
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId="+taskId+"";
		}
		if(sectionId!=null && !sectionId.equals("")){
			hql+="and a.sectionId="+sectionId+"";
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
