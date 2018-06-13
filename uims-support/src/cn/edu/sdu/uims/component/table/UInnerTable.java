package cn.edu.sdu.uims.component.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.table.rowrender.RowSpecificRenderI;
import cn.edu.sdu.uims.util.PrintComponent;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author zzy
 */
public class UInnerTable extends JPanel {

	// private boolean ALLOW_COLUMN_SELECTION = false;
	// private boolean ALLOW_ROW_SELECTION = true;
	private int ROW_HEADER_WIDTH = 30;// 行号对应得宽度

	public static final Color tableBackground = new Color(240, 245, 255);

	TableSorter sorter;// 排序

	private int rowSize = 0;// 行数

	private int columnSize = 0;// 列数

	private Object[][] data = null;// 数据数组

	public List dataList = new ArrayList();// 新增的，将每一行视为一个对象，整个表是一个对象数组

	private String[] columnNames = null;// 列名数组

	boolean columnEditable[] = null;

	RowHeaderTable rowHeader;// //每行的行号

	int[] columnwidth;// 每列的宽度数组

	JTable table;

	MyTableModel mytableModel = new MyTableModel();

	protected UScrollPane scrollPane = new UScrollPane();// 存table

	boolean isCheck = true;// 是否在每行头 有checkbox

	boolean isSelectedWithChecked = false;// 是否在选中表格某行时同时选中该行的checkBox
	// zhaopeng -2007.8.24

	private HashMap dataChangedRows = new HashMap();// 用于更新视图中的数据

	TableCellRenderer[] columnRenderer = null;

	protected JPopupMenu popupMenu = null;

	protected int mouseX, mouseY;

	private MouseProcessor mouseProcessor = new MouseProcessor();

	private ActionListener selectActionListener = null;

	// private boolean isCellEditable=false;

	/**
	 * 用指定的宽度，高度等信息创建对象
	 * 
	 * @param nWidth
	 * @param nHigh
	 * @param ALLOW_COLUMN_SORT
	 */
	public UInnerTable(int nWidth, int nHigh, boolean ALLOW_COLUMN_SORT,
			boolean isCheck) {
		// 设置滚动条宽窄 祁斌
		// scrollPane.getVerticalScrollBar().setPreferredSize(
		// new Dimension(10, 10));
		// scrollPane.getHorizontalScrollBar().setPreferredSize(
		// new Dimension(10, 10));

		// setLayout(new BorderLayout());//设置布局
		this.isCheck = isCheck;
		if (!isCheck)
			ROW_HEADER_WIDTH = 20;
		sorter = new TableSorter(mytableModel); // ADDED THIS
		table = new EditTable(sorter);

		// table.setPreferredScrollableViewportSize(new Dimension(nWidth
		table.setShowGrid(true);
		// table.setBackground(tableBackground);
		table.setBackground(tableBackground); // ////////////////////
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		if (ALLOW_COLUMN_SORT) {
			// Set up tool tips for column headers.
			table.getTableHeader().setToolTipText(
					"Click to sort; Shift-Click to sort in reverse order");
			// 增加行排序的事件
			sorter.addMouseListenerToHeaderInTable(table);
		}
		scrollPane.setViewportView(table);
		// add table header ---- added by zzy 20061017
		if (isCheck) {
			rowHeader = new RowHeaderTable(this);
			scrollPane.setRowHeaderView(rowHeader);
			scrollPane.getRowHeader().setPreferredSize(
					new Dimension(ROW_HEADER_WIDTH, 10));

		}
		this.setLayout(new BorderLayout());
		add(scrollPane); // 添加

		// this.setCellSelectionEnabled(true);
		// ///////使得支持鼠标拖动复制 added by zzy//////////
		/*
		 * MouseListener listener = new DragMouseAdapter();
		 * table.addMouseListener(listener); table.setDragEnabled(true);
		 */
		// 下面的代码作用是不允许列互换 祁斌
		JTableHeader hdr = table.getTableHeader();
		hdr.setReorderingAllowed(false);

		popupMenu = new JPopupMenu();
		table.addMouseListener(mouseProcessor);
		scrollPane.addMouseListener(mouseProcessor);
	}

	/**
	 * 设置是否允许列互换 祁斌
	 * 
	 * @param isChange
	 */
	public UScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setColumChanged(boolean isChange) {
		table.getTableHeader().setReorderingAllowed(isChange);
	}

