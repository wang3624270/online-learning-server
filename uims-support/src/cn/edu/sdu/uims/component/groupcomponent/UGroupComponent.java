package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.ChangeListener;

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
import cn.edu.sdu.uims.def.UGroupElementTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UGroupComponent implements UGroupComponentI {
	protected UGroupElementTemplate groupElementTemplate = null;
	protected int comnum;
	protected Container container;
	protected String componentName;
	protected UPanelI parent;
	protected UElementTemplate elementTemplate;
	protected ChangeListener changeListener = null;
	public void addChangeListener(ChangeListener listener){
		changeListener = listener;
	}
	public void removeChangeListener(ChangeListener Listener){
		changeListener = null;
	}
	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(null);
		}
		container.add(com.getAWTComponent());
		comnum++;
	}

	public void setContainer(List List) {

	}

	public void addEvents(UEventAttribute events[]) {
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
		return container;
	}

	public String getComponentName() {
		// TODO Auto-generated method stub
		return componentName;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return groupElementTemplate;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public void initContents() {
		// TODO Auto-generated method stub

	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isVisible() {
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
		this.groupElementTemplate = (UGroupElementTemplate) template;
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void setInitComponentData(Object data) {

	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		container.setBounds(x, y, w, h);
		// this.container.setLocation(x,y);
		// this.container.setPreferredSize(new Dimension(w, h));
	}

	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	public void onClose() {

	}

	public void repaintComponent() {
	}

	public void addComponentBefore() {

	}

	public void addComponentAfter() {

	}

	public UPanelI getCurrentDisplayPagePanel() {
		return null;
	}
	public UComponentI getCurrentDisplayPageCommonent() {
		return null;
	}

	public void reLayoutComponents() {

	}

	public void setSubComponentVisible(String name, boolean visible) {

	}
	public void setSubComponentVisibleAttribute(String name, boolean visible) {

	}

	public void setParameters(HashMap paras) {

	}

	public HashMap getParameters() {
		return null;
	}

	public URect makeSubComponentsBox(UComponentI[] coms) {
		UPanelI p;
		URect rect2 = null;
		if(coms == null)
			return null;
		for (int i = 0; i < coms.length; i++) {
			if (coms[i] instanceof UPanelI) {
				p = (UPanelI) coms[i];
				rect2 = p.makeInnerBounds();
			}
		}
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

	public void setComponentBounds(UComponentI[] coms) {
		UPanelI p;
		if(coms == null)
			return;
		for (int i = 0; i < coms.length; i++) {
			if (coms[i] instanceof UPanelI) {
				p = (UPanelI) coms[i];
				p.setComponentBounds();
			}
		}
	}
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}


	public void setCurrentPagePanel(int index) {
		// TODO Auto-generated method stub
		
	}
	public void setCurrentPagePanelByComponentName(String comName) {
		// TODO Auto-generated method stub
		
	}

	public boolean getCanScrolling() {
		// TODO Auto-generated method stub
		return false;
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
	public void setPageIconDisplayInfo(List<String> infoList) {
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
	public void componentRepaint(){
		
	}
}
