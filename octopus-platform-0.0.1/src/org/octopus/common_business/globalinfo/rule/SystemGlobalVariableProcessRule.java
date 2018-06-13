package org.octopus.common_business.globalinfo.rule;

import java.util.ArrayList;
import java.util.List;

import org.octopus.common_business.globalinfo.dao.SystemGlobalVariableDao;
import org.octopus.common_business.globalinfo.form.SystemGlobalVariableForm;
import org.octopus.common_business.globalinfo.model.SystemGlobalVariable;
import org.octopus.common_business.globalinfo.util.SystemGlobalVariableUtils;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.spring_util.ApplicationContextHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemGlobalVariableProcessRule  {
	@Autowired
	private SystemGlobalVariableDao systemGlobalVariableDao;

	
	public String getSystemGlobalVariableByVariableName(String name) {
		SystemGlobalVariable reg = systemGlobalVariableDao
				.findSystemGlobalVariableByVariableName(name);
		if (reg == null)
			return null;
		else
			return reg.getVariableValue();
		
	}
	
	public void getSystemGlobalVariableList(RmiRequest request, RmiResponse respond){
		List<SystemGlobalVariable> list = systemGlobalVariableDao.findAll();
		if(list == null || list.size() == 0)
			return;
		List retList = new ArrayList();
		SystemGlobalVariableForm f;
		SystemGlobalVariable po;
		for(int i = 0; i < list.size();i++){
			po = list.get(i);
			f = new SystemGlobalVariableForm();
			f.setVariableId(po.getVariableId());
			f.setVariableValue(po.getVariableValue());
			f.setVariableName(po.getVariableName());
			f.setVariableDes(po.getVariableDes());
			f.setVariableType(po.getVariableType());
			retList.add(f);
		}
		respond.add(RmiKeyConstants.KEY_FORMLIST, retList);
	}

	
	public void updateSystemGlobalVariable(RmiRequest request, RmiResponse respond){
		SystemGlobalVariableForm f = (SystemGlobalVariableForm)request.get(RmiKeyConstants.KEY_FORM);
		SystemGlobalVariable po = systemGlobalVariableDao.findSystemGlobalVariableById(f.getVariableId());
		if(po == null)
			return;
		po.setVariableValue(f.getVariableValue());
		systemGlobalVariableDao.savePo(po);
	}
	
	public void updateSystemGlobalVariableByName(RmiRequest request, RmiResponse respond){
		String name  = (String )request.get("name");
		String value  =  (String )request.get("value");
		SystemGlobalVariable po = systemGlobalVariableDao.findSystemGlobalVariableByVariableName(name);
		if(po == null)
			return;
		po.setVariableValue(value);
		systemGlobalVariableDao.savePo(po);
		SystemGlobalVariableUtils.getInstance().updateSystemGlobalVariable(name, value);
	}
	public void getSystemGlobalVariableMap(RmiRequest request, RmiResponse respond){
		respond.add(RmiKeyConstants.KEY_HASH, SystemGlobalVariableUtils.getInstance().getDataMap());
	}
	
	public void getApplicationName(RmiRequest request, RmiResponse response) {
		String applicationName = ApplicationContextHandle.getApplicationName();
		response.add(RmiKeyConstants.APPLICATIONNAME, applicationName);
	}

	
}
