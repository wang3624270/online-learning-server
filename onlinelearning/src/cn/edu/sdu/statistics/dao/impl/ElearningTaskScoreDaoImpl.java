package cn.edu.sdu.statistics.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.exam.model.ElearningExamScoreInfo;
import cn.edu.sdu.statistics.dao.ElearningTaskScoreDao;
import cn.edu.sdu.statistics.model.ElearningTaskScore;


@Repository
public class ElearningTaskScoreDaoImpl extends GenericServiceImpl<ElearningTaskScore>
implements ElearningTaskScoreDao {

	public ElearningTaskScoreDaoImpl(){
	super(ElearningTaskScore.class);
	} 

	@Override
	public List getScoreListByConditions(Integer stuId,Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningTaskScore a where 1 = 1 ";
		if(stuId!=null && !stuId.equals("")){
			hql+="and a.stuId ='"+stuId+"'";
		}
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId ='"+taskId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
}
