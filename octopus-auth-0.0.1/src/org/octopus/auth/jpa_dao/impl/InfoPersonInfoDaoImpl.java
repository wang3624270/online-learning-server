package org.octopus.auth.jpa_dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.common.form.ListOptionInfo;

@Repository
public class InfoPersonInfoDaoImpl extends GenericServiceImpl<InfoPersonInfo> implements InfoPersonInfoDao {

	public InfoPersonInfoDaoImpl() {
		super(InfoPersonInfo.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List getAllInfoPersonInfo() {
		// TODO Auto-generated method stub
		String sql = "from InfoPersonInfo where 1 = 1";
		return this.queryForList(sql);
	}

	@Override
	public List getInfoPersonInfoByPerName(String perName) {
		// TODO Auto-generated method stub
		String sql = "from InfoPersonInfo a where 1 = 1 and a.perName like '%" + perName + "%'";
		return this.queryForList(sql);
	}
	
	@Override
	public List findByPerIdCard(String driverIdCardNum) {
		String sql = "from InfoPersonInfo a where 1 = 1 and a.perIdCard like '%" + driverIdCardNum + "%'";
		return this.queryForList(sql);
	}

	@Override
	public InfoPersonInfo getInfoPersonInfoByPersonId(Integer personId) {
		String sql = "from InfoPersonInfo where personId = " + personId;
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			return (InfoPersonInfo) list.get(0);
		}
		return null;
	}

	@Override
	public InfoPersonInfo getInfoPersonInfoByPerIdCard(String perIdCard) {
		String sql = "from InfoPersonInfo where perIdCard = '" + perIdCard + "'";
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			return (InfoPersonInfo) list.get(0);
		}
		return null;
	}

	@Override
	public String getMaxPerNum() {

		String sql = "select max(p.perNum) from InfoPersonInfo p";
		String num = (String) this.queryForObject(sql, null);
		if (num == null || num.length() == 0) {
			return null;
		} else
			return num;
	}

