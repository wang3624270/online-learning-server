package org.octopus.common_business.data_dictionary.rule;

import org.octopus.common_business.data_dictionary.constants.DataDictionaryConstants;
import org.octopus.common_business.data_dictionary.form.DataDictionary;
import org.octopus.common_business.data_dictionary.jpa_dao.BaseDataDictionaryDao;
import org.octopus.common_business.data_dictionary.server.DataDictionaryInfoBean;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 系统信息初始化_访问数据库类
 * 
 */
@Service
public class DataDictionaryInfoInitRule {

	@Autowired
	private BaseDataDictionaryDao baseDataDictionaryDao;

	public DataDictionaryInfoInitRule() {

	}

	public DataDictionary getDataDictionaryRoot() {
		DataDictionary datadicroot = baseDataDictionaryDao.getDataDictionaryList();
		return datadicroot;
	}

	public void dataDictionaryGet(RmiRequest request, RmiResponse response) throws Exception {
		// 取得数据字典列表
		DataDictionary datadicroot = DataDictionaryInfoBean.getDatadicroot();
		response.add(DataDictionaryConstants.DataDicKey, datadicroot);
	}



}
