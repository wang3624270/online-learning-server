package org.octopus.reportdog.dao;

import java.util.Date;
import java.util.List;

import org.octopus.reportdog.model.DataSourceConfigInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;

public class DataSourceConfigInfoDao extends
GenericServiceImpl<DataSourceConfigInfo> {
	// 根据名称获得数据来源配置信息

	public DataSourceConfigInfoDao() {
		super(DataSourceConfigInfo.class);
		// TODO Auto-generated constructor stub
	}

	public String getDataSourceConfigInfo(String pageName) {
		// TODO Auto-generated method stub
		String hql = "select sourceConfig.dataSource from DataSourceConfigInfo sourceConfig where sourceConfig.sourceName='"
				+ pageName + "'";
		List<String> sourceConfigList = this.queryForList(hql, null);
		if (sourceConfigList != null && sourceConfigList.size() > 0) {
			return sourceConfigList.get(0);
		}
		return null;
	}

	public Date getDataSourceConfigDateInfo(String pageName) {
		// TODO Auto-generated method stub
		String hql = "select sourceConfig.timestamp from DataSourceConfigInfo sourceConfig where sourceConfig.sourceName='"
				+ pageName + "'";
		List<Date> sourceConfigList = this.queryForList(hql, null);
		if (sourceConfigList != null && sourceConfigList.size() > 0) {
			return sourceConfigList.get(0);
		}
		return null;
	}

}
