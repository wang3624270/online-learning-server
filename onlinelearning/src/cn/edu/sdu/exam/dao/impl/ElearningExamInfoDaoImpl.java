package cn.edu.sdu.exam.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.exam.dao.ElearningExamInfoDao;
import cn.edu.sdu.exam.model.ElearningExamInfo;


@Repository
public class ElearningExamInfoDaoImpl extends GenericServiceImpl<ElearningExamInfo>
implements ElearningExamInfoDao{
	public ElearningExamInfoDaoImpl(){
		super(ElearningExamInfo.class);
		}
	
	@Override
	public List getExamListByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningExamInfo a where 1 = 1 ";
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
