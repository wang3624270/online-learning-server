package cn.edu.sdu.uims.component.tree;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.UScrollPanelEventAdaptor;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UTreeTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.filter.TreeFilterI;
import cn.edu.sdu.uims.form.impl.UTreeNodeForm;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UTreePanel extends JPanel implements UComponentI {
	protected UTreeNode rootNode;
	protected DefaultTreeModel treeModel;
	private TreeFilterI filter = null;

	protected JTree tree;
	private String componentName;
	private UPanelI parent = null;
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();

	DefaultMutableTreeNode fromNode, toNode;
	boolean mouseCanSend = false;
	private UScrollPanelEventAdaptor eventProcessor = null;

	private MouseListener mouseListener = null;
	private UPopupMenu popupMenu = null;
	protected UTreeTemplate elementTemplate;
	private boolean enablePopupMenu = true;
	public UTreePanel() {
		super(new GridLayout(1, 0));
		rootNode = new UTreeNode("Root Node");
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());

		tree = new JTree(treeModel);
		tree.setRootVisible(true);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
//		tree.setEditable(true);
//		tree.getSelectionModel().setSelectionMode(
//				TreeSelectionModel.SINGLE_TREE_SELECTION);
//		tree.setShowsRootHandles(true);
		
		TreeMouseProcess pt = new TreeMouseProcess();
		tree.addMouseListener(pt);
		tree.addMouseMotionListener(pt);
		UScrollPane scrollPane = new UScrollPane(tree);
		scrollPane.addMouseListener(pt);
		add(scrollPane);
	}

	/** Remove all nodes except the root node. */
	public void resetTree(UTreeNode node) {
		rootNode = node;
		treeModel.setRoot(node);
	}

	public void clear() {
		rootNode.removeAllChildren();
		treeModel.reload();
	}

	/**
	 * 设置自己的鼠标事件
	 * 
	 * @param lis
	 */
	public void setMyMouseListener(MouseListener lis) {
		tree.addMouseListener(lis);
	}

	public void setMyMouseMotionListener(MouseMotionListener lis) {
		tree.addMouseMotionListener(lis);
	}

	/** Remove the currently selected node. */
	public DefaultMutableTreeNode getRootNode() {
		return rootNode;
	}

	public void removeCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection
					.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
			if (parent != null) {
				treeModel.removeNodeFromParent(currentNode);
				return;
			}
		}

		// Either there was no selection, or the root was selected.
		toolkit.beep();
	}

	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();

		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath
					.getLastPathComponent());
		}

		return addObject(parentNode, child, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
			Object child) {
		return addObject(parent, child, false);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
			Object child, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = rootNode;
		}

		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}

	/**
	 * 获得当前选中的对象
	 * 
	 * @return
	 */
	public Object getSelectedLeafObj() {
		if (tree.getLastSelectedPathComponent() == null)
			return null;
		return ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent())
				.getUserObject();
	}
	public Object getSelectedLeafComponent() {
		return tree.getLastSelectedPathComponent();
	}

	/**
	 * 展开树的所有节点
	 * 
	 * @param tree
	 *            JTree
	 */
	public void expandOneLevelNodes() {
		int old = 0; // 展开之前的树的row
		int now = 0; // 展开之后的树的row
		old = tree.getRowCount();
		for (int i = 0; i < old; i++) {
			tree.expandRow(i);
		}
	}

	public void expandAllNodes() {
		int old = 0; // 展开之前的树的row
		int now = 0; // 展开之后的树的row
		do {
			old = tree.getRowCount();
			for (int i = 0; i < old; i++) {
				tree.expandRow(i);
			}
			now = tree.getRowCount();
		} while (now > old);
	}

	public void setMyTreeModelListener(TreeModelListener ls) {
		treeModel.addTreeModelListener(ls);
	}

	class MyTreeModelListener implements TreeModelListener {
		public void treeNodesChanged(TreeModelEvent e) {
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode) (e.getTreePath()
					.getLastPathComponent());

			/*
			 * If the event lists children, then the changed node is the child
			 * of the node we've already gotten. Otherwise, the changed node and
			 * the specified node are the same.
			 */
			try {
				int index = e.getChildIndices()[0];
				node = (DefaultMutableTreeNode) (node.getChildAt(index));
			} catch (NullPointerException exc) {
			}

			System.out.println("The user has finished editing the node.");
			System.out.println("New value: " + node.getUserObject());
		}

		public void treeNodesInserted(TreeModelEvent e) {
		}

		public void treeNodesRemoved(TreeModelEvent e) {
		}

		public void treeStructureChanged(TreeModelEvent e) {
		}
	}

	public class TreeMouseProcess implements MouseListener, MouseMotionListener {
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
				if(enablePopupMenu)
				displyPopMenu((Component) mouseEvent.getSource(), mouseEvent
						.getX(), mouseEvent.getY());
			}
			if (mouseEvent.getSource() == tree) {
				if (mouseCanSend) 
					eventProcessor.mouseClicked(mouseEvent);
				if(mouseListener != null )
					mouseListener.mouseClicked(mouseEvent);
			}
		}

		public void mousePressed(MouseEvent mouseEvent) {
			if (mouseEvent.getSource() instanceof JTree) {
				fromNode = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
//				System.out.println(fromNode);
			}
		}

		public void mouseReleased(MouseEvent mouseEvent) {

			tree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			fromNode = null;
			toNode = null;
		}

		public void mouseEntered(MouseEvent mouseEvent) {
		}

		public void mouseExited(MouseEvent mouseEvent) {
		}

		public void mouseDragged(MouseEvent mouseEvent) {
			if (mouseEvent.getSource() instanceof JTree) {
				tree.setCursor(DragSource.DefaultMoveDrop);

				// //////// / 拖动结点 //////////
				// 如果不想在拖动时显示移动结果，可以把下面代码放到MouseReleased里
				Point p = mouseEvent.getPoint();
				TreePath path = tree.getClosestPathForLocation(p.x, p.y);
				toNode = (DefaultMutableTreeNode) path.getLastPathComponent();

				if (fromNode != null && toNode != null
						&& (!fromNode.equals(toNode)) && !toNode.isLeaf() // 如果禁止拖动到叶结点，应加上这句
						&& fromNode.isLeaf()) {// 只能拖动叶结点
					treeModel.insertNodeInto(fromNode, toNode, 0);
					// Make sure the user can see the lovely new node.
					tree.scrollPathToVisible(new TreePath(fromNode.getPath()));
					tree.updateUI();
				}
				// ////////////////////////////
			}

		}

		public void mouseMoved(MouseEvent mouseEvent) {
		}
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if(elementTemplate != null)
			return new URect(elementTemplate.x,elementTemplate.y,elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		if (border.obj == null || border.obj instanceof Border)
			this.setBorder((Border) border.obj);
	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub
		if (width == 0)
			setBorder((Border) null);
		else
			setBorder(BorderFactory.createLineBorder(color.color, width));
	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub
		if(c!= null)
			this.setForeground(c.color);
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub

	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		this.filter = (TreeFilterI) filter;
	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		if(agr0 != null)
			setFont(agr0.font);

	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		elementTemplate= (UTreeTemplate)template;
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		eventProcessor = new UScrollPanelEventAdaptor(this, getUParent()
				.getEventAdaptor());
		for (int i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_MOUSE)) {
				mouseCanSend = true;
			}
		}

	}


	public void setAddedDatas(Object[] a) {
		// TODO Auto-generated method stub

	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void setComponentName(String name) {
		this.componentName = name;
	}

	public String getComponentName() {
		return componentName;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

		if (filter == null)
			return;
		Object data =  filter.getAddedData();
		updateAddedDatas(data);
	}
	public void updateAddedDatas(Object data) {
		// TODO Auto-generated method stub
		List list;
		tree.setRootVisible(true);
		if(data instanceof UTreeNodeForm) {
			UTreeNodeForm tForm = (UTreeNodeForm)data;
			rootNode = new UTreeNode(tForm.getObj());
			treeModel.setRoot(rootNode);
			list = (List) tForm.getSonList();
			if (list == null)
				return;
		}else {
			String rootName = filter.getRootName();
			if (rootName == null) {
				rootName = "root";
			}
			rootNode = new UTreeNode(rootName);
			treeModel.setRoot(rootNode);
			list = (List) data;
			if (list == null)
				return;
		}
		addListToTree(rootNode, list);
		expandOneLevelNodes();
		tree.setRootVisible(elementTemplate.rootVisible);
//		expandAllNodes();
//		tree.setRootVisible(false);
		// this.updateUI();
	}

	public void initContents() {
		// TODO Auto-generated method stub
		if(elementTemplate == null)
			return;
		tree.setRootVisible(elementTemplate.rootVisible);
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		popupMenu = new UPopupMenu();
		int n = menu.getItemCount();
		while (menu.getItemCount() > 0) {
			popupMenu.add(menu.getItem(0));
		}
	}

	public UPopupMenu getUPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(UPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void displyPopMenu(Component com, int x, int y) {
		if (popupMenu != null && popupMenu.getSubElements().length >= 1)
			popupMenu.show(com, x, y);
	}

	public void addListToTree(DefaultMutableTreeNode node, List list) {
		if (list == null)
			return;
		UTreeNodeForm obj = null;
		Object o;
		DefaultMutableTreeNode childNode = null;
		for (int i = 0; i < list.size(); i++) {
			obj = (UTreeNodeForm) list.get(i);
			o = obj.getObj();
			childNode = new DefaultMutableTreeNode(o);
			treeModel.insertNodeInto(childNode, node, node.getChildCount());
			if (obj.getSonList() != null) {
				addListToTree(childNode, (List) obj.getSonList());
			}
		}
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public void updateSelectLeaf(Object obj) {
		DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		if (selectedTreeNode != null) {
			selectedTreeNode.setUserObject(obj);
			tree.updateUI();
		}
	}

	public List getSelectChildObj() {
		List list = new ArrayList();
		if (tree.getLastSelectedPathComponent() == null)
			return null;
		DefaultMutableTreeNode treeNode = ((DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent());
		list.add(((DefaultMutableTreeNode) treeNode).getUserObject());
		Enumeration e = treeNode.children();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
					.nextElement();
			list.add(node.getUserObject());

			this.getSelectChildObj(node, list);
		}
		return list;
	}

	public void getSelectChildObj(DefaultMutableTreeNode treeNode, List list) {
		if (treeNode.isLeaf())
			return;
		Enumeration e = treeNode.children();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
					.nextElement();
			list.add(node.getUserObject());
			this.getSelectChildObj(node, list);
		}
	}
	public void setInitComponentData(Object data){
		
	}
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		Dimension dimension = new Dimension(w, h);
		this.setLocation(x, y);
		this.setSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
	}
	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}
	public void onClose(){
		
	}
	public void repaintComponent() {
	}
	public void setParameters(HashMap paras){
		
	}
	public HashMap getParameters(){
		return null;
	}
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}

	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = (UTreeTemplate)elementTemplate;
	}
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setdisplayText(String text) {
		// TODO Auto-generated method stub
		
	}
	public void addMouseListener(MouseListener l){
		mouseListener = l;
	}
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setActionComandString(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserData(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		enablePopupMenu = enable;
	}

	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}



}
