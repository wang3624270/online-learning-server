package org.octopus.common_business.notices.dao.Impl;

import java.util.List;

import org.octopus.common_business.notices.dao.NoticesInfoDao;
import org.octopus.common_business.notices.model.NoticesInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;


@Repository
public class NoticesInfoDaoImpl extends GenericServiceImpl<NoticesInfo> implements NoticesInfoDao {
   
	
	public NoticesInfoDaoImpl() {
		super(NoticesInfo.class);
	}
	
	public List<NoticesInfo>  getNoticesInfo(){
		String hql = "from NoticesInfo";
		List list = this.queryForList(hql);
		if(list!=null&list.size()!=0){
			return list;
		}else{
			return null;
		}
	}

}
