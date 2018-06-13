package cn.edu.sdu.uims.service;

import java.util.HashMap;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.component.event.UEventObject;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.util.UimsUtils;

public class UHandlerSessionRemote extends UHandlerSession {
	public UHandlerI newHandlerInstance(String name, String className,String componentName, String parentId,String dataFormClassName){
		RmiRequest request = new RmiRequest();
		request.setCmd("newHandlerInstance");
		request.add("name", name);
		request.add("className", className);
		request.add("componentName", componentName);
		request.add("parentId", parentId);
		request.add("dataFormClassName", dataFormClassName);
		RmiResponse respond = UimsUtils.requestProcesser(request);
		UHandlerI handlerI = (UHandlerI)respond.get(RmiKeyConstants.KEY_OBJECT);
		return handlerI;
	}
	public UFormI newFormInstance(String handlerId, String className){
		RmiRequest request = new RmiRequest();
		request.setCmd("newFormInstance");
		request.add("handlerId", handlerId);
		request.add("className", className);
		RmiResponse respond =  UimsUtils.requestProcesser(request);
		UFormI form = (UFormI)respond.get(RmiKeyConstants.KEY_OBJECT);
		return form;
	}
	public UFormI getDataFormFromHandler(String handlerId){
		RmiRequest request = new RmiRequest();
			request.setCmd("getDataFormFromHandler");
			request.add("handlerId", handlerId);
			RmiResponse respond = UimsUtils.requestProcesser(request);
			UFormI form = (UFormI)respond.get(RmiKeyConstants.KEY_OBJECT);
			return form;
	}
	public List getinitDataFromHandler(String handlerId, HashMap parasMap){
		RmiRequest request = new RmiRequest();
			request.setCmd("getinitDataFromHandler");
			request.add("handlerId", handlerId);
			request.add("parasMap", parasMap);
			RmiResponse respond = UimsUtils.requestProcesser(request);
			List list = (List)respond.get(RmiKeyConstants.KEY_OBJECT);
			return list;
		}
	public List sendhandlerProcess(String handlerId, String methodName, UEventObject event, String actionCmd){
		RmiRequest request = new RmiRequest();
			request.setCmd("sendhandlerProcess");
			request.add("handlerId", handlerId);
			request.add("methodName", methodName);
			request.add("event", event);
			request.add("actionCmd", actionCmd);
			RmiResponse respond = UimsUtils.requestProcesser(request);
			List list = (List)respond.get(RmiKeyConstants.KEY_OBJECT);
			return list;
		}
	public void removeHandlerObject(String handlerId){
		RmiRequest request = new RmiRequest();
		request.setCmd("removeHandlerObject");
		request.add("handlerId", handlerId);
		RmiResponse respond = UimsUtils.requestProcesser(request);
		return ;
	}
	public List sendHandlerRequestData(String handlerId, HashMap map){
		RmiRequest request = new RmiRequest();
		request.setCmd("sendHandlerRequestData");
		request.add("handlerId", handlerId);
		request.add("dataMap", map);
		RmiResponse respond = UimsUtils.requestProcesser(request);
		List list = (List)respond.get(RmiKeyConstants.KEY_OBJECT);
		return list;
	}

}
