package cn.edu.sdu.uims.sheet;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.USheetParameter;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UContentRadomTable;
import cn.edu.sdu.uims.def.UBlockContent;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;
import cn.edu.sdu.uims.util.USheetUtil;
import cn.edu.sdu.uims.util.UTextUtil;

public class USRandomTable extends UContentRadomTable implements USElementI {
	public USRandomTable() {
		super();
	}

	public USRandomTable(UTableTemplate template) {
		super(template);
	}

	public void setParameterData(HashMap map, UFormI form, UComponentI obj) {
		int i;
		for (i = 0; i < data.length; i++) {
			data[i].content = UTextUtil.replaceString(data[i].content, map,
					form, obj);
		}
	}

	public void exSheet(USheetUtil util, USheetParameter par,
			UBlockContent constant) {
		try {
			par.columnNum = this.getCellColumnNum();
			util.addCells(par, data);
			par.row += getRowNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void imSheet(Object[] data) {
		// TODO Auto-generated method stub

	}

	public void flush(int contNum) {
		// TODO Auto-generated method stub

	}

	public DataInitHandlerI getDataInitHandler() {
		// TODO Auto-generated method stub
		DataInitHandlerI dataInitHandlerI = null;
		HashMap initMap = null;
		String ruleName = tableTemplate.ruleName;
		String methodName = tableTemplate.methodName;
		String initMethodName = tableTemplate.initMethodName;
		try {
			if (tableTemplate.dataInitClassName != null) {
				dataInitHandlerI = (DataInitHandlerI) Class.forName(
						tableTemplate.dataInitClassName).newInstance();
				dataInitHandlerI.setComponent(this);
			}
			// if (dataInitHandlerI != null) {
			// initMap = new HashMap();
			// if (ruleName != null) {
			// initMap.put("ruleName", ruleName);
			// }
			// if (methodName != null) {
			// initMap.put("methodName", methodName);
			// }
			// if (initMethodName != null) {
			// initMap.put("initMethodName", initMethodName);
			// }
			// dataInitHandlerI.setOperateParaMap(initMap);
			// }
		} catch (Exception e) {
			System.out.println("panelTemplate.handlerClassName ="
					+ tableTemplate.dataInitClassName);
			e.printStackTrace();
		}
		return dataInitHandlerI;
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
		super.initComponents();
	}

	public void initNotEmpytComponent(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub

	}
}
