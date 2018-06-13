package cn.edu.sdu.uims.component.event;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.EventObject;



public class UEventObject implements  Serializable{
	private  EventObject javaEventObject;
	private UEvent flexEventObject;
	private String sourceComponentName;
	private Object result;
	
	public UEventObject(){
		
	}
	public  UEventObject(Object javaEventObject, String sourceComponentName, Object result){
		this.javaEventObject = (EventObject)javaEventObject;
		this.flexEventObject = null;
		this.sourceComponentName = sourceComponentName;
		this.result = result;
	}
	public  void setUEventObject(Object javaEventObject, Object flexEventObject, String sourceComponentName, Object result){
		this.javaEventObject = (EventObject)javaEventObject;
		this.flexEventObject = (UEvent)flexEventObject;
		this.sourceComponentName = sourceComponentName;
		this.result = result;
	}
	public EventObject getEventObject() {
		if(javaEventObject instanceof EventObject)
			return (EventObject)javaEventObject;
		else 
			return null;			
	}
	public void setEventObject(Object eventObject) {
		if(eventObject instanceof EventObject) {
			this.javaEventObject = (EventObject)eventObject;
		}
	}
	public String getSourceComponentName() {
		return sourceComponentName;
	}
	public void setSourceComponentName(String componentName) {
		this.sourceComponentName = componentName;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getActionCommand(){
		if(javaEventObject != null && javaEventObject instanceof ActionEvent){
			ActionEvent ae = (ActionEvent)javaEventObject;
			return ae.getActionCommand();
		}
//		else if(flexEventObject != null){
//			UEvent e = (UEvent )flexEventObject;
//			return e.getActionCommand();
//		}
		return null;
	}
	public UEvent getFlexEventObject() {
		return flexEventObject;
	}
	public void setFlexEventObject(UEvent flexEventObject) {
		this.flexEventObject = flexEventObject;
	}
}
