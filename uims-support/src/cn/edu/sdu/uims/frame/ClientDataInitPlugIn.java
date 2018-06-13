package cn.edu.sdu.uims.frame;

import org.sdu.rmi.RmiClientRequest;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.pi.ClientInitPlugInI;

public class ClientDataInitPlugIn implements ClientInitPlugInI {
	String startupProcessMessage = "正在从服务器获取应用程序信息，请稍等...";

	public String getStartupProcessMessage() {
		return this.startupProcessMessage;
	}

	public void init() {
		// TODO Auto-generated method stub
		initApplicationName();
	}

	// 取得系统应用名
	public String initApplicationName() {
		RmiRequest request = new RmiRequest();
		request.setCmd("globalinfoGetApplicationName");

		RmiResponse respond = RmiClientRequest.getClientRequest().request(request);

		if (null == respond.getErrorMsg()) {
			String applicationName = (String) respond
					.get(RmiKeyConstants.APPLICATIONNAME);
			RmiClientRequest.getClientRequest().setAppName(applicationName);
		} else {
			return respond.getErrorMsg();
		}

		return null;
	}
}
