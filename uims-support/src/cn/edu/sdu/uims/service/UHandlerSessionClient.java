package cn.edu.sdu.uims.service;

import java.util.HashMap;


public class UHandlerSessionClient extends UHandlerSession implements UHandlerSessionI {

	String getClientSesionId(){
		return "0";
	}
	public void initHandlerMap(){
		handlerMap = new HashMap<String, HashMap>();
		HashMap<String, HashMap> map;
		map = new HashMap<String, HashMap>();
		handlerMap.put(UHandlerSessionI.CLIENT_TYPE_JAVA, map);
		map = new HashMap<String, HashMap>();
		handlerMap.put(UHandlerSessionI.CLIENT_TYPE_FLEX, map);
	}
}
