package org.octopus.reportdog.handler;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public interface DocumentHandlerI {
	public void processBefore(RmiRequest request, RmiResponse response)
			throws Exception;

	public void process(RmiRequest request, RmiResponse response)
			throws Exception;

	public void processAfter(RmiRequest request, RmiResponse response)
			throws Exception;
}
