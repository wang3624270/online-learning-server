package cn.edu.sdu.uims.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.frame.def.UStatusbarTemplate;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.progress.ProgressBar;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;

public class UStatusBar extends JPanel implements UComponentI {
	UStatusbarTemplate template;
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	static final int progressBarLen=100;
	private ProgressBar progressBar = null;
	
	private String componentName;
	private UPanelI parent;
	protected UElementTemplate elementTemplate;

	public void UStatusBar(){
		
	}
	public void init(){
		if(template == null)
			return;
		setLayout(new GridLayout(1,3));
		this.setSize(1600, template.height);
		if(template.bgColorName != null)
			this.setBackground(UFactory.getModelSession().getColorByName(template.bgColorName).color);
		add(label1);
		String str = template.department+"  "+"version "+template.version+"  ";
		if(template.telephone != null)
			str += "电话:"+template.telephone + "  ";
		if(template.email != null)
			str += "邮箱:"+template.email + "  ";
		JLabel l = new JLabel(str);
		if(template.colorName != null)
			l.setForeground(UFactory.getModelSession().getColorByName(template.colorName).color);
		if(template.fontName != null)
			l.setFont(UFactory.getModelSession().getFontByName(template.fontName).font);
		add(l);
		if(!template.hasProgress){
			add(label2);
		}
		else {
			initProgressbar();
		}
	}

	private void initProgressbar(){
		progressBar = new ProgressBar();
		progressBar.setOrientation(JProgressBar.HORIZONTAL);
		progressBar.setMinimum(0);
		progressBar.setMaximum(progressBarLen);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setString("");
		progressBar.setPreferredSize(new Dimension(400,15));
		progressBar.setBorderPainted(false);
		progressBar.setForeground(new Color(84,123,216));
		progressBar.setVisible(true);
		add(progressBar);
	}				
	public void setTemplate(UTemplate template) {
		this.template = (UStatusbarTemplate)template;
	}

	public ProgressBar getProgressBar(){
		return progressBar;
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
		// TODO Auto-generated method stub

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
		super.setFont(agr0.font);
	}


	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}







	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}


	public void setVerticalAlignment(int arg0) {
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
	public void setInitComponentData(Object data){
		
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
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}

}
