package cn.edu.sdu.uims.component.event;

import java.util.EventObject;

public class TableCellEvent extends EventObject {
	/** Identifies the addtion of new rows or columns. */
	public static final int INSERT = 1;
	/** Identifies a change to existing data. */
	public static final int UPDATE = 0;
	/** Identifies the removal of rows or columns. */
	public static final int DELETE = -1;

	/** Identifies the header row. */
	public static final int HEADER_ROW = -1;

	/** Specifies all columns in a row or rows. */
	public static final int ALL_COLUMNS = -1;

	//
	// Instance Variables
	//

	protected int type;
	protected int firstRow;
	protected int lastRow;
	protected int column;

	public TableCellEvent(Object source) {
		// Use Integer.MAX_VALUE instead of getRowCount() in case rows were
		// deleted.
		this(source, 0, Integer.MAX_VALUE, ALL_COLUMNS, UPDATE);
	}

	public TableCellEvent(Object source, int row) {
		this(source, row, row, ALL_COLUMNS, UPDATE);
	}

	/**
	 * The data in rows [<I>firstRow</I>, <I>lastRow</I>] have been updated.
	 */
	public TableCellEvent(Object source, int firstRow, int lastRow) {
		this(source, firstRow, lastRow, ALL_COLUMNS, UPDATE);
	}

	/**
	 * The cells in column <I>column</I> in the range [<I>firstRow</I>,
	 * <I>lastRow</I>] have been updated.
	 */
	public TableCellEvent(Object source, int firstRow, int lastRow, int column) {
		this(source, firstRow, lastRow, column, UPDATE);
	}

	/**
	 * The cells from (firstRow, column) to (lastRow, column) have been changed.
	 * The <I>column</I> refers to the column index of the cell in the model's
	 * co-ordinate system. When <I>column</I> is ALL_COLUMNS, all cells in the
	 * specified range of rows are considered changed.
	 * <p>
	 * The <I>type</I> should be one of: INSERT, UPDATE and DELETE.
	 */
	public TableCellEvent(Object source, int firstRow, int lastRow, int column,
			int type) {
		super(source);
		this.firstRow = firstRow;
		this.lastRow = lastRow;
		this.column = column;
		this.type = type;
	}

	public int getFirstRow() {
		return firstRow;
	};

	/** Returns the last row that changed. */
	public int getLastRow() {
		return lastRow;
	};

	public int getColumn() {
		return column;
	};

	/**
	 * Returns the type of event - one of: INSERT, UPDATE and DELETE.
	 */
	public int getType() {
		return type;
	}
}
