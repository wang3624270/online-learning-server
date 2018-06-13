/*
 * Created on 2005/08/08
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.edu.sdu.uims.component.menu;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Element;

import cn.edu.sdu.common.util.Queue;
import cn.edu.sdu.uims.component.tree.UTreeNode;
import cn.edu.sdu.uims.component.tree.UTreeNodeMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.constant.UimsConstants;
import cn.edu.sdu.uims.def.UMenuItemTemplate;
import cn.edu.sdu.uims.def.UMenuTemplate;

/*
 * 由CJY添加
 */
/**
 * @author shi-bo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MenuGenerator {

	private int nodeid = 0;


	private void readAllNode(int currentid, Element element, List list) {
		List childlist = element.elements();
		Element node = null;
		for (int i = 0; i < childlist.size(); i++) {
			node = (Element) childlist.get(i);

			nodeid++;
			list.add(createMenuInfo(nodeid, currentid, node));

			if (node.getName().equals("mainmenu")) {
				readAllNode(nodeid, node, list);
			}

		}
	}

	/**
	 * 
	 * 
	 * @param name
	 * @param des
	 * @param add
	 * @param typeflag
	 *            1代表是叶子菜单,2代表非叶子,0代表分隔符
	 * @return
	 */
	public UMenuInfo createMenuInfo(int nodeid, int parentid, Element node) {
		UMenuInfo info = new UMenuInfo();
		info.menuNo = new Integer(nodeid);
		info.menuName = node.attributeValue("name");
		if (node.getName().equals("mainmenu")) {
			info.IsLeaf = 2;
		} else if (node.getName().equals("menuitem")) {
			info.IsLeaf = 1;
		} else {
			info.IsLeaf = 0;
		}

		info.menuRight = null;
		info.menuID = node.attributeValue("addr");
		info.menuUpNo = new Integer(parentid);
		info.menudes = node.attributeValue("description");

		return info;
	}

	/**
	 * 此函数用于前台通过后台生成的ArrayList生成菜单 以及 树导航
	 * 
	 * @param menulist
	 *            存储MenuInfo的List
	 * @param menubar
	 *            生成的菜单条
	 * @param root
	 *            生成的树的根节点
	 * @param listener
	 *            对菜单关联的事件处理
	 */
	// to be tested －－－ test passed by Zzy 20060923
	public static void initMenuAndTree(List menulist, JMenuBar menubar,
			DefaultMutableTreeNode root, ActionListener listener) {
		// 这个list是怎么生成的？格式如何？能否根据它生成一棵树//to be tested －－ Passed
		JMenu menu;
		UMenuItem item;
		UTreeNode menuNode = null;
		UTreeNode itemNode = null;
		UMenuInfo menuInfo = new UMenuInfo();
		UMenuInfo submenuInfo = new UMenuInfo();
		Queue qMenu = new Queue();
		Queue qTree = new Queue();
		menuInfo.menuNo = new Integer(0);
		qMenu.put(menuInfo);
		int nodeIndex = 0;
		// MyTreeNode root = new MyTreeNode(nodeIndex++, "菜单");
		qTree.put(root);
		// jTree = new JTree(root);
		while (!qMenu.isEmpty()) {
			menuInfo = (UMenuInfo) qMenu.get();
			menuNode = (UTreeNode) qTree.get();
			boolean bRet = false; // 判断分隔符是否显现
			for (int i = 0; i < menulist.size(); i++) {
				submenuInfo = (UMenuInfo) menulist.get(i);
				if (submenuInfo.menuUpNo.equals(menuInfo.menuNo)
						&& (submenuInfo.menuRight != null && !submenuInfo.menuRight
								.equals(UimsConstants.AuthFlag_InVisible))) {
					if (submenuInfo.menuUpNo.intValue() == 0) {
						menu = new JMenu(submenuInfo.menuName);
						submenuInfo.menu = menu;
						menu.setFont(new java.awt.Font("Dialog", 0, 12));
						menubar.add(menu);
						itemNode = new UTreeNodeMenu(nodeIndex++,
								submenuInfo.menuName, menu, submenuInfo);
						root.add(itemNode);
						itemNode.setParent(root);
					} else if (submenuInfo.IsLeaf == 1) {
						item = new UMenuItem(submenuInfo.menuName,
								menuInfo.menu, submenuInfo);
						item.setActionCommand(submenuInfo.menuID);
						item.setToolTipText(submenuInfo.menuName);
						// if (submenuInfo.accelerator != null)
						// item.setAccelerator(submenuInfo.accelerator);

						if (listener != null)
							item.addActionListener(listener);
						item.setFont(new java.awt.Font("Dialog", 0, 12));
						menuInfo.menu.add(item);
						bRet = true;
						itemNode = new UTreeNodeMenu(nodeIndex++,
								submenuInfo.menuName, item, submenuInfo);
						menuNode.add(itemNode);
						itemNode.setParent(menuNode);
					} else if (submenuInfo.IsLeaf == 2) {
						menu = new JMenu(submenuInfo.menuName);
						submenuInfo.menu = menu;
						menu.setFont(new java.awt.Font("Dialog", 0, 12));
						menuInfo.menu.add(menu);
						bRet = true;
						itemNode = new UTreeNodeMenu(nodeIndex++,
								submenuInfo.menuName, menu, submenuInfo);
						menuNode.add(itemNode);
						itemNode.setParent(menuNode);
					} else {
						if (bRet == true) {
							menuInfo.menu.addSeparator();
						}
						bRet = false;
					}
					if (!submenuInfo.menuName.equals("separator")
							&& submenuInfo.IsLeaf == 2) {
						qMenu.put(submenuInfo);
						qTree.put(itemNode);
					}
				}
			}
		}
		// tree = new MyTree(root);
		// tree.addMouseListener(this);
		// jScrollPaneLeft.getViewport().add(tree, null);
	}

	/**
	 * 此函数用于菜单权限中的菜单树
	 * 
	 * @param menulist
	 *            存储MenuInfo的List
	 * @param menubar
	 *            生成的菜单条
	 * @param root
	 *            生成的树的根节点
	 * @param listener
	 *            对菜单关联的事件处理
	 */
	// to be tested －－－ test passed by Zzy 20060923
	public static void initMenuAndTree2(List menulist, JMenuBar menubar,
			DefaultMutableTreeNode root, ActionListener listener) {
		// 这个list是怎么生成的？格式如何？能否根据它生成一棵树//to be tested －－ Passed
		JMenu menu;
		UMenuItem item;
		UTreeNode menuNode = null;
		UTreeNode itemNode = null;
		UMenuInfo menuInfo = new UMenuInfo();
		UMenuInfo submenuInfo = new UMenuInfo();
		Queue qMenu = new Queue();
		Queue qTree = new Queue();
		menuInfo.menuNo = new Integer(0);
		qMenu.put(menuInfo);
		int nodeIndex = 0;
		// MyTreeNode root = new MyTreeNode(nodeIndex++, "菜单");
		qTree.put(root);
		// jTree = new JTree(root);
		while (!qMenu.isEmpty()) {
			menuInfo = (UMenuInfo) qMenu.get();
			menuNode = (UTreeNode) qTree.get();
			boolean bRet = false; // 判断分隔符是否显现
			for (int i = 0; i < menulist.size(); i++) {
				submenuInfo = (UMenuInfo) menulist.get(i);
				if (submenuInfo.menuUpNo.equals(menuInfo.menuNo)) {
					if (submenuInfo.menuUpNo.intValue() == 0) {
						menu = new JMenu(submenuInfo.menuName);
						submenuInfo.menu = menu;
						menu.setFont(new java.awt.Font("Dialog", 0, 12));
						menubar.add(menu);
						itemNode = new UTreeNodeMenu(nodeIndex++,
								submenuInfo.menuName, menu, submenuInfo);
						root.add(itemNode);
						itemNode.setParent(root);
					} else if (submenuInfo.IsLeaf == 1) {
						item = new UMenuItem(submenuInfo.menuName,
								menuInfo.menu, submenuInfo);
						item.setActionCommand(submenuInfo.menuID);
						item.setToolTipText(submenuInfo.menuName);
						// if (submenuInfo.accelerator != null)
						// item.setAccelerator(submenuInfo.accelerator);

						if (listener != null)
							item.addActionListener(listener);
						item.setFont(new java.awt.Font("Dialog", 0, 12));
						menuInfo.menu.add(item);
						bRet = true;
						itemNode = new UTreeNodeMenu(nodeIndex++,
								submenuInfo.menuName, item, submenuInfo);
						menuNode.add(itemNode);
						itemNode.setParent(menuNode);
					} else if (submenuInfo.IsLeaf == 2) {
						menu = new JMenu(submenuInfo.menuName);
						submenuInfo.menu = menu;
						menu.setFont(new java.awt.Font("Dialog", 0, 12));
						menuInfo.menu.add(menu);
						bRet = true;
						itemNode = new UTreeNodeMenu(nodeIndex++,
								submenuInfo.menuName, menu, submenuInfo);
						menuNode.add(itemNode);
						itemNode.setParent(menuNode);
					} else {
						if (bRet == true) {
							menuInfo.menu.addSeparator();
						}
						bRet = false;
					}
					if (submenuInfo != null) {
						String menuName = submenuInfo.menuName;
						String menuCmd=submenuInfo.menuContent;
						System.out.print(menuCmd +"    ");
						System.out.println(menuName);
					}
					if (submenuInfo!=null&&submenuInfo.menuName!=null&&!submenuInfo.menuName.equals("separator")
							&& submenuInfo.IsLeaf == 2) {
						qMenu.put(submenuInfo);
						qTree.put(itemNode);
					}
				}
			}
		}
		// tree = new MyTree(root);
		// tree.addMouseListener(this);
		// jScrollPaneLeft.getViewport().add(tree, null);
	}

	public static UMenu parseMenu(UMenuTemplate menuTemplate,
			ActionListener listener) {
		if (menuTemplate == null)
			return null;
		String nodeName = "";
		if (menuTemplate.content != null
				&& !menuTemplate.content.trim().equals("")) {
			nodeName = menuTemplate.content;
		} else if (menuTemplate.name != null
				&& !menuTemplate.name.trim().equals("")) {
			nodeName = menuTemplate.name;
		}

		UMenu menu = new UMenu(nodeName);

		UMenuItemTemplate itemTemplate = null;
		UMenu childMenu = null;
		UMenuItem menuItem = null;
		URadioMenuItem radioItem = null;
		ButtonGroup group = new ButtonGroup();

		// 根据菜单项的类型进行不同的处琄1�7
		for (int i = 0; i < menuTemplate.items.length; i++) {
			itemTemplate = menuTemplate.items[i];
			// 子菜卄1�7
			if (itemTemplate.type == UConstants.COMPONENT_MENU_MENU) {
				childMenu = parseMenu((UMenuTemplate) itemTemplate, listener);
				if (childMenu != null) {
					menu.add(childMenu);
				}
			}
			// 普�1�7�菜单项
			else if (itemTemplate.type == UConstants.COMPONENT_MENU_ITEM) {
				menuItem = parseMenuItem(itemTemplate, listener);
				if (menuItem != null) {
					menu.add(menuItem);
				}
			}
			// radio菜单顄1�7
			else if (itemTemplate.type == UConstants.COMPONENT_RADIO_MENU_ITEM) {
				radioItem = parseRadioMenuItem(itemTemplate, listener);
				if (radioItem != null) {
					group.add(radioItem);
					menu.add(radioItem);
				}

			}
			// 菜单分割纄1�7
			else if (itemTemplate.type == UConstants.COMPONENT_SEPARATOR) {
				menu.addSeparator();
			}
		}
		return menu;
	}

	public static UMenuItem parseMenuItem(UMenuItemTemplate itemTemplate,
			ActionListener listener) {
		if (itemTemplate == null) {
			return null;
		}
		String nodeName = "";
		if (itemTemplate.content != null
				&& !itemTemplate.content.trim().equals("")) {
			nodeName = itemTemplate.content;
		} else if (itemTemplate.name != null
				&& !itemTemplate.name.trim().equals("")) {
			nodeName = itemTemplate.name;
		}

		UMenuItem menuItem = new UMenuItem();
		menuItem.addActionListener(listener);
		menuItem.setText(nodeName);
		menuItem.setActionCommand(itemTemplate.cmd);
		menuItem.setSelected(itemTemplate.defaultSelected);
		//以下added by yxj 2010年4月17日
		menuItem.setTemplate(itemTemplate);
		menuItem.setComponentName(itemTemplate.name);

		return menuItem;

	}

	public static URadioMenuItem parseRadioMenuItem(
			UMenuItemTemplate itemTemplate, ActionListener listener) {
		if (itemTemplate == null) {
			return null;
		}

		URadioMenuItem menuItem = new URadioMenuItem(itemTemplate.content);
		menuItem.addActionListener(listener);
		menuItem.setActionCommand(itemTemplate.cmd);
		menuItem.setSelected(itemTemplate.defaultSelected);
		//added by yxj 2010年4月17日
		menuItem.setTemplate(itemTemplate);
		menuItem.setComponentName(itemTemplate.name);
		return menuItem;

	}

}
