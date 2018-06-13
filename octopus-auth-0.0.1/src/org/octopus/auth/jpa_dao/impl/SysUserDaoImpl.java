package org.octopus.auth.jpa_dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.octopus.auth.jpa_dao.SysUserDao;
import org.octopus.auth.jpa_model.SysUser;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserDaoImpl extends GenericServiceImpl<SysUser> implements SysUserDao {

	public SysUserDaoImpl() {
		super(SysUser.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SysUser getSysUsersByUserid(Integer userid) {
		// TODO Auto-generated method stub
		String sql = "from SysUser where userid = " + userid;
		List list = this.queryForList(sql);

		if (list != null && list.size() > 0) {
			SysUser user = (SysUser) list.get(0);
			return user;
		} else {
			return null;
		}
	}

	@Override
	public SysUser getAuthUsersByLoginName(String loginName) {
		// TODO Auto-generated method stub
		String sql = "from SysUser where loginName = '" + loginName + "'";
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			SysUser user = (SysUser) list.get(0);
			return user;
		}
		return null;
	}

	@Override
	public List getAllAuthUsers() {
		// TODO Auto-generated method stub
		String sql = "from SysUser where 1 = 1";
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public int getUseridByUnionid(String unionid) {
		String sql = "from SysUser where unionid = '" + unionid + "'";
		List list = this.queryForList(sql);
		if (list.size() == 0) {
			return -1;
		}
		SysUser user = (SysUser) list.get(0);
		return user.getSysusrid();
	}

	@Override
	public SysUser getUserByUnionid(String unionid) {
		String sql = "from SysUser where unionid = '" + unionid + "'";
		List list = this.queryForList(sql);
		if (list.size() == 0) {
			return null;
		}
		SysUser user = (SysUser) list.get(0);
		return user;
	}

	@Override
	public List findByNickName(String nickName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getPhoneNumIsExist(String userName, String phoneName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getSysUserbyName(String username) {
		String hql = "from SysUser where loginName ='" + username + "'";
		List<SysUser> list = (List<SysUser>) this.queryForList(hql);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public List getSysUserListbyName(String username) {
		String hql = "from SysUser where loginName like '%" + username + "%'";
		List<SysUser> list = (List<SysUser>) this.queryForList(hql);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public Map getMapFromPo(SysUser s) {
		Map o = new HashMap();
		o.put("sysusrid", s.getSysusrid());
		o.put("userid", s.getUserid());
		o.put("loginName", s.getLoginName());
		o.put("password", s.getPassword());
		o.put("unionid", s.getUnionid());
		o.put("authority", s.getAuthority());
		o.put("enabled", s.getEnabled());
		o.put("pwd", s.getPwd());
		return o;
	}

}
