package cn.edu.sdu.uims.component.event;

import java.util.EventObject;

public class GraphInteractionEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private String userTaskName;
	private String methodName;
	private int statusItemNo;
	private boolean statusValue;
	public GraphInteractionEvent(Object source) {
		super(source);
	}
	public GraphInteractionEvent(Object source, int statusItemNo,  boolean statusValue) {
		super(source);
		this.statusItemNo = statusItemNo;
		this.statusValue = statusValue;
	}
	public GraphInteractionEvent(Object source, String userTaskName, String methodName){
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
	public int getStatusItemNo() {
		return statusItemNo;
	}
	public void setStatusItemNo(int statusItemNo) {
		this.statusItemNo = statusItemNo;
	}
	public boolean isStatusValue() {
		return statusValue;
	}
	public void setStatusValue(boolean statusValue) {
		this.statusValue = statusValue;
	}

}
