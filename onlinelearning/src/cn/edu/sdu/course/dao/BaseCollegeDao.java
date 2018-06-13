package cn.edu.sdu.course.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.BaseCollege;

public interface BaseCollegeDao extends GenericServiceI<BaseCollege>{
	public String getCollegeNameById(Integer collegeId);
	public List getAllCollegeList();
}
