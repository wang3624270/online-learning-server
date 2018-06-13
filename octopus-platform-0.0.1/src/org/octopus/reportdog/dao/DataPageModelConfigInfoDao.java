package org.octopus.reportdog.dao;

import java.util.Date;
import java.util.List;

import org.octopus.reportdog.model.DataPageModelConfigInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.transaction.annotation.Transactional;

public class DataPageModelConfigInfoDao extends GenericServiceImpl<DataPageModelConfigInfo> {
	public DataPageModelConfigInfoDao() {
		super(DataPageModelConfigInfo.class);
		// TODO Auto-generated constructor stub
	}

	public String getDataPageModelConfigInfo(String name) {
		// TODO Auto-generated method stub
		String hql = "select modelConfig.modelConfig from DataPageModelConfigInfo modelConfig where modelConfig.modelName='"
				+ name + "'";
		List<String> modelConfigList = this.queryForList(hql, null);
		if (modelConfigList != null && modelConfigList.size() > 0) {
			return modelConfigList.get(0);
		}
		return null;
	}

	public Date getDataPageModelConfigDateInfo(String name) {
		// TODO Auto-generated method stub
		String hql = "select modelConfig.timestamp from DataPageModelConfigInfo modelConfig where modelConfig.modelName='"
				+ name + "'";
		List<Date> modelConfigList = this.queryForList(hql, null);
		if (modelConfigList != null && modelConfigList.size() > 0) {
			return modelConfigList.get(0);
		}
		return null;
	}
	
	@Transactional
	public void updatePo(DataPageModelConfigInfo po){
		this.update(po);
	}
}
