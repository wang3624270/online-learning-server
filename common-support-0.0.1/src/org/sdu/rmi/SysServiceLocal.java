package org.sdu.rmi;

import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sdu.rmi.parse.Cmd;
import org.sdu.spring_util.ApplicationContextHandle;
import org.sdu.spring_util.JPAUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.starfish.logs.StarfishExceptionLogHelper;


public class SysServiceLocal implements RmiServiceI {

	protected static Log log = LogFactory.getLog(SysServiceLocal.class);
	public static SysServiceLocal sysServiceLocal;

	public static void initSysServiceLocal() {
		sysServiceLocal = new SysServiceLocal();
	}

	private SysServiceLocal() {
		ApplicationContext aa = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		RmiClientRequest.initRmiClientInLocalMode(this);
	}


	/**
	 * 请求处理
	 */
	public RmiResponse requestProcesser(RmiRequest request)
			throws RemoteException {
		// 响应返回
		RmiResponse response = new RmiResponse();
		try {
			String cmd = (String) request.getCmd();
//			 System.out.println(cmd);
			if (cmd == null)
				return response;
			Cmd cmd_ = RmiCmdHelper.getTotalCmdMap(cmd);
			if(cmd_ == null)
				return response;
			JPAUtils.beginJPASession();
			Object ob = ApplicationContextHandle.getBean(cmd_.getBeanId());
			Method m = ob.getClass().getMethod(cmd_.getMethod(), RmiRequest.class, RmiResponse.class);
			m.invoke(ob, request, response);
		} catch (Exception e) {
			StarfishExceptionLogHelper.rmiExceptionLog((Throwable) e);
		} finally {
			JPAUtils.endJPASession();
		}
		return response;
	}

}
