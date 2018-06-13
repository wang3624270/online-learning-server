package cn.edu.sdu.uims.component.label;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.UActionComponentI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class ULabel extends JLabel implements UActionComponentI {

	private UPanelI parent;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String componentName;
	protected UElementTemplate elementTemplate;

	public ULabel() {
		this("");
	}

	public ULabel(String arg0) {
		super(arg0);
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
	}

	public void setBorder(int width, UColor color) {
		if (width == 0)
			setBorder((Border) null);
		else
			setBorder(BorderFactory.createLineBorder(color.color, width));
	}

	public void setBorder(UBorder border) {
		if (border.obj == null || border.obj instanceof Border)
			this.setBorder((Border) border.obj);
	}

	public Object getData() {
		return this.getText();
	}

	public void setData(Object obj) {
		if (obj != null)
			this.setText(obj.toString());
		else 
			this.setText("");

	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasEmptyFileds() {
		return false;
	}

	public void setBorderType(int type) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub\
		if(c != null)
			setForeground(c.color);
	}

	public Component getAWTComponent() {
		return this;
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
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
		if(agr0 != null)
		super.setFont(agr0.font);
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if(elementTemplate != null)
			return new URect(elementTemplate.x,elementTemplate.y,elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public void addActionListener(ActionListener l) {
		// TODO Auto-generated method stub

	}

	public void setActionCommand(String name) {
		// TODO Auto-generated method stub

	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub

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

	public void initContents() {
		// TODO Auto-generated method stub

	}

	public void setInitComponentData(Object data) {

	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds((int) (x * UConstants.SA / UConstants.SB), (int) (y
				* UConstants.SA / UConstants.SB),
				(int) (w * UConstants.SA / UConstants.SB), (int) (h
						* UConstants.SA / UConstants.SB));
	}

	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	public void onClose() {

	}

	public void setParameters(HashMap paras) {

	}

	public HashMap getParameters() {
		return null;
	}

	public void repaintComponent() {
		// TODO Auto-generated method stub

	}

	public void setForeground(Color color) {
		super.setForeground(color);
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
		if(label != null)
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
		if(c != null)
			this.setBackground(c.color);
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
