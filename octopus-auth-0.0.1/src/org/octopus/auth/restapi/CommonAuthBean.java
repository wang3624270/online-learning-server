package org.octopus.auth.restapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.octopus.auth.client_data_model.WebMenuStruct;
import org.octopus.auth.client_form.WebLoginForm;
import org.octopus.auth.jpa_dao.GroupFunresDao;
import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_dao.SysFunResDao;
import org.octopus.auth.jpa_dao.SysUserDao;
import org.octopus.auth.jpa_dao.SysUserGroupDao;
import org.octopus.auth.jpa_dao.UserGroupDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.auth.jpa_model.SysFunRes;
import org.octopus.auth.jpa_model.SysUser;
import org.octopus.auth.jpa_model.SysUserGroup;
import org.octopus.auth.jpa_model.UserGroup;
import org.octopus.auth.sdjpa_dao.SecurityUsersJpaDao;
import org.octopus.spring_utils.SpringContextHelper;
import org.octopus.web_ui.easyui.data_struct.EUMenuNode;
import org.sdu.rmi.RestConstants;
import org.sdu.rmi.UserTokenClientSide;
import org.sdu.spring_util.ApplicationContextHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.starfish.Starfish;
import org.starfish.constants.StarfishConstants;
import org.starfish.login_users.CommonAuthUseInfoTool;
import org.starfish.login_users.LoginUsersManager;
import org.starfish.login_users.UserTokenServerSide;
import org.starfish.pageside_access.menu.MenuNode;
import org.starfish.sf_auth.StarfishMenuManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.edu.sdu.common.util.Base64;
import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.common.util.Md5Util;

@RestController
@Scope("session")
public class CommonAuthBean {
	private static Logger logger = Logger.getLogger(CommonAuthBean.class);
	@Autowired
	private SecurityUsersJpaDao securityUsersJpaDao;
	@Autowired
	private SysUserDao sysUsersDao;
	@Autowired
	private InfoPersonInfoDao personInfoDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private InfoPersonInfoDao infoPersonInfoDao;
	@Autowired
	private UserGroupDao userGroupDao;
	@Autowired
	private SysUserGroupDao sysUserGroupDao;
	@Autowired
	private GroupFunresDao groupFunresDao;
	@Autowired
	private SysFunResDao sysFunResDao;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	private WebMenuStruct webMenuStruct;

	public CommonAuthBean() {

	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/auth/webLogin")
	public UserTokenClientSide webLogin(HttpServletRequest request,
			@RequestBody WebLoginForm loginForm) {

		UserTokenClientSide cs = new UserTokenClientSide();
		List<String> li = loginForm.validForMessages();
		cs.setLoginName(loginForm.getLoginName());
		if (li.size() > 0) {
			cs.setReCode(RestConstants.ret_error);
			cs.setErrorMessageList(li);
			return cs;
		}

		SysUser authUsers = sysUsersDao.getAuthUsersByLoginName(loginForm
				.getLoginName());
		InfoPersonInfo info = null;
		if (authUsers == null) {
			Boolean checkType = this.isNumeric(loginForm.getLoginName());
			if (checkType) {
				info = personInfoDao.getInfoPersonInfoByMobilePhone(loginForm
						.getLoginName());
			}
			if (info == null) {
				cs.setReCode(RestConstants.ret_error);
				cs.addErrorMessage("此用户名不存在！");
				return cs;
			} else {
				authUsers = sysUsersDao.getSysUsersByUserid(info.getPersonId());
				cs.setLoginName(authUsers.getLoginName());
			}
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				authUsers.getLoginName(), loginForm.getPassword());
		InfoPersonInfo personInfo;
		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			webMenuStruct = null;
			// 验证码检查
			String strVerify = loginForm.getValidateCode();

			UserTokenServerSide ss = new UserTokenServerSide();
			ss.setPersonId(authUsers.getUserid());
			ss.setLoginName(cs.getLoginName());
			token.setDetails(ss);
			ss.setSfIdentity(LoginUsersManager.transUserSFIdentity(authUsers
					.getAuthority()));
			CommonAuthUseInfoI di = (CommonAuthUseInfoI) ApplicationContextHandle
					.getBean("commonAuthUseInfoBean");
			if (di != null)
				di.addUserData(loginForm, ss, cs);
			request.getSession().setAttribute(StarfishConstants.userToken, ss);
			Starfish.evaluateUserPermissions(request.getSession());

		} catch (BadCredentialsException e) {

			logger.info("User " + cs.getLoginName() + " login failed.");
			cs.setReCode(RestConstants.ret_error);
			ReloadableResourceBundleMessageSource s = (ReloadableResourceBundleMessageSource) SpringContextHelper
					.getBean("messageSource");
			cs.addErrorMessage(s
					.getMessage("auth.loginValid.error", null, null));
			return cs;

		}
		LoginUsersManager.addLoginUsers(token);
		cs.setLoginName(authUsers.getLoginName());
		cs.setPersonId(authUsers.getUserid());
		cs.setSessionId(request.getSession().getId());
//		System.out.println(request.getSession().getId());
		cs.setReCode(RestConstants.ret_success);
		logger.info("User " + cs.getLoginName() + " login success.");
		return cs;

	}

