package org.octopus.res_manage.sys_menu;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.octopus.auth.client_data_model.WebMenuStruct;
import org.octopus.auth.jpa_dao.SysUserDao;
import org.octopus.auth.sdjpa_dao.SecurityUsersJpaDao;
import org.octopus.web_ui.easyui.data_struct.EUMenuNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.starfish.pageside_access.menu.MenuNode;
import org.starfish.sf_auth.StarfishMenuManager;

@Controller
@Scope("application")
public class SysMenuManageBean {

	@Autowired
	private SecurityUsersJpaDao securityUsersJpaDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	public SysMenuManageBean() {

	}

	@RequestMapping(value = "/resManage/sysMenuManage", method = RequestMethod.GET)
	public String sysMenuManage(HttpServletRequest request) {
		return "pages/org/octopus/res_manage/sys_menu/sysMenuManage";
	}

	@RequestMapping(value = "/resManage/allSysMenu", method = RequestMethod.GET)

	public @ResponseBody WebMenuStruct getAllSysMenu(HttpServletRequest request) {
		List<WebMenuStruct> l = new ArrayList<WebMenuStruct>();
		WebMenuStruct webMenuStruct = new WebMenuStruct();

		MenuNode n = StarfishMenuManager.getAllMenuTree();
		webMenuStruct = new WebMenuStruct();
		EUMenuNode euMenu = new EUMenuNode();
		transEUMenuNode(request, euMenu, n);
		webMenuStruct.setRootMenu(euMenu);

		return webMenuStruct;
	}

	private void transEUMenuNode(HttpServletRequest request, EUMenuNode euMenu, MenuNode n) {

		euMenu.setId(n.getLogicId());
		euMenu.setName(n.getMenuName());

		euMenu.getAttributes().put("url", request.getContextPath() + n.getMenuURL());
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
}