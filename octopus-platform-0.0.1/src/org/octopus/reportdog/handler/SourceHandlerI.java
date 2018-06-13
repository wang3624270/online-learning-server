package org.octopus.reportdog.handler;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public interface SourceHandlerI {
	
	public void process(RmiRequest request,RmiResponse response)throws Exception;

}
