package org.starfish.sf_auth;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiSession;
import org.starfish.Starfish;
import org.starfish.constants.StarfishConstants;
import org.starfish.pageside_access.Access;
import org.starfish.pageside_access.menu.MenuNode;
import org.starfish.permi_dimension.PermissionDim;

public class StarfishMenuManager {
	public static List<MenuNode> allMenuList;
	public static MenuNode rootAllMenuTreeNode;

	public StarfishMenuManager() {
		allMenuList = getMenuList();
		getAllMenuTree();
	}

	public static void refreshAllMemus() {
		allMenuList = getMenuList();
		refreshAllMenuTree();
	}

	private static List<MenuNode> getMenuList() {
		List<MenuNode> menuList = new ArrayList<MenuNode>();
		String sql = "select logicId,menuName,menuURL,positionNum,authDetail,parentLogicId from starfish_menu_access";
		List list = Starfish.jdbcTemplate.queryForList(sql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				menuList.add(StarfishMenuManager.convertMenu((Map) list.get(i)));
				menuList.get(i).parseAuth();

			}
		}
		return menuList;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Extract the menu based on the role of user
	public static MenuNode getAccessMenuList(PermissionDim userPermisson) {
		MenuNode rootMenu = new MenuNode();
		rootMenu.setLogicId("root");
		rootMenu.setMenuName("root");
		genAccessMenuTree(rootMenu, rootAllMenuTreeNode, userPermisson);

		return rootMenu;
	}

	public static MenuNode getAccessMenuList(HttpServletRequest request) {
		return getAccessMenuList((PermissionDim) request.getSession()
				.getAttribute(StarfishConstants.userPermission));
	}

	public static MenuNode getAccessMenuList(RmiSession rmiSession) {
		return getAccessMenuList((PermissionDim) rmiSession
				.getAttribute(StarfishConstants.userPermission));
	}

	private static void genAccessMenuTree(MenuNode rootMenu,
			MenuNode rootAllMenuTreeNode, PermissionDim userPermisson) {
		int i;
		for (i = 0; i < rootAllMenuTreeNode.getSfMenuList().size(); i++) {
			if (rootAllMenuTreeNode.getSfMenuList().get(i)
					.checkUserPermission(userPermisson)) {
				MenuNode temp = copySfMenuForUser(rootAllMenuTreeNode
						.getSfMenuList().get(i));
				rootMenu.getSfMenuList().add(temp);
				genAccessMenuTree(temp, rootAllMenuTreeNode.getSfMenuList()
						.get(i), userPermisson);
			}
		}
	}

	private static MenuNode copySfMenuForUser(MenuNode menu) {
		MenuNode temp = new MenuNode();
		// temp.setId(menu.getId());
		temp.setMenuName(menu.getMenuName());
		temp.setMenuURL(menu.getMenuURL());
		// temp.setMenuAuth(menu.getMenuAuth());
		temp.setLogicId(menu.getLogicId());
		temp.setParentLogicId(menu.getParentLogicId());
		temp.setParentMenuName(menu.getParentMenuName());
		temp.setPositionNum(menu.getPositionNum());
		temp.setIconURL(menu.getIconURL());
		// temp.setMenuAccess(menu.getMenuAccess());
		return temp;
	}

	// Sort tree elements by position number.
	public static void sort_list(MenuNode rootMenu) {
		List<MenuNode> list = rootMenu.getSfMenuList();
		if (list.size() == 0)
			return;
		sortNptMenu(list);
		for (int i = 0; i < list.size(); i++) {
			sort_list(list.get(i));
		}
	}

	public static <T> void sortNptMenu(List<MenuNode> list) {
		Collections.sort(list, new Comparator<MenuNode>() {
			@Override
			public int compare(MenuNode o1, MenuNode o2) {

				if (o1.getPositionNum() > o2.getPositionNum())
					return 1;
				else if (o1.getPositionNum() < o2.getPositionNum()) {
					return -1;
				} else
					return 0;
			}
		});
	}

	// Get id of all menus
	public static List<String> getMenuId(List<MenuNode> tempList) {
		List<String> menuIdsList = new ArrayList<String>();
		for (int i = 0; i < tempList.size(); i++) {
			menuIdsList.add(tempList.get(i).getLogicId());
		}
		return menuIdsList;
	}

	// Get the menu tree which contains all menus in system. The root tree node
	// is named 'root'. All NptMenu objects are wrapped by DefaultTreeNode
	// objects.
	public static MenuNode getAllMenuTreeWithTreeStrc() {
		MenuNode rootMenu = getAllMenuTree();
		// DefaultTreeNode rootNode = new DefaultTreeNode();
		// rootNode.setData(rootMenu);

		// getAllMenuTreeWithTreeStrc_(rootNode, rootMenu);

		return rootMenu;
	}