	@Override
	public List getAllInfoPersonInfoList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfoPersonInfo getInfoPersonInfoByMobilePhone(String mobilePhone) {
		// TODO Auto-generated method stub
		String sql = "from InfoPersonInfo where mobilePhone = " + mobilePhone;
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			return (InfoPersonInfo) list.get(0);
		}
		return null;
	}

	@Override
	public InfoPersonInfo getInfoPersonInfoByMobilePhone(String perTypeCode, String mobilePhone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfoPersonInfo findPersonInfoByPerNameAndPerIdCard(String perTypeCode, String mobilePhone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InfoPersonInfo getPersonInfoByIdentityNumAndPerType(String perIdCard, String perTypeCode) {
		// TODO Auto-generated method stub
		String hql = "from InfoPersonInfo p where 1 = 1 and p.perIdCard = '" + perIdCard + "' and p.perTypeCode = '" + perTypeCode + "'";
		List  list = this.queryForList(hql);
		if(list != null && list.size() > 0) {
			return (InfoPersonInfo)list.get(0);
		}
		return null;
	}

	@Override
	public List<InfoPersonInfo> getInfoPersonInfoList(String perTypeCode, String perNum, String perName,
			String perIdCard, String genderCode, String mobilePhone, String email, String wechat, String qq) {
		// TODO Auto-generated method stub
		String hql = "from InfoPersonInfo p where 1 = 1";
		if(perTypeCode != null && !perTypeCode.equals("") && !perTypeCode.equals("-1")) {
			hql += "and p.perTypeCode = '" + perTypeCode + "'";
		}
		if(perName != null && !perName.equals("") ) {
			hql += "and p.perName like '%" + perName + "%'";
		}
		if(perNum != null && !perNum.equals("") ) {
			hql += "and p.perNum like '%" + perNum + "%'";
		}
		if(perIdCard != null && !perIdCard.equals("") ) {
			hql += "and p.perIdCard like '%" + perIdCard + "%'";
		}
		if(genderCode != null && !genderCode.equals("") && !genderCode.equals("-1")) {
			hql += "and p.genderCode = '" + genderCode + "'";
		}
		if(mobilePhone != null && !mobilePhone.equals("") ) {
			hql += "and p.mobilePhone like '%" + mobilePhone + "%'";
		}
		if(email != null && !email.equals("") ) {
			hql += "and p.email like '%" + email + "%'";
		}
		if(wechat != null && !wechat.equals("") ) {
			hql += "and p.wechat like '%" + wechat + "%'";
		}
		if(qq != null && !qq.equals("") ) {
			hql += "and p.qq like '%" + qq + "%'";
		}
		return this.queryForList(hql);
	}

	@Override
	public InfoPersonInfo getInfoPersonInfoByPerNum(String perNum) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
		String sql = "from InfoPersonInfo where perNum ='" + perNum + "'";
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			return (InfoPersonInfo) list.get(0);
		}
		return null;
	}
	
	@Override
	public Map getMapFromPo(InfoPersonInfo p) {
		Map o = new HashMap();
		o.put("personId", p.getPersonId());
		o.put("perName", p.getPerName());
		o.put("perNum", p.getPerNum());
		o.put("perTypeCode", p.getPerTypeCode());
		o.put("perIdCard", p.getPerIdCard());
		o.put("genderCode", p.getGenderCode());
		o.put("perBirthday", p.getPerBirthday());
		o.put("mobilePhone", p.getMobilePhone());
		o.put("perAddress", p.getPerAddress());
		o.put("perPostalCode", p.getPerPostalCode());
		o.put("qq", p.getQq());
		o.put("email", p.getEmail());
		o.put("createTime", p.getCreateTime());
		o.put("remark", p.getRemark());
		return o;
	}


	@Override
	public ListOptionInfo getInfoPersonInfoPerNameOptionInfoList(Integer personId) {
		// TODO Auto-generated method stub
		String sql = "select perName from InfoPersonInfo where 1 = 1 and personId = "+ personId;
		List list = this.queryForList(sql);
		if(list == null || list.size() == 0) {
			return null;
		}
		String perName;
		ListOptionInfo info;
		for(int i = 0; i <list.size(); i++) {
			perName = (String) list.get(i);
			info = new ListOptionInfo();
			info.setValue(personId.toString());
			info.setLabel(perName);
			return info;
		}
		return null;
	}

	@Override
	public InfoPersonInfo getInfoPersonInfobyPerName(String perName) {
		// TODO Auto-generated method stub
		String sql = "from InfoPersonInfo a where 1 = 1 and a.perName like '%" + perName + "%'";
		List list = this.queryForList(sql);
		if(list != null && list.size() > 0) {
			return (InfoPersonInfo)list.get(0);
		}
		return null;
	}
	
	public InfoPersonInfo getInfoPersonInfobyPrecisePerName(String perName) {
		String sql = "from InfoPersonInfo a where 1 = 1 and a.perName ='" + perName + "'";
		List list = this.queryForList(sql);
		if(list != null && list.size() >0) {
			return (InfoPersonInfo)list.get(0);
		}
		return null;
	}

	@Override
	public InfoPersonInfo getPersonInfoByPerNameAndPerIdCardNum(String perName, String perIdCardNum) {
		// TODO Auto-generated method stub
		String hql = "from InfoPersonInfo p where 1 = 1 and p.perIdCard = '" + perIdCardNum + "' and p.perName = '" + perName + "'";
		List  list = this.queryForList(hql);
		if(list != null && list.size() > 0) {
			return (InfoPersonInfo)list.get(0);
		}
		return null;
	}
	
	@Override
	public List getPersonListbyConditions(Integer personId,String perName,Integer groupid){
		// TODO Auto-generated method stub
		String sql = "select DISTINCT a from InfoPersonInfo a, SysUser s , UserGroup u where a.personId=s.userid ";
		if(personId!=null && !personId.equals("")){
			sql+="and a.personId='"+personId+"'";
		}
		if(perName!=null && !perName.equals("")){
			sql+="and a.perName='"+perName+"'";
		}
		if(groupid!=null && !groupid.equals("")){
			sql+="and s.sysusrid=u.sysusrid and u.groupid='"+groupid+"'";
		}
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	
	@Override
	public List getListbyConditions(String loginName,String perName,String perTypeCode,Integer groupid){
		// TODO Auto-generated method stub
		String sql = "select DISTINCT a from InfoPersonInfo a, SysUser s , UserGroup u where a.personId=s.userid ";
		if(perTypeCode!=null && !perTypeCode.equals("")){
			sql+="and a.perTypeCode='"+perTypeCode+"'";
		}
		if(perName!=null && !perName.equals("")){
			sql+="and a.perName='"+perName+"'";
		}
		if(groupid!=null && !groupid.equals("")){
			sql+="and s.sysusrid=u.sysusrid and u.groupid='"+groupid+"'";
		}
		if(loginName!=null && !loginName.equals("")){
			sql+="and s.loginName='"+loginName+"'";
		}
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}


}
