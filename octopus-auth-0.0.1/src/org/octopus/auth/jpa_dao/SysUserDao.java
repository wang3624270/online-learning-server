package org.octopus.auth.jpa_dao;

import java.util.List;

import org.octopus.auth.jpa_model.SysUser;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;


public interface SysUserDao extends GenericServiceI<SysUser>   {

	public SysUser getAuthUsersByLoginName(String loginName);
	public SysUser getSysUsersByUserid(Integer userid);
	public List getAllAuthUsers();
	int getUseridByUnionid(String unionid);
	public List findByNickName(String nickName);
	boolean getPhoneNumIsExist(String userName, String phoneName);
	boolean getSysUserbyName(String username);
	public SysUser getUserByUnionid(String unionid);
	public List getSysUserListbyName(String username);

}