	public UInnerTable(boolean isCheck) {
		this(400, 200, false, isCheck);
		javax.swing.border.Border border1 = BorderFactory.createMatteBorder(6,
				6, 6, 6, Color.white);
		table.setBackground(tableBackground);
		// this.setBackground(Color.lightGray);
		//this.setForeground(Color.black);
		this.setBorder(border1);
	}

	public UInnerTable(int nWidth, int nHigh) {
		this(nWidth, nHigh, true, false);
	}

	/**
	 * 构造函数，指定是否排序，和是否显示复选框
	 * 
	 * @param ALLOW_COLUMN_SORT
	 * @param isCheck
	 */
	public UInnerTable(boolean ALLOW_COLUMN_SORT, boolean isCheck) {
		this(0, 0, ALLOW_COLUMN_SORT, isCheck);
	}

	public UInnerTable(int nWidth, int nHigh, boolean ALLOW_COLUMN_SORT) {
		this(nWidth, nHigh, ALLOW_COLUMN_SORT, false);
	}

	/**
	 * @param columnNames
	 *            列名
	 * @param data
	 *            数据
	 * @param isCheck
	 */
	public UInnerTable(String[] columnNames, Object[][] data, boolean isCheck) {
		this.columnNames = columnNames;
		this.data = data;
		this.rowSize = data.length;
		this.columnSize = data[0].length;
		this.isCheck = isCheck;
		if (!isCheck)
			ROW_HEADER_WIDTH = 35;
		sorter = new TableSorter(new MyTableModel());
		table = new JTable(sorter);

		// ////////////////////////////////////////////////
		//table.setDefaultRenderer(Object.class, new TableRowColorRender());
		//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		// //////////////////////////////////////////////////////
		table.setBackground(tableBackground);
		// table.setSelectionMode(SINGLE_SELECTION);
		// Set up tool tips for column headers.
		table.getTableHeader().setToolTipText(
				"Click to sort; Shift-Click to sort in reverse order");
		sorter.addMouseListenerToHeaderInTable(table);
		// Create the scroll pane and add the table to it.
		scrollPane = new UScrollPane(table);
		// add table header ---- added by zzy 20061017
		if (isCheck) {
			rowHeader = new RowHeaderTable(this);
			scrollPane.setRowHeaderView(rowHeader);
			scrollPane.getRowHeader().setPreferredSize(new Dimension(35, 10));

			// ////// clear rowheader table selection /////////
			table.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					rowHeader.clearColor();
				}
			});
		}
		// Add the scroll pane to this panel.
		add(scrollPane);
		this.setCellSelectionEnabled(true);
		int hight = data.length * 16;
		if (hight > 600) {
			hight = 600;
		}
		table.setPreferredScrollableViewportSize(new Dimension(200, hight));
		int width = columnNames.length * 100;
		if (width > 800) {
			width = 800;
		}
		table.setPreferredScrollableViewportSize(new Dimension(width, hight));

	}

	public UInnerTable(String[] columnNames, Object[][] data, int nWidth,
			int nHigh) {
		this(columnNames, data, false);
		table.setPreferredScrollableViewportSize(new Dimension(nWidth, nHigh));
	}

	public JTable getTable() {
		return table;
	}

	public TableModel getModel() {
		return mytableModel;
	}

	/**
	 * by qibin
	 * 
	 * @param lis
	 */
	public void setMyTableMouseListener(MouseListener lis) {
		table.addMouseListener(lis);
	}

	/**
	 * 设置table选择方式 added by zzy
	 * 
	 * @param b
	 */
	public void setCellSelectionEnabled(boolean b) {
		table.setCellSelectionEnabled(b);
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

	/**
	 * 设置table表头CheckBox选定某行 zhaopeng 2007.8.24 使表格行的选种和行头checkbox的选种分离。
	 * 
	 * @param row
	 */
	public void setCheckedRow(int row) {
		if (isCheck)
			rowHeader.setCheckedRow(row);
	}

	public void setCancelCheckedAll() {
		if (isCheck)
			rowHeader.setAllChecked(false);
	}

	/**
	 * 增加table选定的行
	 * 
	 * @param row
	 */
	public void addSelectedRow(int row) {
		table.addRowSelectionInterval(row, row);
		table.setColumnSelectionInterval(0, table.getColumnCount() - 1);
	}

	/**
	 * 设置table选定多行
	 * 
	 * @param rows
	 */
	public void setSelectedRows(int[] rows) {

		for (int i = 0; i < rows.length; i++)
			table.addRowSelectionInterval(rows[i], rows[i]);
	}

	/**
	 * 设置table选定多行 zhaopeng 2007.8.24 使表格行的选种和行头checkbox的选种分离。
	 * 
	 * @param rows
	 */
	public void setCheckedRows(int[] rows) {

		/*
		 * 使选中行显示为选中CheckBox Modify by dongb 2006-11-22
		 */
		if (isCheck)
			for (int i = 0; i < rows.length; i++) {
				// table.addRowSelectionInterval(rows[i], rows[i]);
				rowHeader.setCheckedRow(rows[i]);
			}

	}

	public void setSelectionInterval(int i, int j) {
		table.setRowSelectionInterval(i, j);
		table.setColumnSelectionInterval(0, table.getColumnCount() - 1);
	}

	/**
	 * 选则table的全部
	 */
	public void selectAll() {
		table.selectAll();
	}

	/**
	 * 选中table的全部checkbox zhaopeng 2007.8.24 使表格行的选种和行头checkbox的选种分离。
	 */
	public void checkAll() {
		if (isCheck)
			rowHeader.setAllChecked(true);
	}

	/**
	 * 清楚table的选择
	 */
	public void clearSelection() {
		table.clearSelection();

	}

	/**
	 * 清除table的所有checkbox的选中 zhaopeng 2007.8.24 使表格行的选种和行头checkbox的选种分离。
	 */
	public void clearChecked() {
		if (isCheck)
			rowHeader.setAllChecked(false);
	}

	/**
	 * 反选表格中没有选择的行
	 */
	public void selectReverse() {
		if (isCheck) {
			int currSelectedRows[] = this.getCheckedRows();
			rowHeader.setAllChecked(false);
			int rowCount = table.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				boolean select = true;
				for (int j = 0; j < currSelectedRows.length; j++) {
					if (currSelectedRows[j] == i) {
						select = false;
						break;
					}
				}
				if (select)
					rowHeader.setCheckedRow(i);
			}
		} else {
			int currSelectedRows[] = table.getSelectedRows();
			table.clearSelection();
			int rowCount = table.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				boolean select = true;
				for (int j = 0; j < currSelectedRows.length; j++) {
					if (currSelectedRows[j] == i) {
						select = false;
						break;
					}
				}
				if (select)
					addSelectedRow(i);
			}
		}
	}

	/**
	 * 设置一列的多个单元被选中
	 * 
	 * @param rows
	 * @param col
	 */
	public void setSelectedUnits(int[] rows, int col) {
		if (table.getCellSelectionEnabled() == false)
			return;
		this.setSelectedRows(rows);
		table.setColumnSelectionInterval(col, col);
	}

	/**
	 * 批量置数用，设置table中一列中多个单元的值为obj
	 * 
	 * @param obj
	 */
	public void setDataToSelectedUnits(Object obj) {
		int[] rows = table.getSelectedRows();
		if (rows.length == 0)
			return;
		int[] cols = table.getSelectedColumns();
		if (cols.length == 0)
			return;
		else if (cols.length > 1) {
			JOptionPane.showMessageDialog(null, "批量置数只能对一列执行");
			return;
		}
		for (int i = 0; i < rows.length; i++)
			for (int j = 0; j < cols.length; j++)
				data[rows[i]][cols[j]] = obj;
		sorter.setModel(mytableModel);
	}

	/**
	 * 将table中选定的内容拷贝到系统剪贴板
	 */
	public void copySecletionToClipboard() {
		StringBuffer sbf = new StringBuffer();
		int[] rowsSelected;
		int[] colsSelected;
		int numcols;
		int numrows;
		if (isCheck) {
			rowsSelected = rowHeader.getCheckedRows();
			colsSelected = new int[table.getColumnCount()];
			for (int i = 0; i < colsSelected.length; i++)
				colsSelected[i] = i;
		} else {
			rowsSelected = table.getSelectedRows();
			colsSelected = table.getSelectedColumns();
		}
		numrows = rowsSelected.length;
		numcols = colsSelected.length;
		/*
		 * if (!((numrows - 1 == rowsselected[rowsselected.length - 1] -
		 * rowsselected[0] && numrows == rowsselected.length) && (numcols - 1 ==
		 * colsselected[colsselected.length - 1] - colsselected[0] && numcols ==
		 * colsselected.length))) { JOptionPane.showMessageDialog(null, "Invalid
		 * Copy Selection", "Invalid Copy Selection",
		 * JOptionPane.ERROR_MESSAGE);
		 * 
		 * return; }
		 */
		for (int i = 0; i < numrows; i++) {
			for (int j = 0; j < numcols; j++) {
				sbf.append(table.getValueAt(rowsSelected[i], colsSelected[j]));
				if (j < numcols - 1)
					sbf.append("\t");
			}
			if (i < numrows - 1)// 选中得最后一行不换行
				sbf.append("\n");
		}
		StringSelection strsel = new StringSelection(sbf.toString());
		Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
		system.setContents(strsel, strsel);
	}

	public void cutSecletionToClipboard() {
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

	/**
	 * 增加列名，不能在中间插入，只能在表格的尾部插入
	 */
	public void addTabColumn(int modelIndex, int width, String columnName) {
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

		// /////////////增加列宽度数组///////////////
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
	}

	/**
	 * 增加一行
	 * 
	 * @param rowData
	 *            行中的数据
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
		// 如果插入的行的索引小于0 则插入到尾部
		if (nRowIndex < 0) {
			nRowIndex = rowSize - 1;
		}
		for (i = 0; i < nRowIndex; i++) {
			newdata[i] = data[i];
		}
		newdata[nRowIndex] = rowData;
		for (i = nRowIndex + 1; i < rowSize; i++) {
			newdata[i] = data[i - 1];
		}
		data = newdata;
		sorter.setModel(mytableModel);
		// update the row header
		// rowHeader = new RowHeader(mytableModel);
		if (isCheck) {
			((RowHeaderModel) rowHeader.getModel())
					.setRelatedTableModel(mytableModel);
			scrollPane.setRowHeaderView(rowHeader);
		}
		setTableColumnWidth();
		resetTableAllColumnRenderer();
	}

	/**
	 * 将数据插入到最后一行 改用如下的方式
	 */
	public void insertTabRow(Object[] rowData) {
		insertTabRow(rowData, -1);
	}

	/**
	 * 插入空行
	 */
	public void insertBlankRow() {
		Object[] obj = new Object[this.getColumnCount()];
		for (int i = 0; i < obj.length; i++)
			obj[i] = "";
		insertTabRow(obj, -1);
	}

	/**
	 * 下面这6个方法只用在rmi框架中，用于添加rmiClientForm对象 zzy
	 */
	@SuppressWarnings("unchecked")
	/**
	 * 向表格中添加一个对象 现在这个对象只能是RMIClientForm的子类，且重写了toArray方法
	 */
	public void insertTabRow(Object obj) {
		if (dataList == null)
			dataList = new ArrayList();
		dataList.add(obj);
//		insertTabRow(( obj).toArray());
	}

	/**
	 * 设置表格某行信息 赵鹏 2006.11.18
	 * 
	 * @param index
	 * @param obj
	 */
	public void setTabRow(int index, Object obj) {
		if (obj != null && index > -1 && index < dataList.size()) {
//			this.setRowData(((BaseForm) obj).toArray(), index);
			dataList.set(index, obj);
			setTableColumnWidth();
		}
	}

	/**
	 * 返回选中的那行的行号 这个行号不应该用来从dataList中取Form对象(因为一个排序了,一个没排序)
	 * 
	 * @return
	 */
	public int getSelectedRow() {
		return table.getSelectedRow();
		// return sorter.getSelectedRow(table.getSelectedRow());
	}

	/**
	 * 返回选中的多行行号的数组,同样不应该用来从dataList中取Form对象
	 * 
	 * @return
	 */
	public int[] getSelectedRows() {
		return table.getSelectedRows();
	}

	/**
	 * 返回一个行号对应到dataList中的序号
	 * 
	 * @return
	 */
	private int getSelectedRowInList() {
		return sorter.getSelectedRow(table.getSelectedRow());
	}

	/**
	 * 等价于getRowIndexInList(getSelectedRow()); 返回的是你选中的那行对应到未排序的dataList中序号,
	 * 可以用该序号从dataList中取数据, 但private, 外部不可见.
	 * 
	 * @param i
	 * @return
	 */
	public int getRowIndexInList(int i) {
		return sorter.getSelectedRow(i);
	}

	/**
	 * 返回选中的那行对应的Form对象
	 * 
	 * @return
	 */
	public Object getSeletedRowObject() {
		return dataList.get(this.getSelectedRowInList());
	}

	/**
	 * 返回选中的多行对应的Form对象集合
	 * 
	 * @return
	 */
	public Object[] getSeletedRowObjects() {
		int[] i = this.getSelectedRows();
		int indexes[];
		indexes = new int[i.length];
		for (int j = 0; j < i.length; j++)
			indexes[j] = sorter.getSelectedRow(i[j]);
		List list = new ArrayList();
		for (int j = 0; j < indexes.length; j++)
			list.add(dataList.get(indexes[j]));
		return list.toArray();
	}

	/**
	 * 设置list的数据并刷新
	 * 
	 * @param list
	 */
	public void setDataList(List list) {
		this.deleteAllTabRow();
		if (list == null) {
			this.dataList = null;
			return;
		}
		this.dataList = list;
		for (int i = 0; i < dataList.size(); i++) {
//			insertTabRow(((BaseForm) dataList.get(i)).toArray());
		}
	}

	/**
	 * 设置list的数据但是不调用toArray方法
	 * 
	 * @param list
	 * @param isToArray
	 */
	public void setDataListNotToArray(List list) {
		this.deleteAllTabRow();
		if (list == null) {
			this.dataList = null;
			return;
		}
		this.dataList = list;
	}

	public List getDataList() {
		return dataList;
	}

	/**
	 * 返回对应于dataList中的序号 zhanghua add
	 * 
	 * @return
	 */
	public int[] getCheckRows() {
		int[] idx = this.getCheckedRows();
		int indexes[];
		indexes = new int[idx.length];
		for (int j = 0; j < idx.length; j++)
			indexes[j] = sorter.getSelectedRow(idx[j]);
		return indexes;
	}

	/**
	 * 返回对应于dataList中的序号 zhanghua add
	 * 
	 * @return
	 */
	public int[] getSelectRows() {
		int[] idx = this.getSelectedRows();
		int indexes[];
		indexes = new int[idx.length];
		for (int j = 0; j < idx.length; j++)
			indexes[j] = sorter.getSelectedRow(idx[j]);
		return indexes;
	}

	/**
	 * 返回对应于dataList中的序号 zhanghua add
	 * 
	 * @return
	 */
	public int getSelectRow() {
		int idx = this.getSelectedRow();
		int indexe = sorter.getSelectedRow(idx);
		return indexe;
	}

	/**
	 * 获得表格中check的对象数组
	 * 
	 * @return
	 */
	public Object[] getCheckedObjects() {
		int[] idx = this.getCheckedRows();
		int indexes[];
		indexes = new int[idx.length];
		for (int j = 0; j < idx.length; j++)
			indexes[j] = sorter.getSelectedRow(idx[j]);
		Object[] obj = new Object[indexes.length];
		for (int i = 0; i < obj.length; i++) {
			obj[i] = dataList.get(indexes[i]);
		}
		return obj;
	}

	/**
	 * 获得表格中check的行号数组
	 * 
	 * @return
	 */
	public int[] getCheckedRows() {
		if (!isCheck) {
			System.err
					.println("The Table you are using is with No Check Header!!!");
			return null;
		}
		int idx[] = rowHeader.getCheckedRows();
		for (int i = 0; i < idx.length; i++)
			System.out.println(idx[i]);
		return rowHeader.getCheckedRows();
		/*
		 * int indexes[]; indexes = new int[idx.length]; for (int j = 0; j <
		 * idx.length; j++) indexes[j] = sorter.getSelectedRow(idx[j]); return
		 * indexes;
		 */
	}

	/**
	 * 删除表中的一行
	 * 
	 * @param nRowIndex
	 */
	public void deleteTabRow(int nRowIndex) {
		if (rowSize <= 0 || rowSize <= nRowIndex || nRowIndex < 0) {
			return;
		}
		int dataRowIndex = sorter.getSelectedRow(nRowIndex);
		// 删除对象列表中的对象 —— zzy
		// 添加溢出判断---赵鹏 2007.4.24
		if (dataRowIndex < dataList.size() && dataRowIndex >= 0) {
			Object o = dataList.get(dataRowIndex);
			// if (o instanceof TestForm) {
			// TestForm f = (TestForm) o;
			// System.out.println("delete object " + f.name + " " + f.age
			// + " " + f.wage);
			// }
			dataList.remove(dataRowIndex);
		}
		Object[][] newdata = new Object[rowSize - 1][];
		int i;
		for (i = 0; i < dataRowIndex; i++) {
			newdata[i] = data[i];
		}
		for (i = dataRowIndex + 1; i < rowSize; i++) {
			newdata[i - 1] = data[i];
		}
		sorter.setDeletedModel(mytableModel, nRowIndex);
		data = newdata;
		rowSize--;
		// update the row header
		// rowHeader = new RowHeader(mytableModel);
		if (isCheck) {
			((RowHeaderModel) rowHeader.getModel())
					.setRelatedTableModel(mytableModel);
			scrollPane.setRowHeaderView(rowHeader);
		}
	}

	/**
	 * 删除表中所有数据
	 */
	public void deleteAllTabRow() {
		if (rowSize <= 0) {
			return;
		}
		data = null;
		rowSize = 0;
		sorter.setModel(mytableModel);
		// update the row header
		// rowHeader = new RowHeader(mytableModel);
		if (isCheck) {
			((RowHeaderModel) rowHeader.getModel())
					.setRelatedTableModel(mytableModel);
			scrollPane.setRowHeaderView(rowHeader);
		}
		dataList = null;
	}

	/**
	 * 删除指定的一行
	 * 
	 * @param index
	 */
	public void deleteTabRows(int[] index) {
		// 应先对index中对应于sorter中indexes的序号排序，
		// 然后从大序号(indexes对应的index号)开始删除，最后删除最小序号，避免序号错误
		int sorterIndex[] = new int[index.length];
		Hashtable hash = new Hashtable();
		for (int i = 0; i < index.length; i++) {
			int idx = sorter.getSelectedRow(index[i]);
			sorterIndex[i] = idx;
			hash.put(new Integer(idx), new Integer(index[i]));
		}
		Arrays.sort(sorterIndex);
		for (int i = 0; i < sorterIndex.length; i++)
			System.out.println("bbb " + sorterIndex[i]);
		for (int i = 0; i < index.length; i++) {
			index[i] = ((Integer) hash.get(new Integer(sorterIndex[i])))
					.intValue();
		}
		// //////////////need to change later,use it temperily
		// ////////////// 编号太乱了...//////////
		// 作用：如果先删了第1行，后面的行号前移，则原来的第2行对应现在的第一行...
		int[] idx = (int[]) index.clone();
		for (int i = 0; i < index.length; i++)
			for (int j = i + 1; j < index.length; j++)
				if (idx[i] > idx[j])
					index[i]--;
		for (int i = index.length - 1; i >= 0; i--) {
			this.deleteTabRow(index[i]);
		}
	}

	/**
	 * 删除选中的那些行
	 * 
	 */
	public void deleteSelectedRows() {
		this.deleteTabRows(this.getSelectedRows());
		setTableColumnWidth();
	}

	/**
	 * 删除check的那些行
	 * 
	 */
	public void deleteCheckedRows() {
		if (isCheck) {
			this.deleteTabRows(this.getCheckedRows());
			setTableColumnWidth();
		}
	}

	public void setTableRowCount(int nRowCount) {
		deleteAllTabRow();
		data = new Object[nRowCount][];
	}

	public void setTableModel() {
		sorter.setModel(mytableModel);
		setTableColumnWidth();
	}

	// /////////////////////setTableColumnWidth()///////////////////////////
	void setTableColumnWidth() {
		int i;
		for (i = 0; i < columnSize; i++) {
			setColumnWidth(i, columnwidth[i]);
		}
		invalidate();
	}

	void resetTableAllColumnRenderer() {
		int i;
		for (i = 0; i < columnSize; i++) {
			if (columnRenderer[i] != null)
				this.setColumnColorRenderer(i, columnRenderer[i]);
		}
	}

	public Object[] getRowData(int nRowNo) {
		if (nRowNo <= rowSize && nRowNo >= 0) {
			mytableModel.getValueAt(nRowNo, 0);
			return data[nRowNo];
		} else {
			return null;
		}
	}

	public boolean setRowData(Object[] rowData, int nRowIndex) {
		boolean bRet = false;
		if (nRowIndex <= rowSize && nRowIndex >= 0) {
			data[nRowIndex] = rowData;
			bRet = true;
		}
		sorter.setModel(mytableModel);
		return bRet;
	}

	public int getRowCount() {
		return rowSize;
	}

	public int getColumnCount() {
		return columnSize;
	}

	/**
	 * 将TABLE中的数据保存到EXCEL中 需要IMPORT的类包：jxl.jar 编写人： 刘海波 编写时间：2004.10.24
	 */
	public boolean saveTabalDataToExcel(String sFilePath) {
		boolean bRet = true;
		if (table.getRowCount() <= 0) {
			JOptionPane.showMessageDialog(null, "空表不能转出");
			// CommMethod.MessageBoxError("空表不能转出");
			return bRet;
		}
		// 保存EXCEL表改用循环处理，防止大数据的内存溢出 修改日期 2004-12-06 刘海波
		int nCurrentRow = 0;
		int nRowStep = 5000;
		while (nCurrentRow < table.getRowCount()) {
			try {
				WritableWorkbook book;
				WritableSheet sheet1;
				int i, j;
				if (nCurrentRow == 0) {
					// //第一次打开文件要创建文件
					book = Workbook.createWorkbook(new File(sFilePath));
					// 生成名为 第一页 的工作表，参数0表示这是第一页
					sheet1 = book.createSheet("第一页", 0);
					// 写入表头 在Label对象的构造子中指名单元格位置是第一列 第一行(0,0)
					// 注意构造的顺序为先列 后行
					jxl.write.Label labelColumnName;
					for (i = 0; i < table.getColumnCount(); i++) {
						labelColumnName = new jxl.write.Label(i, 0, table
								.getColumnName(i));
						// 将定义好的单元格添加到工作表中
						sheet1.addCell(labelColumnName);
					}
				} else {
					Workbook bookRoot;
					bookRoot = Workbook.getWorkbook(new File(sFilePath));
					book = Workbook.createWorkbook(new File(sFilePath),
							bookRoot);
					sheet1 = book.getSheet(0);
				}
				// 生成一个保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义 单元格位置是第二列，第一行，值为789.123

				if (nCurrentRow + nRowStep < table.getRowCount()) {
					for (i = nCurrentRow; i < nRowStep; i++) {
						for (j = 0; j < table.getColumnCount(); j++) {
							String str = "";
							if (data[i][j] != null)
								str = data[i][j].toString();
							jxl.write.Label label1 = new jxl.write.Label(j,
									i + 1, str);
							// 将定义好的单元格添加到工作表中
							sheet1.addCell(label1);
						}
					}
				} else {
					for (i = nCurrentRow; i < table.getRowCount(); i++) {
						for (j = 0; j < table.getColumnCount(); j++) {
							String str = "";
							if (data[i][j] != null)
								str = data[i][j].toString();
							jxl.write.Label label1 = new jxl.write.Label(j,
									i + 1, str);
							// jxl.write.Label label1 = new jxl.write.Label(j,
							// i + 1, data[i][j].toString());
							// 将定义好的单元格添加到工作表中
							sheet1.addCell(label1);
						}
					}
				}
				// jxl.write.Number number = new jxl.write.Number(1,1,789.123);
				// sheet1.addCell(number);
				// 写入数据并关闭文件
				book.write();
				book.close();
				bRet = true;
				nCurrentRow += nRowStep;
			} catch (Exception e) {
				System.out.println("Save excel :" + e);
				bRet = false;
			}
		}
		return bRet;
	}

	/**
	 * 将TABLE中的数据保存到DBF文件中 需要IMPORT的类包：jdbf 编写人： 赵鹏 编写时间：2006.10.21
	 */
	/**
	 * 打印当前表格
	 * 
	 */
	public void printTable() {
		try {
			PrinterJob prnJob = PrinterJob.getPrinterJob();
			PrintComponent cp = new PrintComponent(table);
			PageFormat pageformat = prnJob.defaultPage();
			prnJob.setPrintable(cp, pageformat);
			javax.print.attribute.HashPrintRequestAttributeSet attrs = new javax.print.attribute.HashPrintRequestAttributeSet();
			attrs
					.add(javax.print.attribute.standard.OrientationRequested.REVERSE_LANDSCAPE);
			if (!prnJob.printDialog()) {
				return;
			} else
				prnJob.print(attrs);
		} catch (PrinterException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * 删除所有列和行，将表重新初始化
	 * 
	 * @author 赵鹏2006.10.27
	 */
	public void clearAndInitTable() {
		rowSize = 0;// 行数
		columnSize = 0;// 列数
		data = null;// 数据数组
		if (dataList != null) {
			dataList.clear();
		}
		columnNames = null;// 列名数组
		columnwidth = null;// 每列的宽度数组
		sorter.setModel(mytableModel);
		if (isCheck) {
			rowHeader = new RowHeaderTable(this);
			scrollPane.setRowHeaderView(rowHeader);
			scrollPane.getRowHeader().setPreferredSize(
					new Dimension(ROW_HEADER_WIDTH, 10));
		}
	}

	/**
	 * 刷新表
	 * 
	 * @author 赵鹏2006.11.8
	 */
	public void refreshTable() {
		table.updateUI();
		if (isCheck)
			rowHeader.updateUI();
	}

	/**
	 * 返回表中发生变化的单元格及相关属性
	 * 
	 * @return
	 */
	public HashMap getDataChangedRows() {
		return this.dataChangedRows;
	}

	public void clearDataChangedRows() {
		this.dataChangedRows.clear();
	}

	public void addChangedData(int row, int col, Object object) {
		ViewUpdateUtils changedData = new ViewUpdateUtils(row, col, object,
				null);
		dataChangedRows.put(row + "_" + col, changedData);
	}

	public void setCellEditable(boolean isEditable) {
		// this.isCellEditable=isEditable;
		for (int i = 0; i < columnSize; i++)
			columnEditable[i] = isEditable;
	}

	/**
	 * 设置某一列的可编辑
	 * 
	 * @param col
	 * @param b
	 */
	public void setColumnEditable(int col, boolean b) {
		columnEditable[col] = b;
	}

	/**
	 * 设置某一列的变色规则，当符合该规则时，变成红色显示
	 * 
	 * @param col
	 * @param rule
	 */
	public void setColumnColorRule(int col, RowSpecificRenderI rule) {
		setColumnColorRenderer(col, new RuleTableCellRender(rule));
	}

	void setColumnColorRenderer(int col, TableCellRenderer renderer) {
		TableColumnModel colModel = table.getColumnModel();
		TableColumn column = colModel.getColumn(col);
		column.setCellRenderer(renderer);

		columnRenderer[col] = renderer;
	}

	// ///////////////////////////////////////////////////////
	class TableRowColorRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);

			if (row % 2 == 0) {
				setBackground(Color.ORANGE);
			} else {
				setBackground(Color.WHITE);
			}
			return this;
		}
	}

	// ////////////////////////////////////////////////////////
	class MyTableModel extends AbstractTableModel {

		public int getColumnCount() {
			if (columnNames == null) {
				return 0;
			} else {
				return columnNames.length;
			}
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

	/**
	 * 相应鼠标拖动，并使得支持复制得类
	 * 
	 * @author zzy
	 */
	/*
	 * private class DragMouseAdapter extends MouseAdapter { public void
	 * mousePressed(MouseEvent e) { JComponent c = (JComponent) e.getSource();
	 * TransferHandler handler = c.getTransferHandler(); handler.exportAsDrag(c,
	 * e, TransferHandler.COPY); } }
	 */
	class TableGraphNameListSelectionListener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent e) {
			// Ignore extra messages.
			if (e.getValueIsAdjusting()) {
				return;
			}
			String graphName = null;
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (lsm.isSelectionEmpty()) {
				// System.out.println("No rows are selected.");
			} else {
				int selectedRow = lsm.getMinSelectionIndex();
				int row = table.getSelectedRow();
				graphName = (String) table.getModel().getValueAt(row, 0);
			}
			if (graphName != null) {
				try {
					// businessControl.businessState.getOpenGraphState().setGraphName(graphName);
					// businessControl.openGraph(graphName);
				} catch (Exception ex) {
					System.out.println(ex.toString());
				}
			}
		}
	}

	class MouseProcessor extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (isCheck)
				rowHeader.clearColor();
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
					if (isCheck && isSelectedWithChecked) {
						setCheckedRow(row);
					}
				}
			}
		}
	}

	public void displyPopMenu() {
		if (popupMenu.getSubElements().length >= 1)
			popupMenu.show(this, mouseX, mouseY);
	}

	public JPopupMenu getUPopupMenu() {
		return popupMenu;
	}

	public boolean isSelectedWithChecked() {
		return isSelectedWithChecked;
	}

	public void setSelectedWithChecked(boolean isSelectedWithChecked) {
		this.isSelectedWithChecked = isSelectedWithChecked;
	}

	public void setAutoResizeMode(int type) {
		table.setAutoResizeMode(type);
	}

	public ActionListener getSelectActionListener() {
		return selectActionListener;
	}

	public void setSelectActionListener(ActionListener selectActionListener) {
		this.selectActionListener = selectActionListener;
	}
	public JPopupMenu getComponentPopupMenu() {
		return popupMenu;
	}
}