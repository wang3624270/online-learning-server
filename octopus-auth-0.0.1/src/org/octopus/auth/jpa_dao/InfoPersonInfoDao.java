package org.octopus.auth.jpa_dao;

import java.util.List;

import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.common.form.ListOptionInfo;



public interface InfoPersonInfoDao extends GenericServiceI<InfoPersonInfo>{
	
    
	public List findByPerIdCard(String driverIdCardNum);
	public List getAllInfoPersonInfo();
	public List getInfoPersonInfoByPerName(String name);
	public String getMaxPerNum();
    
	
	public InfoPersonInfo getInfoPersonInfoByPersonId(Integer personId);
	public InfoPersonInfo getInfoPersonInfoByPerIdCard(String perIdCard);
	public InfoPersonInfo getInfoPersonInfoByPerNum(String perNum);
	public InfoPersonInfo getInfoPersonInfobyPerName(String perName);
	public List getAllInfoPersonInfoList();
	public InfoPersonInfo getInfoPersonInfoByMobilePhone(String mobilePhone);
	public InfoPersonInfo getInfoPersonInfoByMobilePhone(String perTypeCode, String mobilePhone);
	public InfoPersonInfo findPersonInfoByPerNameAndPerIdCard(String perTypeCode, String mobilePhone);
	public InfoPersonInfo getPersonInfoByIdentityNumAndPerType(String perIdCard, String perTypeCode);
	public InfoPersonInfo getPersonInfoByPerNameAndPerIdCardNum(String perName,String perIdCardNum);
	public List<InfoPersonInfo> getInfoPersonInfoList(String perTypeCode, String perNum, String perName,String perIdCard,String genderCode,
			String mobilePhone, String email, String wechat, String qq);
	
	public ListOptionInfo getInfoPersonInfoPerNameOptionInfoList(Integer personId);
    public InfoPersonInfo getInfoPersonInfobyPrecisePerName(String perName);
    public List getPersonListbyConditions(Integer personId,String perName,Integer groupid);
    public List getListbyConditions(String loginName,String perName,String perTypeCode,Integer groupid);
}
