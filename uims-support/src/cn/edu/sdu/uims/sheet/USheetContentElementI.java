package cn.edu.sdu.uims.sheet;

import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UDataContentElementI;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;

public interface USheetContentElementI extends UDataContentElementI {
	/** liuyang begin */
	public UFormI[] initFormDataReFromSheet(Object data);

	public UFormI[] initFormDataReFromDB(Object data);

	public void imContent(HashMap requestMap, HashMap respondMap, UFormI[] items);

	public DataInitHandlerI getDataInitHandler();

	public void flush(int contNum);

	public void initEmpytComponent(HashMap requestMap, HashMap respondMap);

	public void initNotEmpytComponent(HashMap requestMap, HashMap respondMap);

	/** liuyang end */
}
