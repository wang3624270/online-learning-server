package cn.edu.sdu.uims.component.table;

import java.io.Serializable;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

public class UTableModel extends DefaultTableModel implements Serializable {

	private HashMap<Integer, Boolean> columnMap = new HashMap<Integer, Boolean>();
	private HashMap<Integer, Boolean> rowMap = new HashMap<Integer, Boolean>();
	private HashMap<Integer, String> columnNameMap = new HashMap<Integer, String>();
	private HashMap columnClassMap = new HashMap();

	public void setColumEditable(boolean isEditable, int column) {
		columnMap.put(column, isEditable);
	}
	public void setRowEditable(boolean isEditable, int row) {
		rowMap.put(row, isEditable);
	}
	public void clearRowEditableMark(){
		rowMap.clear();
	}
	public boolean isCellEditable(int row, int column) {
		Boolean colEdit = columnMap.get(column);
		Boolean rowEdit = rowMap.get(row);
		if(colEdit == null || colEdit.equals(false)) {
			return false;
		}else {
			if(rowEdit != null && rowEdit.equals(false)) {
				return false;
			}
			else
				return true;
		}
	}

	public Class getColumnClass(int columnIndex) {
		return (Class) columnClassMap.get(columnIndex);
	}

	public void setColumnClass(Integer columnIndex, Class columnClass) {
		columnClassMap.put(columnIndex, columnClass);
	}
	public String getColumnName(int columnIndex){
		String name = columnNameMap.get(columnIndex);
		if(name == null)
			name = "";
		return name;
	}
	public void setColumnName(Integer columnIndex, String name){
		columnNameMap.put(columnIndex, name);
	}
}
