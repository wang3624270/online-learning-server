package cn.edu.sdu.uims.handler;

import java.util.HashMap;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;

public interface DataOperatorHandlerI extends UHandlerI {

	public int getDataItem(HashMap requestMap, HashMap respondMap,
			Object dataItemId, DataInitHandlerI dataInitHandler, int compNum);

	public void dataOperateBegin(HashMap requestMap, HashMap respondMap);

	public void dataOperate(HashMap requestMap, HashMap respondMap, int compNum);

	public void emptyDataOperate(HashMap requestMap, HashMap respondMap,
			int compNum);

	public void dataOperateEnd(HashMap requestMap, HashMap respondMap);

	public int getDataItem(HashMap requestMap, HashMap respondMap,
			Object dataItemId, DataInitHandlerI dataInitHandler);

	public void dataOperate(HashMap requestMap, HashMap respondMap);

	public void emptyDataOperate(HashMap requestMap, HashMap respondMap);

	public void setComponent(UComponentI com);

	public void flush(int compNum);

}
