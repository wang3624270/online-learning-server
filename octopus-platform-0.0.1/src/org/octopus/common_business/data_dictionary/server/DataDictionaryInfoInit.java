package org.octopus.common_business.data_dictionary.server;

import java.lang.reflect.Field;


import javax.annotation.PostConstruct;

import org.octopus.common_business.common.rule.RefreshBufferedDataProcessorI;
import org.octopus.common_business.data_dictionary.form.DataDictionary;
import org.octopus.common_business.data_dictionary.rule.DataDictionaryInfoInitRule;
import org.sdu.spring_util.JPAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataDictionaryInfoInit implements RefreshBufferedDataProcessorI {
	@Autowired
	private DataDictionaryInfoInitRule dataDictionaryInfoInitRule;

	@PostConstruct
	public void init() {
		JPAUtils.beginJPASession();
		initDataDictionary(); 
		JPAUtils.endJPASession();
	}

	/**
	 * 将数据库常用信息载入内存
	 * 
	 * @param servlet
	 * @param config
	 */
	synchronized public void initDataDictionary() {
		// 取得数据字典列表
		DataDictionary datadicroot = dataDictionaryInfoInitRule.getDataDictionaryRoot();
		// 对SystemInfoBean对象设值
		this.setDataDictionaryInfoBeanProperty("datadicroot", datadicroot);
	}

	/**
	 * 对SystemInfoBean对象设值
	 * 
	 * @param fieldname
	 * @param value
	 */
	private void setDataDictionaryInfoBeanProperty(String fieldname, Object value) {
		try {
			Field field = DataDictionaryInfoBean.class.getDeclaredField(fieldname);

			field.setAccessible(true);
			field.set(DataDictionaryInfoBean.class.newInstance(), value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setDataMapTableInfoBeanProperty(String fieldname, Object value) {
		try {
			Field field = DataMapTableInfoBean.class.getDeclaredField(fieldname);

			field.setAccessible(true);
			field.set(DataMapTableInfoBean.class.newInstance(), value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DataDictionaryInfoInitRule getDataDictionaryInfoInitRule() {
		return dataDictionaryInfoInitRule;
	}

	public void setDataDictionaryInfoInitRule(DataDictionaryInfoInitRule dataDictionaryInfoInitRule) {
		this.dataDictionaryInfoInitRule = dataDictionaryInfoInitRule;
	}

	@Override
	public void doRefreshData() {
		// TODO Auto-generated method stub
		initDataDictionary();
	}
}
