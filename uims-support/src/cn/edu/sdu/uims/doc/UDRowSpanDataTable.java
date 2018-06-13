package cn.edu.sdu.uims.doc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UTableAttribute;
import cn.edu.sdu.uims.def.UParagraphContent;
import cn.edu.sdu.uims.def.UTableTemplate;

public class UDRowSpanDataTable extends UDDataTable implements UDElementI {
	public UDRowSpanDataTable() {
		super();
	}

	public UDRowSpanDataTable(UTableTemplate template) {
		super(template);
	}

	public void initComponents(HashMap requstMap, HashMap respondMap) {
		int i, j, k, n, nt, ccn, sccn, ct = 0, numberCount = 0, number = 0;
		String s, methodName;
		UCellAttribute obj;
		Method method;
		UFormI form;
		Object temp;
		List numberList = null;
		List valueList = null;
		initCellObjects();
		ccn = getCellColumnNum();
		sccn = ccn / tableTemplate.subTableNum;
		nt = 0;
		if (tableTemplate.rowSpanNo != null) {
			nt++;
		}
		int rowNum = getRowNum();
		int tr = getTopRowNum();
		n = tableTemplate.columnNum;
		tableAttribute = new UTableAttribute();
		tableAttribute.colNum = ccn;
		tableAttribute.width = new float[ccn];
		for (k = 0; k < tableTemplate.subTableNum; k++) {
			if (tableTemplate.rowSpanNo != null) {
				ct = k * sccn;
				tableAttribute.width[ct] = tableTemplate.rowSpanNo.width;
			}
			for (i = 0; i < n; i++) {
				ct = k * sccn + i + nt;
				tableAttribute.width[ct] = tableTemplate.columnTemplates[i].width;
			}
		}
		UCellAttribute[] topItems = tableTemplate.topItems;
		for (k = 0; k < tableTemplate.subTableNum; k++) {
			if (tableTemplate.topNum >= 1) {
				int topItemNums = topItems.length;
				for (k = 0; k < tableTemplate.subTableNum; k++) {
					for (i = 0; i < topItemNums; i++) {
						if (topItems[i] != null) {
							ct = k * sccn + i + nt;
							data[ct] = new UCellAttribute(
									tableTemplate.topStrings[i]);
							data[ct].fontName = tableTemplate.columnTemplates[i % n].fontName;
							setCellRowCol(data[ct], topItems[i].col,
									topItems[i].row, topItems[i].colSpan,
									topItems[i].rowSpan);
						}
					}
				}
			}
		}
		if (tableTemplate.rowSpanNo != null) {
			numberList = (List) requstMap.get("rowSpans");
			valueList = (List) requstMap.get("values");
		}

		for (k = 0; k < tableTemplate.subTableNum; k++) {
			for (i = 0; i < rowNum; i++) {
				if (i == number && tableTemplate.rowSpanNo != null) {
					number += (Integer) numberList.get(numberCount);
					ct = (i + tr) * ccn + k * sccn;
					data[ct] = new UCellAttribute((String) valueList
							.get(numberCount));
					data[ct].fontName = tableTemplate.rowSpanNo.fontName;
					setCellRowCol(data[ct], k * sccn, i + tr, 1,
							(Integer) numberList.get(numberCount));
					numberCount++;
				}
				form = (UFormI) items[k * rowNum + i];
				j = 0;
				for (; j < n; j++) {
					s = tableTemplate.columnTemplates[j].itemFormMember;
					obj = new UCellAttribute();
					if (s != null) {
						try {
							methodName = "get"
									+ s.substring(0, 1).toUpperCase()
									+ s.substring(1, s.length());
							method = form.getClass().getMethod(methodName);
							temp = method.invoke(form);
							if (temp == null)
								obj.content = "";
							else
								obj.content = temp.toString();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ct = (i + tr) * ccn + k * sccn + nt + j;
					data[ct] = obj;
					data[ct].fontName = tableTemplate.columnTemplates[j].fontName;
					setCellRowCol(data[ct], k * sccn + nt + j, i + tr, 1, 1);
				}
			}
		}
		if (tableTemplate.bottomNum >= 1) {
			for (k = 0; k < tableTemplate.subTableNum; i++) {
				for (i = 0; i < n; i++) {
					ct = (rowNum + tr) * ccn + k * sccn + i + nt;
					data[ct] = new UCellAttribute(
							tableTemplate.bottomStrings[i]);
					data[ct].fontName = tableTemplate.columnTemplates[i].fontName;
					setCellRowCol(data[ct], k * sccn + i * nt, rowNum + tr, 1,
							1);
				}
			}
		}
	}

	public void initContent(UParagraphContent constant, HashMap requestMap,
			HashMap respondMap) {
		// TODO Auto-generated method stub
		initComponents(requestMap, respondMap);
	}

	public int getCellColumnNum() {
		int n = tableTemplate.columnNum;
		if (tableTemplate.rowSpanNo != null)
			n++;
		n *= tableTemplate.subTableNum;
		return n;
	}

	public void setTitleNoteObject() {
	}

}
