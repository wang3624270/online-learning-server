package cn.edu.sdu.uims.component.panel;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UTableI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.dialog.UOldDialogBase;
import cn.edu.sdu.uims.component.event.UEventListener;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UMenuPanelMapTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.form.UPanelStatusFormI;
import cn.edu.sdu.uims.frame.UClientFrame;
import cn.edu.sdu.uims.handler.ToolCommandHandlerI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;

public class UOldPanelBase extends SuperPanel implements UPanelI {

	private String componentName = null;
	private UPanelI parent = null;
	protected HashMap paraMap = null;
	protected UElementTemplate elementTemplate;

	public void setParameters(HashMap paras){
		paraMap = paras;
	}
	public HashMap getParameters(){
		return paraMap;
	}
	
	public UOldPanelBase() {
		// TODO Auto-generated constructor stub
	}
	public void init(){
		
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
		return componentName;
	}


	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}


	public ToolCommandHandlerI getToolCommandHandler() {
		// TODO Auto-generated method stub
		return this;
	}



	
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
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

	

	public void setAddedDatas(Object[] o) {
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

	


	
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub

	}

	
	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}


	public void setUParent(UPanelI parent) {
		this.parent = parent;

	}


	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}
	
	public void addActionListener(ActionListener l) {
		// TODO Auto-generated method stub
		
	}

	public void addComponent(UComponentI com, int layout) {
		// TODO Auto-generated method stub
		
	}

	public UEventListener getEventAdaptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeAtionListener(ActionListener l) {
		// TODO Auto-generated method stub
		
	}
	
	public void sendActionEventToParent(String cmd) {
		// TODO Auto-generated method stub
		
	}

	public void initComponents() {
		// TODO Auto-generated method stub
		
	}

	public void initContents() {
		// TODO Auto-generated method stub
		
	}

	public void initHandlerObject(String name,UHandlerI parent) {
		// TODO Auto-generated method stub
		
	}

	public void updatePanel(UFormI form) {
		// TODO Auto-generated method stub
		
	}
	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		
	}

	public void setDialogForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	public UTableI getTableComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		
	}
	public UPanelI startDialog(String name, UFormI form) {
		UTemplate temp;
		String className = null;
		UOldDialogBase p = null;
		try {
			temp = (UTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_DIALOG, name);
			UDialogPanel item = (UDialogPanel) temp.newComponent();
			if (item != null) {
				item.setComponentName(name);
				item.setTemplate(temp);
				item.setDialogForm(form);
				item.init();
//				item.addActionListener(component.getEventAdaptor());
				return item;
			}
		} catch (Exception e) {
			System.out.println(className);
		}
		return null;
	}
	
	public void ResetComponentContent() {
		// TODO Auto-generated method stub
		
	}

	public HashMap getParamenters() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParamenters(HashMap paras) {
		// TODO Auto-generated method stub
		
	}
	
	public void deactivated(){
		UClientFrame frame = UClientFrame.getFrame();
//		frame.disableToolBarButtons();
	}
	
	public void closed(){
		UClientFrame frame = UClientFrame.getFrame();
//		frame.disableToolBarButtons();
	}
	public void updateAddedData(String name) {
		// TODO Auto-generated method stub
		
	}
	public void setInitComponentData(Object data){
		
	}

	public UFormI initDataByHandlerAfterInitContents() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getSubComponentInitData(String name) {
		// TODO Auto-generated method stub
		return null;
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

	public UComponentI[] getAllSubComponents() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public UPanelI getCurrentDisplayPagePanel(){
		return null;
	}

	public void reLayoutComponents() {
		// TODO Auto-generated method stub
		
	}
	public void setSubComponentVisible(String name, boolean visible) {
		// TODO Auto-generated method stub
		
	}

	public void initInteraction() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setUpperNoScrolling() {
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

	public URect makeInnerBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	public URect gerInnerBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setComponentBounds() {
		// TODO Auto-generated method stub
		
	}
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentPagePanel(int index) {
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
	public void replaceTemplateContent() {
		// TODO Auto-generated method stub
		
	}
	
	public void setOriginalTemplate(UMenuPanelMapTemplate originalTemplate) {
		// TODO Auto-generated method stub
		
	}

	public void setPathNameString(String pathNameString) {
		// TODO Auto-generated method stub
		
	}

	public void sendhandlerProcess(String methodName, EventObject event,
			String actionCmd) {
		Method method;
		if (methodName.substring(0, 2).equals("do")) {
			try{
			method = this.getClass().getMethod(methodName);
			method.invoke(this);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public UHandlerI getHandler() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void sendFormHandler(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	public void setHandlerOutFormData() {
		// TODO Auto-generated method stub
		
	}

	public String getHandlerId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStartMenuName(String startMenuName) {
		// TODO Auto-generated method stub
		
	}

	public void processCallBack(List map) {
		// TODO Auto-generated method stub
		
	}

	public void processUpdate(String methodName, Object paras) {
		// TODO Auto-generated method stub
		
	}

	public UPanelI searchUPanelById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void resetTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public UTableI getInnerTable() {
		// TODO Auto-generated method stub
		return null;
	}
	public Boolean isFinishedInit(){
		return true;
	}
	@Override
	public String[] getClosePanels() {
		// TODO Auto-generated method stub
		return null;
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
	public void setTimeControlActionItem(List list) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void closePopUpPanel(int type) {
		// TODO Auto-generated method stub
		
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
	public void setUseOutContainer(boolean isUse) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setOutCantiner(Container outContainer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void refreshComponentData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPanelDisplayStatus(UPanelStatusFormI form) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCurrentPagePanelByComponentName(String comName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public UComponentI getCurrentDisplayPageCommonent() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateComAddedDataList(String panelName, String comName,
			List dataList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateQueryComAddedDataList(String panelName, String queryName,
			String comName, List dataList) {
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
