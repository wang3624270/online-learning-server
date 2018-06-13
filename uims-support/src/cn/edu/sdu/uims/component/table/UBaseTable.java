package cn.edu.sdu.uims.component.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import cn.edu.sdu.uims.base.UScrollPane;

public class UBaseTable extends JPanel {

	private JTable table = null;
	private MyTableModel mytableModel = new MyTableModel();
	private static final Color tableBackground = new Color(240, 245, 255);
	private UScrollPane scrollPane = new UScrollPane();
	boolean isCheck = true;// 是否在每行头有checkbox
	protected JPopupMenu popupMenu = null;
	protected int mouseX, mouseY;
	private MouseProcessor mouseProcessor = new MouseProcessor();
	private ActionListener selectActionListener = null;
	boolean isSelectedWithChecked = false;// 是否在选中表格某行时同时选中该行的checkBox
	TableCellRenderer[] columnRenderer = null;// 每列填充的tableCellRender
	int[] columnwidth;// 每列的宽度数

	private String[] columnNames = null;// 列名数组

	private Object[][] data = null;// 数据数组

	private int rowSize = 0;// 行数

	private int columnSize = 0;// 列数

	boolean columnEditable[] = null;

	private HashMap dataChangedRows = new HashMap();// 用于更新视图中的数据

	public UBaseTable(int nWidth, int nHigh, boolean ALLOW_COLUMN_SORT,
			boolean isCheck) {
		this.isCheck = isCheck;
		this.columnNames = columnNames;
		this.columnNames = new String[3];
		this.columnNames[0] = "First Name";
		this.columnNames[1] = "Position";
		this.columnNames[2] = "Telephone";
		this.data = new Object[3][3];
		this.data[0] = new Object[] { "Wangdong", "Executive",
				new Boolean(false) };
		this.data[1] = new Object[] { "LiHong", "Secretary", new Boolean(true) };
		this.data[2] = new Object[] { "Wangdong", new Integer(3500),
				new Boolean(false) };

		table = new JTable(mytableModel);
		table.setShowGrid(true);
		table.setBackground(tableBackground);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);

		this.setLayout(new BorderLayout());
		add(scrollPane);
		/** *不允许列互换* */
		JTableHeader hdr = table.getTableHeader();
		hdr.setReorderingAllowed(false);

