package org.octopus.common_business.globalinfo.util;

import java.util.HashMap;
import java.util.List;

import org.octopus.common_business.globalinfo.dao.SystemGlobalVariableDao;
import org.octopus.common_business.globalinfo.model.SystemGlobalVariable;
import org.sdu.spring_util.ApplicationContextHandle;

public class SystemGlobalVariableUtils {
	private static SystemGlobalVariableUtils instance = new SystemGlobalVariableUtils();
	private HashMap<String, String> dataMap = new HashMap<String, String>();

	public static SystemGlobalVariableUtils getInstance() {
		return instance;
	}

	public  SystemGlobalVariableUtils(){
		initData();
	}
	public synchronized void initData(){
		List<SystemGlobalVariable> list = this.getSystemGlobalVariableDao().findAll();
		SystemGlobalVariable po;
		for(int i = 0; i < list.size();i++) {
			po = list.get(i);
			dataMap.put(po.getVariableName(), po.getVariableValue());
		}
	}
	public  SystemGlobalVariableDao getSystemGlobalVariableDao(){
		return (SystemGlobalVariableDao) ApplicationContextHandle.getBean("systemGlobalVariableDaoImpl");
	}

	public String getSystemGlobalVariableValue(String name) {
		return dataMap.get(name);
	}

	public HashMap getDataMap(){
		return dataMap;
	}
	public void updateSystemGlobalVariable(String name, String value){
		 dataMap.put(name, value);
	}

}
