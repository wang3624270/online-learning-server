package cn.edu.sdu.uims.component.tree.checktree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * CheckBox树,树的节点必须是CheckBoxNode类型
 * @author zhaopeng 2007.3.22
 * 
 */
public class CheckBoxTree extends JTree {

	/**一般类型*/
	public static final String TYPE_NOMAL="normal";
	/**如果子节点有选中的则父节点保持选中,如果子节点全不选中,则父节点不选中*/
	public static final String TYPE_FATHERCHANGE="fatherchange";
	
	private String type=TYPE_NOMAL;
	public CheckBoxTree(){
		this(new DefaultTreeModel(null),new CheckBoxTreeCellRenderer());
	}
	public CheckBoxTree(DefaultTreeModel model) {
		this(model,new CheckBoxTreeCellRenderer());

	}

	public CheckBoxTree(DefaultTreeModel model,CheckBoxTreeCellRenderer render) {
		super(model);
		this.setCellRenderer(render);
		this.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.addMouseListener(new NodeSelectionListener(this));
		this.setShowsRootHandles(true);   
	}

	/**
	 * 取得选中的节点
	 * @return 选中的节点数组，里面存放的是勾选中的CheckBoxNode对象
	 */
	public List<DefaultMutableTreeNode>  getCheckedNodes(){
		List<DefaultMutableTreeNode> checkedNodes=
			new LinkedList<DefaultMutableTreeNode>();
		DefaultTreeModel model=(DefaultTreeModel) this.getModel();
		findCheckedNodes((CheckBoxNode) model.getRoot(),checkedNodes);
	    return checkedNodes;
	}
	/**
	 * 递归遍历树节点，取得所有选中的节点。
	 * @param node 遍历起始节点
	 * @param nodeList 选中节点列表
	 */
	private void findCheckedNodes(CheckBoxNode node,List<DefaultMutableTreeNode> nodeList){
		if(node==null)return;
		if(node.isSelected){
			nodeList.add(node);
		}
		int childCount=node.getChildCount();
		for(int i=0;i<childCount;i++){
			findCheckedNodes((CheckBoxNode) node.getChildAt(i),nodeList);
		}
	}
	/**
	 * 取得未选中的节点
	 * @return 选中的节点数组，里面存放的是未勾选中的CheckBoxNode对象
	 */
	public List<DefaultMutableTreeNode>  getNotCheckedNodes(){
		List<DefaultMutableTreeNode> notCheckedNodes=
			new LinkedList<DefaultMutableTreeNode>();
		DefaultTreeModel model=(DefaultTreeModel) this.getModel();
		findNotCheckedNodes((CheckBoxNode) model.getRoot(),notCheckedNodes);
	    return notCheckedNodes;
	}
	/**
	 * 递归遍历树节点，取得所有未选中的节点。
	 * @param node 遍历起始节点
	 * @param nodeList 选中节点列表
	 */
	private void findNotCheckedNodes(CheckBoxNode node,List<DefaultMutableTreeNode> nodeList){
		if(node==null)return;
		if(!node.isSelected){
			nodeList.add(node);
		}
		int childCount=node.getChildCount();
		for(int i=0;i<childCount;i++){
			findNotCheckedNodes((CheckBoxNode) node.getChildAt(i),nodeList);
		}
	}

	/**
	 * 树节点选中点击事件响应类。
	 * @author Administrator
	 *
	 */
	class NodeSelectionListener extends MouseAdapter {
		JTree tree;

		NodeSelectionListener(JTree tree) {
			this.tree = tree;
		}

		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int row = tree.getRowForLocation(x, y);
			TreePath path = tree.getPathForRow(row);
			if (path != null) {
				CheckBoxNode node = (CheckBoxNode) path.getLastPathComponent();
				boolean isSelected = !(node.isSelected());
				node.setSelected(isSelected);
				// 赵鹏注释掉，-----防止点中一个节点后树的收起
				// if (node.getSelectionMode() == CheckBoxNode.DIG_IN_SELECTION)
				// {
				// if (isSelected) {
				// tree.expandPath(path);
				// }
				// else {
				// tree.collapsePath(path);
				// }
				// }
				((DefaultTreeModel) tree.getModel()).nodeChanged(node);
				if(type.equals(CheckBoxTree.TYPE_FATHERCHANGE)){
					
					CheckBoxNode parent=(CheckBoxNode) node.getParent();
					while(parent!=null){
						int selectionMode=parent.getSelectionMode();
						parent.setSelectionMode(CheckBoxNode.SINGLE_SELECTION);
						parent.setSelected(isChildSelected(parent));
						parent.setSelectionMode(selectionMode);
						((DefaultTreeModel) tree.getModel()).nodeChanged(parent);
						parent=(CheckBoxNode) parent.getParent();
					}
				}
				if (row == 0) {
					tree.revalidate();
					tree.repaint();
				}		

				this.tree.updateUI();
			}

		}
	}
	/**
	 * 取得根节点
	 * @return
	 */
	public CheckBoxNode getRootNode(){
		DefaultTreeModel model=(DefaultTreeModel) this.getModel();
		return (CheckBoxNode) model.getRoot();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 判断一个节点的子节点是否有选中的
	 * @param parent
	 * @return
	 */
	public boolean isChildSelected(CheckBoxNode parent){
		int childCount=parent.getChildCount();
		boolean b=false;
		CheckBoxNode child;
		for(int i=0;i<childCount;i++){
			child=(CheckBoxNode) parent.getChildAt(i);
			if(child.isSelected){
				b=true;
				break;
			}
		}
		return b;
	}
}

