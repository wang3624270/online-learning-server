package cn.edu.sdu.course.dao.impl;

import java.util.Date;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.course.dao.AccessoriesCourseFolderDao;
import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;

@Repository
public class AccessoriesCourseFolderDaoImpl extends GenericServiceImpl<AccessoriesCourseFolder>
		implements AccessoriesCourseFolderDao {

	public AccessoriesCourseFolderDaoImpl(){
		super(AccessoriesCourseFolder.class);
	}

	@Override
	public AccessoriesCourseFolder getFolferByCourseIdAndTaskId(
			Integer courseId, Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesCourseFolder a where 1 = 1 ";
		if(courseId!=null && !courseId.equals("")){
			hql+="and a.courseId='"+courseId+"'";
		}
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (AccessoriesCourseFolder) list.get(0);
		}
	}
	
	@Override
	public AccessoriesCourseFolder getFolderById(Integer id) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesCourseFolder a where 1=1";
		if(id!=null && !id.equals("")){
			hql+="and a.id='"+id+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (AccessoriesCourseFolder) list.get(0);
		}
	}
	
	
	
	@Override
	public List getFolferListByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesCourseFolder a where a.fatherId='0' and isLeaf='0'";
		if(courseId!=null && !courseId.equals("")){
			hql+="and a.courseId='"+courseId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public List getAllListByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesCourseFolder a where 1=1 ";
		if(courseId!=null && !courseId.equals("")){
			hql+="and a.courseId='"+courseId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}

	@Override
	public List getFolferTreeById(Integer id) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesCourseFolder a where 1 = 1 ";
		if(id!=null && !id.equals("")){
			hql+="and a.fatherId='"+id+"'";
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
	public List getFolferListByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesCourseFolder a where 1=1";
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