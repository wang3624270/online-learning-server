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

}
