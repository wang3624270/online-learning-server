package cn.edu.sdu.uims.base;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;

public interface UDataContentElementI extends UContentElementI {
	public DataInitHandlerI getDataInitHandler(int compNum);

	public DataInitHandlerI getDataInitHandler();

	public List getInitDataList(HashMap requestMap, HashMap respondMap);

	public Object[] getDataItemById(HashMap requestMap, HashMap respondMap,
			Object dataId);

}
