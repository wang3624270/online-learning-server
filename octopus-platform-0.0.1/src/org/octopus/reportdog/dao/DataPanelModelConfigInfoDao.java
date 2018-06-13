package org.octopus.reportdog.dao;

import java.util.Date;
import java.util.List;

import org.octopus.reportdog.model.DataPageModelConfigInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;

public class DataPanelModelConfigInfoDao extends
GenericServiceImpl<DataPageModelConfigInfo> {
	public DataPanelModelConfigInfoDao() {
		super(DataPageModelConfigInfo.class);
	}

	public Date getDataPanelModelConfigDateInfo(String name) {
		// TODO Auto-generated method stub
		String hql = "select modelConfig.timestamp from DataPanelModelConfigInfo modelConfig where modelConfig.modelName='"
				+ name + "'";
		List<Date> modelConfigList = this.queryForList(hql, null);
		if (modelConfigList != null && modelConfigList.size() > 0) {
			return modelConfigList.get(0);
		}
		return null;
	}

	public String getDataPanelModelConfigInfo(String name) {
		// TODO Auto-generated method stub
		String hql = "select modelConfig.modelConfig from DataPanelModelConfigInfo modelConfig where modelConfig.modelName='"
				+ name + "'";
		List<String> modelConfigList = this.queryForList(hql, null);
		if (modelConfigList != null && modelConfigList.size() > 0) {
			return modelConfigList.get(0);
		}
		return null;
	}
}
