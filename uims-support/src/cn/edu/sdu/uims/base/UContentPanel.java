package cn.edu.sdu.uims.base;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.component.event.UEventListener;
import cn.edu.sdu.uims.def.UContentTemplate;
import cn.edu.sdu.uims.def.UHandlerTemplate;
import cn.edu.sdu.uims.def.UMenuPanelMapTemplate;
import cn.edu.sdu.uims.form.UPanelStatusFormI;
import cn.edu.sdu.uims.handler.DataOperatorHandlerI;
import cn.edu.sdu.uims.handler.ToolCommandHandlerI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UContentPanel extends UComponentAdapoter implements UPanelI {
	protected UContentTemplate contentTemplate = null;
	public UHandlerI handler = null;
	protected String componentName;
	protected UPanelI parent;

	public UContentPanel() {
		this(null);
	}

	public UContentPanel(UContentTemplate contentTemplate) {
		super();
		this.contentTemplate = contentTemplate;
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.contentTemplate = (UContentTemplate) template;
	}

	public void init() {

		// // initPaperSize();
		// form = initFormData();
		// setData(form);
		initHandlerObject(contentTemplate.name,null);
		// initComponents();
		// setParameterData(null, form, null);
		// exContent("doc");
	}

	public void exContent(String type) {

	}


	public void setData(Object obj) {
		String attrName, methodName;
		UContentElementI elementI = null;
		Method method;
		int i;
		UFormI dataForm = (UFormI) obj;
		if (dataForm != null)
			try {
				for (i = 0; i < contentTemplate.contentNum; i++) {
					elementI = contentTemplate.contents[i].object;
					attrName = contentTemplate.contents[i].dataFormMember;
					if (elementI != null && attrName != null
							&& !attrName.equals("")) {
						methodName = "get"
								+ attrName.substring(0, 1).toUpperCase()
								+ attrName.substring(1, attrName.length());
						method = dataForm.getClass().getMethod(methodName);
						elementI.setData(method.invoke(dataForm));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void initPaperSize() {
	}

	public void initTitle() {

	}

	public void initComponents() {
		int i;
		for (i = 0; i < contentTemplate.contentNum; i++) {
			contentTemplate.contents[i].object.init();
		}
	}


	public Object getData() {
		return null;
	}

	public UFormI initFormData() {
		UFormI form = null;
		UHandlerTemplate ruleTemplate;
		try {
			// if (contentTemplate.handlerClassName != null) {
			// ruleTemplate = (UHandlerTemplate) UFactory.getInstance()
			// .getTemplate(UConstants.MAPKEY_HANDLER,
			// contentTemplate.handlerClassName);
			// ruleTemplate.instance.initFormData();
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	public void setParameterData(HashMap map, UFormI form, UComponentI father) {
		UContentElementI obj;
		int i;
		for (i = 0; i < contentTemplate.contentNum; i++) {
			obj = (UContentElementI) contentTemplate.contents[i].object;
			obj.setParameterData(contentTemplate.contents[i].paras, form,
					father);
		}
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

	public UTableI getTableComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getToolActions() {
		// TODO Auto-generated method stub
		return null;
	}

	public ToolCommandHandlerI getToolCommandHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeAtionListener(ActionListener l) {
		// TODO Auto-generated method stub

	}

	public void sendActionEventToParent(String cmd) {
		// TODO Auto-generated method stub

	}

	public void setDialogForm(UFormI form) {
		// TODO Auto-generated method stub

	}

	public void updatePanel(UFormI form) {
		// TODO Auto-generated method stub

	}

	public void ResetComponentContent() {
		// TODO Auto-generated method stub

	}

	public void initHandlerObject(String name,UHandlerI parent) {
		// TODO Auto-generated method stub
		UFormI form;
		try {
			if (contentTemplate.handlerClassName != null) {
				handler = (UHandlerI) Class.forName(
						contentTemplate.handlerClassName).newInstance();
				if (contentTemplate.dataFormClassName != null) {
					form = (UFormI) Class.forName(
							contentTemplate.dataFormClassName).newInstance();
					handler.setForm(form);
					// getFormMemberMethod(form);
				} else
					handler.setForm(null);
				((DataOperatorHandlerI) handler).setComponent(this);
//				((DataOperatorHandlerI) handler).setComponent(this);
				// handler.initFormData();
				// ((UFormHandlerI) handler).initAddedData();
			}
		} catch (Exception e) {
			System.out.println("panelTemplate.handlerClassName ="
					+ contentTemplate.handlerClassName);
			e.printStackTrace();
		}

	}

	public HashMap getParamenters() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParamenters(HashMap paras) {
		// TODO Auto-generated method stub

	}
	public void updateAddedData(String name){
		
	}


	public UFormI initDataByHandlerAfterInitContents() {
		// TODO Auto-generated method stub
		return null;
	}


	public Object getSubComponentInitData(String name) {
		// TODO Auto-generated method stub
		return null;
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

	public void setUpperNoScrolling() {
		// TODO Auto-generated method stub
		
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

	@Override
	public void setCurrentPagePanel(int index) {
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
			String action) {
		// TODO Auto-generated method stub
		return ;
	}


	public UHandlerI getHandler() {
		// TODO Auto-generated method stub
		return this.handler;
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


	public UPanelI searchUPanelById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processCallBack(List map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processUpdate(String methodName, Object paras) {
		// TODO Auto-generated method stub
		
	}

	
	public void resetTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UTableI getInnerTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isFinishedInit() {
		// TODO Auto-generated method stub
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
	public void setTimeControlActionItem(List list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closePopUpPanel(int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean testInvalidateData() {
		// TODO Auto-generated method stub
		return false;
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

 
	
}
