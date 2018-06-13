package cn.edu.sdu.uims.base;

import java.io.Serializable;

public class RequestStruct implements Serializable{
	public String handlerId;
	public String comName;
	public String methodName;
	public String state;
	public Object dataObject;
	
	public String getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Object getDataObject() {
		return dataObject;
	}
	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}
	
}
