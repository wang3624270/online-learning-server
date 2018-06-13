package cn.edu.sdu.uims.handler.impl;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.rmi.RmiServiceI;
import org.sdu.spring_util.ApplicationContextHandle;

import cn.edu.sdu.uims.graph.model.GraphModelI;

public class UFormHandlerLocal extends UFormHandler {

	public RmiResponse request(RmiRequest request) {

		try {
			RmiServiceI rmiServiceI = (RmiServiceI) ApplicationContextHandle.getBean("springRmiSysService");
			return rmiServiceI.requestProcesser(request);
		}catch(Exception e) {
			RmiResponse response = new RmiResponse();
			response.setErrorMsg("系统错误！");
			return response;
		}
	}

	public GraphModelI getGraphModel2DObject(String name) {
		// TODO Auto-generated method stub
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, name);
		request.setCmd("getGraphModel2DObject");
		RmiResponse respond = request(request);
		if (respond.getErrorMsg() != null)
			return null;
		else {
			GraphModelI model = (GraphModelI) respond
					.get(RmiKeyConstants.KEY_OBJECT);
			return model;
		}
	}
}
