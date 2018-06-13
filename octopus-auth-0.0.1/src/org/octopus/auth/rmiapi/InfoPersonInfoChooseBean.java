package org.octopus.auth.rmiapi;

import java.util.ArrayList;
import java.util.List;

import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.sdu.common.form.InfoPersonInfoForm;

@Service
@Scope("singleton")
public class InfoPersonInfoChooseBean {
	@Autowired
	private InfoPersonInfoDao infoPersonInfoDao;
	public void getInfoPersonInfoformListUserChoose(RmiRequest request, RmiResponse response){
		String perTypeCode = (String)request.get("perTypeCode");
		String perNum = (String)request.get("perNum");
		String perName =(String)request.get("perName");
		String mobilePhone = (String)request.get("mobilePhone");
		List<InfoPersonInfo> list = infoPersonInfoDao.getInfoPersonInfoList(perTypeCode, perNum, perName, null,null, mobilePhone, null, null, null);
		if(list == null || list.size() == 0)
			return;
		InfoPersonInfoForm f;
		InfoPersonInfo p;
		List formList = new ArrayList();
		for(int i = 0; i < list.size();i++){
			p = list.get(i);
			f = new InfoPersonInfoForm();
			f.setPerTypeCode(p.getPerTypeCode());
			f.setPersonId(p.getPersonId());
			f.setPerNum(p.getPerNum());
			f.setPerName(p.getPerName());
			f.setMobilePhone(p.getMobilePhone());
			f.setGenderCode(p.getGenderCode());
			f.setPerIdCard(p.getPerIdCard());
			formList.add(f);
		}
		response.add(RmiKeyConstants.KEY_FORMLIST, formList);
	}
}
