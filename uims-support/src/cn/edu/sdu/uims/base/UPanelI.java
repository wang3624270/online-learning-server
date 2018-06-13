package cn.edu.sdu.uims.base;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.component.event.UEventListener;
import cn.edu.sdu.uims.def.UMenuPanelMapTemplate;
import cn.edu.sdu.uims.form.UPanelStatusFormI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public interface UPanelI extends UTemplateComponentI {
	void addActionListener(ActionListener l);

	void removeAtionListener(ActionListener l);

	UEventListener getEventAdaptor();

	UComponentI getSubComponent(String name);

	void sendActionEventToParent(String cmd);
 

	String[] getToolActions();

	void updatePanel(UFormI form);

	void setDialogForm(UFormI form);

	UTableI getTableComponent();

	void updateAddedData(String name);

	Object getSubComponentInitData(String name);

	UComponentI[] getAllSubComponents();

	UPanelI getCurrentDisplayPagePanel();
	UComponentI getCurrentDisplayPageCommonent();

	void setCurrentPagePanel(int index);

	void setSubComponentVisible(String name, boolean visible);

	void reLayoutComponents();

	void initInteraction();

	void setUpperNoScrolling();

	URect makeInnerBounds();

	URect gerInnerBounds();

	void setComponentBounds();

	void setOriginalTemplate(UMenuPanelMapTemplate originalTemplate);

	void setPathNameString(String pathNameString);

	void replaceTemplateContent();

	void initHandlerObject(String name, UHandlerI parent);

	UFormI initDataByHandlerAfterInitContents();

	void sendhandlerProcess(String methodName, EventObject event, String action);

	UHandlerI getHandler();

	void sendFormHandler(UFormI form);

	void setHandlerOutFormData();

	void setStartMenuName(String startMenuName);

	String getHandlerId();

	UPanelI searchUPanelById(String id);

	void processCallBack(List map);

	void processUpdate(String methodName, Object paras);

	UTableI getInnerTable();

	Boolean isFinishedInit();

	String[] getClosePanels();

	void processDispControlAfterInited();

	void setTimeControlActionItem(List list);

	void closePopUpPanel(int type);
	void setUseOutContainer(boolean isUse);
	void setOutCantiner(Container outContainer);
	
	void refreshComponentData();
	void setPanelDisplayStatus(UPanelStatusFormI form);
	void setCurrentPagePanelByComponentName(String comName);
	void updateComAddedDataList( String panelName,String comName, List dataList);
	void updateQueryComAddedDataList( String panelName,String queryName, String comName, List dataList);
}
