package cn.edu.sdu.uims.service;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.FormI;
import cn.edu.sdu.uims.component.event.UEventObject;
import cn.edu.sdu.uims.flex.FHashMap;
import cn.edu.sdu.uims.handler.UHandlerI;

public interface UHandlerSessionI {
	String CLIENT_TYPE_JAVA = "java";
	String CLIENT_TYPE_FLEX = "flex";
	
	String getClientSessionId();
	UHandlerI getHandlerObject(String prifex, String seesionId, String handlerId);	

	void sendFormHandler(String prifex, String sessionId, String id, FormI form);
	UHandlerI newHandlerInstance(String prifex, String sessionId, String name, String className,
			String componentName, UHandlerI parent, String dataFormClassName, Boolean clearSessionHandler);
	FormI newFormInstance(String prifex, String sessionId, String handlerId, String className);
	FormI getDataFormFromHandler(String prifex, String sessionId, String id);
	List getinitDataFromHandler(String prifex, String sessionId, String id, HashMap map);
	List sendhandlerProcess(String prifex, String sessionId, String id, String methodName,
			UEventObject event, String actionCmd);
	void removeHandlerObject(String prifex, String sessionId, String id);
	public void putHandlerObject(String prifex, String sessionId, String id, UHandlerI hi);
	List getinitDataFromHandlerFHashMap(String prifex, String sessionId, String id, FHashMap map);
	List sendhandlerProcessFHashMap(String prifex, String sessionId, String id, String methodName,
			UEventObject event, String actionCmd);
	List sendHandlerRequestData(String prifex, String sessionId, String id, HashMap map);
	List sendHandlerRequestDataFHashMap(String prifex, String sessionId, String id,FHashMap map);

	List initPanelServerData(String prifex, String sessionId, String menuName);
	List initDialogServerData(String prifex, String sessionId, String menuName);
	List initFlexDialogServerData(String prifex, String sessionId, String menuName);
	List initPanelServerDataFHashMap(String prifex, String sessionId,  String menuName);
	List initDialogServerDataFHashMap(String prifex, String sessionId,  String menuName);

}
