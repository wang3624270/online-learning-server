package cn.edu.sdu.uims.component.label;

import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UTemplateComponentI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.ULabelTitleTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class ULabelTitle extends JPanel implements UTemplateComponentI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String componentName;
	private UPanelI parent;
	protected UTemplate template;
	JLabel content;
	protected UElementTemplate elementTemplate;
	public ULabelTitle() {

	}

	public void init() {
		
	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub

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

	public String getComponentName() {
		// TODO Auto-generated method stub
		return this.componentName;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return this.template;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return this.parent;
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
		this.componentName = name;
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
		this.template = template;

	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub
		if(arg0 != null)
			content.setText(arg0.toString());
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void ResetComponentContent() {
		// TODO Auto-generated method stub

	}

	public void initComponents() {
	}

	public void initContents() {
		if(template == null){
			return;
		}
		ULabelTitleTemplate temp = (ULabelTitleTemplate)template;
		setLayout(new BorderLayout());
		if(temp.text != null)
			content = new JLabel(temp.title +temp.text);
		else 
			content = new JLabel(temp.title);
		add(content);
	}

	public void initHandlerObject() {
		// TODO Auto-generated method stub

	}


	public void setData(Object obj) {
		// TODO Auto-generated method stub
		ULabelTitleTemplate temp = (ULabelTitleTemplate)template;		
		if(obj != null)
			content.setText(temp.title + obj.toString());	
		else 
			content.setText(temp.title);	
			
	}
	public void setInitComponentData(Object data){
		
	}

	public void initDataByHandlerAfterInitContents() {
		// TODO Auto-generated method stub
		
	}
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
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

	public HashMap getParameters() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub
		
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
	public void resetTemplate(UTemplate template) {
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
	public boolean testInvalidateData() {
		// TODO Auto-generated method stub
		return false;
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
