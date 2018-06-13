package cn.edu.sdu.uims.component.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.event.CommonActionListener;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UMenuItemTemplate;
import cn.edu.sdu.uims.def.UMenuTemplate;
import cn.edu.sdu.uims.def.UTreeMenuTemplate;

public class UTreeMenu extends JTree {
	private UTreeMenuTemplate treeMenuTemplate;
	private UTreeNode root;
	private CommonActionListener commonActionListener = new CommonActionListener();

	public UTreeMenu() {
		super();
	}

	public UTreeMenu(Hashtable<?, ?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	public UTreeMenu(Object[] value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	public UTreeMenu(TreeModel newModel) {
		super(newModel);
		// TODO Auto-generated constructor stub
	}

	public UTreeMenu(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		// TODO Auto-generated constructor stub
	}

	public UTreeMenu(TreeNode root) {
		super(root);
		// TODO Auto-generated constructor stub
	}

	public UTreeMenu(UTreeNode root) {
		super(root);
		this.root = root;
	}

	public UTreeMenu(Vector<?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	public void addActionListener(ActionListener listener) {
		commonActionListener.addListener(listener);
	}

	/**
	 * 移出事件监听
	 * 
	 * @param listener
	 */
	public void removeActionListener(ActionListener listener) {
		commonActionListener.removeListener(listener);
	}

	public void init(int tt) {

		if (treeMenuTemplate == null)
			return;
		int size = treeMenuTemplate.items.length;
		for (int i = 0; i < size; i++) {

			parseTreeNode((UMenuItemTemplate) treeMenuTemplate.items[i], root);
		}
		setRootVisible(false);
		if(treeMenuTemplate.isExpandAll)
			expandAll(this, new TreePath(root), true); 
		else 
			expandPath(new TreePath(root));

		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				UTreeNode node = (UTreeNode) getLastSelectedPathComponent();
				if (node == null)
					return;
				if (e.getClickCount() == 2 && node.isLeaf()) {
					UMenuItemTemplate itemTemplate = (UMenuItemTemplate) node
							.getTemplate();
					commonActionListener.actionPerformed(new ActionEvent(node,
							e.getID(), itemTemplate.cmd));
				}
				// selectedMenuInfo=node.getMenuInfo();
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setTemplate(UTemplate template) {
		treeMenuTemplate = (UTreeMenuTemplate) template;
	}

	/**
	 * 初始匄1�7 menu
	 * 
	 * @param menuTemplate
	 *            menu模板
	 * @author 赵鹏 2008.6.23
	 */
	private void parseTreeNode(UMenuItemTemplate menuTemplate,
			UTreeNode parentNode) {
		if (menuTemplate == null) {
			return;
		}
		String nodeName = "";
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		if(b && menuTemplate.enContent != null
				&& !menuTemplate.enContent.trim().equals("")) {
			nodeName = menuTemplate.enContent;
		}
		else if (menuTemplate.content != null
				&& !menuTemplate.content.trim().equals("")) {
			nodeName = menuTemplate.content;
		} else if (menuTemplate.name != null
				&& !menuTemplate.name.trim().equals("")) {
			nodeName = menuTemplate.name;
		}
		UTreeNode treeNode = new UTreeNode(nodeName);
		treeNode.setTemplate(menuTemplate);
		// treeNode.
		parentNode.add(treeNode);

		UMenuItemTemplate itemTemplate = null;

		if (menuTemplate.type == UConstants.COMPONENT_MENU_MENU) {
			UMenuTemplate template = (UMenuTemplate) menuTemplate;
			for (int i = 0; i < template.items.length; i++) {
				parseTreeNode(template.items[i], treeNode);
			}
		}

	}

	// ///////////////////////////////////////////////////////////

	public void refresh(String menus) {
		String[] strs = menus.split("\\,");

		if (treeMenuTemplate == null)
			return;
		int size = treeMenuTemplate.items.length;
		for (int i = 0; i < size; i++) {
			if (!treeMenuTemplate.items[i].content.equals(strs[0]))
				continue;
			parseTreeNode((UMenuItemTemplate) treeMenuTemplate.items[i], root,
					strs, 0);
		}
		setRootVisible(false);
		this.expandPath(new TreePath(root));
		// this.expandRow(0);
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				UTreeNode node = (UTreeNode) getLastSelectedPathComponent();
				if (node == null)
					return;
				if (e.getClickCount() == 2 && node.isLeaf()) {
					UMenuItemTemplate itemTemplate = (UMenuItemTemplate) node
							.getTemplate();
					commonActionListener.actionPerformed(new ActionEvent(node,
							e.getID(), itemTemplate.cmd));
				}
				// selectedMenuInfo=node.getMenuInfo();
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	// /////////////////////////////////////////////////////////
	private void parseTreeNode(UMenuItemTemplate menuTemplate,
			UTreeNode parentNode, String[] menus, int t) {
		if (menuTemplate == null) {
			return;
		}

		if (t >= menus.length)
			return;
		String nodeName = "";
		if (menuTemplate.content != null
				&& !menuTemplate.content.trim().equals("")) {
			nodeName = menuTemplate.content;
		} else if (menuTemplate.name != null
				&& !menuTemplate.name.trim().equals("")) {
			nodeName = menuTemplate.name;
		}
		UTreeNode treeNode = new UTreeNode(nodeName);
		treeNode.setTemplate(menuTemplate);
		// treeNode.
		parentNode.add(treeNode);

		UMenuItemTemplate itemTemplate = null;

		if (menuTemplate.type == UConstants.COMPONENT_MENU_MENU) {
			UMenuTemplate template = (UMenuTemplate) menuTemplate;

			for (int i = 0; i < template.items.length; i++) {

				if (!template.items[i].content.equals(menus[t]))
					continue;

				parseTreeNode(template.items[i], treeNode, menus, t++);
			}
		}

	}

	private void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}

		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}

	}
}