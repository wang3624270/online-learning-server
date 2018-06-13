package org.octopus.reportdog.module;

import java.util.ArrayList;
import java.util.List;

public class RowConfig {

	private List<CellConfig> cellList = new ArrayList<CellConfig>();

	private String id;

	private String height = "17";

	private String rowType;

	private int printOnlyOnce = 0;

	public void addCellConfig(CellConfig cellConfig) {
		cellList.add(cellConfig);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getRowType() {
		return rowType;
	}

	public void setRowType(String rowType) {
		this.rowType = rowType;
	}

	public List<CellConfig> getCellList() {
		return cellList;
	}

	public void setCellList(List<CellConfig> cellList) {
		this.cellList = cellList;
	}

	public void setPrintOnlyOnce(int printOnlyOnce) {
		this.printOnlyOnce = printOnlyOnce;
	}

	public int getPrintOnlyOnce() {
		return printOnlyOnce;
	}

}
