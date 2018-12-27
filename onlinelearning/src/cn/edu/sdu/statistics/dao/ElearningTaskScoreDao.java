package cn.edu.sdu.statistics.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.statistics.model.ElearningTaskScore;


public interface ElearningTaskScoreDao extends GenericServiceI<ElearningTaskScore> {

	public List getScoreListByConditions(Integer stuId,Integer taskId);
}
