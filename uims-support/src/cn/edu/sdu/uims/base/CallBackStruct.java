package cn.edu.sdu.uims.base;

import java.io.Serializable;
import java.util.List;

public class CallBackStruct implements Serializable {
	public String handlerId;
	public List commandList;
	public String getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}
	public List getCommandList() {
		return commandList;
	}
	public void setCommandList(List commandList) {
		this.commandList = commandList;
	}
}
