package org.octopus.reportdog.controller;

import java.util.HashMap;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

/**
 * 
 *控制器的通用接口
 * 
 *定义了控制器的通用操作
 * 
 * @author 刘洋
 * 
 */
public class DataController {

	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
	}

	public HashMap splitMultiPageConfig(RmiRequest request,
			RmiResponse response, String key, int startConfigNum) {
		return null;
	}

}