package cn.edu.sdu.manage.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningTermDao;
import cn.edu.sdu.course.model.ElearningTerm;
import cn.edu.sdu.manage.dao.ElearningPayRecordDao;
import cn.edu.sdu.manage.model.ElearningPayRecord;

@Repository
public class ElearningPayRecordDaoImpl extends GenericServiceImpl<ElearningPayRecord>
implements ElearningPayRecordDao {

	public ElearningPayRecordDaoImpl(){
	super(ElearningPayRecord.class);
	} 
	
	@Override
	public ElearningPayRecord getRecordByConditions(Integer personId,String state,Integer planId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningPayRecord a where 1=1";
		if (personId != null && !personId.equals("")) {
			hql += "and a.personId='" + personId + "'";
		}
		if (state != null && !state.equals("")) {
			hql += "and a.state='" + state + "'";
		}
		if (planId != null && !planId.equals("")) {
			hql += "and a.planId='" + planId + "'";
		}
		List list = this.queryForList(hql);
		if (list == null || list.size() == 0)
			return null;
		else {
			return (ElearningPayRecord) list.get(0);
		}
	}
	
	@Override
	public List getRecordListByConditions(String taskName,String courseType, String loginName,String perName,String state){
		// TODO Auto-generated method stub
		String hql = "select distinct a from ElearningPayRecord a , InfoPersonInfo p , SysUser u ,ElearningPlanCourse plan where a.planId=plan.id and a.personId=p.personId and a.personId=u.userid ";
		if (taskName != null && !taskName.equals("")) {
			hql += "and plan.elearningTeachTask.taskName='" + taskName + "'";
		}
		if (courseType != null && !courseType.equals("")) {
			hql += "and plan.elearningTeachTask.elearningCourse.courseType='" + courseType + "'";
		}
		if (loginName != null && !loginName.equals("")) {
			hql += "and u.loginName='" + loginName + "'";
		}
		if (perName != null && !perName.equals("")) {
			hql += "and p.perName='" + perName + "'";
		}
		if (state != null && !state.equals("")) {
			hql += "and a.state='" + state + "'";
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
