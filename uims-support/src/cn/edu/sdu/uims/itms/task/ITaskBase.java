package cn.edu.sdu.uims.itms.task;

import java.awt.Graphics;
import java.util.EventObject;

import cn.edu.sdu.uims.itms.def.ITaskTemplate;
import cn.edu.sdu.uims.itms.handler.IHandler;
import cn.edu.sdu.uims.trans.UFPoint;

public class ITaskBase {
	protected ITaskTemplate taskTemplate;
	protected IHandler handler;
	protected ISubTask owner;

	public ITaskBase() {
	}

	public void init() {
	}

//	public void start() {
//
//	}

	public void start(String enterStatusFlag, UFPoint firstP) {

	}

	public void setTemplate(ITaskTemplate temp) {
		taskTemplate = temp;
	}

	public ITaskTemplate getTemplate() {
		return taskTemplate;
	}

	public void setHandler(IHandler handler) {
		this.handler = handler;
	}

	public IHandler getHandler() {
		return handler;
	}

	public int processEvent(String eventName, EventObject e) {
		return -1;
	}

	public void setParaData(Object data) {

	}

	public void setCurrentPoint(UFPoint p) {
		//
	}

	public void setOwner(ISubTask o) {
		owner = o;
	}

	public void drawCursor(Graphics dc) {

	}

}
