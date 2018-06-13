package cn.edu.sdu.uims.component.event;

import java.util.EventObject;

public class UTextFieldEventAdaptor extends UEventAdaptor {
	private UEventListener parent = null;

	public UTextFieldEventAdaptor(UEventListener parent) {
		this.parent = parent;
	}

	public void processEvent(EventObject e, String eventName, String cmd) {
		parent.processEvent(e, eventName, cmd);
	}
}
