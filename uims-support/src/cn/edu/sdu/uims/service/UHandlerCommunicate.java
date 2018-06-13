package cn.edu.sdu.uims.service;

import java.util.HashMap;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.FormI;
import cn.edu.sdu.uims.component.event.UEventObject;
import cn.edu.sdu.uims.handler.UHandlerI;

public class UHandlerCommunicate  {
	private String getClientSessionId(){
		return "0";
	}
	public void newHandlerInstance(RmiRequest request, RmiResponse respond) {
		UHandlerSessionI sessionI = UFactory.getHandlerSession();
		String name = (String) request.get("name");
		String className = (String) request.get("className");
		String componentName = (String) request.get("componentName");
		String parentId = (String) request.get("parentId");
		String dataFormClassName = (String)request.get("dataFormClassName");
		Boolean clearSessionHandler = (Boolean)request.get("clearSessionHandler");
		UHandlerI handlerI = sessionI.newHandlerInstance(UHandlerSessionI.CLIENT_TYPE_JAVA, getClientSessionId(),name, className,
				componentName, null, dataFormClassName,clearSessionHandler);
		respond.add(RmiKeyConstants.KEY_OBJECT, handlerI);
	}

	public void newFormInstance(RmiRequest request, RmiResponse respond) {
		UHandlerSessionI sessionI = UFactory.getHandlerSession();
		String handlerId = (String) request.get("handlerId");
		String className = (String) request.get("className");
		FormI form = sessionI.newFormInstance(UHandlerSessionI.CLIENT_TYPE_JAVA, getClientSessionId(),handlerId, className);
		respond.add(RmiKeyConstants.KEY_OBJECT, form);
	}

	public void getDataFormFromHandler(RmiRequest request, RmiResponse respond) {
		UHandlerSessionI sessionI = UFactory.getHandlerSession();
		String handlerId = (String) request.get("handlerId");
		FormI form = sessionI.getDataFormFromHandler(UHandlerSessionI.CLIENT_TYPE_JAVA, getClientSessionId(),handlerId);
		respond.add(RmiKeyConstants.KEY_OBJECT, form);

	}

	public void getinitDataFromHandler(RmiRequest request, RmiResponse respond) {
		UHandlerSessionI sessionI = UFactory.getHandlerSession();
		String handlerId = (String) request.get("handlerId");
		HashMap parasMap = (HashMap) request.get("parasMap");
		List retList = sessionI.getinitDataFromHandler(UHandlerSessionI.CLIENT_TYPE_JAVA, getClientSessionId(),handlerId, parasMap);
		respond.add(RmiKeyConstants.KEY_OBJECT, retList);

	}

	public void removeHandlerObject(RmiRequest request, RmiResponse respond) {
		UHandlerSessionI sessionI = UFactory.getHandlerSession();
		String handlerId = (String) request.get("handlerId");
		sessionI.removeHandlerObject(UHandlerSessionI.CLIENT_TYPE_JAVA, getClientSessionId(),handlerId);
	}

	public void sendhandlerProcess(RmiRequest request, RmiResponse respond) {
		UHandlerSessionI sessionI = UFactory.getHandlerSession();
		String handlerId = (String) request.get("handlerId");
		String methodName = (String) request.get("methodName");
		UEventObject event = (UEventObject) request.get("event");
		String actionCmd = (String) request.get("actionCmd");
		List retList = sessionI.sendhandlerProcess(UHandlerSessionI.CLIENT_TYPE_JAVA, getClientSessionId(),handlerId, methodName,
				event, actionCmd);
		respond.add(RmiKeyConstants.KEY_OBJECT, retList);
	}
	public void sendHandlerRequestData(RmiRequest request, RmiResponse respond) {
		UHandlerSessionI sessionI = UFactory.getHandlerSession();
		String handlerId = (String) request.get("handlerId");
		HashMap map = (HashMap) request.get("dataMap");
		List retList = sessionI.sendHandlerRequestData(UHandlerSessionI.CLIENT_TYPE_JAVA, getClientSessionId(),handlerId,map);
		respond.add(RmiKeyConstants.KEY_OBJECT, retList);
	}
	public  void  initPanelServerData(RmiRequest request, RmiResponse respond) {
		System.out.println("initPanelServerData");
		UHandlerSessionI sessionI = UFactory.getHandlerSession();
		String menuName = (String) request.get("menuName");
		List retList = sessionI.initPanelServerData(UHandlerSessionI.CLIENT_TYPE_FLEX, getClientSessionId(), menuName);
		respond.add(RmiKeyConstants.KEY_OBJECT, retList);
	}
	
}
