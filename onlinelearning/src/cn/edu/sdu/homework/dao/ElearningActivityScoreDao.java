package cn.edu.sdu.homework.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.homework.model.ElearningActivityScore;
import cn.edu.sdu.homework.model.ElearningHomeworkAnswer;

public interface ElearningActivityScoreDao extends GenericServiceI<ElearningActivityScore>{

	public ElearningActivityScore getScoreByIdAndStuId(Integer activityId,Integer stuId);
}