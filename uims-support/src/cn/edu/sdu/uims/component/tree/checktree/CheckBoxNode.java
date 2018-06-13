package cn.edu.sdu.uims.component.tree.checktree;

/**
 * checkBoxt  reeNode
 * @author zhaopeng 2007.3.22
 *
 */

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

public class CheckBoxNode extends DefaultMutableTreeNode {
	public final static int SINGLE_SELECTION = 0;

	public final static int DIG_IN_SELECTION = 4;

	private final String NULL_LABEL = "未知";

	protected int selectionMode;

	protected boolean isSelected;

	public CheckBoxNode() {
		this(null);
	}

	public CheckBoxNode(Object userObject) {
		this(userObject, false);
	}

	public CheckBoxNode(Object userObject,boolean isSelected) {
		super();
		this.setUserObject(userObject);
		this.isSelected = isSelected;
		setSelectionMode(DIG_IN_SELECTION);
		// setSelectionMode(SINGLE_SELECTION);
	}

	/**
	 * 覆盖setUserObject和getUserObject 方法以"未知"代替空值
	 */
	public void setUserObject(Object userObject) {
		if (userObject == null) {
			super.setUserObject(this.NULL_LABEL);
		} else {
			super.setUserObject(userObject);
		}
	}

	public Object getUserObject() {

		if (super.getUserObject() instanceof String
				&& super.getUserObject().equals(this.NULL_LABEL)) {
			return null;
		} else {
			return super.getUserObject();
		}
	}

	public void setSelectionMode(int mode) {
		selectionMode = mode;
	}

	public int getSelectionMode() {
		return selectionMode;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
			Enumeration enume = this.children.elements();
			while (enume.hasMoreElements()) {
				CheckBoxNode node = (CheckBoxNode) enume.nextElement();
				node.setSelected(isSelected);
			}
		}
	}
	
	public boolean isSelected() {
		return isSelected;
	}
}
