package cn.edu.sdu.uims.handler;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;

public interface UFormHandlerI extends UHandlerI {
	void setComponent(UComponentI com);
	void initAddedData();
	void formToComponent();
	boolean componentToForm();
	Object getSubComponentData(String name);
	void  setSubComponentData(String name, Object o);
	void updateAddedData();
	void updateAddedData(String name);
	Object getAddedDate(String name);
	void putAddedData(String name,Object []list);
	UPanelI startDialog(String name, UFormI form);
	void processPopupMenuEvent(ActionEvent e);
	void initTableAddedData(List list);
	void startPanel(String name,String content); 
	UHandlerI getParentHandler();
	UHandlerI getSubComponentHandler(String name);
	void setInitData(UDialogPanel p);
	HashMap getComponentInitDataMap();
	
	void processActionEvent(Object o, String cmd);
	void processMouseEvent(Object o, String cmd);
}
