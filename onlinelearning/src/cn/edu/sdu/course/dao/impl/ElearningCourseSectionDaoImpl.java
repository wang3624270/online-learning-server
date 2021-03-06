package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.BaseCollegeDao;
import cn.edu.sdu.course.dao.ElearningCourseSectionDao;
import cn.edu.sdu.course.model.BaseCollege;
import cn.edu.sdu.course.model.ElearningCourseSection;

@Repository
public class ElearningCourseSectionDaoImpl extends GenericServiceImpl<ElearningCourseSection>
implements ElearningCourseSectionDao{
	public ElearningCourseSectionDaoImpl(){
		super(ElearningCourseSection.class);
	}
	
	@Override
	public List getSectionListByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningCourseSection a where 1=1";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId="+taskId;
		}
		hql+="order by a.orderNum";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public Integer getMaxOrderNum(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningCourseSection a where 1=1";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId="+taskId;
		}
		hql+="order by a.orderNum desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return 0;
		else{
			ElearningCourseSection section=(ElearningCourseSection) list.get(0);
			return section.getOrderNum();
		}
	}
	
	@Override
	public ElearningCourseSection getSectionByConditions(Integer taskId,Integer orderNum) {
		// TODO Auto-generated method stub
		String hql = "from ElearningCourseSection a where 1=1";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId="+taskId;
		}
		if(orderNum!=null && !orderNum.equals("")){
			hql+="and a.orderNum="+orderNum;
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningCourseSection) list.get(0);
		}
	}
}
