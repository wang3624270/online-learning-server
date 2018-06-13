package cn.edu.sdu.uims.base;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.edu.sdu.common.form.FormI;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UTableAttribute;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.handler.UHandlerI;

public class UContentDataTable extends UDataTable {
	protected UCellAttribute data[] = null;

	protected UTableAttribute tableAttribute = null;

	public UContentDataTable() {
		super();
	}

	public UContentDataTable(UTableTemplate template) {
		super(template);
	}


	public String getDataTotal() {
		if (items == null)
			return "0";
		else
			return "" + items.length;
	}

	public void setData(Object obj) {
		items = (UFormI[]) obj;
	}

	public Object getData() {
		return null;
	}

	public int getRowNum() {
		if (items == null)
			return 0;
		else {
			int n = items.length / tableTemplate.subTableNum;
			if (items.length % tableTemplate.subTableNum == 1)
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

	public int getBodyCellNum() {
		return (getRowNum() + getTopRowNum() + getBottomRowNum())
				* getCellColumnNum();
	}

	public void initCellObjects() {
		int n = getBodyCellNum();
		data = new UCellAttribute[n];
	}

	public void setTitleNoteObject() {
	}

	public void setCellRowCol(UCellAttribute data, int col, int row,
			int colSpan, int rowSpan) {
		data.col = col;
		data.row = row;
		data.colSpan = colSpan;
		data.rowSpan = rowSpan;
	}
	
	public String getTopValueFromPanel(String attrName){
		String value = null;
		UPanelI p = this.getUParent();
		if(p == null)
			return "";
		UHandlerI h= p.getHandler();
		if(h == null)
			return "";
		FormI f = h.getForm();
		if(f == null)
			return "";
		try {
			String methodName = "get" + attrName.substring(0, 1).toUpperCase()
			+ attrName.substring(1, attrName.length());
			Method m = f.getClass().getMethod(methodName);
			Object o = m.invoke(f);
			if(o == null)
				return "";
			else
				return o.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	public void initComponents() {
		int i, j, k, n, nt, ccn, sccn, ct = 0;
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
		int height = tableTemplate.rowHeight;

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
		// if (tableTemplate.topNum >= 1) {
		// for (k = 0; k < tableTemplate.subTableNum; k++) {
		// if (tableTemplate.no != null) {
		// ct = k * sccn;
		// data[ct] = new UCellAttribute("序号");
		// data[ct].font = tableTemplate.no.font;
		// setCellRowCol(data[ct],k*sccn,0,1,1);
		// }
		// for (i = 0; i < n; i++) {
		// ct =k * sccn + i + nt;
		// data[ct] = new UCellAttribute(
		// tableTemplate.topStrings[i]);
		// data[ct].font = tableTemplate.columnTemplates[i].font;
		// setCellRowCol(data[ct],k*sccn+i+nt,0,1,1);
		// }
		// }
		// }

		UCellAttribute[] topItems = tableTemplate.topItems;

		if (tableTemplate.topNum >= 1) {
			int topItemNums = topItems.length;
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				if (tableTemplate.no != null) {
					ct = k * sccn;
					data[ct] = new UCellAttribute("序号");
					data[ct].fontName = tableTemplate.no.fontName;
					if(height>0){
						data[ct].fixHeight = height;
					}
					setCellRowCol(data[ct], k * sccn, 0, 1,
							getTopItemsRowSpan());
				}
				String topString;
				for (i = 0; i < topItemNums; i++) {
					if (topItems[i] != null) {
						ct = k * sccn + i + nt;
						topString = tableTemplate.topStrings[i];
						if(topString != null && topString.length() > 1 && topString.charAt(0)=='@') {
							topString = getTopValueFromPanel(topString.substring(1, topString.length()));
						}
						data[ct] = new UCellAttribute(topString);
						
						data[ct].horizontalAlignment = topItems[i].horizontalAlignment;
						data[ct].verticalAlignment = topItems[i].verticalAlignment;
						data[ct].fontName = tableTemplate.columnTemplates[i % n].fontName;
						if(height>0){
							data[ct].fixHeight = height;
						}
						setCellRowCol(data[ct], topItems[i].col,
								topItems[i].row, topItems[i].colSpan,
								topItems[i].rowSpan);
					}
				}
			}
		}
		for (k = 0; k < tableTemplate.subTableNum; k++) {
			for (i = 0; i < rowNum; i++) {
				if ((k * rowNum + i) >= items.length) {
					if (tableTemplate.no != null) {
						ct = (i + tr) * ccn + k * sccn;
						data[ct] = new UCellAttribute();
						if(height>0){
							data[ct].fixHeight = height;
						}
						setCellRowCol(data[ct], k * sccn, (i + tr), 1, 1);
					}
					for (j = 0; j < n; j++) {
						ct = (i + tr) * ccn + k * sccn + nt + j;
						data[ct] = new UCellAttribute("");
						if(height>0){
							data[ct].fixHeight = height;
						}
						setCellRowCol(data[ct], k * sccn + nt + j, i + tr, 1, 1);
					}
					continue;
				}
				form = (UFormI) items[k * rowNum + i];
				if (tableTemplate.no != null) {
					ct = (i + tr) * ccn + k * sccn;
					data[ct] = new UCellAttribute("" + (k * rowNum + i + 1));
					data[ct].fontName = tableTemplate.no.fontName;
					if(height>0){
						data[ct].fixHeight = height;
					}
					setCellRowCol(data[ct], k * sccn, i + tr, 1, 1);
				}
				for (j = 0; j < n; j++) {
					s = tableTemplate.columnTemplates[j].itemFormMember;
					obj = new UCellAttribute();
					if (s != null) {
						try {
							methodName = "get"
									+ s.substring(0, 1).toUpperCase()
									+ s.substring(1, s.length());
							method = form.getClass().getMethod(methodName);
//							System.out.println(methodName);
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
					data[ct].horizontalAlignment = tableTemplate.columnTemplates[j].horizontalAlignment;
					data[ct].verticalAlignment = tableTemplate.columnTemplates[j].verticalAlignment;
					if(height>0){
						data[ct].fixHeight = height;
					}
					setCellRowCol(data[ct], k * sccn + nt + j, i + tr, 1, 1);
				}
			}
		}
		UCellAttribute[] bottomItems = tableTemplate.bottomItems;
		if (tableTemplate.bottomNum >= 1) {
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				if (tableTemplate.no != null) {
					ct = (rowNum + tr) * ccn + k * sccn;
					data[ct] = new UCellAttribute("");
					data[ct].fontName = tableTemplate.no.fontName;
					if(height>0){
						data[ct].fixHeight = height;
					}
					setCellRowCol(data[ct], k * sccn, rowNum + tr, 1, 1);
				}
				for (i = 0; i < bottomItems.length; i++) {
					if (bottomItems[i] != null) {
						ct = (rowNum + tr) * ccn + k * sccn + i;
						data[ct] = new UCellAttribute(
								tableTemplate.bottomStrings[i]);
						data[ct].horizontalAlignment = bottomItems[i].horizontalAlignment;
						data[ct].verticalAlignment = bottomItems[i].verticalAlignment;
						data[ct].fontName = tableTemplate.columnTemplates[i % n].fontName;
						if(height>0){
							data[ct].fixHeight = height;
						}
						setCellRowCol(data[ct], bottomItems[i].col,
								bottomItems[i].row + rowNum + tr,
								bottomItems[i].colSpan, bottomItems[i].rowSpan);
					}
				}
			}
		}
	}

	public int getTopItemsRowSpan() {
		int topItemsRowSpan = 1;
		UCellAttribute[] topItems = tableTemplate.topItems;
		if (topItems != null) {
			int i;
			for (i = 0; i < topItems.length; i++) {
				if (topItems[i] != null
						&& topItemsRowSpan < topItems[i].rowSpan) {
					topItemsRowSpan = topItems[i].rowSpan;
				}
			}
		}
		return topItemsRowSpan;
	}

	public int getTopRowNum() {
		if (tableTemplate != null && tableTemplate.topNum >= 1) {
			return tableTemplate.topNum;
		} else
			return 0;
	}

	public void setParameterData(HashMap map, UFormI form, UContentElementI obj) {
	}

}
