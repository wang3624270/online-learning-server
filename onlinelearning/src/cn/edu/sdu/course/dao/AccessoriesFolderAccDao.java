package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;



public interface AccessoriesFolderAccDao extends GenericServiceI<AccessoriesFolderAcc>{
	public AccessoriesFolderAcc getFolferAccByConditions(Integer folderId,String type);
	public AccessoriesFolderAcc getFolderByAccId(Integer accId);
	public List getAccListByFolderId(Integer folderId);
	public AccessoriesFolderAcc getFolferAccByCondition(Integer folderId,Integer accId,String type);
	public List getFolderListByAccId(Integer accId);
}
