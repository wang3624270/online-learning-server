package cn.edu.sdu.uims.base;

import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UTableAttribute;
import cn.edu.sdu.uims.def.UTableTemplate;

public class UContentRadomTable extends URandomTable {
	protected UCellAttribute data[] = null;

	protected UTableAttribute tableAttribute = null;

	public UContentRadomTable() {
		super();
	}

	public UContentRadomTable(UTableTemplate template) {
		super(template);
	}

	public void setData(Object obj) {
	}

	public Object getData() {
		return null;
	}

	public int getRowNum() {
		int i;
		int row = 0;
		for (i = 0; i < tableTemplate.cells.length; i++) {
			if (tableTemplate.cells[i].row + tableTemplate.cells[i].rowSpan > row)
				row = tableTemplate.cells[i].row
						+ tableTemplate.cells[i].rowSpan;
		}
		return row;
	}

	public int getCellColumnNum() {
		int n = tableTemplate.columnNum;
		if (tableTemplate.no != null)
			n++;
		n *= tableTemplate.subTableNum;
		return n;
	}

	public int getBodyCellNum() {
		return tableTemplate.cells.length;
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

	public void initComponents() {
		int i;
		initCellObjects();
		tableAttribute = new UTableAttribute();
		int cn = tableTemplate.columnNum;
		tableAttribute.colNum = cn;
		tableAttribute.width = new float[cn];
		for (i = 0; i < cn; i++) {
			tableAttribute.width[i] = tableTemplate.columnTemplates[i].width;
		}
		for (i = 0; i < tableTemplate.cells.length; i++) {
			data[i] = new UCellAttribute(tableTemplate.cells[i]);
		}
	}

	public void setParameterData(HashMap map, UFormI form, UContentElementI obj) {
	}

}
