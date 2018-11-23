package org.octopus.auth.jpa_dao;

import java.util.List;

import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.common.form.ListOptionInfo;



public interface InfoPersonInfoDao extends GenericServiceI<InfoPersonInfo>{
	
    
	List findByPerIdCard(String driverIdCardNum);
	List getAllInfoPersonInfo();
	List getInfoPersonInfoByPerName(String name);
	String getMaxPerNum();
    
	
	public InfoPersonInfo getInfoPersonInfoByPersonId(Integer personId);
	public InfoPersonInfo getInfoPersonInfoByPerIdCard(String perIdCard);
	public InfoPersonInfo getInfoPersonInfoByPerNum(String perNum);
	public InfoPersonInfo getInfoPersonInfobyPerName(String perName);
	List getAllInfoPersonInfoList();
	InfoPersonInfo getInfoPersonInfoByMobilePhone(String mobilePhone);
	InfoPersonInfo getInfoPersonInfoByMobilePhone(String perTypeCode, String mobilePhone);
	InfoPersonInfo findPersonInfoByPerNameAndPerIdCard(String perTypeCode, String mobilePhone);
	InfoPersonInfo getPersonInfoByIdentityNumAndPerType(String perIdCard, String perTypeCode);
	InfoPersonInfo getPersonInfoByPerNameAndPerIdCardNum(String perName,String perIdCardNum);
	List<InfoPersonInfo> getInfoPersonInfoList(String perTypeCode, String perNum, String perName,String perIdCard,String genderCode,
			String mobilePhone, String email, String wechat, String qq);
	
	public ListOptionInfo getInfoPersonInfoPerNameOptionInfoList(Integer personId);
    public InfoPersonInfo getInfoPersonInfobyPrecisePerName(String perName);
    public List getPersonListbyConditions(Integer personId,String perName,Integer groupid);
}
