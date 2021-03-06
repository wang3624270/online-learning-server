package cn.edu.sdu.course.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.ElearningTeachTaskDao;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.course.model.ElearningTeachTask;

@Repository
public class ElearningTeachTaskDaoImpl extends GenericServiceImpl<ElearningTeachTask>
implements ElearningTeachTaskDao {

	public ElearningTeachTaskDaoImpl(){
	super(ElearningTeachTask.class);
	}

	@Override
	public List getAllTeachTaskList() {
		// TODO Auto-generated method stub
		String hql = "from ElearningTeachTask a where 1 = 1";
		hql+="order by a.endDate desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public List getTeachTaskListOfNotOverDate() throws ParseException {
		// TODO Auto-generated method stub
		String hql = "from ElearningTeachTask a where 1 = 1";
		hql+="order by a.endDate desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			List InDateList=new ArrayList();
			for(int i=0;i<list.size();i++){
				ElearningTeachTask teachTask=(ElearningTeachTask) list.get(i);
				String endDate=teachTask.getEndDate();
				String pattern ="yyyy-MM-dd hh:mm:ss";//格式化日期格式
				SimpleDateFormat sf = new SimpleDateFormat(pattern);
				Date endTime = sf.parse(endDate);//把时间格式化
				Date nowDate=new Date();
				if(endTime.after(nowDate)){
					InDateList.add(teachTask);
				}
			}
			return InDateList;
		}
	}
	
	@Override
	public ElearningTeachTask getTeachTaskByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningTeachTask a where 1 = 1 ";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningTeachTask) list.get(0);
		}
	}
	@Override
	public List getTeachTaskListOfNotOverDateByTaskId(Integer taskId) throws ParseException {
		// TODO Auto-generated method stub
		String hql = "from ElearningTeachTask a where a.taskId='"+taskId+"'";
		hql+="order by a.endDate desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			List InDateList=new ArrayList();
			for(int i=0;i<list.size();i++){
				ElearningTeachTask teachTask=(ElearningTeachTask) list.get(i);
				String endDate=teachTask.getEndDate();
				String pattern ="yyyy-MM-dd hh:mm:ss";//格式化日期格式
				SimpleDateFormat sf = new SimpleDateFormat(pattern);
				Date endTime = sf.parse(endDate);//把时间格式化
				Date nowDate=new Date();
				if(endTime.after(nowDate)){
					InDateList.add(teachTask);
				}
			}
			return InDateList;
		}
	}
	@Override
	public Integer getTaskIdByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningTeachTask a where a.elearningCourse.courseId="+courseId;
		List list = this.queryForList(hql);
		Integer taskId;
		if(list == null || list.size()== 0)
			return null;
		else{
			ElearningTeachTask teachTask=(ElearningTeachTask) list.get(0);
			taskId = teachTask.getTaskId();
				}
	
			return taskId;
	}

	
	@Override
	public List getTeachTaskListByConditions(String taskName,String courseType) {
		// TODO Auto-generated method stub
		String hql = "select distinct a from ElearningTeachTask a ,ElearningCourse b where 1 = 1 ";
		if(taskName!=null && !taskName.equals("")){
			hql+="and a.taskName like '%"+taskName+"%'";
		}
		if(courseType!=null && !courseType.equals("")){
			hql+="and a.elearningCourse.courseId=b.courseId and b.courseType='"+courseType+"'";
		}
		hql+="order by a.modifyTime desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	

}
