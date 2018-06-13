package cn.edu.sdu.uims.base;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UTableAttribute;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.util.UTextUtil;

public class UContentFixTable extends UFixTable {
	protected UCellAttribute data[] = null;
	protected UTableAttribute tableAttribute = null;

	public UContentFixTable() {
		super();
	}

	public UContentFixTable(UTableTemplate template) {
		super(template);
	}

	public void setData(Object obj) {
		items = (UFormI[]) obj;
	}

	public Object getData() {
		return null;
	}

	public int getRowNum() {
		if (tableTemplate.cells == null)
			return 0;
		else {
			int n = tableTemplate.cells.length / tableTemplate.subTableNum;
			if (tableTemplate.cells.length % tableTemplate.subTableNum == 1)
				n++;
			return n;
		}
	}

	public int getCellColumnNum() {
		int n = tableTemplate.columnNum;
		if (tableTemplate.no != null)
			n++;
		n *= tableTemplate.subTableNum;
		return n;
	}

	public void getTitleNoteObject() {
	}

	public void initComponents() {
		int i, j = 0, k, n, nt, ccn, sccn, ct = 0;
		String s, methodName;
		UCellAttribute obj;
		Method method;
		UFormI form;
		Object temp;
		initCellObjects();
		setTitleNoteObject();
		ccn = getCellColumnNum();
		sccn = ccn / tableTemplate.subTableNum;
		n = tableTemplate.columnNum;
		nt = 0;
		if (tableTemplate.no != null)
			nt++;
		int rowNum = getRowNum();
		int tr = getTopRowNum();
		int br = this.getBottomRowNum();

		tableAttribute = new UTableAttribute();
		tableAttribute.colNum = ccn;
		tableAttribute.width = new float[ccn];

		for (k = 0; k < tableTemplate.subTableNum; k++) {
			if (tableTemplate.no != null) {
				ct = k * sccn;
				tableAttribute.width[ct] = tableTemplate.no.width;
			}
			for (i = 0; i < n; i++) {
				ct = k * sccn + i + nt;
				tableAttribute.width[ct] = tableTemplate.columnTemplates[i].width;
			}
		}
		if (tableTemplate.topNum >= 1) {
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				if (tableTemplate.no != null) {
					ct = k * sccn;
					data[ct] = new UCellAttribute("序号");
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, 0, 1, 1);
				}
				for (i = 0; i < n; i++) {
					ct = k * sccn + i + nt;
					data[ct] = new UCellAttribute(tableTemplate.topStrings[i]);
					data[ct].fontName = tableTemplate.columnTemplates[i].fontName;
					setCellRowCol(data[ct], k * sccn + i + nt, 0, 1, 1);
				}
			}
		}
		for (k = 0; k < tableTemplate.subTableNum; k++) {
			// for (i = 0; i < tableTemplate.cells.length; i++) {
			for (i = 0; i < rowNum; i++) {
				if ((k * rowNum + i) * n >= tableTemplate.cells.length) {
					if (tableTemplate.no != null) {
						ct = (i + tr) * ccn + k * sccn;
						data[ct] = new UCellAttribute();
						setCellRowCol(data[ct], k * sccn, (i + tr), 1, 1);
					}
					for (j = 0; j < n; j++) {
						ct = (i + tr) * ccn + k * sccn + nt + j;
						data[ct] = new UCellAttribute("");
						setCellRowCol(data[ct], k * sccn + nt + j, i + tr, 1, 1);
					}
					continue;
				}
				form = (UFormI) items[k * rowNum + i];
				if (tableTemplate.no != null) {
					ct = (i + tr) * ccn + k * sccn;
					data[ct] = new UCellAttribute("" + (k * rowNum + i + 1));
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, i + tr, 1, 1);
				}
				UCellAttribute cellAttribute = tableTemplate.cells[i];
				ct = (cellAttribute.row + tr) * ccn + k * sccn + nt
						+ cellAttribute.col;
				data[ct] = cellAttribute;
				data[ct].fontName = tableTemplate.columnTemplates[cellAttribute.col].fontName;
				setCellRowCol(data[ct], k * sccn + nt + j, i + tr, 1, 1);
				for (j = 0; j < n; j++) {
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
				if (tableTemplate.no != null) {
					ct = (rowNum + tr) * ccn + k * sccn;
					data[ct] = new UCellAttribute("");
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, rowNum + tr, 1, 1);
				}
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
		getTitleNoteObject();
	}

	public void setTitleNoteObject() {
	}

	public void initCellObjects() {
		int n = getBodyCellNum();
		data = new UCellAttribute[n];
	}

	public int getBodyCellNum() {
		return (getRowNum() + getTopRowNum() + getBottomRowNum())
				* getCellColumnNum();
	}

	public void setCellRowCol(UCellAttribute data, int col, int row,
			int colSpan, int rowSpan) {
		data.col = col;
		data.row = row;
		data.colSpan = colSpan;
		data.rowSpan = rowSpan;
	}

	public void setParameterData(HashMap map, UFormI form, UComponentI obj) {
		if (data != null) {
			int i;
			UCellAttribute cellAttribute;
			for (i = 0; i < data.length; i++) {
				cellAttribute = data[i];
				cellAttribute.content = UTextUtil.replaceString(
						cellAttribute.content, map, form, obj);
			}
		}
	}

}
