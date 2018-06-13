package cn.edu.sdu.uims.base;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UComponentAdapoter implements UComponentI {

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}


	
	
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
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


	public void setBorder(UBorder border) {
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
		// TODO Auto-generated method stub

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


	public URect getBoundRect() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setArrangeType(int type) {
		// TODO Auto-generated method stub
		
	}


	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub
		
	}


	public void setTemplate(UTemplate Template) {
		// TODO Auto-generated method stub
		
	}


	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}



	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
	}





	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub	
	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub
	}


	public void init() {
		// TODO Auto-generated method stub	
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
	}
	public void setComponentName(String name){
	}
	public String getComponentName(){
		return null;
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



	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}



	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}


	public void initContents() {
		// TODO Auto-generated method stub
		
	}





	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setInitComponentData(Object data){
		
	}



	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
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
		return null;
	}



	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		
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


	public void setEnabled(boolean b) {
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
