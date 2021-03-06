package cn.edu.sdu.homework.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.homework.dao.ElearningActivityInfoDao;
import cn.edu.sdu.homework.model.ElearningActivityInfo;

@Repository
public class ElearningActivityInfoDaoImpl extends GenericServiceImpl<ElearningActivityInfo>
    implements ElearningActivityInfoDao{
	
	public ElearningActivityInfoDaoImpl(){
		super(ElearningActivityInfo.class);
	}

	@Override
	public List getActivitylistByConditions(String activityName,String taskName) {
		// TODO Auto-generated method stub
		String hql = "select distinct a from ElearningActivityInfo a , ElearningTeachTask t  where 1 = 1 ";
		if(activityName!=null && !activityName.equals("")){
			hql+="and a.activityName like '%"+activityName+"%'";
		}
		if(taskName!=null && !taskName.equals("")){
			hql+=" and a.taskId=t.taskId and t.taskName like '%"+taskName+"%'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public List getListByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningActivityInfo a where 1 = 1 ";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
}
