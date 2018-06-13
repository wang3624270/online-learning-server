package cn.edu.sdu.uims.pi;

import java.awt.datatransfer.Clipboard;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.sdu.rmi.RmiRequest;

import cn.edu.sdu.common.form.UFormIdI;
import cn.edu.sdu.uims.form.UTextFieldDataFormI;

public interface ClientMainI {
	void addObjectSystemRequestAttribute(RmiRequest request);
	boolean isEnglishVersion();
	Clipboard getClipboard();
	HashMap getSysRolePanelMap();
	String getWebSeverUrl();
	void shutdown();
	Object getClientParamerter(String key);
	List getMenuList();
	HashMap getParameterMap();
	void setParameterMap(HashMap map);
	UClientFrameI getUClientFrame();
	boolean requestIsLog();
	Logger getLogger();
	ImageDataDriverI getImgeDataDriver();
	Integer getManageCollegeId();
	UTextFieldDataFormI creatUTextFieldDataForm();
	UFormIdI creatNewsInfoForm();
	Boolean isFileDataInDB();
	
}
