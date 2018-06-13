package cn.edu.sdu.uims.frame.form;

import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.form.impl.UMForm;
import cn.edu.sdu.uims.util.UimsUtils;

public class SysUserCForm extends UMForm {


	private Integer sysusrid;

	private String loginName;

	private String password;

	private String usertype;

	private String hostIp;

	private String hostName;

	private Integer userid;

	private String perIdCard;

	private Integer[] groupids;

	private String groupId;// 用户组名称
	private String groupName;// 用户组名称
	private ListOptionInfo groupIdInfo;
	private String perName;// 用户姓名

	private List groupOptionInfo;

	private List userInfoList;
	private String password_ag;// 重复密码，判断注册密码是否一致

	private boolean isCheck = true;
    private Integer collegeId;
    
	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	public List getGroupOptionInfo() {
		return groupOptionInfo;
	}

	public void setGroupOptionInfo(List groupOptionInfo) {
		this.groupOptionInfo = groupOptionInfo;
	}

	public List getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List userInfoList) {
		this.userInfoList = userInfoList;
	}

	// 可访问的人员的编码的匹配码---赵鹏 2007.4.24
	private String matchedPerCode;

	public Integer getSysusrid() {
		return this.sysusrid;
	}

	public void setSysusrid(Integer sysusrid) {
		this.sysusrid = sysusrid;
	}

	public String getLoginName() {
		if (!UimsUtils.checkParameter(loginName))
			loginName = null;
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		if (!UimsUtils.checkParameter(password))
			password = null;
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer[] getGroupids() {
		return groupids;
	}

	public void setGroupids(Integer[] groupids) {
		this.groupids = groupids;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Object[] toArray() {
		Object[] objs = new Object[4];
		objs[0] = this.sysusrid;
		objs[1] = this.loginName;
		objs[2] = this.password;
		objs[3] = this.usertype;

		return objs;
	}

	public String toString() {

		return this.loginName;
	}

	public String getMatchedPerCode() {
		return matchedPerCode;
	}

	public void setMatchedPerCode(String matchedPerCode) {
		this.matchedPerCode = matchedPerCode;
	}

	public String getPerIdCard() {
		return perIdCard;
	}

	public void setPerIdCard(String perIdCard) {
		this.perIdCard = perIdCard;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public ListOptionInfo getGroupIdInfo() {
		return groupIdInfo;
	}

	public void setGroupIdInfo(ListOptionInfo groupIdInfo) {
		this.groupIdInfo = groupIdInfo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPassword_ag() {
		return password_ag;
	}

	public void setPassword_ag(String passwordAg) {
		password_ag = passwordAg;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

}
