package org.sdu.rmi;

import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.sdu.rmi.parse.Cmd;
import org.sdu.spring_util.ApplicationContextHandle;
import org.sdu.spring_util.JPAUtils;
import org.starfish.logs.StarfishExceptionLogHelper;

public class SpringRmiSysService implements RmiServiceI {
	private static final long serialVersionUID = 8760443247590031611L;
	RmiAccessAuthI rmiAccessAuthI;

	public RmiAccessAuthI getRmiAccessAuthI() {
		return rmiAccessAuthI;
	}

	public void setRmiAccessAuthI(RmiAccessAuthI rmiAccessAuthI) {
		this.rmiAccessAuthI = rmiAccessAuthI;
	}

	public SpringRmiSysService() throws Exception {

	}

	public RmiResponse requestProcesser(RmiRequest request) throws RemoteException {

		RmiResponse response = new RmiResponse();
		try {
			String cmd = (String) request.getCmd();
//			 System.out.println(cmd);
			if (cmd == null)
				return response;
			Cmd cmd_ = RmiCmdHelper.getTotalCmdMap(cmd);

			String ss = cmd_.getAuthToken();
			JPAUtils.beginJPASession();
			if (ss != null && ss.indexOf("permitAll") >= 0) {
				Object ob = ApplicationContextHandle.getBean(cmd_.getBeanId());
				Method m = ob.getClass().getMethod(cmd_.getMethod(), RmiRequest.class, RmiResponse.class);
				m.invoke(ob, request, response);
			} else {

				if (rmiAccessAuthI.auth(request)) {

					Object ob = ApplicationContextHandle.getBean(cmd_.getBeanId());
					Method m = ob.getClass().getMethod(cmd_.getMethod(), RmiRequest.class, RmiResponse.class);

					m.invoke(ob, request, response);
				}

			}

		} catch (Exception e) {

			StarfishExceptionLogHelper.rmiExceptionLog((Throwable) e);
		} finally {
			JPAUtils.endJPASession();
		}

		return response;
	}
}