	// 登出
	@RequestMapping(value = "/auth/webLogout")
	public UserTokenClientSide webLogout(HttpServletRequest request) {

		UserTokenServerSide userToken = (UserTokenServerSide) request
				.getSession().getAttribute("userToken");

		HttpSession session = request.getSession();
		session.invalidate();

		UserTokenClientSide cs = new UserTokenClientSide();
		cs.setReCode(RestConstants.ret_success);

		logger.info("User " + userToken.getLoginName() + " logout success.");

		return cs;
	}

	@RequestMapping(value = "/auth/menuStruct", method = RequestMethod.GET)
	public WebMenuStruct menuStruct(HttpServletRequest request) {
		if (webMenuStruct != null)
			return webMenuStruct;
		// UserTokenServerSide ss = (UserTokenServerSide)
		// request.getSession().getAttribute(StarfishConstants.userToken);
		MenuNode n = StarfishMenuManager.getAccessMenuList(request);
		webMenuStruct = new WebMenuStruct();
		EUMenuNode euMenu = new EUMenuNode();
		transEUMenuNode(request, euMenu, n);
		webMenuStruct.setRootMenu(euMenu);
		return webMenuStruct;
	}
	
	@RequestMapping(value = "/auth/menuInit", method = RequestMethod.GET)
	public Map menuInit(HttpServletRequest request) {
		UserTokenServerSide userToken = (UserTokenServerSide) request
				.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer personId=userToken.getPersonId();
			InfoPersonInfo personInfo=infoPersonInfoDao.getInfoPersonInfoByPersonId(personId);
			SysUser sysuser=sysUserDao.getSysUsersByUserid(personId);
			UserGroup re=userGroupDao.getRelationBySys(sysuser.getSysusrid());
			SysUserGroup group=sysUserGroupDao.find(re.getGroupid());
			List idList=groupFunresDao.getIdListByGroupId(group.getGroupid());
			List reList=new ArrayList();
			if(idList!=null){
				for(int i=0;i<idList.size();i++){
					Integer id=(Integer)idList.get(i);
					reList.add(sysFunResDao.find(id));
				}
			}
			List menuList=new ArrayList();
			for(int i=0;i<reList.size();i++){
				List tList=new ArrayList();
				SysFunRes res=(SysFunRes) reList.get(i);
				menuList.add(res);
			}
			data.put("perTypeCode", personInfo.getPerTypeCode());
			data.put("loginName", userToken.getLoginName());
			data.put("perName", personInfo.getPerName());
			data.put("menuList", menuList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	public static void transEUMenuNode(HttpServletRequest request,
			EUMenuNode euMenu, MenuNode n) {

		euMenu.setId(n.getLogicId());
		euMenu.setText(n.getMenuName());

		euMenu.getAttributes().put("url",
				request.getContextPath() + n.getMenuURL());
		if (n.getSfMenuList().size() > 0) {
			int i;
			for (i = 0; i < n.getSfMenuList().size(); i++) {
				EUMenuNode eu = new EUMenuNode();
				transEUMenuNode(request, eu, n.getSfMenuList().get(i));
				euMenu.getChildren().add(eu);
			}
		} else
			euMenu.setChildren(null);
	}

	@RequestMapping(value = "/auth/webChangePassword", method = RequestMethod.POST)
	public Map webChangePassword(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		UserTokenServerSide ss = CommonAuthUseInfoTool.checkUser(httpRequest,
				obj);
		Map request = (Map) obj;
		Map response = new HashMap();
		String oldPassword = (String) request.get("oldPassword");
		String newPassword = (String) request.get("newPassword");
		SysUser authUsers = sysUsersDao.getAuthUsersByLoginName(ss
				.getLoginName());
		if (authUsers == null) {
			response.put("errMessage", "此用户名不存在！");
			return response;
		}
		String pw = Md5Util.GetMD5Code(oldPassword);
		if (!pw.equals(authUsers.getPassword())) {
			response.put("errMessage", "旧密码输入错！");
			return response;
		}
		pw = Md5Util.GetMD5Code(newPassword);
		authUsers.setPassword(pw);
		pw = Base64.getBase64Code(newPassword);
		authUsers.setPwd(pw);
		sysUsersDao.update(authUsers);
		return response;
	}

	@RequestMapping(value = "/auth/failure", method = RequestMethod.GET)
	public @ResponseBody ObjectNode visitFailure(HttpServletRequest request,
			HttpServletResponse response) {
		String header = request.getHeader("User-Agent").toLowerCase();
		if (header.indexOf("windows") >= 0 || header.indexOf("mac") >= 0) {
			try {
				response.sendRedirect(request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath()
						+ "/login.html");
			} catch (IOException e) {

			}
			return null;
		} else {
			ObjectNode re = objectMapper.createObjectNode();
			re.put("re", -100);

			return re;
		}

	}
}