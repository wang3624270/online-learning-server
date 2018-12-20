package cn.edu.sdu.homework.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.homework.dao.ElearningActivityScoreDao;
import cn.edu.sdu.homework.dao.ElearningHomeworkInfoDao;
import cn.edu.sdu.homework.model.ElearningActivityScore;
import cn.edu.sdu.homework.model.ElearningHomeworkAnswer;
import cn.edu.sdu.homework.model.ElearningHomeworkInfo;

@Repository
public class ElearningActivityScoreDaoImpl extends GenericServiceImpl<ElearningActivityScore>
    implements ElearningActivityScoreDao{
	
	public ElearningActivityScoreDaoImpl(){
		super(ElearningActivityScore.class);
	}
	
	@Override
	public ElearningActivityScore getScoreByIdAndStuId(Integer activityId,Integer stuId){
		// TODO Auto-generated method stub
		String hql = "from ElearningActivityScore a where 1 = 1 ";
		if(activityId!=null && !activityId.equals("")){
			hql+="and a.activityId='"+activityId+"'";
		}
		if(stuId!=null && !stuId.equals("")){
			hql+="and a.stuId='"+stuId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningActivityScore) list.get(0);
		}
	}
}
