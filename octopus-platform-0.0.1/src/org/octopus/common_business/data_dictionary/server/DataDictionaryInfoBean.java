package org.octopus.common_business.data_dictionary.server;

import org.octopus.common_business.data_dictionary.form.DataDictionary;

public class DataDictionaryInfoBean {
	/** 数据字典根节点 */
	private static DataDictionary datadicroot;
	
	/**
	 * 取得数据字典根节点
	 * 
	 * @return
	 */
	public static DataDictionary getDatadicroot() {
		return datadicroot;
	}

}
