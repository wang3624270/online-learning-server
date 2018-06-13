package cn.edu.sdu.uims.component.jfx.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;

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
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

public class JFXControl extends JFXPanel implements UComponentI{
	protected HashMap parameters;
	protected String componentName;
	protected UPanelI parent = null;
	protected UElementTemplate elementTemplate;
	protected  Scene scene = null;
	protected Control control = null;
	protected Pane pane;
	
	public void  initControl(){
	}
	
	public void initJFX(){
        scene = new Scene(new Group());
        initControl();
        this.setScene(scene);		
	}
	
	
	
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initJFX();
            }
        });
	
	}

	@Override
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
	}

	@Override
	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if (elementTemplate != null)
			return new URect(elementTemplate.x, elementTemplate.y,
					elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	@Override
	public String getComponentName() {
		// TODO Auto-generated method stub
		return componentName;
	}

	public Object getData() {
		return null;
	}

	public void setData(Object obj) {

	}
	public void setParameters(HashMap map) {
		parameters = map;
	}

	@Override
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return this.elementTemplate;
	}

	@Override
	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap getParameters() {
		// TODO Auto-generated method stub
		return parameters;
	}


	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		// control.closeDevice();
	}

	@Override
	public void repaintComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActionComandString(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBorder(UBorder border) {
		this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));
	}

	@Override
	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		this.componentName = name;
	}

	@Override
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}

	@Override
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInitComponentData(Object data) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	@Override
	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setdisplayText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
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
	public void AddCommpntToContainer(Container container){
		container.add(this.getAWTComponent());
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
