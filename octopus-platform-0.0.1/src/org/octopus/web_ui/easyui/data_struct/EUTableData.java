package org.octopus.web_ui.easyui.data_struct;

import java.util.ArrayList;
import java.util.List;

public class EUTableData {

	private Integer total;
	private List<Object> rows;

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public EUTableData() {
		rows = new ArrayList<Object>();
	}

	public void addData(Object o) {
		rows.add(o);
	}
}