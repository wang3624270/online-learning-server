package cn.edu.sdu.uims.component.event;

import java.util.EventObject;

public class SelectObjectEvent extends EventObject {
	private Object selectObject;
	public SelectObjectEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public SelectObjectEvent(Object source, Object selectObject) {
		this(source);
		this.selectObject = selectObject;
	}
	public Object getSselectObject(){
		return selectObject;
	}

}
