package cn.edu.sdu.uims.base;

import java.util.HashMap;

public interface UDataPanelI extends UPanelI {
	/** liuyang start */
	void initFormDataFromSheet(Object data, int contentNum);

	void initFormDataFromDB(Object data, int contentNum);

	void imContent(HashMap requestMap, HashMap respondMap, int compNum);

	void flush(int contNum);

	void setData(Object obj, int compNum);

	void initComponents(int compNum);

	void initNotEmptyComponents(HashMap requestMap, HashMap respondMap,
			int compNum);

	void exContent(int compNum);
	
	void exContent();

	void initEmptyComponents(HashMap requestMap, HashMap respondMap, int compNum);

	void setWholeCount(int count);

	void initNotEmptyComponents(HashMap requestMap, HashMap respondMap);

	void initEmptyComponents(HashMap requestMap, HashMap respondMap);

	void dataOperateBegin(HashMap requestMap, HashMap respondMap);

	void dataOperateEnd(HashMap requestMap, HashMap respondMap);
	
	void initFormDataFromDB(Object data);
	/** liuyang end */
}
