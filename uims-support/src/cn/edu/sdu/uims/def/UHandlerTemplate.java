package cn.edu.sdu.uims.def;

import java.util.HashMap;

import cn.edu.sdu.uims.handler.UHandlerI;

public class UHandlerTemplate {
	public String className;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public UHandlerI getInstance() {
		return instance;
	}
	public void setInstance(UHandlerI instance) {
		this.instance = instance;
	}
	public HashMap getCommandMap() {
		return commandMap;
	}
	public void setCommandMap(HashMap commandMap) {
		this.commandMap = commandMap;
	}
	public UHandlerI instance = null;
	public HashMap commandMap;
}
