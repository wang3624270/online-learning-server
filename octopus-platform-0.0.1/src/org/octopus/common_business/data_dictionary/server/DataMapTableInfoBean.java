package org.octopus.common_business.data_dictionary.server;

import java.util.HashMap;

public class DataMapTableInfoBean {
	private static HashMap<String, HashMap<String,String>> datamaptable;
	
	/**
	 * 取得数据字典根节点
	 * 
	 * @return
	 */
	public static HashMap<String, HashMap<String,String>> getDataMap() {
		return datamaptable;
	}

}
