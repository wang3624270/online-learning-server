package cn.edu.sdu.uims.handler;

import cn.edu.sdu.uims.handler.impl.UToolHandler;

public interface QueryListHandlerI {
	void setOwnerHandler(UToolHandler owner);
	void queryProcessActionEvent(Object o, String cmd);
	void queryProcessListSelectionEvent(Object o, String cmd);
}
