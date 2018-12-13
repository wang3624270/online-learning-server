package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesCourseFolderDao;
import cn.edu.sdu.course.dao.ElearningInterlocutionInfoDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.ElearningInterlocutionInfo;

@Repository
public class ElearningInterlocutionInfoDaoImpl extends GenericServiceImpl<ElearningInterlocutionInfo>
    implements ElearningInterlocutionInfoDao{
	
	public ElearningInterlocutionInfoDaoImpl(){
		super(ElearningInterlocutionInfo.class);
	}
	
	@Override
	public List getInterlocutionListByCourseIdAndSectionId(Integer taskId, Integer sectionId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningInterlocutionInfo a where 1 = 1 ";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		if(sectionId!=null && !sectionId.equals("")){
			hql+="and a.sectionId='"+sectionId+"'";
		}
		hql+="order by a.taskId,a.sectionId";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return  list;
		}
	}
	
	@Override
	public List getInterlocutionListByConditions(Integer taskId, String state) {
		// TODO Auto-generated method stub
		String hql = "from ElearningInterlocutionInfo a where 1 = 1 ";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		if(state!=null && !state.equals("")){
			hql+="and a.state='"+state+"'";
		}
		hql+="order by a.createTime desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return  list;
		}
	}
}
