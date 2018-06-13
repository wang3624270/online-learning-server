package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.USplitPane;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentPageTree extends UGroupComponentPage implements TreeSelectionListener{
	protected USplitPane splitPane = null;
	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;
	private JTree tree;
	private class NodeObject{
		private String label;
		private Object obj;
		public NodeObject(String label1, Object o) {
			label = label1;
			obj = o;
		}
		public String toString(){
			return label;
		}
	}
	public Component getContentPanel(){
		return splitPane;
	}
	public void addMapToTree(DefaultMutableTreeNode node, HashMap map) {
		if (map == null)
			return;
		Object o;
		DefaultMutableTreeNode childNode = null;
		Iterator ie = map.keySet().iterator();
		String key;
		UComponentI com;
		while(ie.hasNext()) {
			key =(String)ie.next();
			o = map.get(key);
			childNode = new DefaultMutableTreeNode(key);
			treeModel.insertNodeInto(childNode, node, node.getChildCount());
			if (o instanceof HashMap) {
				addMapToTree(childNode, (HashMap)o);
				childNode.setUserObject(new NodeObject(key,null));
			}
			else {
				childNode.setUserObject(new NodeObject(key,o));
			}
		}
	}

	public void expandOneLevelNodes() {
		int old = 0; 
		old = tree.getRowCount();
		for (int i = 0; i < old; i++) {
			tree.expandRow(i);
		}
	}
	public HashMap<String , Object> changeComListToMap(){
		if(comList == null)
			return null;
		Object o;
		UPageComponentDescription comDescription;
		UComponentI com;
		UElementTemplate ele;
		String str;
		StringTokenizer sz;
		int i,j,count;
		HashMap<String, Object>dataMap = new LinkedHashMap<String, Object>(); 
		HashMap<String, Object> map, tmp;
		for (i = 0; i < comList.size(); i++) {
			comDescription = (UPageComponentDescription)comList.get(i);
			com = (UComponentI)comDescription.com;
			ele = com.getElementTemplate();
			sz = new StringTokenizer(ele.text, "-");
			count = sz.countTokens();
			map = dataMap;
			for( j = 0; j < count-1;j++) {
				str = sz.nextToken();
				o = map.get(str);
				if(o == null) {
					o = new LinkedHashMap<String, Object>();
					map.put(str, o);
				} 
				map = (HashMap<String, Object>)map.get(str);		
			}
			str = sz.nextToken();
			map.put(str, comDescription);
		}
		return dataMap;
	}
	public void getInitControlData(){
		HashMap<String , Object> dataMap = this.changeComListToMap();
		if(dataMap == null)
			return;
		addMapToTree(rootNode, dataMap);
		expandOneLevelNodes();
		tree.setRootVisible(false);
	}

	public void initControlPanel() {
		splitPane = new USplitPane(USplitPane.HORIZONTAL_SPLIT);
		rootNode = new DefaultMutableTreeNode("Root Node");
		treeModel = new DefaultTreeModel(rootNode);
		tree = new JTree(treeModel);
		tree.setRootVisible(true);
		tree.addTreeSelectionListener(this);
//		tree.getSelectionModel().setSelectionMode(
//				TreeSelectionModel.SINGLE_TREE_SELECTION);		
		splitPane.setLeftComponent(tree);	
		splitPane.setDividerLocation(groupElementTemplate.divw);
		splitPane.setDividerSize(groupElementTemplate.divsw);
		splitPane.setRightComponent(contentPanel);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if(selectedNode == null)
			return;
		NodeObject o = (NodeObject)selectedNode.getUserObject();
		if(comList == null ||o == null || o.obj == null )
			return ;
		for(int i= 0; i < comList.size();i++) {
			if( o.obj == comList.get(i))
				setCurrentPagePanel(i);
		}
	}
}
