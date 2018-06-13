package cn.edu.sdu.uims.component.checkbox;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.JCheckBox;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.LabeValueTransI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UCheckBox extends JCheckBox implements UComponentI,
		LabeValueTransI {
	private String componentName;
	private UPanelI parent = null;
	protected UElementTemplate elementTemplate;

	protected UElementTemplate checkBoxTemplate;

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
		return new Boolean(isSelected());
	}

	public void initContents() {
		// TODO Auto-generated method stub
		this.setOpaque(false);
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return checkBoxTemplate;
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

	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public void setData(Object obj) {
		if (obj != null) {
			Boolean b = (Boolean) obj;
			this.setSelected(b.booleanValue());
		}
		else {
			this.setSelected(false);
		}
	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		setFont(agr0.font);
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.checkBoxTemplate = (UElementTemplate)template;
	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		int i;
		UPanelI p = (UPanelI) getParent();
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_CHANGE)) {
				this.addChangeListener(p.getEventAdaptor());
			}
		}
	}


	public void setAddedDatas(Object[] o) {
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

	public Object getLabel(Object value) {
		// TODO Auto-generated method stub
		return value;
	}

	public void setInitComponentData(Object data) {

	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
		// this.setLocation(x, y);
		// this.setPreferredSize(new Dimension(w,h));
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
		setText(label);
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
