package cn.edu.sdu.course.dao;


import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.ElearningInterlocutionInfo;

public interface ElearningInterlocutionInfoDao extends GenericServiceI<ElearningInterlocutionInfo>{
	public List getInterlocutionListByCourseIdAndSectionId(Integer taskId, Integer sectionId);
}
