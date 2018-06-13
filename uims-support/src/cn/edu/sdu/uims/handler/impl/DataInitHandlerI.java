package cn.edu.sdu.uims.handler.impl;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.handler.UHandlerI;

public interface DataInitHandlerI extends UHandlerI {

	public List getInitDataList(HashMap requestMap,HashMap respondMap);

	public Object[] getDataItemById(HashMap requestMap,HashMap respondMap,Object dataId);
	
	public void setComponent(UComponentI com);

}
