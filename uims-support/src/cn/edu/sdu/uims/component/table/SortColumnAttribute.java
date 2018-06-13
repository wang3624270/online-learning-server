package cn.edu.sdu.uims.component.table;

import java.util.Comparator;

public class SortColumnAttribute {
	private int column;
	private boolean ascending;
	private Comparator comparator;
	
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public boolean isAscending() {
		return ascending;
	}
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
	public Comparator getComparator() {
		return comparator;
	}
	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}
	public SortColumnAttribute(int column, boolean ascending,
			Comparator comparator) {
		this.column = column;
		this.ascending = ascending;
		this.comparator = comparator;
	}

}
