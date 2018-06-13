package cn.edu.sdu.uims.component.button;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JToggleButton;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.UActionComponentI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UButtonTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UimsUtils;

public class UIconToggleButton extends JToggleButton implements
		UActionComponentI {

	protected UPanelI parent = null;
	protected String componentName;
	protected UButtonTemplate buttonTemplate;
	protected UElementTemplate elementTemplate;

	public void initContents() {
		// TODO Auto-generated method stub
		Icon icon = null;
		if (buttonTemplate != null) {
			this.setText(buttonTemplate.content);
			this.setActionCommand(buttonTemplate.cmd);
			if (buttonTemplate.iconName != null) {
				icon = UimsUtils.getCSClientIcon(buttonTemplate.iconName);
				setIcon(icon);
			}
			if (buttonTemplate.selectedIconName != null) {
				icon = UimsUtils.getCSClientIcon(buttonTemplate.selectedIconName);
				setSelectedIcon(icon);
			}
			if (buttonTemplate.pressedIconName != null) {
				icon = UimsUtils.getCSClientIcon(buttonTemplate.pressedIconName);
				setIcon(icon);
			}
		}
		this.setRequestFocusEnabled(true);
		this.setEnabled(true);
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
		if (buttonTemplate != null) {
			return buttonTemplate.iconName;
		}
		return null;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		requestFocusInWindow();
		return true;
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
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub
		this.setText((String) obj);
	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		this.setEnabled(b);
	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		super.setFont(agr0.font);
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		buttonTemplate = (UButtonTemplate) template;
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return buttonTemplate;
	}


	public void setAddedDatas(Object[] o) {
		// TODO Auto-generated method stub

	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				addActionListener(getUParent().getEventAdaptor());
			} else if (events[i].name.equals(EventConstants.EVENT_KEY)) {
				this.addKeyListener(getUParent().getEventAdaptor());
			}
		}

	}

	public void setComponentName(String name) {
		this.componentName = name;
	}

	public String getComponentName() {
		return componentName;
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void setInitComponentData(Object data) {

	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		Dimension dimension = new Dimension(w, h);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
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
		return getActionCommand();
	}

	public void setActionComandString(String str) {
		// TODO Auto-generated method stub
		setActionCommand(str);
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
