package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.AccessoriesInfo;



public interface AccessoriesInfoDao extends GenericServiceI<AccessoriesInfo>{
	public AccessoriesInfo getAccById(Integer id);
	public List getAllResourceList();
	public List getResourceListByConditions(String accName,String accType,String uploader);
}
