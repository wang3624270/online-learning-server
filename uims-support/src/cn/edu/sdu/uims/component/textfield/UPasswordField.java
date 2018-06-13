package cn.edu.sdu.uims.component.textfield;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UTextFieldTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;

public class UPasswordField extends JPasswordField implements UComponentI {
	protected String componentName;
	protected UPanelI parent = null;
	protected UTextFieldTemplate template;
	protected UElementTemplate elementTemplate;

	
	public FilterI getFilter() {
		return null;
	}

	public void setFilter(FilterI filter) {
	}
	public FilterI getFilter1() {
		return null;
	}

	public void setFilter1(FilterI filter) {
	}

	
	public void init() {
		
	}

	public void setBorder(int width, UColor color){
		if(width == 0 )
			setBorder((Border)null);
		else
			setBorder(BorderFactory.createLineBorder(color.color,width));				
	}
	
	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		if(border.obj == null)
			this.setBorder((Border) border.obj);
		else
		setBorder((int)border.width,UFactory.getModelSession().getColorByName(border.colorName));
	}

	
	
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.template = (UTextFieldTemplate)template;
		
	}

	
	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public Object getData() {
		String pwd = String.valueOf(getPassword());
		return pwd;
	}

	
	public void setData(Object obj) {
		if(obj!= null)
			setText(obj.toString());
	}


	
	public boolean requestFirstFoucus() {
		if(this.isEditable()){
			this.requestFocus();
			return true;
		}
		return false;
	}


	
	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		if(this.isEditable() && getPassword().toString().equals(""))
			return true;
		else
			return false;
	}


	
	public UHandlerI getURuleI() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void getBinuessData() {
		// TODO Auto-generated method stub
		
	}

	
	public void setBinuessData() {
		// TODO Auto-generated method stub
		
	}

	
	public void submit() {
		// TODO Auto-generated method stub
		
	}



	public void setBorderType(int type) {
		// TODO Auto-generated method stub
		
	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub
		
	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public void setFont(UFont agr0) {
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

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return template;
	}



	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				this.addActionListener(getUParent().getEventAdaptor());
			}
		}
		
	}




	public void setAddedDatas(Object[] o) {
		// TODO Auto-generated method stub
		
	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub
		
	}
	public void setComponentName(String name){
		this.componentName = name;
	}
	public String getComponentName(){
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


	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		
	}
	public Object getLabel(Object value) {
		// TODO Auto-generated method stub
		return value;
	}
	public void initContents() {
		// TODO Auto-generated method stub
		this.setColumns(template.maxLength);
	}
	public void setInitComponentData(Object data){
		
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x,y,w,h);
//		this.setLocation(x, y);
//		this.setPreferredSize(new Dimension(w,h));
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
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}



}
