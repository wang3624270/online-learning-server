package cn.edu.sdu.uims.component.checkbox;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UCheckBoxGroupTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UCheckBoxGroup extends JPanel implements UComponentI {

	protected UCheckBoxGroupTemplate groupTemplate;
	protected String compoundName;
	protected UPanelI parentI;
	protected UCheckBoxValue checkBoxs[];
	protected int row = 0, column = 0;
	protected int innerWidth = -1, innerHeight = -1, innerX = -1, innerY = -1;
	protected UElementTemplate elementTemplate;

	protected ActionListener actionListener = null;

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub

	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		return new URect(this.innerX, this.innerY, this.innerWidth,
				this.innerHeight);
	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public String getComponentName() {
		// TODO Auto-generated method stub
		return compoundName;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return this.groupTemplate;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parentI;
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

	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub
	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub

	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		compoundName = name;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		if (checkBoxs == null)
			return null;
		int i, j;
		j = 0;
		for (i = 0; i < checkBoxs.length; i++) {
			if (checkBoxs[i].isSelected())
				j++;
		}
		ListOptionInfo a[] = new ListOptionInfo[j];
		j = 0;
		for (i = 0; i < checkBoxs.length; i++) {
			if (checkBoxs[i].isSelected()) {
				a[j++] = checkBoxs[i].getListOptionInfo();
			}
		}
		return a;
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return;
		int i, j;
		ListOptionInfo a[] = (ListOptionInfo[]) obj;
		for (j = 0; j < checkBoxs.length; j++)
			checkBoxs[j].setSelected(false);
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < checkBoxs.length; j++) {
				if (checkBoxs[j].getListOptionInfo().getValue().equals(
						a[i].getValue())) {
					checkBoxs[j].setSelected(true);
					break;
				}
			}
		}
	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	public ActionListener getActionListener() {

		return actionListener;
	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub

	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		groupTemplate = (UCheckBoxGroupTemplate) template;
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		parentI = parent;
	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void initContents() {
		// TODO Auto-generated method stub
		UCheckBoxGroupTemplate temp;
		JCheckBox jBox;
		HashMap map = null;
		int i;
		Object obj = null;
		if (parentI != null)
			obj = parentI.getSubComponentInitData(compoundName);
		if (obj == null) {
			temp = this.groupTemplate;
		} else {
			map = (HashMap) obj;
			temp = (UCheckBoxGroupTemplate) map.get("template");
		}
		if (temp == null)
			temp = new UCheckBoxGroupTemplate();
		if (map != null) {
			Integer ii = (Integer) map.get("row");
			temp.row = ii.intValue();
			ii = (Integer) map.get("column");
			temp.column = ii.intValue();
			ArrayList list = (ArrayList) map.get("items");
			temp.addedDatas = list;
		} else {
			
		}
		this.setLayout(new GridLayout(temp.row, temp.column));
		if (temp.addedDatas == null)
			return;
		checkBoxs = new UCheckBoxValue[temp.addedDatas.size()];
		ListOptionInfo li;
		for (i = 0; i < checkBoxs.length; i++) {
			if (temp.addedDatas.get(i) != null) {
				li = (ListOptionInfo)temp.addedDatas.get(i);
				checkBoxs[i] = new UCheckBoxValue();
				checkBoxs[i].setListOptionInfo(li);
				checkBoxs[i].setText(li.getLabel());
				checkBoxs[i].setTemplate(temp);
				if (getActionListener() != null)
					checkBoxs[i].addActionListener(getActionListener());
				this.add(checkBoxs[i].getAWTComponent());
			}
		}
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		Dimension dimension = new Dimension(w, h);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
		this.innerHeight = h;
		this.innerWidth = w;
		this.innerX = x;
		this.innerY = y;
	}

	public void setInitComponentData(Object data) {
		// TODO Auto-generated method stub

	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	public void onClose() {

	}

	public void repaintComponent() {
	}

	public void setParameters(HashMap paras) {

	}

	public HashMap getParameters() {
		return null;
	}
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}



	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
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
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAddedInnerTextFiledValues() {
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

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
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
