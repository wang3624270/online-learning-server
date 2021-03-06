package cn.edu.sdu.course.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.ElearningCourse;

@Repository
public class ElearningCourseDaoImpl extends GenericServiceImpl<ElearningCourse>
implements ElearningCourseDao {

	public ElearningCourseDaoImpl(){
	super(ElearningCourse.class);
	}

	@Override
	public List getAllCoursesList() {
		// TODO Auto-generated method stub
		String hql = "from ElearningCourse a where 1 = 1";
		hql+="order by a.courseNum";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public List getCoursesListByPersonId(Integer personId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningCourse a where 1 = 1";
		if(personId!=null && !personId.equals("")){
			hql+="and a.creatorId='"+personId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public ElearningCourse getCourseInfoByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningCourse a where 1 = 1";
		if(courseId!=null && !courseId.equals("")){
			hql+="and a.courseId='"+courseId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningCourse) list.get(0);
		}
	}
	public List<ElearningCourse> getCourseInfoByCourseType(String courseType){
		String hql = "from ElearningCourse a where 1 = 1";
		if(courseType!=null && !courseType.equals("")){
			hql+="and a.courseType='"+courseType+"'";
		}
		List<ElearningCourse> list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return  list;
		}
	}
	
	@Override
	public List getCoursesListByConditions(String courseName,String courseType) {
		// TODO Auto-generated method stub
		String hql = "from ElearningCourse a where 1 = 1";
		if(courseName!=null && !courseName.equals("")){
			hql+="and a.courseName='"+courseName+"'";
		}
		if(courseType!=null && !courseType.equals("")){
			hql+="and a.courseType='"+courseType+"'";
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
