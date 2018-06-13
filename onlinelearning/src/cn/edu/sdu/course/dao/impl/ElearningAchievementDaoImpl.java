package cn.edu.sdu.course.dao.impl;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.ElearningAchievementDao;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.ElearningAchievement;

@Repository
public class ElearningAchievementDaoImpl extends GenericServiceImpl<ElearningAchievement>
     implements ElearningAchievementDao {

	public ElearningAchievementDaoImpl(){
	    super(ElearningAchievement.class);
	}




}
