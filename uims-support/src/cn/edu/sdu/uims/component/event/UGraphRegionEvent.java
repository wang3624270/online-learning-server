package cn.edu.sdu.uims.component.event;

import java.util.EventObject;

public class UGraphRegionEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	private String userTaskName;
	private String methodName;
	public UGraphRegionEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	public UGraphRegionEvent(Object source, String userTaskName, String methodName){
		super(source);
		this.userTaskName = userTaskName;
		this.methodName = methodName;
	}
	public String getUserTaskName() {
		return userTaskName;
	}
	public void setUserTaskName(String userTaskName) {
		this.userTaskName = userTaskName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
