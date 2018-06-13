package org.octopus.common_business.notices.dao;

import java.util.List;

import org.octopus.common_business.notices.model.NoticesInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;

public interface NoticesInfoDao extends GenericServiceI<NoticesInfo> {
	public List<NoticesInfo>  getNoticesInfo();
	
}
