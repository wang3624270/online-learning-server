package cn.edu.sdu.course.dao.impl;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.BaseCampusInfoDao;
import cn.edu.sdu.course.model.BaseCampusInfo;
import cn.edu.sdu.course.model.BaseCollege;

@Repository
public class BaseCampusInfoDaoImpl extends GenericServiceImpl<BaseCampusInfo>
       implements BaseCampusInfoDao{

	public BaseCampusInfoDaoImpl(){
		super(BaseCampusInfo.class);
	}

}
