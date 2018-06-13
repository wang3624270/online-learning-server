package cn.edu.sdu.uims.service;

import java.util.HashMap;

public class UHandlerSessionServer extends UHandlerSession {
	
	public void initHandlerMap() {
		handlerMap = new HashMap<String, HashMap>();
		HashMap<String, HashMap> map;
		map = new HashMap<String, HashMap>();
		handlerMap.put(UHandlerSessionI.CLIENT_TYPE_FLEX, map);
		handlerMap.put(UHandlerSessionI.CLIENT_TYPE_JAVA, map);
	}
}
