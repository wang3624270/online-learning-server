package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.ElearningPlanCourseDao;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.ElearningPlanCourse;

@Repository
public class ElearningPlanCourseDaoImpl extends GenericServiceImpl<ElearningPlanCourse>
		implements ElearningPlanCourseDao {

	public ElearningPlanCourseDaoImpl() {
		super(ElearningPlanCourse.class);
	}

	@Override
	public List getCoursesListByPersonId(Integer personId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningPlanCourse a where 1 = 1 ";
		if (personId != null && !personId.equals("")) {
			hql += "and a.stuId='" + personId + "'";
		}
		List list = this.queryForList(hql);
		if (list == null || list.size() == 0)
			return null;
		else {
			return list;
		}
	}
	
	@Override
	public List getTaskIdByStuId(Integer personId) {
		String hql = "from ElearningPlanCourse a where a.stuId=" + personId;
		List list = this.queryForList(hql);
		if (list == null || list.size() == 0)
			return null;
		else {
			return list;
		}
	}
	
	@Override
	public List getPlanListByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningPlanCourse a where 1 = 1 ";
		if (taskId != null && !taskId.equals("")) {
			hql += "and a.elearningTeachTask.taskId='" + taskId + "'";
		}
		List list = this.queryForList(hql);
		if (list == null || list.size() == 0)
			return null;
		else {
			return list;
		}
	}
	


	@Override
	public List getTaskListByConditions(Integer personId,String courseName,String courseType,Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningPlanCourse a where 1=1";
		if (personId != null && !personId.equals("")) {
			hql += "and a.stuId='" + personId + "'";
		}
		if (courseName != null && !courseName.equals("")) {
			hql += "and a.elearningTeachTask.elearningCourse.courseName like'%" + courseName + "%'";
		}
		if (courseType != null && !courseType.equals("")) {
			hql += "and a.elearningTeachTask.elearningCourse.courseType='" + courseType + "'";
		}
		if (taskId != null && !taskId.equals("")) {
			hql += "and a.elearningTeachTask.taskId='" + taskId + "'";
		}
		List list = this.queryForList(hql);
		if (list == null || list.size() == 0)
			return null;
		else {
			return list;
		}
	}
	
	@Override
	public List getListByConditions(Integer personId,String taskName,String courseType,Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningPlanCourse a where 1=1";
		if (personId != null && !personId.equals("")) {
			hql += "and a.stuId='" + personId + "'";
		}
		if (taskName != null && !taskName.equals("")) {
			hql += "and a.elearningTeachTask.taskName like'%" + taskName + "%'";
		}
		if (courseType != null && !courseType.equals("")) {
			hql += "and a.elearningTeachTask.elearningCourse.courseType='" + courseType + "'";
		}
		if (taskId != null && !taskId.equals("")) {
			hql += "and a.elearningTeachTask.taskId='" + taskId + "'";
		}
		List list = this.queryForList(hql);
		if (list == null || list.size() == 0)
			return null;
		else {
			return list;
		}
	}
	
	@Override
	public List getPlanListByConditions(String loginName,String perName,Integer taskId,String state){
		// TODO Auto-generated method stub
		String hql = "select distinct a from ElearningPlanCourse a , InfoPersonInfo p , SysUser u where 1=1";
		if (taskId != null && !taskId.equals("")) {
			hql += "and a.elearningTeachTask.taskId='" + taskId + "'";
		}
		if (state != null && !state.equals("")) {
			hql += "and a.state='" + state + "'";
		}
		if (perName != null && !perName.equals("")) {
			hql += "and a.stuId=p.personId and p.perName='" + perName + "'";
		}
		if (loginName != null && !loginName.equals("")) {
			hql += "and a.stuId=u.userid and u.loginName='" + loginName + "'";
		}
		hql+=" order by a.createTime desc";
		List list = this.queryForList(hql);
		if (list == null || list.size() == 0)
			return null;
		else {
			return list;
		}
	}

}
