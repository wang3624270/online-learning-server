package org.octopus.auth;


import org.sdu.rmi.ReturnToClientStruct;

public class ResponseContain extends ReturnToClientStruct {
	private String response;
	
	private Object resList;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	public Object getResList(){
		return resList;
	}
	
	public void setResList(Object resList){
		this.resList = resList;
	}

}