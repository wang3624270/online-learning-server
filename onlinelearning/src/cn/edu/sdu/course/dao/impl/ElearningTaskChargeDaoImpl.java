package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningTaskChargeDao;
import cn.edu.sdu.course.dao.ElearningTermDao;
import cn.edu.sdu.course.model.ElearningTaskCharge;
import cn.edu.sdu.course.model.ElearningTerm;

@Repository
public class ElearningTaskChargeDaoImpl extends GenericServiceImpl<ElearningTaskCharge>
implements ElearningTaskChargeDao {

	public ElearningTaskChargeDaoImpl(){
	super(ElearningTaskCharge.class);
	} 
	
	@Override
	public ElearningTaskCharge getChargeByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningTaskCharge a where 1 = 1 ";
		if(taskId!=null && !taskId.equals("")){
			hql+="and a.taskId='"+taskId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return  (ElearningTaskCharge) list.get(0);
		}
	}

}
