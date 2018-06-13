package org.octopus.auth.rmiapi;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.octopus.auth.jpa_dao.SysUserDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.auth.jpa_model.SysUser;
import org.octopus.auth.restapi.CommonAuthUseInfoI;
import org.octopus.auth.sdjpa_dao.SecurityUsersJpaDao;
import org.octopus.spring_utils.SpringContextHelper;
import org.sdu.cs_datastructure.CSMenuNode;
import org.sdu.rmi.RestConstants;
import org.sdu.rmi.RmiAccessAuthI;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.rmi.RmiSession;
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
import org.springframework.stereotype.Service;
import org.starfish.Starfish;
import org.starfish.constants.StarfishConstants;
import org.starfish.login_users.LoginUsersManager;
import org.starfish.login_users.UserTokenServerSide;
import org.starfish.pageside_access.menu.MenuNode;
import org.starfish.sf_auth.StarfishMenuManager;

import cn.edu.sdu.common.util.Base64;
import cn.edu.sdu.common.util.Md5Util;

@Service
@Scope("singleton")
public class RmiCommonAuthBean implements RmiAccessAuthI {
	private static Logger logger = Logger.getLogger(RmiCommonAuthBean.class);
	@Autowired
	private SecurityUsersJpaDao securityUsersJpaDao;
	@Autowired
	private SysUserDao authUsersDao;
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	public RmiCommonAuthBean() {

	}

	public void rmiLogin(RmiRequest request, RmiResponse response) throws Exception {

		UserTokenClientSide cs = new UserTokenClientSide();
		cs.setLoginName((String) request.get("loginName"));

		SysUser authUsers = authUsersDao.getAuthUsersByLoginName((String) request.get("loginName"));
		if (authUsers == null) {
			cs.setReCode(RestConstants.ret_error);
			cs.addErrorMessage("此用户名不存在！");
			response.add("userTokenClientSide", cs);
			return;
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				(String) request.get("loginName"), (String) request.get("password"));
		InfoPersonInfo personInfo;
		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);

			UserTokenServerSide ss = new UserTokenServerSide();
			ss.setPersonId(authUsers.getUserid());
			ss.setLoginName(cs.getLoginName());
			token.setDetails(ss);
			ss.setSfIdentity(LoginUsersManager.transUserSFIdentity(authUsers.getAuthority()));

			cs.setRoles(ss.getSfIdentity().getDimPositions("role"));
			cs.setPersonId(authUsers.getUserid());

			LoginUsersManager.addRmiLoginUsers(token);
			LoginUsersManager.getRmiUserSession(token.getName()).setAttribute(StarfishConstants.userToken, ss);
			Starfish.evaluateUserPermissions(LoginUsersManager.getRmiUserSession(token.getName()));
			CommonAuthUseInfoI di = (CommonAuthUseInfoI)ApplicationContextHandle.getBean("commonAuthUseInfoBean");
			if(di != null)
				di.addUserDataCs(request,ss,cs);
		} catch (BadCredentialsException e) {

			logger.info("User " + cs.getLoginName() + " login failed.");
			cs.setReCode(RestConstants.ret_error);
			ReloadableResourceBundleMessageSource s = (ReloadableResourceBundleMessageSource) SpringContextHelper.getBean("messageSource");
			cs.addErrorMessage(s.getMessage("auth.loginValid.error", null, null));
			response.add("userTokenClientSide", cs);
			return;
		}

		cs.setPersonId(authUsers.getUserid());
		cs.setReCode(RestConstants.ret_success);
		cs.setSessionId(UUID.randomUUID().toString());

		LoginUsersManager.getRmiUserSession(cs.getLoginName()).setSessionId(cs.getSessionId());

		logger.info("User " + cs.getLoginName() + " login success.");

		response.add("userTokenClientSide", cs);

		return;

	}

	public void menuStrcture(RmiRequest request, RmiResponse response) throws Exception {
		UserTokenClientSide cs = (UserTokenClientSide) request.get("userTokenClientSide");
		MenuNode n = StarfishMenuManager.getAccessMenuList(LoginUsersManager.getRmiUserSession(cs.getLoginName()));
		CSMenuNode csMenu = new CSMenuNode();
		transMenuNode(csMenu, n);
		response.add("rootMenu", n);
	}

	private void transMenuNode(CSMenuNode csMenu, MenuNode n) {

		csMenu.setId(n.getLogicId());
		csMenu.setText(n.getMenuName());

		csMenu.getAttributes().put("url", n.getMenuURL());
		if (n.getSfMenuList().size() > 0) {
			int i;
			for (i = 0; i < n.getSfMenuList().size(); i++) {
				CSMenuNode cs = new CSMenuNode();
				transMenuNode(cs, n.getSfMenuList().get(i));
				csMenu.getChildren().add(cs);
			}
		} else
			csMenu.setChildren(null);
	}

	// 登出
	public UserTokenClientSide webLogout(HttpServletRequest request) {

		UserTokenServerSide userToken = (UserTokenServerSide) request.getSession().getAttribute("userToken");

		HttpSession session = request.getSession();
		session.invalidate();

		UserTokenClientSide cs = new UserTokenClientSide();
		cs.setReCode(RestConstants.ret_success);

		logger.info("User " + userToken.getLoginName() + " logout success.");

		return cs;
	}

	@Override
	public boolean auth(RmiRequest request) {
		// TODO Auto-generated method stub
		try {
			UserTokenClientSide userTokenClientSide = (UserTokenClientSide) request.get("userTokenClientSide");
			RmiSession se = LoginUsersManager.getRmiUserSession(userTokenClientSide.getLoginName());
			if (se.getSessionId().equals(userTokenClientSide.getSessionId())) {

				// MenuNode n = StarfishMenuManager.getAccessMenuList(request);

				return true;
			}
		} catch (Exception e) {
		}

		return false;
	}
	
	public void changePassword(RmiRequest request, RmiResponse response) throws Exception {
//		changePasswordBase64(request, response);	
		UserTokenClientSide cs = (UserTokenClientSide)request.get("userTokenClientSide");
		String oldPassword = (String)request.get("oldPassword");
		String newPassword = (String)request.get("newPassword");
		SysUser authUsers = authUsersDao.getAuthUsersByLoginName(cs.getLoginName());
		if (authUsers == null) {
			response.setErrorMsg("此用户名不存在！");
			return;
		}
		String pw = Md5Util.GetMD5Code(oldPassword);
		if(!pw.equals(authUsers.getPassword())) {
			response.setErrorMsg("旧密码输入错！");
			return;
		}
		pw = Md5Util.GetMD5Code(newPassword);
		authUsers.setPassword(pw);
		pw = Base64.getBase64Code(newPassword);
		authUsers.setPwd(pw);
		authUsersDao.update(authUsers);
		return;		
	}

	public void changePasswordBase64(RmiRequest request, RmiResponse response) throws Exception {
		UserTokenClientSide cs = (UserTokenClientSide)request.get("userTokenClientSide");
		String oldPassword = (String)request.get("oldPassword");
		String newPassword = (String)request.get("newPassword");
		SysUser authUsers = authUsersDao.getAuthUsersByLoginName(cs.getLoginName());
		if (authUsers == null) {
			response.setErrorMsg("此用户名不存在！");
			return;
		}
		String pw = Base64.getBase64Code(oldPassword);
		if(!pw.equals(authUsers.getPassword())) {
			response.setErrorMsg("旧密码输入错！");
			return;
		}
		pw = Base64.getBase64Code(newPassword);
		authUsers.setPassword(pw);
		authUsersDao.update(authUsers);
		return;
	}
	
}