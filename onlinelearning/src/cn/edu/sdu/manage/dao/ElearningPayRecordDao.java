package cn.edu.sdu.manage.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.manage.model.ElearningPayRecord;

public interface ElearningPayRecordDao extends GenericServiceI<ElearningPayRecord> {

	public ElearningPayRecord getRecordByConditions(Integer personId,String state,Integer planId);
	public List getRecordListByConditions(String taskName,String courseType, String loginName,String perName,String state);
}
