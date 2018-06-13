package org.octopus.reportdog.handler;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public interface MidHandlerI {
	
	public void tranformMidData(RmiRequest request,RmiResponse response);

}