	// private static void getAllMenuTreeWithTreeStrc_(TreeNode node, MenuNode
	// menu) {
	// ((DefaultTreeNode) node).setData(menu);
	// int i;
	// for (i = 0; i < menu.getNptMenuList().size(); i++) {
	// DefaultTreeNode tmpNode = new DefaultTreeNode();
	// node.getChildren().add(tmpNode);
	// getAllMenuTreeWithTreeStrc_(tmpNode, menu.getNptMenuList().get(i));
	// }
	// }

	// Get the menu tree which contains all menus in system. The root tree node
	// is named 'root'.
	public static MenuNode getAllMenuTree() {
		if (rootAllMenuTreeNode != null)
			return rootAllMenuTreeNode;
		MenuNode nn = new MenuNode();
		nn.setLogicId("root");

		List<MenuNode> tempList = new ArrayList<MenuNode>();

		for (MenuNode n : allMenuList) {
			tempList.add(n);
			if (n.getParentLogicId() == null
					|| n.getParentLogicId().length() == 0) {
				n.setParentLogicId("root");
			}
		}
		createDynamicTreeNode(nn, tempList);
		sort_list(nn);
		rootAllMenuTreeNode = nn;
		return rootAllMenuTreeNode;
	}

	// Get the menu tree which contains all menus in system. The root tree node
	// is named 'root'.
	public static MenuNode refreshAllMenuTree() {
		MenuNode nn = new MenuNode();
		nn.setLogicId("root");
		List<MenuNode> tempList = new ArrayList<MenuNode>();

		for (MenuNode n : allMenuList) {
			tempList.add(n);
			if (n.getParentLogicId() == null
					|| n.getParentLogicId().length() == 0) {
				n.setParentLogicId("root");
			}
		}
		createDynamicTreeNode(nn, tempList);
		sort_list(nn);
		rootAllMenuTreeNode = nn;
		return rootAllMenuTreeNode;
	}

	// Traverse all menu and create a tree
	public static void createDynamicTreeNode(MenuNode treeNode,
			List<MenuNode> list) {
		int i;
		for (i = 0; i < list.size(); i++) {
			if (list.get(i).getParentLogicId().equals(treeNode.getLogicId())) {
				treeNode.getSfMenuList().add(list.get(i));
				list.remove(i);
				i--;
			}
		}
		for (i = 0; i < treeNode.getSfMenuList().size(); i++) {
			createDynamicTreeNode(treeNode.getSfMenuList().get(i), list);
		}
	}

	// The method is to convert Map type into NptMenu
	public static MenuNode convertMenu(Map access) {
		MenuNode menu = new MenuNode();
		// menu.setId(Integer.valueOf(access.get("id").toString()));
		menu.setMenuName(access.get("menuName").toString());
		if (access.get("menuURL") != null) {
			menu.setMenuURL(access.get("menuURL").toString());
		}
		menu.setMenuAuth(access.get("authDetail").toString());

		menu.setLogicId(access.get("logicId").toString());
		menu.setPositionNum(Integer.valueOf(access.get("positionNum")
				.toString()));
		if (access.get("parentLogicId") != null) {
			menu.setParentLogicId(access.get("parentLogicId").toString());
		}
		menu.setSfMenuList(new ArrayList<MenuNode>());
		return menu;
	}

	// Some problem here, why get menu information according menu url??
	public static MenuNode getNptMenuInfoByMenuURL(String path) {
		String sql = "select id,logicId,menuName,menuURL,positionNum,description,parentLogicId from starfish_menu_access where menuURL='"
				+ path + "'";
		List list = Starfish.jdbcTemplate.queryForList(sql);
		Map map = new HashMap();
		if (list != null && list.size() > 0) {
			map = (Map) list.get(0);
		}
		MenuNode menu = convertMenu(map);
		return menu;
	}

	public static void updatePageElementsDescription(String path,
			String description) {
		String sql = "update tp_npt_page_element_access set description='"
				+ description + "',updateTime=now() where path='" + path + "'";
		Starfish.jdbcTemplate.update(sql);
	}

	public static void deletePageElementsDescription(String path) {
		String sql = "delete from tp_npt_page_element_access where path='"
				+ path + "'";
		Starfish.jdbcTemplate.update(sql);
	}

	public static String getPageElementsDescription(String pagePath) {
		String sql = "select description from tp_npt_page_element_access where path='"
				+ pagePath + "'";
		List list = Starfish.jdbcTemplate.queryForList(sql);
		Map map = new HashMap();
		if (list != null && list.size() > 0) {
			map = (Map) list.get(0);
		}
		if (map != null && !map.equals("") && map.get("description") != null
				&& !map.get("description").equals("")) {
			return map.get("description").toString();
		}
		return "";
	}

	public static void addPageElementsDescription(String path,
			String description) {
		String sql = "insert into tp_npt_page_element_access(path,description) values('"
				+ path + "','" + description + "')";
		Starfish.jdbcTemplate.execute(sql);
	}

}