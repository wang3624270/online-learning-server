package cn.edu.sdu.uims.sheet;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.USheetParameter;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UFixTable;
import cn.edu.sdu.uims.def.UBlockContent;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;
import cn.edu.sdu.uims.util.USheetUtil;

public class USFixTable extends UFixTable implements USElementI {

	public void exSheet(USheetUtil util, USheetParameter par,
			UBlockContent constant) {

	}

	public void setParameterData(HashMap map, UFormI form, UComponentI obj) {
	}

	public void imSheet(Object[] data) {
		// TODO Auto-generated method stub

	}

	public void flush(int contNum) {
		// TODO Auto-generated method stub

	}

	public DataInitHandlerI getDataInitHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap imContent(UFormI[] items) {
		// TODO Auto-generated method stub
		return null;
	}

	public UFormI[] initFormData(Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataInitHandlerI getDataInitHandler(int compNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public UFormI[] initFormDataReFromDB(Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public UFormI[] initFormDataReFromSheet(Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getDataItemById(HashMap requestMap, HashMap respondMap,
			Object dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getInitDataList(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public void imContent(HashMap requestMap, HashMap respondMap, UFormI[] items) {
		// TODO Auto-generated method stub

	}

	public void initEmpytComponent(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub

	}

	public void initNotEmpytComponent(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub

	}

}
