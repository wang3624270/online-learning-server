package cn.edu.sdu.uims.handler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.FormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.graph.model.GraphModelI;

public interface UHandlerI {
	FormI getForm();
	void setForm(FormI form);
	void initFormData();
	void setParameters(HashMap paras);
	HashMap getParameters();
	Field[] getFormFields();
	void setInitData(UDialogPanel dialogPanel);
	UHandlerI [] getAllSubHandler();
	UHandlerI getSubComponentHandler(String name);
	void initAddedData();
	UComponentI getComponentByActionCommandString(String str);
	void setOutFormData();
	String getId();
	void  setId(String id);
	UHandlerI getParent();
	void  setParent(UHandlerI id);
	String getComponentName();
	void setComponentName(String componentName);
	List getinitDataFromHandler();
	List processActionEventFromSubHandler(String actionCommand);
	List sendHandlerRequestData(HashMap map);
	void addSubHandler(UHandlerI handler);
	GraphModelI getGraphModel2DObject(String name);
	void setDisplayPanel();
	void start();
	HashMap getBusinessParaMap(String name);
	Object getSelectedValue(String name);
	int[] getSelectedIndices(String name);
	void resetPanelData();
	HashMap getUserDataHashMap();
	List getInitAddedDataListByName(String name);
	List getRowAddedDataListByName(int col,Object data, String name);
	List getNextComItemList(String name, String code[]);
	void refreshComponentData();
	void initAllAddedData();
	List getInitAddedDataList(String comName);
	void componentProcessFished(String comName);
	void initAllFormData();
	void windowGainedFocus();
}
