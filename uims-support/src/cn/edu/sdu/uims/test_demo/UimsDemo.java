package cn.edu.sdu.uims.test_demo;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.stereotype.Service;

@Service
public class UimsDemo {
	public void rmiDemo(RmiRequest request, RmiResponse response) {

		// 处理业务

		response.add("ddd", "tt");
		response.add("xxx", new Object());

	}

}