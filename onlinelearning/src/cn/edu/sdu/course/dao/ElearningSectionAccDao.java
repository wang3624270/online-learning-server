package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.ElearningSectionAcc;

public interface ElearningSectionAccDao extends GenericServiceI<ElearningSectionAcc>{

	public List getAllListBySectionId(Integer sectionId);
	public List getListByAccId(Integer accId);
	public ElearningSectionAcc getMapById(Integer id);
	public ElearningSectionAcc getMapBySectionIdAndAccId(Integer sectionId,Integer accId,String type);
	public Integer getAccIdBySectionId(Integer sectionId);
}
