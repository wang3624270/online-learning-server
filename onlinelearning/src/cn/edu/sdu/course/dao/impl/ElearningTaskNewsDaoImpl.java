package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesCourseFolderDao;
import cn.edu.sdu.course.dao.ElearningTaskNewsDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.ElearningTaskNews;

@Repository
public class ElearningTaskNewsDaoImpl extends GenericServiceImpl<ElearningTaskNews>
implements ElearningTaskNewsDao{

	public ElearningTaskNewsDaoImpl(){
		super(ElearningTaskNews.class);
	}
	
	@Override
	public List getNewsListByTaskId(Integer taskId){
		// TODO Auto-generated method stub
		String hql = "from ElearningTaskNews a where 1 = 1 ";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		hql+="order by createTime desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
}
