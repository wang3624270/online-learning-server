package cn.edu.sdu.course.dao;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningTaskCharge;

public interface ElearningTaskChargeDao extends GenericServiceI<ElearningTaskCharge> {
	
	public ElearningTaskCharge getChargeByTaskId(Integer taskId);
}