		popupMenu = new JPopupMenu();
		table.addMouseListener(mouseProcessor);
		scrollPane.addMouseListener(mouseProcessor);
	}

	/**
	 * 增加列名，不能在中间插入，只能在表格的尾部插入（默认为不可编辑的JTable）
	 * 
	 * @param modelIndex
	 *            列数
	 * @param width
	 *            该列的宽度
	 * @param columnName
	 *            该列的名称
	 */
	public void addTabColumn(int modelIndex, int width, String columnName) {
		addTabColumn(modelIndex, width, columnName, false, null);
	}

	/**
	 * 增加列名，不能在中间插入，只能在表格的尾部插入
	 * 
	 * @param modelIndex
	 *            列数
	 * @param width
	 *            该列的宽度
	 * @param columnName
	 *            该列的名称
	 * @param isCellEditable
	 *            该列是否可编辑
	 * @param cellRender
	 *            该列要填充的tableCellRender
	 */
	public void addTabColumn(int modelIndex, int width, String columnName,
			boolean isCellEditable, TableCellRenderer cellRender) {

		TableColumn column = new TableColumn();
		column.setHeaderValue(columnName);
		table.addColumn(column);
		columnSize++;
		String[] scNames = new String[columnSize];
		TableCellRenderer[] renderer = new TableCellRenderer[columnSize];
		int i;
		for (i = 0; i < columnSize - 1; i++) {
			scNames[i] = columnNames[i];
			renderer[i] = columnRenderer[i];
		}
		scNames[columnSize - 1] = columnName;
		renderer[columnSize - 1] = null;
		columnNames = scNames;
		columnRenderer = renderer;
		// //

		boolean[] editable = new boolean[columnSize];
		for (i = 0; i < columnSize - 1; i++) {
			editable[i] = columnEditable[i];
		}
		editable[columnSize - 1] = false;
		columnEditable = editable;

		// /////////////增加列宽度数�?//////////////
		int[] ntmp;
		ntmp = new int[columnSize];
		for (i = 0; i < columnSize - 1; i++) {
			ntmp[i] = columnwidth[i];
		}
		// ////////////////////////////////////////
		ntmp[columnSize - 1] = width;
		columnwidth = ntmp;
		// mytableModel.set
		// sorter.setTableModel();

		//		
		//		
		//		
		//		
		//		
		// TableColumn column = new TableColumn();
		// column.setHeaderValue(columnName);
		// table.addColumn(column);
		// columnSize++;
		// String[] scNames = new String[columnSize];
		// TableCellRenderer[] renderer = new TableCellRenderer[columnSize];
		// int i;
		// for (i = 0; i < columnSize - 1; i++) {
		// scNames[i] = columnNames[i];
		// renderer[i] = columnRenderer[i];
		// }
		// scNames[columnSize - 1] = columnName;
		// renderer[columnSize - 1] = cellRender;
		// columnNames = scNames;
		// columnRenderer = renderer;
		//
		// boolean[] editable = new boolean[columnSize];
		// for (i = 0; i < columnSize - 1; i++) {
		// editable[i] = columnEditable[i];
		// }
		// editable[columnSize - 1] = isCellEditable;
		// columnEditable = editable;
		//
		// /** ***************管理每列宽度数**************** */
		// int[] ntmp;
		// ntmp = new int[columnSize];
		// for (i = 0; i < columnSize - 1; i++) {
		// ntmp[i] = columnwidth[i];
		// }
		// ntmp[columnSize - 1] = width;
		// columnwidth = ntmp;
	}

	/**
	 * 增加mytable中的数据
	 * 
	 * @param rowData
	 * @param nRowIndex
	 */
	public void insertTabRow(Object[] rowData, int nRowIndex) {
		rowSize++;
		Object[][] newdata = new Object[rowSize][columnSize];
		int i;
		// 如果当前行小于要插入的行则插入到尾部
		if (rowSize < nRowIndex) {
			nRowIndex = rowSize - 1;
		}
		// 如果插入的行的索引小于0， 则插入到尾部
		if (nRowIndex < 0) {
			nRowIndex = rowSize - 1;
		}
		for (i = 0; i < nRowIndex; i++) {
			newdata[i] = data[i];
		}
		// if (isCheck) {
		// Object[] addedData=new Object[columnSize];
		// addedData[0]=false;
		// for(int j=1;j<columnSize;j++){
		// addedData[j]=rowData[j-1];
		// }
		// newdata[nRowIndex] = addedData;
		// }else{
		// newdata[nRowIndex] = rowData;
		// }
		newdata[nRowIndex] = rowData;
		for (i = nRowIndex + 1; i < rowSize; i++) {
			newdata[i] = data[i - 1];
		}
		data = newdata;
		table.setModel(mytableModel);
		table.revalidate();
		// update the row header
		// rowHeader = new RowHeader(mytableModel);

		setTableColumnWidth();
		resetTableAllColumnRenderer();
	}

	/**
	 * 将数据插入到table的尾部
	 */
	public void insertTabRow(Object[] rowData) {
		insertTabRow(rowData, -1);
	}

	void setTableColumnWidth() {
		int i;
		for (i = 0; i < columnSize; i++) {
			setColumnWidth(i, columnwidth[i]);
		}
		invalidate();
	}

	/**
	 * 指定特定列的宽度
	 */
	public void setColumnWidth(int pColumn, int pWidth) {
		// 得到列的样式
		TableColumnModel colModel = table.getColumnModel();
		// 得到pColumn列然后设置它的最佳宽度
		colModel.getColumn(pColumn).setPreferredWidth(pWidth);
	}

	void resetTableAllColumnRenderer() {
		int i;
		for (i = 0; i < columnSize; i++) {
			if (columnRenderer[i] != null)
				this.setColumnColorRenderer(i, columnRenderer[i]);
		}
	}

	void setColumnColorRenderer(int col, TableCellRenderer renderer) {
		TableColumnModel colModel = table.getColumnModel();
		TableColumn column = colModel.getColumn(col);
		column.setCellRenderer(renderer);

		columnRenderer[col] = renderer;
	}

	class MyTableModel extends AbstractTableModel {
		public int getColumnCount() {
			// if (columnNames == null) {
			// return 0;
			// } else {
			// return columnNames.length;
			// }
			return columnSize;
		}

		public int getRowCount() {
			if (data == null) {
				return 0;
			} else {
				return data.length;
			}
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			if (row < rowSize) {
				return data[row][col];
			} else {
				return null;
			}
		}

		public Class getColumnClass(int c) {
			if (getValueAt(0, c) != null) {
				return getValueAt(0, c).getClass();
			} else {
				return "".getClass();
			}
		}

		public boolean isCellEditable(int row, int col) {
			// return isCellEditable;
			return columnEditable[col];
		}

		public void setCellEditable(int col, boolean isCellEditable) {
			columnEditable[col] = isCellEditable;
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			ViewUpdateUtils changedData = new ViewUpdateUtils(row, col, value,
					getColumnClass(col));
			dataChangedRows.put(row + "_" + col, changedData);
			fireTableCellUpdated(row, col);
		}

		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();
			for (int i = 0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++) {
					System.out.print("  " + data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}

	class MouseProcessor extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (isCheck)
				table.clearSelection();
		}

		public void mouseClicked(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			if (e.getButton() == MouseEvent.BUTTON3) {
				displyPopMenu();
			}
			if (e.getSource() == table) {
				int row = table.rowAtPoint(new Point(mouseX, mouseY));
				if (row != -1) {
					setSelectedRow(row);
					if (selectActionListener != null) {
						ActionEvent ae = new ActionEvent(table, 0, "selectRow");
						selectActionListener.actionPerformed(ae);
					}
				}
			}
		}
	}

	public void displyPopMenu() {
		if (popupMenu.getSubElements().length >= 1)
			popupMenu.show(this, mouseX, mouseY);
	}

	/**
	 * 设置table选定某行
	 * 
	 * @param row
	 */
	public void setSelectedRow(int row) {
		table.setRowSelectionInterval(row, row);
		table.setColumnSelectionInterval(0, table.getColumnCount() - 1);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JPopupMenu getComponentPopupMenu() {
		return popupMenu;
	}

}
