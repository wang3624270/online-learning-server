package cn.edu.sdu.homework.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesCourseFolderDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.ElearningTeachTask;
import cn.edu.sdu.homework.dao.ElearningHomeworkInfoDao;
import cn.edu.sdu.homework.model.ElearningHomeworkInfo;

@Repository
public class ElearningHomeworkInfoDaoImpl extends GenericServiceImpl<ElearningHomeworkInfo>
    implements ElearningHomeworkInfoDao{
	public ElearningHomeworkInfoDaoImpl(){
		super(ElearningHomeworkInfo.class);
	}
	
	@Override
	public List getHomelistListByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningHomeworkInfo a where 1 = 1 ";
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
	
	@Override
	public  ElearningHomeworkInfo getHomeworkInfoById(Integer homeworkId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningHomeworkInfo a where 1 = 1 ";
		if(homeworkId!=null && !homeworkId.equals("")){
			hql+="and a.homeworkId='"+homeworkId+"'";
		}
		ElearningHomeworkInfo homework = (ElearningHomeworkInfo) this.queryForList(hql);
		if(homework == null )
			return null;
		else{
			return homework;
		}
	}
}
