package org.octopus.reportdog.handler;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public interface TansHandlerI {

	public void transformData(RmiRequest request,RmiResponse response);

}
