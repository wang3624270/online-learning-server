package cn.edu.sdu.uims.service;

import java.util.HashMap;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.spring_util.ApplicationContextHandle;
import org.springframework.stereotype.Service;

@Service
public class UimsRmiRequestProcessRule {

	public void getQueryDataListByInputString(RmiRequest request, RmiResponse respond) {
		String input = (String)request.get("inputString");
		String beanName  = (String)request.get("beanName");
		HashMap map= (HashMap)request.get("parameters");
		if(map == null)
			map = new HashMap();
		map.put("dataClassName", request.get("dataClassName"));
//		map.put(RmiKeyConstants.KEY_USER_INFO,request.get(RmiKeyConstants.KEY_USER_INFO));
		map.put("RmiRequest", request);
		if(beanName.endsWith("Bean"))
			beanName = beanName.substring(0, beanName.length()-4);
		GetQueryDataListProcessorI p = (GetQueryDataListProcessorI)ApplicationContextHandle.getBean(beanName);
		if(p != null)
			respond.add(RmiKeyConstants.KEY_FORMLIST,p.getQueryDataListByInputString(input,map));
	}


}
