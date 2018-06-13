package cn.edu.sdu.uims.base;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UColumnTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;

public class UTable implements UTableI {
	private String componentName;
	UPanelI parent;
	UElementTemplate elementTemplate;
	protected int xo, yo;

	protected int startNo = 0;

	protected UTableTemplate tableTemplate = null;

	protected UComponentI columnFields[], sumFields[];

	protected UFormI items[];

	public UTable() {
		this(null);
	}

	public UTable(UTableTemplate template) {
		super();
		tableTemplate = template;
	}

	public void init() {
		// TODO Auto-generated method stub
		initComponents();
	}

	public void addComponent(UComponentI com, int layout) {

	}

	public Method[] getColumnSetMethods(Class c) {
		String attrName, methodName;
		Method methods[];
		int j;
		try {
			methods = new Method[tableTemplate.columnNum];
			for (j = 0; j < tableTemplate.columnNum; j++) {
				attrName = tableTemplate.columnTemplates[j].itemFormMember;
				if (attrName != null && !attrName.equals("")) {
					methodName = "set" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					methods[j] = c.getMethod(methodName, Object.class);
				}
			}
			return methods;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Method[] getColumnGetMethods(Class c) {
		String attrName, methodName;
		Method methods[];
		int j;
		try {
			methods = new Method[tableTemplate.columnNum];
			for (j = 0; j < tableTemplate.columnNum; j++) {
				attrName = tableTemplate.columnTemplates[j].itemFormMember;
				if (attrName != null && !attrName.equals("")) {
					methodName = "get" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					methods[j] = c.getMethod(methodName);
				}
			}
			return methods;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public UComponentI getStaticTextObject(String str) {
		return null;
	}

	public UComponentI getFieldsObject() {
		return null;
	}

	public int getTitleRowNum() {
		if (tableTemplate != null && tableTemplate.title != null) {
			return 1;
		}
		return 0;
	}

	public int getNoteRowNum() {
		if (tableTemplate != null && tableTemplate.note != null) {
			return 1;
		}
		return 0;
	}

	public int getTopRowNum() {
		if (tableTemplate != null && tableTemplate.topNum >= 1) {
			return 1;
		} else
			return 0;
	}

	public int getBottomRowNum() {
		if (tableTemplate != null && tableTemplate.bottomNum >= 1) {
			return 1;
		} else
			return 0;
	}

	public void setOtherAttribute(UComponentI component, UColumnTemplate c,
			UCellAttribute e) {
		component.setHorizontalAlignment(c.horizontalAlignment);
		component.setVerticalAlignment(c.verticalAlignment);
		component.setFont(UFactory.getModelSession().getFontByName(e.fontName));
		if (e.content != null) {
			component.setText(e.content);
		}
		component.setBorder(UFactory.getModelSession().getBorderByName(
				e.borderName));
		component.setColor(UFactory.getModelSession().getColorByName(
				e.frontColorName));
	}

	public void initComponents() {
		int i, j, ki, kj, w, h, kki, kkj, xt, x, y, n, st;
		String s;
		xt = xo;
		UComponentI label = null;
		boolean mark[];
		y = yo;
		if (tableTemplate.title != null) {
			w = getWidth();
			label = getStaticTextObject(tableTemplate.title.content);
			label.setShellBounds(xo, y, getWidth(), tableTemplate.title.h);
			addComponent(label, 0);
			y += tableTemplate.title.h;
		}
		if (tableTemplate.leftTopString != null) {
			if (tableTemplate.topNum >= 1) {
				label = getStaticTextObject(tableTemplate.leftTopString);
				label.setShellBounds(xt, y, tableTemplate.leftWidth,
						tableTemplate.rowHeight * tableTemplate.topNum);
				addComponent(label, 0);
				y += tableTemplate.rowHeight * tableTemplate.topNum;
			}
			st = 0;
			for (i = 0; i < tableTemplate.leftStrings.length; i++) {
				label = getStaticTextObject(tableTemplate.leftStrings[i]);
				label.setShellBounds(xt, y, tableTemplate.leftWidth,
						tableTemplate.rowHeight
								* (tableTemplate.leftIndexs[i] - st));
				addComponent(label, 0);
				y += tableTemplate.rowHeight
						* (tableTemplate.leftIndexs[i] - st);
				st = tableTemplate.leftIndexs[i];
			}
			if (tableTemplate.sumColumnNum > 0) {
				y += tableTemplate.rowHeight * tableTemplate.bottomNum;
			}
			if (tableTemplate.bottomNum >= 1) {
				label = getStaticTextObject("");
				label.setShellBounds(xt, y, tableTemplate.leftWidth,
						tableTemplate.rowHeight * tableTemplate.bottomNum);
				addComponent(label, 0);
				y += tableTemplate.rowHeight * tableTemplate.bottomNum;
			}
			xt += tableTemplate.leftWidth;
		}
		y = yo;
		if (tableTemplate.title != null) {
			y += tableTemplate.title.h;
		}
		if (tableTemplate.no != null) {
			if (tableTemplate.topNum >= 1) {
				label = getStaticTextObject("序号");
				label.setShellBounds(xt, y, tableTemplate.no.width,
						tableTemplate.rowHeight * tableTemplate.topNum);
				addComponent(label, 0);
				y += tableTemplate.rowHeight * tableTemplate.topNum;
			}
			for (i = 0; i < tableTemplate.rowNum; i++) {
				label = getStaticTextObject("" + (startNo + i + 1));
				label.setShellBounds(xt, y, tableTemplate.no.width,
						tableTemplate.rowHeight);
				addComponent(label, 0);
				y += tableTemplate.rowHeight;
			}
			if (tableTemplate.sumColumnNum > 0) {
				y += tableTemplate.rowHeight * tableTemplate.bottomNum;
			}
			if (tableTemplate.bottomNum >= 1) {
				label = getStaticTextObject("");
				label.setShellBounds(xt, y, tableTemplate.no.width,
						tableTemplate.rowHeight * tableTemplate.bottomNum);
				addComponent(label, 0);
				y += tableTemplate.rowHeight * tableTemplate.bottomNum;
			}
			xt += tableTemplate.no.width;
		}
		y = yo;
		if (tableTemplate.title != null)
			y += tableTemplate.title.h;
		if (tableTemplate.topNum > 0) {
			mark = new boolean[tableTemplate.topNum * tableTemplate.columnNum];
			for (i = 0; i < tableTemplate.topNum * tableTemplate.columnNum; i++) {
				mark[i] = false;
			}
			i = 0;
			while (i < tableTemplate.topNum) {
				x = xt;
				j = 0;
				while (j < tableTemplate.columnNum) {
					if (!mark[i * tableTemplate.columnNum + j]) {
						s = tableTemplate.topStrings[i
								* tableTemplate.columnNum + j];
						mark[i * tableTemplate.columnNum + j] = true;
						kj = 0;
						w = tableTemplate.columnTemplates[j].width;
						h = tableTemplate.rowHeight;
						while (j + kj + 1 < tableTemplate.columnNum) {
							if (s.equals(tableTemplate.topStrings[(i)
									* tableTemplate.columnNum + j + kj + 1])) {
								w += tableTemplate.columnTemplates[j + kj + 1].width;
								kj++;
							} else {
								break;
							}
						}
						ki = 0;
						while (i + ki + 1 < tableTemplate.topNum) {
							if (s.equals(tableTemplate.topStrings[(i + ki + 1)
									* tableTemplate.columnNum + j])) {
								h += tableTemplate.rowHeight;
								ki++;
							} else {
								break;
							}
						}
						for (kki = 0; kki <= ki; kki++)
							for (kkj = 0; kkj <= kj; kkj++)
								mark[(i + kki) * tableTemplate.columnNum + j
										+ kkj] = true;
						label = getStaticTextObject(s);
						label.setShellBounds(x, y, w, h);
						addComponent(label, 0);
					}
					x += tableTemplate.columnTemplates[j].width;
					j++;
				}
				y += tableTemplate.rowHeight;
				i++;
			}
		}
		columnFields = new UComponentI[tableTemplate.columnNum
				* tableTemplate.rowNum];
		for (i = 0; i < tableTemplate.rowNum; i++) {
			x = xt;
			for (j = 0; j < tableTemplate.columnNum; j++) {
				if (tableTemplate.cells[i * tableTemplate.columnNum + j].fieldClassName == null) {
					columnFields[i * tableTemplate.columnNum + j] = getFieldsObject();
				} else {
					try {
						columnFields[i * tableTemplate.columnNum + j] = (UComponentI) (Class
								.forName(tableTemplate.cells[i
										* tableTemplate.columnNum + j].fieldClassName)
								.newInstance());
						columnFields[i * tableTemplate.columnNum + j]
								.setFilter((FilterI)UFactory
										.getModelSession()
										.getFilterTemplateByName(
												tableTemplate.cells[i
														* tableTemplate.columnNum
														+ j].filter));
						columnFields[i * tableTemplate.columnNum + j]
								.setFilter1((FilterI)UFactory
										.getModelSession()
										.getFilterTemplateByName(
												tableTemplate.cells[i
														* tableTemplate.columnNum
														+ j].filter1));
					} catch (Exception e) {
						columnFields[i * tableTemplate.columnNum + j] = getFieldsObject();
					}
				}
				columnFields[i * tableTemplate.columnNum + j].setShellBounds(x,
						y, tableTemplate.columnTemplates[j].width,
						tableTemplate.rowHeight);
				if (tableTemplate.cells[i * tableTemplate.columnNum + j].content != null) {
					columnFields[i * tableTemplate.columnNum + j]
							.setText(tableTemplate.cells[i
									* tableTemplate.columnNum + j].content);
				}
				columnFields[i * tableTemplate.columnNum + j]
						.setEditable((tableTemplate.cells[i
								* tableTemplate.columnNum + j].editable));
				if (tableTemplate.columnTemplates[j].maxLength > 0)
					// columnFields[i * tableTemplate.columnNum + j]
					// .setMaxLength(tableTemplate.columnTemplates[j].maxLength);
					columnFields[i * tableTemplate.columnNum + j]
							.setHorizontalAlignment(tableTemplate.columnTemplates[j].horizontalAlignment);

				setOtherAttribute(
						columnFields[i * tableTemplate.columnNum + j],
						tableTemplate.columnTemplates[j], tableTemplate.cells[i
								* tableTemplate.columnNum + j]);
				addComponent(columnFields[i * tableTemplate.columnNum + j], 0);
				x += tableTemplate.columnTemplates[j].width;
			}
			y += tableTemplate.rowHeight;
		}
		if (tableTemplate.sumColumnNum > 0) {
			sumFields = new UComponentI[tableTemplate.columnNum
					- tableTemplate.sumColumnNum];
			x = xt;
			n = tableTemplate.sumColumnNum;
			w = 0;
			if (tableTemplate.leftTopString != null) {
				x -= tableTemplate.leftWidth;
				n++;
				w += tableTemplate.leftWidth;
			}
			if (tableTemplate.no != null) {
				x -= tableTemplate.no.width;
				n++;
				w += tableTemplate.no.width;
			}
			for (j = 0; j < tableTemplate.sumColumnNum; j++) {
				w += tableTemplate.columnTemplates[j].width;
			}
			label = getStaticTextObject(tableTemplate.sumString);
			label.setShellBounds(x, y, w, tableTemplate.rowHeight);
			addComponent(label, 0);
			x += w;
			for (j = tableTemplate.sumColumnNum; j < tableTemplate.columnNum; j++) {
				sumFields[j - tableTemplate.sumColumnNum] = getFieldsObject();
				sumFields[j - tableTemplate.sumColumnNum].setShellBounds(x, y,
						tableTemplate.columnTemplates[j].width,
						tableTemplate.rowHeight);
				sumFields[j - tableTemplate.sumColumnNum].setEditable(false);
				addComponent(sumFields[j - tableTemplate.sumColumnNum], 0);
				x += tableTemplate.columnTemplates[j].width;
			}
			y += tableTemplate.rowHeight;
		}
		if (tableTemplate.note != null) {
			w = getWidth();
			label = getStaticTextObject(tableTemplate.note.content);
			label.setShellBounds(xo, y, getWidth(), tableTemplate.note.h);
			addComponent(label, 0);
			y += tableTemplate.note.h;
		}
		addActionListenrtoCell();
	}

	public void addActionListenrtoCell() {
	}

	public int getWidth() {
		int w = 0, i = 0;
		if (tableTemplate.leftTopString != null) {
			w += tableTemplate.leftWidth;
		}
		if (tableTemplate.no != null) {
			w += tableTemplate.no.width;
		}
		for (i = 0; i < tableTemplate.columnNum; i++) {
			w += tableTemplate.columnTemplates[i].width;
		}
		return w * tableTemplate.subTableNum;
	}

	public int getHeight() {
		int h;
		h = (tableTemplate.topNum + getRowNum() + tableTemplate.bottomNum)
				* tableTemplate.rowHeight;
		if (tableTemplate.title != null)
			h += tableTemplate.title.h;
		if (tableTemplate.sumColumnNum > 0)
			h += tableTemplate.rowHeight;
		return h;
	}

	public void setStartXY(int xo, int yo) {
		this.xo = xo;
		this.yo = yo;
	}

	public void setStartNo(int no) {
		this.startNo = no;
	}

	public int getRowNum() {
		return tableTemplate.rowNum;
	}

	public void setColumnEditable(int col, boolean b) {
		int i;
		for (i = 0; i < tableTemplate.rowNum; i++) {
			columnFields[i * tableTemplate.columnNum + col].setEditable(b);
		}
	}

	public void setColumnText(int col, Object[] a) {
		int i;
		for (i = 0; i < Math.min(tableTemplate.rowNum, a.length); i++) {
			columnFields[i * tableTemplate.columnNum + col].setText(a[i]
					.toString());
		}
	}

	public UComponentI getCell(int row, int col) {
		if (row < 0 || row >= tableTemplate.rowNum || col < 0
				|| col >= tableTemplate.columnNum)
			return null;
		else
			return columnFields[row * tableTemplate.columnNum + col];
	}

	public void setDataFromForm(int row, Object obj) {
		int col;
		String attrName, methodName;
		Method method;
		if (obj == null || row < 0 || row >= tableTemplate.rowNum)
			return;
		try {
			for (col = 0; col < tableTemplate.columnNum; col++) {
				attrName = tableTemplate.columnTemplates[col].itemFormMember;
				if (attrName != null && !attrName.equals("")) {
					methodName = "get" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					method = obj.getClass().getMethod(methodName);
					columnFields[row * tableTemplate.columnNum + col]
							.setText(method.invoke(obj).toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void requestFoucus(int row, int col) {
		requestFoucus(row * tableTemplate.columnNum + col);
	}

	public void requestFoucus(int i) {
	}

	public boolean requestFirstFoucus() {
		int i;
		return false;
	}

	public UTableTemplate getTableTemplate() {
		return tableTemplate;
	}

	public void setTableTemplate(UTableTemplate tableTemplate) {
		this.tableTemplate = tableTemplate;
	}

	public int getStartNo() {
		return startNo;
	}

	public void clearContentPanel() {
		int i;
		for (i = 0; i < tableTemplate.rowNum; i++) {
			this.clearRowContent(i);
		}
	}

	public void clearRowContent(int row) {
		int col;
		if (row < 0 || row >= tableTemplate.rowNum)
			return;
		for (col = 0; col < tableTemplate.columnNum; col++) {
			columnFields[row * tableTemplate.columnNum + col].setText(null);
		}
	}

	public void setTemplate(UTemplate template) {
		if(template instanceof UTableTemplate)
			tableTemplate = (UTableTemplate) template;
	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setBorder(UBorder border) {
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public Object getData() {
		int i, j;
		String attrName, methodName;
		Method method;
		if (items == null || tableTemplate.itemFormClassName == null)
			return null;
		try {
			for (i = 0; i < tableTemplate.rowNum; i++) {
				if (isEmptyRow(i)) {
					items[i] = null;
				} else {
					if (items[i] == null) {
						items[i] = (UFormI) Class.forName(
								tableTemplate.itemFormClassName).newInstance();
					}
					for (j = 0; j < tableTemplate.columnNum; j++) {
						attrName = tableTemplate.columnTemplates[j].itemFormMember;
						if (attrName != null && !attrName.equals("")) {
							methodName = "set"
									+ attrName.substring(0, 1).toUpperCase()
									+ attrName.substring(1, attrName.length());
							method = items[i].getClass().getMethod(methodName,
									String.class);
							method.invoke(items[i], columnFields[i
									* tableTemplate.columnNum + j].getData());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	public void setData(Object obj) {
		String attrName, methodName;
		Method method;
		int i, j;
		clearContentPanel();
		items = (UFormI[]) obj;
		for (i = 0; i < tableTemplate.rowNum; i++) {
			if (items[i] != null) {
				for (j = 0; j < tableTemplate.columnNum; j++) {
					attrName = tableTemplate.columnTemplates[j].itemFormMember;
					if (attrName != null && !attrName.equals("")) {
						methodName = "get"
								+ attrName.substring(0, 1).toUpperCase()
								+ attrName.substring(1, attrName.length());
						try {
							method = items[i].getClass().getMethod(methodName);
							columnFields[i * tableTemplate.columnNum + j]
									.setText(method.invoke(items[i]).toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						;
					}
				}
			}
		}
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < columnFields.length; i++)
			if (columnFields[i].isEditable()
					&& columnFields[i].getData().equals(""))
				return true;
		return false;
	}

	public boolean isEmptyRow(int row) {
		int i;
		String str;
		for (i = 0; i < tableTemplate.columnNum; i++) {
			str = columnFields[row * tableTemplate.columnNum + i].getData()
					.toString();
			if (str.equals("")) {
				return true;
			}
		}
		return false;
	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub

	}

	public void setMaxLength(int l) {
		// TODO Auto-generated method stub

	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if (elementTemplate != null)
			return new URect(elementTemplate.x, elementTemplate.y,
					elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub

	}


	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub

	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void setComponentName(String name) {
		this.componentName = name;
	}

	public String getComponentName() {
		return componentName;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void initHandlerObject() {
		// TODO Auto-generated method stub

	}

	public void initContents() {
		// TODO Auto-generated method stub

	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	public boolean saveDataToExcel(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public FilterI getColumnFilter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void ResetComponentContent() {
		// TODO Auto-generated method stub

	}

	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setTemplateIsUseFul(boolean bUse) {

	}

	public void setInitComponentData(Object data) {

	}


	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
	}

	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	public void onClose() {

	}

	public void repaintComponent() {
	}

	public void setParameters(HashMap paras) {

	}

	public HashMap getParameters() {
		return null;
	}

	public void resetScrollPanelSize() {
		// TODO Auto-generated method stub
		if (tableTemplate == null)
			return;
		int j;
		int width, height;
		width = xo;
		height = yo;
		if (tableTemplate.title != null) {
			height += tableTemplate.title.h;
		}
		if (tableTemplate.no != null) {
			if (tableTemplate.topNum >= 1) {
				height += tableTemplate.rowHeight * tableTemplate.topNum;
			}
			if (tableTemplate.sumColumnNum > 0) {
				height += tableTemplate.rowHeight * tableTemplate.bottomNum;
			}
			if (tableTemplate.bottomNum >= 1) {
				height += tableTemplate.rowHeight * tableTemplate.bottomNum;
			}
			if (tableTemplate.rowNum >= 0) {
				height += tableTemplate.rowHeight * tableTemplate.rowNum;
			}
			if (items != null && items.length >= 0) {
				height += tableTemplate.rowHeight * items.length;
			}
		}
		if (tableTemplate.no != null)
			width += tableTemplate.no.width;
		for (j = 0; j < tableTemplate.columnNum; j++) {
			width += tableTemplate.columnTemplates[j].width;
		}
	}

	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}

	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setdisplayText(String text) {
		// TODO Auto-generated method stub

	}

	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setActionComandString(String str) {
		// TODO Auto-generated method stub

	}


	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}
	public void resetTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayCurrentRowDetail(UHandlerI h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserData(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableColumnSortSet(UHandlerI handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean testInvalidateData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveDataToDBF(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}




}
