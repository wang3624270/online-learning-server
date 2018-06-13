package cn.edu.sdu.uims.service;

import java.util.List;

public interface TimeControlActionProcessorI {
	List getTimeControlActionListByPanelName(String panelName, Integer []sysRoleIds);
}
