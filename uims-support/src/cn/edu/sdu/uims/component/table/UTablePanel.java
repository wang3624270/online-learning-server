//描述:公共表格
//修改者:赵新晓
//最后修改日期:2009.5.13

package cn.edu.sdu.uims.component.table;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import com.sun.org.apache.xpath.internal.operations.Variable;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.form.UFormModifyStatusI;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.CheckObject;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UCanPopupPanelComponentI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPageActionComponentI;
import cn.edu.sdu.uims.base.UPanel;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.base.UTableI;
import cn.edu.sdu.uims.component.LabeValueTransI;
import cn.edu.sdu.uims.component.PopupPanelShell;
import cn.edu.sdu.uims.component.UPageActionPanel;
import cn.edu.sdu.uims.component.button.UButton;
import cn.edu.sdu.uims.component.checkbox.UCheckBox;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.combobox.UComboBoxDate;
import cn.edu.sdu.uims.component.combobox.UComboBoxDict;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.TableEventAdaptor;
import cn.edu.sdu.uims.component.label.ULabel;
import cn.edu.sdu.uims.component.list.UListCheck;
import cn.edu.sdu.uims.component.list.UListPopup;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.panel.UPopupPanel;
import cn.edu.sdu.uims.component.spinner.USpinner;
import cn.edu.sdu.uims.component.table.rowrender.RowSpecificRenderBaseRule;
import cn.edu.sdu.uims.component.table.rowrender.RowSpecificRenderI;
import cn.edu.sdu.uims.component.table.rowrender.TableRowSpecificRender;
import cn.edu.sdu.uims.component.textfield.UTextField;
import cn.edu.sdu.uims.component.textfield.UTextFieldAttach;
import cn.edu.sdu.uims.component.textfield.UTextFieldQuery;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.TableStatisticsItemTemplate;
import cn.edu.sdu.uims.def.TableViewControlTemplate;
import cn.edu.sdu.uims.def.UColumnTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UFilterTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.def.UTextFieldQueryTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.form.UCellLockedDataI;
import cn.edu.sdu.uims.form.UPopupIoDataI;
import cn.edu.sdu.uims.form.UTextFieldDataFormI;
import cn.edu.sdu.uims.form.impl.TableAttributeValueForm;
import cn.edu.sdu.uims.form.impl.UTableColumnSortItemForm;
import cn.edu.sdu.uims.form.impl.UTableForm;
import cn.edu.sdu.uims.form.impl.UTableQueryDataForm;
import cn.edu.sdu.uims.handler.InputQueryParaProcessorI;
import cn.edu.sdu.uims.handler.TablePageDataQueryHandlerI;
import cn.edu.sdu.uims.handler.UFormHandlerI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.DataProcessUtils;
import cn.edu.sdu.uims.util.UimsUtils;
import cn.edu.sdu.uims.validator.DataValidatorI;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class UTablePanel extends JPanel implements UTableI,
		UCanPopupPanelComponentI, UPageActionComponentI {

	private static final long serialVersionUID = 1L;
	protected UTableTemplate tableTemplate = null;

	protected static final Color tableBackground = new Color(240, 245, 255);

	protected int ROW_HEADER_WIDTH = 50;// 琛屽彿瀵瑰簲寰楀搴�

	protected UTableModel tableModel = null;

	protected JTable table = null;

	protected JTableHeader tableHeader = null;

	protected UScrollPane[] viewScrollPane = null;
	protected UScrollPane tableScrollPane;

	protected int currViewIndex = 0;
	protected JPanel viewContainer;
	private UPopupMenu popupMenu = null;

	// ////////////////////////////////////////////
	protected boolean isRowColorCtl = false;// 行颜色设定功能是否开启
	protected int colorCtlColNum = 0;// 行颜色值所在列

	protected List dataList = new ArrayList();

	// private boolean isNumbered = false;

	protected HashMap map = new HashMap();

	protected String componentName;
	protected UPanelI parent;
	protected TableEventAdaptor eventProcessor = null;
	protected ULabel labels[];
	protected UTextField fields[];
	protected USpinner spinners[];
	protected UComboBox comboBoxs[];
	protected UComboBoxDate comboBoxDates[];
	protected UButton buttons[];
	protected HashMap validatorMap = new HashMap();
	protected HashMap getMethodMap = new HashMap();
	protected HashMap setMethodMap = new HashMap();
	protected Class formClass = null;
	protected int userStartColNo = 0;
	protected boolean tableModelChangeCanSend = false;
	protected boolean mouseClicked = false;
	protected boolean mouseEventCanSend = false;
	protected boolean mouseReleaseEventCanSend = false;
	protected boolean tableModelChangeCanUse = false;
	protected boolean actionEventCanSend = false;
	protected boolean focusEventCanSend = false;
	protected boolean keyEventCanSend = false;
	protected boolean initFinished = false;
	protected boolean cannotEvent = false;
	protected HashMap filterMap = new HashMap();
	protected HashMap comMap = new HashMap();
	protected HashMap colNoMap = new HashMap();
	protected UComponentI startNoField, endNoField;
	protected UElementTemplate elementTemplate;
	protected Dimension tableInnerSize = null;
	protected TableEventProcessorAdaptor tableEventProcessorAdaptor = new TableEventProcessorAdaptor();
	protected TableRowSpecificRender[] columnRender;
	protected RowSpecificRenderI rowSpecificRender = null;
	protected JTextField [] statisticsTextField;

	protected boolean canGetCellValueByPanel = false;
	protected Object[] columnPopupPanel;
	protected int currentPopupCol = -1, currentPopupRow = -1;
	protected UTextFieldQuery queryDataField = null;

	protected InnerButtonActionProcessor innerButtonActionProcessor = new InnerButtonActionProcessor();
	protected boolean[] isColumnModelToData;

	protected UTextFieldDataFormI queryData;

	protected UCheckBox columnCheckBox[];
	protected CheckObject columnCheckObject[];
	protected int savedColumnWidth[];
	protected int savedColumnMaxWidth[];
	protected int savedColumnMinWidth[];

	protected int currentSelectSortColumn = 0 - 1;
	protected int currentSelectSortStatus = 0;
	public static final int SQRT_ASCENDING = 1;
	public static final int SQRT_DESCENDING = 2;
	public int displayColumnCount = 0;

	protected List<SortColumnAttribute> sortList = null;
	UListCheck listCheck= null;
	private boolean enablePopupMenu = true;

	private UPageActionPanel pageActionPanel = null;
	public boolean isRowColorCtl() {
		return isRowColorCtl;
	}

	public int getColorCtlColNum() {
		return colorCtlColNum;
	}

	public void setColorCtlColNum(int colorCtlColNum) {
		this.colorCtlColNum = colorCtlColNum;
	}

	public void setRowColorCtl(boolean isRowColorCtl) {
		this.isRowColorCtl = isRowColorCtl;
	}


	public int getUserStartColNo() {
		return userStartColNo;
	}

	public UTablePanel() {
		this(false);
	}

	public UTablePanel(boolean isNumbered) {
		// this.isNumbered = isNumbered;
	}

	/**
	 * 澧炲姞涓�鍒�
	 * 
	 * @param modelIndex
	 * @param width
	 * @param columnName
	 */
	public void init() {
		initContents();
	}

	public void setTableRowHeight() {
		if (this.tableTemplate == null)
			return;
		if (tableTemplate.rowHeight > 0)
			table.setRowHeight(tableTemplate.rowHeight);
	}

	public void setTableFont() {
		if (this.elementTemplate == null)
			return;
		if (tableTemplate.fontName == null
				|| tableTemplate.fontName.length() == 0)
			return;
		UFont font = UFactory.getModelSession().getFontByName(
				tableTemplate.fontName);
		table.setFont(font.font);
	}

	public void initColumnRender() {
		if (this.tableTemplate == null)
			return;
		int count = 0;
		if (this.tableTemplate.no != null)
			count = 1;
		columnRender = new TableRowSpecificRender[count
				+ this.tableTemplate.columnNum];
		rowSpecificRender = null;
		TableRowSpecificRender render = new TableRowSpecificRender();
		try {
			if (tableTemplate.rowSpecificRenderName != null) {
				rowSpecificRender = (RowSpecificRenderI) Class.forName(
						tableTemplate.rowSpecificRenderName).newInstance();
			} else {
				rowSpecificRender = new RowSpecificRenderBaseRule();
			}
			if (this.tableTemplate.renderClassName != null) {
				render = (TableRowSpecificRender) Class.forName(
						this.tableTemplate.renderClassName).newInstance();
			}
			if (count > 0) {
				columnRender[0] = render;
			}
			render.setTablePanel(this);
			if (rowSpecificRender != null)
				render.setRowSpecificRender(rowSpecificRender);
			for (int i = count; i < count + this.tableTemplate.columnNum; i++) {
				if (this.tableTemplate.columnTemplates[i - count].renderClassName != null) {
					columnRender[i] = (TableRowSpecificRender) Class
							.forName(
									this.tableTemplate.columnTemplates[i
											- count].renderClassName)
							.newInstance();
					columnRender[i].setTablePanel(this);
				} else {
					columnRender[i] = render;
				}
			}
		} catch (Exception e) {

		}
	}

	public TableRowSpecificRender getColumnColorRenderer(int i) {
		return columnRender[i];
	}

	public void setComTemplateValueByUColumnTemplate(UElementTemplate t,
			UColumnTemplate u) {
		t.setExtendAttributeByParas(u.paras);
	}

	public void initColumns() {

		if (tableTemplate == null)
			return;
		initColumnRender();
		if (userStartColNo > 0) {
			addTabColumn(0, tableTemplate.no.width, "序号", "No", Integer.class);
			this.setColumnColorRenderer(0, new RowNumberRender());
			setColumnEditable(0, false);
		}
		int i;
		// ///////////////////////////////////////////
		Object oa[];
		FilterI filter;
		List list;
		UFilterTemplate temp;
		Method m;
		boolean canRowSelectAllowed = true;
		isColumnModelToData = new boolean[tableTemplate.columnNum];
		try {
			for (i = 0; i < tableTemplate.columnNum; i++) {
				m = (Method) getMethodMap
						.get(tableTemplate.columnTemplates[i].itemFormMember);
				Class columnClass = null;
				if (m != null)
					columnClass = m.getReturnType();
				if (tableTemplate.topEnStrings == null
						|| i >= tableTemplate.topEnStrings.length) {
					addTabColumn(i + userStartColNo,
							tableTemplate.columnTemplates[i].width,
							tableTemplate.topStrings[i],
							tableTemplate.topStrings[i], columnClass);
				} else {
					addTabColumn(i + userStartColNo,
							tableTemplate.columnTemplates[i].width,
							tableTemplate.topStrings[i],
							tableTemplate.topEnStrings[i], columnClass);
				}
			}
			for (i = 0; i < tableTemplate.columnNum; i++) {
				setColumnEditable(i + userStartColNo,
						tableTemplate.columnTemplates[i].editable);
				isColumnModelToData[i] = true;
				if (tableTemplate.columnTemplates[i].editable)
					canRowSelectAllowed = false;
				if (tableTemplate.columnTemplates[i].className != null) {
					UComponentI cmb = null;
					cmb = (UComponentI) Class.forName(
							tableTemplate.columnTemplates[i].className)
							.newInstance();
					UElementTemplate t;
					if (tableTemplate.columnTemplates[i].templateName != null) {
						t = (UElementTemplate) Class.forName(
								tableTemplate.columnTemplates[i].templateName)
								.newInstance();
					} else
						t = new UElementTemplate();
					setComTemplateValueByUColumnTemplate(t,
							tableTemplate.columnTemplates[i]);
					cmb.setTemplate(t);
					if (cmb instanceof UTextFieldQuery ||  cmb instanceof UTextFieldAttach
							|| (cmb instanceof UComboBox && !(cmb instanceof UComboBoxDict)))
						isColumnModelToData[i] = false;
					cmb.setScreenOwner(this);
					if (cmb instanceof UCanPopupPanelComponentI) {
						UCanPopupPanelComponentI p = (UCanPopupPanelComponentI) cmb;
						p.setPanelTemplateName(tableTemplate.columnTemplates[i].panelTemplateName);
					}
					cmb.initContents();
					if (tableTemplate.columnTemplates[i].addedDatas != null)
						cmb.setAddedDatas(tableTemplate.columnTemplates[i].addedDatas);
					if (tableTemplate.columnTemplates[i].dictionary != null) {
						UComboBoxDict dCmb = (UComboBoxDict) cmb;
						dCmb.setAddedDatas(tableTemplate.columnTemplates[i].dictionary);
						isColumnModelToData[i] = false;
					}
					comMap.put(tableTemplate.columnTemplates[i].name, cmb);
					if (tableTemplate.columnTemplates[i].filter != null) {
						temp = UFactory
								.getModelSession()
								.getFilterTemplateByName(
										tableTemplate.columnTemplates[i].filter);
						if (temp != null) {
							filter = temp.newInstance();
							filter.init(temp.parameter);
							filterMap.put(
									tableTemplate.columnTemplates[i].name,
									filter);
							cmb.setFilter(filter);
							cmb.updateAddedDatas();
						}
					}
					if (cmb instanceof JComboBox) {
						setCellEditor(i + userStartColNo, (JComboBox) cmb);
					} else if (cmb instanceof JCheckBox) {
						setCellEditor(i + userStartColNo, (JCheckBox) cmb);
					} else if (cmb instanceof JTextField) {
						setCellEditor(i + userStartColNo, (JTextField) cmb);
					}
				}
			}
			// 设置render显示位置
			for (i = 0; i < tableTemplate.columnNum; i++) {
				TableColumnModel colModel = table.getColumnModel();
				TableColumn column = colModel.getColumn(i + userStartColNo);
				// TableCellRenderer render = column.getCellRenderer();

			}
			// ///////////////////////////////////////
			int t = tableTemplate.columnNum;
			if (userStartColNo > 0)
				t++;
			for (i = 0; i < t; i++)
				setColumnColorRenderer(i, getColumnColorRenderer(i));
			enableRowColor();

			canGetCellValueByPanel = false;
			// this.initColumnComboxEvent();
			initColumnPopupPanel();
			if (tableTemplate.mustRowSelect || tableTemplate.mustCellSelect)
				setRowSelectionAllowed(true);
			else
				setRowSelectionAllowed(canRowSelectAllowed);
			if(tableTemplate.mustCellSelect || canGetCellValueByPanel) {
				table.setCellSelectionEnabled(true);
			}				
			// /////////////////////////////////////////
			enableColumnHidden();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ////////////////////////////////////////////////////
	public void enableRowColor() {
		int i;
		for (i = 0; i < tableTemplate.columnNum; i++) {
			if (tableTemplate.columnTemplates[i].itemFormMember != null
					&& "color"
							.endsWith(tableTemplate.columnTemplates[i].itemFormMember))
				break;
		}
		if (i >= tableTemplate.columnNum)
			return;
		if (userStartColNo > 0) {
			if (i < tableTemplate.columnNum + 1) {
				colorCtlColNum = i + 1;
				isRowColorCtl = true;
				hideColumn(i + 1);
			}
		} else {
			if (i < tableTemplate.columnNum) {
				colorCtlColNum = i;
				isRowColorCtl = true;
				hideColumn(i);
			}
		}
	}

	public void enableColumnHidden() {
		int i;
		for (i = 0; i < tableTemplate.columnNum; i++) {
			if (!tableTemplate.columnTemplates[i].visible)
				hideColumn(i + userStartColNo);
		}
	}

	public void enableColumnHidden(List<Integer> colList) {
		int i;
		for (i = 0; i < colList.size(); i++) {
			hideColumn(colList.get(i) + userStartColNo);
		}
	}

	public void initTableBase() {
		if (tableScrollPane == null)
			return;
		tableModel = new UTableModel();
		table = new JTable(tableModel);
		table.setShowGrid(true);
		table.setBackground(tableBackground);
		if(this.tableTemplate.autoResizeLastColumn) {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		}else {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		this.setTableFont();
		this.setTableRowHeight();
		tableScrollPane = new UScrollPane();
		tableScrollPane.setViewportView(table);
		this.setPreferredSize(new Dimension(1, 1));
		tableHeader = table.getTableHeader();
		tableHeader
				.setToolTipText("Click to sort; Shift-Click to sort in reverse order");
		tableHeader.addMouseListener(tableEventProcessorAdaptor);
		tableHeader.setReorderingAllowed(false);
		tableModel.addTableModelListener(tableEventProcessorAdaptor);
		table.addMouseListener(tableEventProcessorAdaptor);
		table.addFocusListener(tableEventProcessorAdaptor);
		table.addKeyListener(tableEventProcessorAdaptor);
		tableScrollPane.addMouseListener(tableEventProcessorAdaptor);
	}

	public void initTableTitle() {
		if (tableTemplate.title == null)
			return;
		if (tableTemplate.title.content == null)
			return;
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label = new JLabel(tableTemplate.title.content);
		panel.add(label);
		add(panel, BorderLayout.NORTH);

	}

	public void addInnertButtonToPanel(HashMap<String, Component> comMap) {
		JPanel tp;
		JLabel l;
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		if (tableTemplate.actionBar.queryDataField != null) {
			newQueryDataFiled();
			comMap.put("queryDataField", queryDataField);
		}
		if (tableTemplate.actionBar.addNewRowButton) {
			UButton bt = new UButton();
			if (b)
				bt.setText("add");
			else
				bt.setText("添加");
			bt.setActionCommand("innerCommandAddNewRowButton");
			bt.addActionListener(innerButtonActionProcessor);
			comMap.put("addNewRowButton", bt);
		}
		if (tableTemplate.actionBar.deleteRowButton) {
			UButton bt = new UButton();
			if (b)
				bt.setText("delete");
			else
				bt.setText("删除");
			bt.setActionCommand("innerCommandDeleteRowButton");
			bt.addActionListener(innerButtonActionProcessor);
			comMap.put("deleteRowButton", bt);
		}
		if (tableTemplate.actionBar.clearButton) {
			UButton bt = new UButton();
			if (b)
				bt.setText("clear");
			else
				bt.setText("清空");
			bt.setActionCommand("innerCommandClearButton");
			bt.addActionListener(innerButtonActionProcessor);
			comMap.put("clearButton", bt);
		}
		if (tableTemplate.actionBar.okButton) {
			UButton bt = new UButton();
			if (b)
				bt.setText("ok");
			else
				bt.setText("确认");
			bt.setActionCommand("innerCommandokButton");
			bt.addActionListener(innerButtonActionProcessor);
			comMap.put("okButton", bt);
		}
		if (tableTemplate.actionBar.cancelButton) {
			UButton bt = new UButton();
			if (b)
				bt.setText("cancel");
			else
				bt.setText("取消");
			bt.setActionCommand("innerCommandcancelButton");
			bt.addActionListener(innerButtonActionProcessor);
			comMap.put("cancelButton", bt);
		}
	}

	private void newQueryDataFiled() {
		UTextFieldQueryTemplate temp = new UTextFieldQueryTemplate();
		temp.setMaxLength(15);
		temp.valueActionNum = tableTemplate.actionBar.queryDataField.valueActionNum;
		temp.beanName = "serviceQueryProcessRuleBean";
		if (tableTemplate.actionBar.queryDataField.beanName != null)
			temp.beanName = tableTemplate.actionBar.queryDataField.beanName;
		if (tableTemplate.actionBar.queryDataField.className != null) {
			try {
				queryData = (UTextFieldDataFormI) Class.forName(
						tableTemplate.actionBar.queryDataField.className)
						.newInstance();
			} catch (Exception et) {
				queryData = null;
			}
		}
		temp.isNotDigit = tableTemplate.actionBar.queryDataField.isNotDigit;
		temp.keyAction = tableTemplate.actionBar.queryDataField.keyAction;
		temp.errPromptMsg = tableTemplate.actionBar.queryDataField.errPromptMsg;
		if (queryData == null)
			queryData = UimsFactory.getClientMainI().creatUTextFieldDataForm();
		queryData.clear();
		queryDataField = new UTextFieldQuery();
		queryDataField.setTemplate(temp);
		queryDataField.initContents();
		queryDataField.setData(queryData);
	}

	public JPanel initActionBar() {
		// //////////////////////////////////////
		JPanel panel = null;
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		HashMap<String, Component> comMap = new HashMap<String, Component>();
		try {
			if (tableTemplate.actionBar != null) {
				int n = 0;
				JLabel label;
				JPanel pt;
				panel = new JPanel();
				if (tableTemplate.actionBar.queryDataField != null)
					n++;
				if (tableTemplate.actionBar.addNewRowButton)
					n++;
				if (tableTemplate.actionBar.deleteRowButton)
					n++;
				if (tableTemplate.actionBar.clearButton)
					n++;
				if (tableTemplate.actionBar.okButton)
					n++;
				if (tableTemplate.actionBar.cancelButton)
					n++;
				if (tableTemplate.actionBar.startEndClassName != null) {
					n += 2;
				}
				if (tableTemplate.actionBar.labels != null) {
					n += tableTemplate.actionBar.labels.length;
				}
				if (tableTemplate.actionBar.fields != null) {
					n += tableTemplate.actionBar.fields.length;
				}
				if (tableTemplate.actionBar.spinners != null) {
					n += tableTemplate.actionBar.spinners.length;
				}
				if (tableTemplate.actionBar.comboBoxs != null) {
					n += tableTemplate.actionBar.comboBoxs.length;
				}
				if (tableTemplate.actionBar.comboBoxDates != null) {
					n += tableTemplate.actionBar.comboBoxDates.length;
				}
				if (tableTemplate.actionBar.buttons != null) {
					n += tableTemplate.actionBar.buttons.length;
				}
				if (tableTemplate.actionBar.rwoArrange == UConstants.ROW_ARRANGE_CLOSE) {
					panel.setLayout(new FlowLayout());
					addInnertButtonToPanel(comMap);
					if (tableTemplate.actionBar.startEndClassName != null) {
						pt = new JPanel();
						pt.setLayout(new FlowLayout());
						if (b)
							label = new JLabel("start");
						else
							label = new JLabel("起始");
						pt.add(label);
						startNoField = (UComponentI) Class.forName(
								tableTemplate.actionBar.startEndClassName)
								.newInstance();
						pt.add(startNoField.getAWTComponent());
						if (b)
							label = new JLabel("end");
						else
							label = new JLabel("结束");
						pt.add(label);
						endNoField = (UComponentI) Class.forName(
								tableTemplate.actionBar.startEndClassName)
								.newInstance();
						pt.add(endNoField.getAWTComponent());
						comMap.put("startEndClassName", pt);
					}
					if (tableTemplate.actionBar.labels != null) {
						labels = new ULabel[tableTemplate.actionBar.labels.length];
						for (int i = 0; i < tableTemplate.actionBar.labels.length; i++) {
							labels[i] = new ULabel();
							if (b)
								labels[i]
										.setText(tableTemplate.actionBar.labels[i].enLabel);
							else
								labels[i]
										.setText(tableTemplate.actionBar.labels[i].text);
							comMap.put(tableTemplate.actionBar.labels[i].name,
									labels[i]);
						}
					}
					if (tableTemplate.actionBar.fields != null) {
						fields = new UTextField[tableTemplate.actionBar.fields.length];
						for (int i = 0; i < tableTemplate.actionBar.fields.length; i++) {
							if (tableTemplate.actionBar.fields[i].text == null && tableTemplate.actionBar.fields[i].enLabel == null) {
								fields[i] = new UTextField(
										tableTemplate.actionBar.fields[i].maxLength);
								fields[i].addActionListener(tableEventProcessorAdaptor);
								fields[i].addFocusListener(tableEventProcessorAdaptor);
								comMap.put(
										tableTemplate.actionBar.fields[i].name,
										fields[i]);
							} else {
								JPanel pt0 = new JPanel();
								pt0.setLayout(new FlowLayout());
								JLabel jl;
								if(!b) {
									jl= new JLabel(
										tableTemplate.actionBar.fields[i].text);
								}else {
									jl= new JLabel(
											tableTemplate.actionBar.fields[i].enLabel);									
								}
								pt0.add(jl);
								fields[i] = new UTextField(
										tableTemplate.actionBar.fields[i].maxLength);
								fields[i].setEditable(tableTemplate.actionBar.fields[i].editable);
								fields[i].addActionListener(tableEventProcessorAdaptor);
								fields[i].addFocusListener(tableEventProcessorAdaptor);
								pt0.add(fields[i]);
								comMap.put(
										tableTemplate.actionBar.fields[i].name,
										pt0);
							}
						}
					}
					if (tableTemplate.actionBar.spinners != null) {
						spinners = new USpinner[tableTemplate.actionBar.spinners.length];
						for (int i = 0; i < tableTemplate.actionBar.spinners.length; i++) {
							if (tableTemplate.actionBar.spinners[i].text == null && tableTemplate.actionBar.spinners[i].enLabel == null) {
								spinners[i] = new USpinner(
										tableTemplate.actionBar.spinners[i].value,tableTemplate.actionBar.spinners[i].minimum, tableTemplate.actionBar.spinners[i].maximum,tableTemplate.actionBar.spinners[i].stepSize);
//								spinners[i].addActionListener(tableEventProcessorAdaptor);
								spinners[i].addFocusListener(tableEventProcessorAdaptor);
								comMap.put(
										tableTemplate.actionBar.spinners[i].name,
										spinners[i]);
							} else {
								JPanel pt0 = new JPanel();
								pt0.setLayout(new FlowLayout());
								JLabel jl;
								if(!b) {
									jl= new JLabel(
										tableTemplate.actionBar.spinners[i].text);
								}else {
									jl= new JLabel(
											tableTemplate.actionBar.spinners[i].enLabel);									
								}
								pt0.add(jl);
								spinners[i] = new USpinner(
										tableTemplate.actionBar.spinners[i].value,tableTemplate.actionBar.spinners[i].minimum, tableTemplate.actionBar.spinners[i].maximum,tableTemplate.actionBar.spinners[i].stepSize);
								spinners[i].setEditable(tableTemplate.actionBar.spinners[i].editable);
//								spinners[i].addActionListener(tableEventProcessorAdaptor);
								spinners[i].addFocusListener(tableEventProcessorAdaptor);
								pt0.add(spinners[i]);
								comMap.put(
										tableTemplate.actionBar.spinners[i].name,
										pt0);
							}
						}
					}
					if (tableTemplate.actionBar.comboBoxs != null) {
						comboBoxs = new UComboBox[tableTemplate.actionBar.comboBoxs.length];
						for (int i = 0; i < tableTemplate.actionBar.comboBoxs.length; i++) {
							if (tableTemplate.actionBar.comboBoxs[i].dictionary != null) {
								UComboBoxDict cc = new UComboBoxDict();
								comboBoxs[i]= cc;
								comboBoxs[i]
										.setTemplate(tableTemplate.actionBar.comboBoxs[i]);
								cc.setAddedDatas(tableTemplate.actionBar.comboBoxs[i].dictionary);
							}
							else {
								comboBoxs[i] = new UComboBox();
								comboBoxs[i]
										.setTemplate(tableTemplate.actionBar.comboBoxs[i]);
							}
							comboBoxs[i].init();
							comMap.put(
									tableTemplate.actionBar.comboBoxs[i].name,
									comboBoxs[i]);
						}
					}
					if (tableTemplate.actionBar.comboBoxDates != null) {
						comboBoxDates = new UComboBoxDate[tableTemplate.actionBar.comboBoxDates.length];
						for (int i = 0; i < tableTemplate.actionBar.comboBoxDates.length; i++) {
							comboBoxDates[i] = new UComboBoxDate();
							comboBoxDates[i]
									.setTemplate(tableTemplate.actionBar.comboBoxDates[i]);
							comboBoxDates[i].init();
							comMap.put(
									tableTemplate.actionBar.comboBoxDates[i].name,
									comboBoxDates[i]);
						}
					}
					if (tableTemplate.actionBar.buttons != null) {
						buttons = new UButton[tableTemplate.actionBar.buttons.length];
						for (int i = 0; i < tableTemplate.actionBar.buttons.length; i++) {
							buttons[i] = new UButton();
							if (b)
								buttons[i]
										.setText(tableTemplate.actionBar.buttons[i].enContent);
							else
								buttons[i]
										.setText(tableTemplate.actionBar.buttons[i].content);
							buttons[i]
									.setActionCommand(tableTemplate.actionBar.buttons[i].cmd);
							buttons[i]
									.setComponentName(tableTemplate.actionBar.buttons[i].name);
							buttons[i]
									.addActionListener(tableEventProcessorAdaptor);
							comMap.put(tableTemplate.actionBar.buttons[i].name,
									buttons[i]);
						}
					}

				} else {
					panel.setLayout(new GridLayout());
					addInnertButtonToPanel(comMap);
					if (tableTemplate.actionBar.startEndClassName != null) {
						pt = new JPanel();
						pt.setLayout(new FlowLayout());
						if (b)
							label = new JLabel("start");
						else
							label = new JLabel("起始");
						pt.add(label);
						startNoField = (UComponentI) Class.forName(
								tableTemplate.actionBar.startEndClassName)
								.newInstance();
						pt.add(startNoField.getAWTComponent());
						if (b)
							label = new JLabel("end");
						else
							label = new JLabel("结束");
						pt.add(label);
						endNoField = (UComponentI) Class.forName(
								tableTemplate.actionBar.startEndClassName)
								.newInstance();
						pt.add(endNoField.getAWTComponent());
						comMap.put("startEndClassName", pt);
					}
					if (tableTemplate.actionBar.labels != null) {
						labels = new ULabel[tableTemplate.actionBar.labels.length];
						for (int i = 0; i < tableTemplate.actionBar.labels.length; i++) {
							labels[i] = new ULabel();
							if (b)
								labels[i]
										.setText(tableTemplate.actionBar.labels[i].enLabel);
							else
								labels[i]
										.setText(tableTemplate.actionBar.labels[i].text);
							pt = new JPanel();
							pt.setLayout(new FlowLayout());
							pt.add(labels[i]);
							comMap.put(tableTemplate.actionBar.labels[i].name,
									pt);
						}
					}
					if (tableTemplate.actionBar.spinners != null) {
						spinners = new USpinner[tableTemplate.actionBar.spinners.length];
						for (int i = 0; i < tableTemplate.actionBar.spinners.length; i++) {
							spinners[i] = new USpinner(
									tableTemplate.actionBar.spinners[i].value,tableTemplate.actionBar.spinners[i].minimum, tableTemplate.actionBar.spinners[i].maximum,tableTemplate.actionBar.spinners[i].stepSize);
							spinners[i].setEditable(tableTemplate.actionBar.spinners[i].editable);
//							spinners[i].addActionListener(tableEventProcessorAdaptor);
							spinners[i].addFocusListener(tableEventProcessorAdaptor);
							pt = new JPanel();
							pt.setLayout(new FlowLayout());
							labels[i] = new ULabel();
							if (b)
								labels[i]
										.setText(tableTemplate.actionBar.labels[i].enLabel);
							else
								labels[i]
										.setText(tableTemplate.actionBar.labels[i].text);
							pt.add(labels[i]);
							pt.add(spinners[i]);
							comMap.put(tableTemplate.actionBar.spinners[i].name,
									pt);
						}
					}
					if (tableTemplate.actionBar.comboBoxs != null) {
						comboBoxs = new UComboBox[tableTemplate.actionBar.comboBoxs.length];
						for (int i = 0; i < tableTemplate.actionBar.comboBoxs.length; i++) {
							comboBoxs[i] = new UComboBox();
							pt = new JPanel();
							pt.setLayout(new FlowLayout());
							labels[i] = new ULabel();
							if (b)
								labels[i]
										.setText(tableTemplate.actionBar.labels[i].enLabel);
							else
								labels[i]
										.setText(tableTemplate.actionBar.labels[i].text);
							pt.add(labels[i]);
							pt.add(comboBoxs[i]);
							panel.add(pt);
							comMap.put(
									tableTemplate.actionBar.comboBoxs[i].name,
									pt);
						}
					}
					if (tableTemplate.actionBar.comboBoxDates != null) {
						comboBoxDates = new UComboBoxDate[tableTemplate.actionBar.comboBoxDates.length];
						for (int i = 0; i < tableTemplate.actionBar.comboBoxDates.length; i++) {
							comboBoxDates[i] = new UComboBoxDate();
							pt = new JPanel();
							pt.setLayout(new FlowLayout());
							pt.add(comboBoxDates[i]);
							panel.add(pt);
							comMap.put(
									tableTemplate.actionBar.comboBoxDates[i].name,
									pt);
						}
					}
					if (tableTemplate.actionBar.buttons != null) {
						buttons = new UButton[tableTemplate.actionBar.buttons.length];
						for (int i = 0; i < tableTemplate.actionBar.buttons.length; i++) {
							buttons[i] = new UButton();
							if (b)
								buttons[i]
										.setText(tableTemplate.actionBar.buttons[i].enContent);
							else
								buttons[i]
										.setText(tableTemplate.actionBar.buttons[i].content);
							buttons[i]
									.setActionCommand(tableTemplate.actionBar.buttons[i].cmd);
							buttons[i]
									.setComponentName(tableTemplate.actionBar.buttons[i].name);
							buttons[i]
									.addActionListener(tableEventProcessorAdaptor);
							pt = new JPanel();
							pt.setLayout(new FlowLayout());
							pt.add(buttons[i]);
							comMap.put(tableTemplate.actionBar.buttons[i].name,
									pt);
						}
					}
				}
				StringTokenizer sz = new StringTokenizer(
						tableTemplate.actionBar.visibleNames, "/");
				String comName;
				while (sz.hasMoreTokens()) {
					comName = sz.nextToken();
					panel.add(comMap.get(comName));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return panel;
	}

	private class ColumnControlActionProcessor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (initFinished) {
				resetColumnDisplayStatus();
				// getTableInnerSize();
				// resetScrollPanelSize();
			}
		}

	}

	public void initColumnWidthValue() {

		TableColumnModel colModel, colModelHead;
		colModel = table.getColumnModel();
		colModelHead = table.getTableHeader().getColumnModel();
		TableColumn tabColumn;
		savedColumnMaxWidth = new int[tableTemplate.columnNum];
		savedColumnWidth = new int[tableTemplate.columnNum];
		savedColumnMinWidth = new int[tableTemplate.columnNum];
		for (int i = 0; i < tableTemplate.columnNum; i++) {
			tabColumn = colModel.getColumn(i + userStartColNo);
			savedColumnMaxWidth[i] = tabColumn.getMaxWidth();
			savedColumnWidth[i] = tabColumn.getWidth();
			savedColumnMinWidth[i] = tabColumn.getMinWidth();
		}
	}

	public void resetColumnDisplayStatusCheckBox() {
		int i;
		if (columnCheckBox == null)
			return;
		TableColumnModel colModel, colModelHead;
		colModel = table.getColumnModel();
		colModelHead = table.getTableHeader().getColumnModel();
		TableColumn tabColumn, tabColumnHead;
		for (i = 0; i < tableTemplate.columnNum; i++) {
			tabColumn = colModel.getColumn(i + userStartColNo);
			tabColumnHead = colModelHead.getColumn(i + userStartColNo);
			if (columnCheckBox[i].isSelected()) {
				if (tabColumn.getWidth() < 1) {
					tabColumn.setMaxWidth(savedColumnMaxWidth[i]);
					tabColumn.setWidth(savedColumnWidth[i]);
					tabColumn.setPreferredWidth(savedColumnWidth[i]);
					tabColumn.setMinWidth(savedColumnMinWidth[i]);
					tabColumnHead.setMaxWidth(savedColumnMaxWidth[i]);
					tabColumnHead.setWidth(savedColumnWidth[i]);
					tabColumnHead.setMinWidth(savedColumnMinWidth[i]);
				}
			} else {
				if (tabColumn.getWidth() > 1) {
					savedColumnWidth[i] = tabColumn.getWidth();
					savedColumnMaxWidth[i] = tabColumn.getMaxWidth();
					savedColumnMinWidth[i] = tabColumn.getMinWidth();
					tabColumn.setMaxWidth(0);
					tabColumn.setWidth(0);
					tabColumn.setPreferredWidth(0);
					tabColumn.setMinWidth(0);
					tabColumnHead.setMaxWidth(0);
					tabColumnHead.setWidth(0);
					tabColumnHead.setMinWidth(0);
				}
			}
		}

	}

	public void initColumnControlCheckBox() {

		if (tableTemplate == null || tableTemplate.viewControl == null)
			return;
		ColumnControlActionProcessor al = new ColumnControlActionProcessor();
		JPanel p = new JPanel();
		int num = tableTemplate.columnNum;
		p.setLayout(new GridLayout(num, 1, 0, 0));
		// p.setLayout(new FlowLayout(FlowLayout.LEFT));
		columnCheckBox = new UCheckBox[tableTemplate.columnNum];
		Dimension d = new Dimension(tableTemplate.viewControl.width, num
				* (tableTemplate.viewControl.rowHeight));
		p.setPreferredSize(d);
		p.setSize(d);
		int i;
		for (i = 0; i < num; i++) {
			columnCheckBox[i] = new UCheckBox();
			columnCheckBox[i].setHorizontalAlignment(SwingConstants.LEFT);
			columnCheckBox[i].setText(tableTemplate.topStrings[i]);
			columnCheckBox[i].setSelected(true);
			columnCheckBox[i].addActionListener(al);
			columnCheckBox[i].setSize(tableTemplate.viewControl.width,
					tableTemplate.viewControl.rowHeight);
			p.add(columnCheckBox[i]);
		}
		JScrollPane sp = new JScrollPane(p);
		sp.getVerticalScrollBar().setPreferredSize(new Dimension(8, 8));
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.updateUI();
		this.add(sp, BorderLayout.WEST);
	}

	public void setColumnSelectAll(boolean b){
		if (columnCheckObject == null)
			return;
		for (int i = 0; i < tableTemplate.columnNum; i++) {
			columnCheckObject[i].bolValue = b;
		}
		resetColumnDisplayStatus();
		listCheck.doAll(b);
	}
	public void resetColumnDisplayStatus() {
		int i;
		if (columnCheckObject == null)
			return;
		TableColumnModel colModel, colModelHead;
		colModel = table.getColumnModel();
		colModelHead = table.getTableHeader().getColumnModel();
		TableColumn tabColumn, tabColumnHead;
		for (i = 0; i < tableTemplate.columnNum; i++) {
			tabColumn = colModel.getColumn(i + userStartColNo);
			tabColumnHead = colModelHead.getColumn(i + userStartColNo);
			if (columnCheckObject[i].bolValue) {
				if (tabColumn.getWidth() < 1) {
					tabColumn.setMaxWidth(savedColumnMaxWidth[i]);
					tabColumn.setWidth(savedColumnWidth[i]);
					tabColumn.setPreferredWidth(savedColumnWidth[i]);
					tabColumn.setMinWidth(savedColumnMinWidth[i]);
					tabColumnHead.setMaxWidth(savedColumnMaxWidth[i]);
					tabColumnHead.setWidth(savedColumnWidth[i]);
					tabColumnHead.setMinWidth(savedColumnMinWidth[i]);
				}
			} else {
				if (tabColumn.getWidth() > 1) {
					savedColumnWidth[i] = tabColumn.getWidth();
					savedColumnMaxWidth[i] = tabColumn.getMaxWidth();
					savedColumnMinWidth[i] = tabColumn.getMinWidth();
					tabColumn.setMaxWidth(0);
					tabColumn.setWidth(0);
					tabColumn.setPreferredWidth(0);
					tabColumn.setMinWidth(0);
					tabColumnHead.setMaxWidth(0);
					tabColumnHead.setWidth(0);
					tabColumnHead.setMinWidth(0);
				}
			}
		}

	}

	public void initContents() {
		this.setLayout(new BorderLayout());
		initMethods();
		initViewControl();
		initTableTitle();
		initTableBase();
		viewContainer.add(tableScrollPane, BorderLayout.CENTER);
		if (tableTemplate.no != null) {
			userStartColNo = 1;
		} else {
			userStartColNo = 0;
		}
		if (getUParent() != null)
			eventProcessor = new TableEventAdaptor(this, getUParent()
					.getEventAdaptor());
		JPanel p = initActionBar();
		int layout = UConstants.ALIGNMENT_BOTTOM;
		if(tableTemplate.actionBar != null) {
			layout = tableTemplate.actionBar.layout;
		}
		if(layout == UConstants.ALIGNMENT_BOTTOM) {
			p = initPageQueryBar(p);
			if (p != null) {
				if(tableTemplate.actionBar!= null && tableTemplate.actionBar.rowNum > 1) {
					p.setSize(1000, 35*tableTemplate.actionBar.rowNum);
					p.setPreferredSize(new Dimension(1000, 35*tableTemplate.actionBar.rowNum));
				}else if(tableTemplate.actionBar!= null && tableTemplate.actionBar.height >0) {
						p.setSize(1000,tableTemplate.actionBar.height);
						p.setPreferredSize(new Dimension(1000,tableTemplate.actionBar.height));
					}					
					add(p, BorderLayout.SOUTH);
				}
		}else {
			JPanel pp = null;
			if(tableTemplate.pageRowNum > 0) {	
				pageActionPanel = new UPageActionPanel(this, tableTemplate.pageRowNum,new JPanel());
				pp =  pageActionPanel.getJPanel();
			}
			if(pp != null) {
				add(p, BorderLayout.SOUTH);
			}
			if (p != null) {
				if(layout == UConstants.ALIGNMENT_TOP) {
					if(tableTemplate.actionBar!= null && tableTemplate.actionBar.rowNum > 1) {
						p.setSize(1000, 35*tableTemplate.actionBar.rowNum);
						p.setPreferredSize(new Dimension(1000, 35*tableTemplate.actionBar.rowNum));
					}else 	if(tableTemplate.actionBar!= null && tableTemplate.actionBar.height > 0) {
						p.setSize(1000,tableTemplate.actionBar.height);
						p.setPreferredSize(new Dimension(1000,tableTemplate.actionBar.height));
					}
					add(p, BorderLayout.NORTH);
				}else if(layout == UConstants.ALIGNMENT_LEFT){
					if(tableTemplate.actionBar!= null && tableTemplate.actionBar.width > 0) {
						p.setSize(tableTemplate.actionBar.width,1000);
						p.setPreferredSize(new Dimension(tableTemplate.actionBar.width,1000));
					}
					add(p, BorderLayout.WEST);					
				}else if(layout == UConstants.ALIGNMENT_RIGHT){
					if(tableTemplate.actionBar!= null && tableTemplate.actionBar.width > 0) {
						p.setSize(tableTemplate.actionBar.width,1000);
						p.setPreferredSize(new Dimension(tableTemplate.actionBar.width,1000));
					}
					add(p, BorderLayout.EAST);					
				}
			}
		}
		initColumns();
		initStatisticsBar();
		initColumnWidthValue();
		initValidatorMap();
		initFinished = true;
	}

	public boolean isCellCanEditable(int row, int col) {
		if (col - userStartColNo < 0)
			return false;
		Object o = dataList.get(row);
		if (o instanceof UCellLockedDataI) {
			UCellLockedDataI f = (UCellLockedDataI) o;
			if (f.isLocked(col - userStartColNo))
				return false;
		}
		if (tableModel.isCellEditable(row, col)
				|| tableTemplate.columnTemplates[col - userStartColNo].popupable)
			return true;
		else
			return false;
	}

	private class TableEventProcessorAdaptor extends MouseAdapter implements
			TableModelListener, ActionListener, FocusListener, KeyListener {

		public void tableChanged(TableModelEvent e) {
			if (!initFinished || cannotEvent)
				return;
			int type = e.getType();
			int col, rowFirst, rowLast;
			if (type == TableModelEvent.UPDATE) {
				col = e.getColumn();
				rowFirst = e.getFirstRow();
				rowLast = e.getLastRow();
				if (isCellCanEditable(rowFirst, col)) {

					setModifySatatus(rowFirst, rowLast);
					if (!modifyCheckColumn(col, rowFirst, rowLast)
							&& !modifySingleCheck(col, rowFirst))
						modeToData(col, rowFirst, rowLast);
					if (eventProcessor != null)
						eventProcessor.tableChanged(e);
				}
			}
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				if (enablePopupMenu)
					displyPopMenu((Component) e.getSource(), e.getX(), e.getY());
			} else {
				if (e.getSource() == tableHeader) {
					if (tableTemplate.isHeadCanSort) {
						TableColumnModel columnModel = table.getColumnModel();
						int viewColumn = columnModel
								.getColumnIndexAtX(e.getX());
						int column = table
								.convertColumnIndexToModel(viewColumn);
						if (e.getClickCount() == 1 && column >= userStartColNo) {
							int shiftPressed = e.getModifiers()
									& InputEvent.SHIFT_MASK;
							if (shiftPressed != 0) {
								sortByColumn(column, false);
								currentSelectSortColumn = 0 - 1;
								currentSelectSortStatus = 0;
							} else {
								if (column == currentSelectSortColumn) {
									if (currentSelectSortStatus != SQRT_ASCENDING) {
										currentSelectSortStatus = SQRT_ASCENDING;
										sortByColumn(column, true);
									} else {
										currentSelectSortStatus = SQRT_DESCENDING;
										sortByColumn(column, false);
									}
								} else {
									currentSelectSortColumn = column;
									currentSelectSortStatus = SQRT_ASCENDING;
									sortByColumn(column, true);
								}
							}
							// boolean ascending = (shiftPressed == 0);
							// sortByColumn(column, ascending);
							ResetNo();
							pushComponentToForm();
						}
					}
				} else if (e.getSource() != tableScrollPane) {
					if (getCellValueByPanel(e)) {
						return;
					}
					if (e.getClickCount() == 2
							&& e.getButton() == MouseEvent.BUTTON1) {
						if (mouseEventCanSend) {
							eventProcessor.mouseClicked(e);
						}
					}
				} else {
					table.clearSelection();
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (mouseReleaseEventCanSend) {
				eventProcessor.mouseReleased(e);
			}
		}

		public void actionPerformed(ActionEvent e) {
			if (actionEventCanSend) {
				eventProcessor.actionPerformed(e);
			}
		}

		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			tableModelChangeCanUse = true;
			if (focusEventCanSend) {
				eventProcessor.focusGained(e);
			}
		}

		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			tableModelChangeCanUse = false;
			if (focusEventCanSend) {
				eventProcessor.focusLost(e);
			}
		}

		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (keyEventCanSend) {
				eventProcessor.keyPressed(e);
			}
		}

		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public void setModifySatatus(int first, int last) {
		Object o;
		UFormModifyStatusI oi;
		if (first < 0)
			return;
		for (int row = first; row <= last; row++) {
			o = dataList.get(row);
			if (o instanceof UFormModifyStatusI) {
				oi = (UFormModifyStatusI) o;
				oi.setModify(true);
			}
		}
	}

	public boolean modifySingleCheck(int col, int row) {
		if (!(tableTemplate.columnTemplates[col - userStartColNo].singleChoice)) {
			return false;
		}
		int rowNum = table.getRowCount();
		Boolean obj = (Boolean) tableModel.getValueAt(row, col);
		obj = (Boolean) tableModel.getValueAt(row, col);
		if (obj.equals(false)) {
			setValueAt(false, row, col);
		} else {
			setValueAt(true, row, col);
			for (int i = 0; i < rowNum; i++) {
				if (i != row) {
					obj = (Boolean) tableModel.getValueAt(i, col);
					if (obj.equals(true)) {
						tableModel.setValueAt(false, i, col);
						setValueAt(false, i, col);
					}
				}
			}
		}
		return true;
	}

	public boolean modifyCheckColumn(int col, int rowFirst, int rowLast) {
		if (tableTemplate.checkGroup == null)
			return false;
		int i, j;
		int groupNo = 0, index = 0;
		boolean b = false;
		i = 0;
		while (i < tableTemplate.checkGroup.length && !b) {
			for (j = 0; j < tableTemplate.checkGroup[i].length; j++) {
				if (col - userStartColNo == tableTemplate.checkGroup[i][j]) {
					b = true;
					groupNo = i;
					index = j;
					break;
				}
			}
			if (!b)
				i++;
		}
		if (!b)
			return false;
		Boolean obj;
		int row, ttt;
		for (row = rowFirst; row <= rowLast; row++) {
			obj = (Boolean) tableModel.getValueAt(row, col);
			if (obj.equals(false)) {
				setValueAt(false, row, col);
			} else {
				setValueAt(true, row, col);
				for (j = 0; j < tableTemplate.checkGroup[groupNo].length; j++)
					if (j != index
							&& tableTemplate.checkGroup[groupNo][j] < tableTemplate.columnNum) {
						ttt = tableTemplate.checkGroup[groupNo][j]
								+ userStartColNo;
						tableModel.setValueAt(false, row, ttt);
						setValueAt(false, row, ttt);
					}
			}
		}
		return true;
	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		// eventProcessor = new TableEventAdaptor(this, getUParent()
		// .getEventAdaptor());

		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_MOUSE)
					|| events[i].name.equals(EventConstants.EVENT_MOUSERELEASE)) {
				table.addMouseListener(eventProcessor);
				if (events[i].name.equals(EventConstants.EVENT_MOUSE)) {
					mouseEventCanSend = true;
				} else {
					mouseReleaseEventCanSend = true;
				}
			} else if (events[i].name.equals(EventConstants.EVENT_TABLEMODE)) {
				tableModelChangeCanSend = true;
			} else if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventCanSend = true;
			} else if (events[i].name.equals(EventConstants.EVENT_KEY)) {
				keyEventCanSend = true;
			} else if (events[i].name.equals(EventConstants.EVENT_FOCUS)) {
				focusEventCanSend = true;
			}
		}
	}

	public void setBorder(int width, UColor color) {
		if (width == 0)
			setBorder((Border) null);
		else
			setBorder(BorderFactory.createLineBorder(color.color, width));
	}

	public void setBorder(UBorder border) {
		if (border.obj == null || border.obj instanceof Border)
			setBorder((Border) border.obj);
	}

	public void initMethods() {
		if (tableTemplate == null)
			return;
		try {
			if (tableTemplate.itemFormClassName != null) {
				formClass = Class.forName(tableTemplate.itemFormClassName);
				getColumnGetMethods(formClass);
				getColumnSetMethods(formClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setColNoMap();
	}

	public Object newFormObject() {

		if (tableTemplate == null)
			return null;
		try {
			return Class.forName(tableTemplate.itemFormClassName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setColNoMap() {
		int j;
		String attrName;

		for (j = 0; j < tableTemplate.columnNum; j++) {
			attrName = tableTemplate.columnTemplates[j].itemFormMember;

			if (attrName == null)
				continue;
			colNoMap.put(attrName, j);
		}
	}

	public void getColumnSetMethods(Class c) {
		String attrName, methodName;
		Method[] methods = c.getMethods();
		int j, k;
		try {
			for (j = 0; j < tableTemplate.columnNum; j++) {
				attrName = tableTemplate.columnTemplates[j].itemFormMember;
				if (attrName != null && !attrName.equals("")) {
					methodName = "set" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					for (k = 0; k < methods.length; k++) {
						if (methods[k].getName().equals(methodName)) {
							setMethodMap
									.put(tableTemplate.columnTemplates[j].itemFormMember,
											methods[k]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getColumnGetMethods(Class c) {
		String attrName, methodName;
		Method method;
		Method[] methods = c.getMethods();
		int j, k;
		try {
			for (j = 0; j < tableTemplate.columnNum; j++) {
				attrName = tableTemplate.columnTemplates[j].itemFormMember;
				if (attrName != null && !attrName.equals("")) {
					methodName = "get" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					for (k = 0; k < methods.length; k++) {
						if (methods[k].getName().equals(methodName)) {
							getMethodMap
									.put(tableTemplate.columnTemplates[j].itemFormMember,
											methods[k]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updataAll() {
	}

	public void ResetNo() {
		if (userStartColNo == 1) {
			for (int i = 0; i < this.getRowCount(); i++) {
				tableModel.setValueAt(i + 1, i, 0);
			}
		}
	}

	public void updateAll() {

		for (int i = 0; i < tableTemplate.columnNum; i++) {
			updateColumn(i);
		}
	}

	public void updateRow(int row) {
		if (tableTemplate == null)
			return;
		for (int i = userStartColNo; i < tableTemplate.columnNum; i++) {
			this.updateCell(row, i);
		}
	}

	public void updateColumn(String name) {
		Integer o = (Integer) colNoMap.get(name);
		if (o == null)
			return;
		int col = o;
		updateColumn(col);
	}

	public void updateColumn(int col) {
		for (int i = 0; i < this.getRowCount(); i++) {
			updateCell(i, col);
		}
	}

	public void updateCell(int row, String name) {
		Integer o = (Integer) colNoMap.get(name);
		if (o == null)
			return;
		int col = o;
		updateCell(row, col + userStartColNo);
	}

	public void updateCell(int row, int col) {
		tableModel.setValueAt(getCellObjectFromForm(row, col), row, col);
	}

	public void processCurrentEditorValue() {
		Component com = table.getEditorComponent();
		if (com == null)
			return;
		String tt = "";
		if (com instanceof JTextField) {
			JTextField jf = (JTextField) com;
			tt = jf.getText();
			int column = table.getEditingColumn();
			int row = table.getEditingRow();
			if (column < 0 || row < 0)
				return;
			setModifySatatus(row, row);
			if (!modifyCheckColumn(column, row, row)) {
				if (tt == null || tt.equals(""))
					setValueAt(null, row, column);
				else {
					Method m = (Method) (getMethodMap
							.get(tableTemplate.columnTemplates[column
									- userStartColNo].itemFormMember));
					try {
						Class c = m.getReturnType();
						if (c == Integer.class) {
							setValueAt(new Integer(tt), row, column);
						} else if (c == Double.class) {
							setValueAt(new Double(tt), row, column);
						} else if (c == String.class) {
							setValueAt(tt, row, column);
						}
					} catch (Exception e) {
						setValueAt(null, row, column);
					}
				}
			}
		}
	}

	public Object getData() {
		processCurrentEditorValue();
		int[] index = getDataChangedRows();
		int i, col, row;
		Object obj = null;
		for (i = 0; i < index.length; i++) {
			row = index[i];
			for (col = 0; col < tableTemplate.columnNum; col++) {
				obj = tableModel.getValueAt(row, col + userStartColNo);
				setValueAt(obj, row, col + userStartColNo);
			}
		}
		// return dataList;
		Object o[] = dataList.toArray();
		return o;
	}

	public Object[] getRowDataArrays( int row ) {
		Object[] obj = new Object [tableTemplate.columnNum];		
		for (int col = 0; col < tableTemplate.columnNum; col++) {
				obj[col] = tableModel.getValueAt(row, col + userStartColNo);
		}
		return obj;
	}
	
	public Object getData(int row) {
		if (dataList == null || dataList.size() < 0 || row < 0
				|| row >= dataList.size())
			return null;
		processCurrentEditorValue();
		int i, col;
		Object obj = null;
		for (col = 0; col < tableTemplate.columnNum; col++) {
			obj = tableModel.getValueAt(row, col + userStartColNo);
			setValueAt(obj, row, col + userStartColNo);
		}
		return dataList.get(row);
	}

	public boolean isCellChanged(int col, int rowFirst, int rowLast) {
		Object os, od;
		int row;
		for (row = rowFirst; row <= rowLast; row++) {
			os = tableModel.getValueAt(row, col);
			od = getCellObjectFromForm(row, col);
			if (!os.equals(od))
				return true;
		}
		return false;
	}

	public Object getCellObjectFromForm(int row, int col) {
		Object o = null;
		try {
			if (row >= 0 && row < dataList.size() && (col >= userStartColNo))
				if (tableTemplate.columnTemplates[col - userStartColNo].itemIndex == null)
					o = ((Method) getMethodMap
							.get(tableTemplate.columnTemplates[col
									- userStartColNo].itemFormMember))
							.invoke(dataList.get(row));
				else
					o = ((Method) getMethodMap
							.get(tableTemplate.columnTemplates[col
									- userStartColNo].itemFormMember))
							.invoke(dataList.get(row),
									tableTemplate.columnTemplates[col
											- userStartColNo].itemIndex);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return o;
	}

	public Object changeValueToString(String dictionary, Object value) {
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if (util == null || value == null)
			return value;
		if (value instanceof String)
			return util.getDataNameByCode(dictionary, value.toString());
		else
			return value;
	}

	public Object changeValueToString(ListOptionInfo[] addedDatas, Object value) {
		if (addedDatas == null || value == null || !(value instanceof String))
			return value;
		ListOptionInfo info;
		for (int i = 0; i < addedDatas.length; i++) {
			info = (ListOptionInfo) addedDatas[i];
			if (info.getValue().equals(value))
				return info.getLabel();
		}
		return value;
	}

	public Object changeValueToString(FilterI filter, Object value) {
		if (filter == null)
			return value;
		Object o = filter.getAddedData();
		if (o == null || value == null || !(value instanceof String))
			return value;
		Object[] items;
		if (o instanceof List) {
			items = ((List) o).toArray();
		} else {
			items = (Object[]) o;
		}
		if (items == null)
			return value;
		ListOptionInfo info;
		for (int i = 0; i < items.length; i++) {
			info = (ListOptionInfo) items[i];
			if (info.getValue().equals(value))
				return info.getLabel();
		}
		return value;
	}

	private int getStartColNo() {
		if(this.pageActionPanel != null)
			return pageActionPanel.getStart();
		else
			return 0;
	}

	public Object[] formToRowArray(int i, Object f) {
		int n = tableTemplate.columnNum;
		int no = 0;
		int j;
		LabeValueTransI t;
		Object[] b;
		b = new Object[userStartColNo + n];
		if (userStartColNo > 0)
			b[0] = (1 + i + getStartColNo());
		if (f instanceof List) {
			List dList = (List) f;
			for (j = 0; j < n; j++) {
				if (j < dList.size())
					b[j + userStartColNo] = dList.get(j);
			}
		} else {
			try {
				for (j = 0; j < n; j++) {
					Method mt;
					if (tableTemplate.columnTemplates[j].itemIndex == null) {
						mt = ((Method) getMethodMap
								.get(tableTemplate.columnTemplates[j].itemFormMember));
						if (mt != null) {
							if(tableTemplate.columnTemplates[j].itemPar != null) {
								b[userStartColNo + j] = mt.invoke(f,tableTemplate.columnTemplates[j].itemPar);
							}else {
								b[userStartColNo + j] = mt.invoke(f);								
							}
						} else {
							if (f instanceof UFormI) {
								b[userStartColNo + j] = ((UFormI) f)
										.getAttributeObject(tableTemplate.columnTemplates[j].itemFormMember);
							}
						}
					} else {
						mt = (Method) getMethodMap
								.get(tableTemplate.columnTemplates[j].itemFormMember);
						if (mt != null) {
							b[userStartColNo + j] = mt.invoke(f,
									tableTemplate.columnTemplates[j].itemIndex);
						} else {
							if (f instanceof UFormI) {
								b[userStartColNo + j] = ((UFormI) f)
										.getAttributeObject(
												tableTemplate.columnTemplates[j].itemFormMember,
												tableTemplate.columnTemplates[j].itemIndex);
							}
						}
					}
					if (tableTemplate.columnTemplates[j].dictionary != null
							&& !(tableTemplate.columnTemplates[j].popupable)) {
						b[userStartColNo + j] = changeValueToString(
								tableTemplate.columnTemplates[j].dictionary,
								b[userStartColNo + j]);
					} else if (tableTemplate.columnTemplates[j].filter != null) {
						b[userStartColNo + j] = changeValueToString(
								(FilterI) filterMap
										.get(tableTemplate.columnTemplates[j].name),
								b[userStartColNo + j]);
					} else if (tableTemplate.columnTemplates[j].addedDatas != null) {
						b[userStartColNo + j] = changeValueToString(
								tableTemplate.columnTemplates[j].addedDatas,
								b[userStartColNo + j]);
					}
					if (b[userStartColNo + j] != null
							&& b[userStartColNo + j] instanceof UTextFieldDataFormI) {
						UTextFieldDataFormI ot = (UTextFieldDataFormI) b[userStartColNo
								+ j];
						b[userStartColNo + j] = ot.getDataObjectText();
					}

					Object ot = comMap
							.get(tableTemplate.columnTemplates[j].name);
					if (ot != null) {
						if (ot instanceof LabeValueTransI) {
							t = (LabeValueTransI) ot;
							b[userStartColNo + j] = t.getLabel(b[userStartColNo
									+ j]);
						}
					}
					b[userStartColNo + j] = this.formatObject(j,
							b[userStartColNo + j]);
					if (b[userStartColNo + j] == null && mt != null) {
						if (mt.getReturnType() == Boolean.class)
							b[userStartColNo + j] = false;
						else
							b[userStartColNo + j] = "";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	public void setData(Object obj) {
		// if(obj == null)
		// return;
		if (obj instanceof List) {
			dataList = (List) obj;
		} else {
			dataList = new ArrayList();
			Object a[];
			int i;
			if (obj != null) {
				a = (Object[]) obj;
				for (i = 0; i < a.length; i++)
					dataList.add(a[i]);
			}
		}
		dataToModel();
		sortByDefault();
		getTableInnerSize();
		resetScrollPanelSize();
		setStatisticsBarData();
		setDynamicColumnVisible();
	}

	public void setRowData(int i, Object obj) {
		// initFinished = false;
		dataList.set(i, obj);
		rowDataToModel(i);
		// initFinished = true;
	}

	public Object formatObject(int i, Object o) {
		return o;
	}

	public Object[] getTotalArrays() {
		Object t[] = null;
		Object f = this.newFormObject();
		if (f != null) {
			t = formToRowArray(-1, f);
			if (this.userStartColNo > 0) {
				t[0] = "合计";
			}
		}
		return t;
	}

	public void mergeDataToTotal(Object t[], Object b[]) {
		for (int i = userStartColNo; i < t.length; i++) {
			if (b[i] == null)
				continue;
			if (t[i] == null || t[i].equals(""))
				t[i] = b[i];
			else if (b[i] instanceof Integer) {
				int x1 = (Integer) b[i];
				int x2 = (Integer) t[i];
				t[i] = x2 + x1;
			} else if (b[i] instanceof Double) {
				double x1 = (Double) b[i];
				double x2 = (Double) t[i];
				t[i] = x2 + x1;
			} else if (b[i] instanceof Float) {
				float x1 = (Float) b[i];
				float x2 = (Float) t[i];
				t[i] = x2 + x1;
			} else {
				t[i] = "";
			}
		}
	}

	public void dataToModel() {
		if (dataList == null)
			return;
		int i;
		Object b[];
		Object t[] = null;
		clearTableModel();
		if (dataList.size() > 0 && tableTemplate.withTotal) {
			t = getTotalArrays();
		}
		for (i = 0; i < dataList.size(); i++) {
			b = formToRowArray(i, dataList.get(i));
			tableModel.insertRow(i, b);
			if (t != null)
				mergeDataToTotal(t, b);
		}
		if (t != null) {
			tableModel.insertRow(dataList.size(), t);
		}
	}

	public void rowDataToModel(int i) {
		int j;
		Object b[];
		b = formToRowArray(i, dataList.get(i));
		for (j = 0; j < b.length; j++) {
			tableModel.setValueAt(b[j], i, j);
		}
	}

	public void modeToData(int col, int rowFirst, int rowLast) {

		for (int row = rowFirst; row <= rowLast; row++)
			setValueToModel(tableModel.getValueAt(row, col), row, col);
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		popupMenu = new UPopupMenu();
		int i;
		int n = menu.getItemCount();
		while (menu.getItemCount() > 0) {
			popupMenu.add(menu.getItem(0));
		}
	}

	public void resetColumnWidth() {
		Set set = map.keySet();
		Iterator it = set.iterator();
		Integer pColumn = null;
		Integer pWidth = null;
		while (it.hasNext()) {
			pColumn = (Integer) it.next();
			pWidth = (Integer) map.get(pColumn);
			setColumnWidth(pColumn, pWidth);
		}
	}

	public UPopupMenu getUPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(UPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void displyPopMenu(Component com, int x, int y) {
		if (popupMenu != null && popupMenu.getSubElements().length >= 1)
			popupMenu.show(com, x, y);
	}

	public Comparator<Object> getColumnComparator(int column) {
		if (tableTemplate == null || tableTemplate.columnTemplates == null
				|| column < 0 || column >= tableTemplate.columnTemplates.length
				|| tableTemplate.columnTemplates[column].comparator == null)
			return null;
		String className = UFactory.getModelSession().getDefaultClassByType(
				tableTemplate.columnTemplates[column].comparator);
		try {
			Comparator<Object> comparator = (Comparator<Object>) Class.forName(
					className).newInstance();
			return comparator;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sortByColumn(int column, boolean asceding) {
		List<SortColumnAttribute> list = new ArrayList<SortColumnAttribute>();
		list.add(new SortColumnAttribute(column, asceding,
				getColumnComparator(column)));
		sortList = list;
		sortByColumnList();
	}

	public void sortByDefault() {
		if (sortList == null) {
			if (tableTemplate != null)
				sortList = tableTemplate.sortList;
			sortByColumnList();
		}
	}

	public void sortByColumnList() {
		try {
			if (dataList != null && sortList != null) {
				Comparator<Object> comparator = new ObjectArraysComparator(
						sortList);
				Object dataArray[] = dataList.toArray();
				Arrays.sort(dataArray, comparator);
				Object dataValue;
				if (dataArray != null) {
					for (int i = 0; i < dataArray.length; i++) {
						dataList.set(i, dataArray[i]);
					}
					dataToModel();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍦ㄦ寚瀹氫綅缃彃鍏ヤ竴琛宱bject[]
	 * 
	 * @param rowData
	 * @param nRowIndex
	 */
	public void insertTabRow(Object[] rowData, int nRowIndex) {
		if (dataList == null) {
			dataList = new ArrayList();
		}
		if (nRowIndex < 0 || nRowIndex > dataList.size()) {
			return;
		}

		tableModel.insertRow(nRowIndex, rowData);
		dataList.add(nRowIndex, rowData);
		if (userStartColNo > 0) {
			int i;
			for (i = nRowIndex; i < this.getRowCount(); i++) {
				tableModel.setValueAt(i + 1, i, 0);
			}
		}
	}

	/**
	 * 
	 * @param form
	 * @param nRowIndex
	 */
	public void insertTabRow(UFormI form, int nRowIndex) {
		if (form == null)
			return;
		if (dataList == null) {
			dataList = new ArrayList();
		}
		if (nRowIndex < 0 || nRowIndex > dataList.size()) {
			return;
		}
		if (form instanceof UFormModifyStatusI) {
			UFormModifyStatusI f = (UFormModifyStatusI) form;
			f.setModify(true);
		}
		tableModel.insertRow(nRowIndex, form.toArray());
		dataList.add(nRowIndex, form);
		if (userStartColNo > 0) {
			int i;
			for (i = nRowIndex; i < this.getRowCount(); i++) {
				tableModel.setValueAt(i + 1, i, 0);
			}
		}
	}

	public int[] getSelectRowsIndex() {
		int selectRowsIndex[] = table.getSelectedRows();
		return selectRowsIndex;
	}

	public List getSelectRowsList() {
		List selectRowsList = new ArrayList();
		int selectRowsIndex[] = this.getSelectRowsIndex();
		int i;
		for (i = 0; i < selectRowsIndex.length; i++) {
			selectRowsList.add(dataList.get(selectRowsIndex[i]));
		}
		return selectRowsList;
	}

	public Object getSelectRowsRowObject() {
		List selectRowsList = new ArrayList();
		int selectRowsIndex[] = this.getSelectRowsIndex();
		int i;
		if (selectRowsIndex == null || selectRowsIndex.length == 0)
			return null;
		else
			return dataList.get(selectRowsIndex[0]);
	}
	
	public Object getSelectRowColumnObject() {
		int[] pos = getSelectRowColumn();
		int row = pos[0];
		int col = pos[1];
		if (col < 0 || col >= tableTemplate.columnNum || row < 0
				|| row >= dataList.size()) {
			return null;
		}
		return this.getCellObjectFromForm(row, col+userStartColNo);
	}

	
	public void setValueAt(Object o, int row, int col) {
		Method m, mg;
		// Object o;
		// if (os instanceof ListOptionInfo)
		// o = ((ListOptionInfo) os).getValue();
		// else
		// o = os;
		if (row < 0 || row >= dataList.size() || col < userStartColNo)
			return;
		if (!isColumnModelToData[col - userStartColNo])
			return;
//		System.out.println("("+ row + "," + col +")");
		setValueAtDo(o, row, col);
	}

	public void setValueToModel(Object o, int row, int col) {
		if (row < 0 || row >= dataList.size() || col < userStartColNo)
			return;
//		System.out.println(row+"-" + col);
		setValueAtDo(o, row, col);
	}

	public void setValueAtDo(Object o, int row, int col) {
		Method m;
		m = (Method) (setMethodMap.get(tableTemplate.columnTemplates[col
				- userStartColNo].itemFormMember));
		Class[] t = null;
		if (m != null)
			t = m.getParameterTypes();
		Object os;
		if (t != null && t.length != 0 && t[0] == String.class
				&& o instanceof ListOptionInfo)
			os = ((ListOptionInfo) o).getValue();
		else
			os = o;
		if (t[0] != String.class && o != null
				&& (o.toString().equals("") || o instanceof String))
			os = null;
		try {
			if (tableTemplate.columnTemplates[col - userStartColNo].itemIndex == null) {
				m.invoke(dataList.get(row), os);
			} else {
				m.invoke(dataList.get(row), tableTemplate.columnTemplates[col
						- userStartColNo].itemIndex, os);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setValueofDataList(Object o, int row, int col) {
		Method m, mg;
		if (row < 0 || row >= dataList.size() || col < userStartColNo)
			return;
		m = (Method) (setMethodMap.get(tableTemplate.columnTemplates[col
				- userStartColNo].itemFormMember));
		try {
			if (tableTemplate.columnTemplates[col - userStartColNo].itemIndex == null) {
				m.invoke(dataList.get(row), o);
			} else {
				m.invoke(dataList.get(row), tableTemplate.columnTemplates[col
						- userStartColNo].itemIndex, o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鍒犻櫎涓�琛�
	 * 
	 * @param nRowIndex
	 */
	public void deleteTabRow(int nRowIndex) {
		if (dataList == null) {
			return;
		}
		if (nRowIndex < 0 || nRowIndex >= dataList.size()) {
			return;
		}
		tableModel.removeRow(nRowIndex);
		dataList.remove(nRowIndex);
		ResetNo();
	}

	// /**
	// * 鍒犻櫎涓�琛�
	// *
	// * @param nRowIndex
	// */
	// public void removeTabRow(int nRowIndex) {
	// tableModel.removeRow(nRowIndex);
	// }

	public int getRowLength() {
		int rowLength = -1;
		rowLength = this.tableModel.getRowCount();
		return rowLength;
	}

	public void setColumnEditable(int column, boolean isCellEditable) {
		tableModel.setColumEditable(isCellEditable, column);
	}

	public void setRowEditable(int row, boolean isCellEditable) {
		tableModel.setRowEditable(isCellEditable, row);
	}

	public void clearRowEditableMark() {
		tableModel.clearRowEditableMark();
	}

	public int rowAtPoint(int x, int y) {
		return table.rowAtPoint(new Point(x, y));
	}

	public int columnAtPoint(int x, int y) {
		return table.columnAtPoint(new Point(x, y));
	}

	/**
	 * 璁剧疆鍒跺畾鍒楃殑瀹�
	 * 
	 * @param pColumn
	 * @param pWidth
	 */
	public void setColumnWidth(int pColumn, int pWidth) {
		// 寰楀埌鍒楃殑鏍峰紡
		TableColumnModel colModel = table.getColumnModel();
		// 寰楀埌pColumn鍒楃劧鍚庤缃畠鐨勬渶浣冲搴�
		int c = table.getColumnCount();
		if (pColumn < c)
			colModel.getColumn(pColumn).setPreferredWidth(pWidth);
	}

	public int getColumnWidth(int pColumn) {
		TableColumnModel colModel = table.getColumnModel();
		return colModel.getColumn(pColumn).getPreferredWidth();
	}

	public int getRowHeight(int row) {
		return table.getRowHeight(row);
	}

	public void setMaxWidth(int pColumn, int pWidth) {
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(pColumn).setMaxWidth(pWidth);
	}

	public void setMinWidth(int pColumn, int pWidth) {
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(pColumn).setMinWidth(pWidth);
	}

	public void hideColumn(int pColumn) {
		TableColumnModel colModel = table.getColumnModel();
		TableColumn tabColumn = colModel.getColumn(pColumn);
		tabColumn.setMaxWidth(0);
		tabColumn.setWidth(0);
		tabColumn.setPreferredWidth(0);
		tabColumn.setMinWidth(0);
		colModel = table.getTableHeader().getColumnModel();
		tabColumn = colModel.getColumn(pColumn);
		tabColumn.setMaxWidth(0);
		tabColumn.setMinWidth(0);
	}

	public void showColumn(int pColumn, int width) {
		TableColumnModel colModel = table.getColumnModel();
		TableColumn tabColumn = colModel.getColumn(pColumn);
		tabColumn.setMaxWidth(width);
		tabColumn.setWidth(width);
		tabColumn.setPreferredWidth(width);
		tabColumn.setMinWidth(width);
		colModel = table.getTableHeader().getColumnModel();
		tabColumn = colModel.getColumn(pColumn);
		tabColumn.setMaxWidth(width);
		tabColumn.setWidth(width);
		tabColumn.setMinWidth(width);
	}

	/**
	 * 璁惧畾鎸囧畾鍒楃殑娓叉煋鍣�
	 * 
	 * @param col
	 * @param renderer
	 */
	public void setColumnColorRenderer(int col, TableCellRenderer renderer) {
		TableColumnModel colModel = table.getColumnModel();
		TableColumn column = colModel.getColumn(col);
		column.setCellRenderer(renderer);
	}

	public void setCellEditor(int col, TableCellEditor editor) {
		TableColumnModel colModel = table.getColumnModel();
		TableColumn column = colModel.getColumn(col);
		column.setCellEditor(editor);
	}

	public void setCellEditor(int col, JComboBox cmb) {
		this.setCellEditor(col, new DefaultCellEditor(cmb));
	}

	public void setCellEditor(int col, JCheckBox ckb) {
		this.setCellEditor(col, new DefaultCellEditor(ckb));
	}

	public void setCellEditor(int col, JTextField ckb) {
		this.setCellEditor(col, new DefaultCellEditor(ckb));

	}

	public void setCellSelectionEnabled(Boolean b) {
		table.setCellSelectionEnabled(b);
	}

	public void setRowHeight(Integer rowHeight) {
		table.setRowHeight(rowHeight);
	}

	public void setRowSelectionAllowed(Boolean b) {
		table.setRowSelectionAllowed(b);
		if (elementTemplate != null && !elementTemplate.multiple)
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// table.setRowSelectionInterval(2, 3);
	}

	public int getColumnCount() {
		return table.getColumnCount();
	}

	/**
	 * 璁惧畾閫変腑鐨勫垪
	 * 
	 * @param row
	 */
	public void setSelectedRow(int row) {
		table.setRowSelectionInterval(row, row);
		table.setColumnSelectionInterval(0, table.getColumnCount() - 1);

	}

	public Object[] getRowData(int nRowIndex) {
		Object obj[] = null;
		if (dataList == null) {
			return null;
		}
		if (nRowIndex < 0 || nRowIndex >= dataList.size()) {
			return null;
		}
		obj = (Object[]) dataList.get(nRowIndex);
		return obj;
	}

	/**
	 * 娓呮鏁翠釜琛ㄦ牸
	 * 
	 */
	public void clearTableModel() {
		int i;
		i = getRowCount();
		for (; i > 0; i--) {
			tableModel.removeRow(0);
		}
	}

	public void clearTableData() {
		clearTableModel();
		dataList = new ArrayList();
	}

	/**
	 * 鑾峰彇Table琛屾暟
	 * 
	 * @return
	 */
	public int getRowCount() {
		return tableModel.getRowCount();
	}

	class ObjectArraysComparator implements Comparator<Object> {
		private List<SortColumnAttribute> sortList;

		public ObjectArraysComparator(int column, boolean ascending,
				Comparator comparator) {
			sortList = new ArrayList<SortColumnAttribute>();
			sortList.add(new SortColumnAttribute(column, ascending, comparator));
		}

		public ObjectArraysComparator(List<SortColumnAttribute> list) {
			sortList = list;
		}

		public int compare(Object row1, Object row2) {
			// TODO Auto-generated method stub
			try {
				return compareRows(row1, row2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

		public int compareRows(Object row1, Object row2) {
			if (sortList == null)
				return 0;
			Class type;
			try {
				Object object1 = null, object2 = null;
				int column;
				Comparator comparator;
				int i;
				SortColumnAttribute s;
				boolean ascending;
				Method m;
				UFormI f;
				String at;
				for (i = 0; i < sortList.size(); i++) {
					s = sortList.get(i);
					column = s.getColumn();
					ascending = s.isAscending();
					comparator = s.getComparator();
					at = tableTemplate.columnTemplates[column - userStartColNo].itemFormMember;
					type = tableModel.getColumnClass(column);
					m = (Method) (getMethodMap.get(at));
					if (m != null) {
						object1 = m.invoke(row1);
						object2 = m.invoke(row2);
					} else {
						if (row1 instanceof UFormI) {
							f = (UFormI) row1;
							object1 = f.getAttributeObject(at);
						}
						if (row2 instanceof UFormI) {
							f = (UFormI) row2;
							object2 = f.getAttributeObject(at);
						}
					}
					if (object1 == null && object2 == null) {
						continue;
					} else if (object1 == null) { // Define null less than
													// everything.
						if (ascending)
							return -1;
						else
							return 1;
					} else if (object2 == null) {
						if (ascending)
							return 1;
						else
							return -1;
					}
					if (comparator != null) {
						int ret = comparator.compare(object1, object2);
						if (ret == 0)
							continue;
						if (ascending)
							return ret;
						else
							return -ret;
					}
					if (object1 instanceof java.lang.Number) {
						Number n1 = (Number) object1;
						double d1 = n1.doubleValue();
						Number n2 = (Number) object2;
						double d2 = n2.doubleValue();

						if (d1 < d2) {
							if (ascending)
								return -1;
							else
								return 1;
						} else if (d1 > d2) {
							if (ascending)
								return 1;
							else
								return -1;
						} else {
							continue;
						}
					} else if (object1 instanceof java.util.Date) {
						Date d1 = (Date) object1;
						long n1 = d1.getTime();
						Date d2 = (Date) object2;
						long n2 = d2.getTime();

						if (n1 < n2) {
							if (ascending)
								return -1;
							else
								return 1;
						} else if (n1 > n2) {
							if (ascending)
								return 1;
							else
								return -1;
						} else {
							continue;
						}
					} else if (object1 instanceof String) {
						String s1 = (String) object1;
						String s2 = (String) object2;
						int result = sort(s1, s2);
						if (result < 0) {
							if (ascending)
								return -1;
							else
								return 1;
						} else if (result > 0) {
							if (ascending)
								return 1;
							else
								return -1;
						} else {
							continue;
						}
					} else if (object1 instanceof Boolean) {
						Boolean bool1 = (Boolean) object1;
						boolean b1 = bool1.booleanValue();
						Boolean bool2 = (Boolean) object2;
						boolean b2 = bool2.booleanValue();

						if (b1 == b2) {
							continue;
						} else if (b1) { // Define false < true
							if (ascending)
								return 1;
							else
								return -1;
						} else {
							if (ascending)
								return -1;
							else
								return 1;
						}
					} else {
						Object v1 = object1;
						String s1 = v1.toString();
						Object v2 = object2;
						String s2 = v2.toString();
						int result = s1.compareTo(s2);

						if (result < 0) {
							if (ascending)
								return -1;
							else
								return 1;
						} else if (result > 0) {
							if (ascending)
								return 1;
							else
								return -1;
						} else {
							continue;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

		/** 瀵规眽瀛楄繘琛屾帓搴� */
		public int sort(String s1, String s2) {
			return Collator.getInstance(Locale.CHINESE).compare(s1, s2);
		}

	}

	public void addTabColumn(int modelIndex, int width, String columnName,
			String enColumnName, Class columnClass) {
		if (UimsFactory.getClientMainI().isEnglishVersion() && enColumnName != null) {
				tableModel.setColumnName(modelIndex, enColumnName);
				tableModel.addColumn(enColumnName);
		} else {
			tableModel.setColumnName(modelIndex, columnName);
			tableModel.addColumn(columnName);
		}
		tableModel.setColumnClass(new Integer(modelIndex), columnClass);
		map.put(new Integer(modelIndex), new Integer(width));
		resetColumnWidth();
	}

	public void initComponents() {
		// TODO Auto-generated method stub

	}

	public void initHandlerObject() {
		// TODO Auto-generated method stub
		int i;
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if (elementTemplate != null)
			return new URect(elementTemplate.x, elementTemplate.y,
					elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public String getComponentName() {
		// TODO Auto-generated method stub
		return componentName;
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return tableTemplate;
	}

	public UTableTemplate getTableTemplate() {
		// TODO Auto-generated method stub
		return tableTemplate;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub
	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		componentName = name;
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

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.tableTemplate = (UTableTemplate) template;
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
		// parent.addMouseListener(p);

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void copySecletionToClipboard() {
		StringBuffer sbf = new StringBuffer();
		int[] rowsSelected;
		int[] colsSelected;
		int numcols;
		int numrows;
		rowsSelected = table.getSelectedRows();
		colsSelected = table.getSelectedColumns();
		numrows = rowsSelected.length;
		numcols = colsSelected.length;
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

	public boolean canExlOut(int col) {
		if (tableTemplate == null)
			return true;
		if (userStartColNo >= 1 && col == 0)
			return true;
		if (!(tableTemplate.columnTemplates[col - userStartColNo].exlout) || columnCheckBox != null && !(columnCheckBox[col - userStartColNo].isSelected()) ||
					columnCheckObject != null && !(columnCheckObject[col - userStartColNo].bolValue))
			return false;
		else
			return true;
	}


	public List getTableAllData() {
		if(pageActionPanel == null)
			return null;
		TablePageDataQueryHandlerI handler = (TablePageDataQueryHandlerI) getUParent()
				.getHandler();
		if (handler == null)
			return null;
		return handler.getDataListByIdList(pageActionPanel.getIdList());

	}

	public int getTableRowCount(List dataList) {
		if (dataList != null)
			return dataList.size();
		else
			return table.getRowCount();
	}

	public String getTableCellValue(List allDataList, int i, int j) {
		String str = "";
		if (allDataList == null) {
			Object cell;
			cell = tableModel.getValueAt(i, j);
			if (cell != null)
				str = cell.toString();
		} else {
			if (userStartColNo > 0 && j == 0)
				return "" + (i + 1);
			else {
				Method m;
				Object os;
				Object o = allDataList.get(i);
				try {
					m = (Method) (getMethodMap
							.get(tableTemplate.columnTemplates[j
									- userStartColNo].itemFormMember));
					if (m != null) {
						if (tableTemplate.columnTemplates[j - userStartColNo].itemIndex == null) {
							os = m.invoke(o);
						} else {
							os = m.invoke(o, tableTemplate.columnTemplates[j
									- userStartColNo].itemIndex);
						}
					} else {
						UFormI f = (UFormI) o;
						os = f.getAttributeObject(tableTemplate.columnTemplates[j
								- userStartColNo].itemFormMember);
					}

				} catch (Exception e) {
					e.printStackTrace();
					return "";
				}
				if (os != null) {
					if (os instanceof ListOptionInfo) {
						os = ((ListOptionInfo) os).getLabel();
					} else {
						if (tableTemplate.columnTemplates[j - userStartColNo].dictionary != null) {
							os = changeValueToString(
									tableTemplate.columnTemplates[j
											- userStartColNo].dictionary, os);
						}
					}
					if (os != null)
						str = os.toString();
					else
						str = "";
				}
			}
		}
		return str;
	}

	public boolean saveDataToExcel(String sFilePath) {
		List allDataList = getTableAllData();
		return saveDataToExcel(sFilePath, allDataList);
	}
	public int [] getcolumnIndexs() {
		int i,count = 0;
	int indexs[] = new int[table.getColumnCount()];
	for (i = 0; i < table.getColumnCount(); i++) {
		if (!canExlOut(i)) {
			indexs[i] = -1;
		}else {
			indexs[i] = count;
			count++;
		}
	}
	return indexs;
	}
	public boolean saveDataToExcel(String sFilePath, List allDataList) {
		int rowCount = getTableRowCount(allDataList);

		boolean bRet = true;
		if (rowCount <= 0) {
			JOptionPane.showMessageDialog(null, "空表不能转出");
			// CommMethod.MessageBoxError("空表不能转出");
			return bRet;
		}
		// 保存EXCEL表改用循环处理，防止大数据的内存溢出 修改日期 2004-12-06 刘海波
		int nCurrentRow = 0;
		int nRowStep = 5000;
		int i;
		int indexs[] = getcolumnIndexs();
		while (nCurrentRow < rowCount) {
			try {
				WritableWorkbook book;
				WritableSheet sheet1;
				int  j;
				if (nCurrentRow == 0) {
					// //第一次打开文件要创建文件
					book = Workbook.createWorkbook(new File(sFilePath));
					// 生成名为 第一页 的工作表，参数0表示这是第一页
					sheet1 = book.createSheet("第一页", 0);
					// 写入表头 在Label对象的构造子中指名单元格位置是第一列 第一行(0,0)
					// 注意构造的顺序为先列 后行
					jxl.write.Label labelColumnName;
					for (i = 0; i < table.getColumnCount(); i++) {
						if (indexs[i] < 0)
							continue;
						labelColumnName = new jxl.write.Label(indexs[i], 0,
								table.getColumnName(i));
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
				Object o;
				if (nCurrentRow + nRowStep < rowCount) {
					for (i = nCurrentRow; i < nCurrentRow + nRowStep; i++) {
						for (j = 0; j < table.getColumnCount(); j++) {
							if (indexs[j] < 0)
								continue;
							jxl.write.Label label1 = new jxl.write.Label(
									indexs[j], i + 1, getTableCellValue(
											allDataList, i, j));
							// 将定义好的单元格添加到工作表中
							sheet1.addCell(label1);
						}
					}
				} else {
					for (i = nCurrentRow; i < rowCount; i++) {
						for (j = 0; j < table.getColumnCount(); j++) {
							if (indexs[j] < 0)
								continue;
							jxl.write.Label label1 = new jxl.write.Label(
									indexs[j], i + 1, getTableCellValue(
											allDataList, i, j));
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
				return false;
			}
		}
		return bRet;
	}

	public boolean saveDataToDBF(String sFilePath) {
		boolean bRet = true;
		if (table.getRowCount() <= 0) {
			JOptionPane.showMessageDialog(null, "空表不能转出");
			return bRet;
		}
		try {
			int i, j;
			DBFWriter writer = new DBFWriter();
			// 构造DBFField
			DBFField fields[] = new DBFField[table.getColumnCount()];
			for (i = 0; i < table.getColumnCount(); i++) {
				fields[i] = new DBFField();
				String headerValue = table.getColumnModel().getColumn(i)
						.getHeaderValue().toString();
				fields[i].setName(headerValue.length() > 10 ? headerValue
						.substring(0, 10) : headerValue);
				fields[i].setDataType(Byte.valueOf(DBFField.FIELD_TYPE_C));
				fields[i].setFieldLength(Integer.valueOf(table.getColumnModel()
						.getColumn(i).getWidth()));
			}
			writer.setFields(fields);
			// 写数据
			for (i = 0; i < table.getRowCount(); i++) {
				Object obj[] = new Object[table.getColumnCount()];
				for (j = 0; j < table.getColumnCount(); j++) {
					obj[j] = tableModel.getValueAt(i, j);
				}
				Object[] rowData = new Object[table.getColumnCount()];
				for (int k = 0; k < rowData.length; k++) {
					rowData[k] = new String(obj[k].toString().getBytes("GBK"),
							"ISO-8859-1");
				}
				writer.addRecord(rowData);
			}
			writer.write(new FileOutputStream(new File(sFilePath)));
			bRet = true;
		} catch (Exception e) {
			System.out.println("Save dbf :" + e);
			bRet = false;
		}
		return bRet;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void updateColumnAddedDatas(String name) {
		// TODO Auto-generated method stub
		UComponentI com = (UComponentI) comMap.get(name);
		com.updateAddedDatas();

	}

	public FilterI getColumnFilter(String name) {
		// TODO Auto-generated method stub
		return (FilterI) filterMap.get(name);
	}

	public UComponentI getColumnCompoent(String name) {
		// TODO Auto-generated method stub
		return (UComponentI) comMap.get(name);
	}

	public int[] getDataChangedRows() {
		UFormModifyStatusI f;
		Object o;
		List list = new ArrayList();
		int i;
		for (i = 0; i < dataList.size(); i++) {
			o = dataList.get(i);
			if (o instanceof UFormModifyStatusI) {
				f = (UFormModifyStatusI) o;
				if (f.isModify())
					list.add(i);
			}
		}
		int ri[] = new int[list.size()];
		for (i = 0; i < list.size(); i++) {
			ri[i] = (Integer) (list.get(i));
		}
		return ri;
	}

	public int[] getCheckedrows(String name) {
		Class c = (Class) colNoMap.get(name);
		Method method = (Method) getMethodMap.get(name);
		if (method == null)
			return null;
		if (method.getReturnType() != Boolean.class)
			return null;
		Boolean b;
		int i;
		List list = new ArrayList();
		try {
			for (i = 0; i < dataList.size(); i++) {
				b = (Boolean) method.invoke(dataList.get(i));
				if (b.booleanValue()) {
					list.add(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		int ri[] = new int[list.size()];
		for (i = 0; i < list.size(); i++) {
			ri[i] = (Integer) (list.get(i));
		}
		return ri;
	}

	public int getActionStartNo() {
		String text;
		if (startNoField != null) {
			Object obj;
			obj = startNoField.getData();
			if (obj != null && !obj.equals(""))
				return Integer.parseInt(obj.toString());
			else
				return -1;
		}
		return -1;
	}

	public int getActionEndNo() {
		if (endNoField != null) {
			Object obj;
			obj = endNoField.getData();
			if (obj != null && !obj.equals(""))
				return Integer.parseInt(obj.toString());
			else
				return -1;
		}
		return -1;
	}

	public void setActionStartNo(int no) {
		if (startNoField != null) {
			startNoField.setText("" + no);
		}
	}

	public void setActionEndNo(int no) {
		if (endNoField != null) {
			endNoField.setText("" + no);
		}
	}

	public void doSelectAll(List list) {
		if (list == null)
			return;
		String fieldName = (String) list.get(0);
		Boolean b = (Boolean) list.get(1);
		doSelectAll(fieldName, b);
	}

	public void doSelectAll(String fieldName, Boolean b) {
		if (dataList == null)
			return;
		if (dataList.size() == 0)
			return;
		if (!(dataList.get(0) instanceof UFormModifyStatusI))
			return;
		int i;
		UFormModifyStatusI form;
		Method m;
		int col;
		try {
			for (i = 0; i < dataList.size(); i++) {
				form = (UFormModifyStatusI) dataList.get(i);
				m = (Method) this.setMethodMap.get(fieldName);
				m.invoke(form, b);
				col = (Integer) this.colNoMap.get(fieldName);
				tableModel.setValueAt(b, i, col + userStartColNo);
				form.setModify(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ResetComponentContent() {
		// TODO Auto-generated method stub

	}

	public void setStartNo(int no) {

	}

	public List getDataChangedList() {
		List changedRowsList = new ArrayList();
		int changedRowsIndex[] = this.getDataChangedRows();
		int i;
		for (i = 0; i < changedRowsIndex.length; i++) {
			changedRowsList.add(dataList.get(changedRowsIndex[i]));
		}
		return changedRowsList;
	}

	// /////////////////////////////////////////////

	public void setLabelsText(String name, String text) {
		if (tableTemplate == null || tableTemplate.actionBar == null
				|| tableTemplate.actionBar.labels == null)
			return;
		for (int i = 0; i < tableTemplate.actionBar.labels.length; i++) {
			if (tableTemplate.actionBar.labels[i].name.equals(name)) {
				setLabelsText(i, text);
				break;
			}
		}
	}

	public void setLabelsText(int pos, String text) {
		if (labels != null && labels.length > pos && pos >= 0) {
			labels[pos].setText(text);
		}
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

	public void getTableInnerSize() {
		// TODO Auto-generated method stub
		if (tableTemplate == null)
			return;
		int width, height;
		TableColumnModel colModel;
		TableColumn column;
		width = 0;
		for (int i = 0; i < tableTemplate.columnNum + userStartColNo; i++) {
			colModel = table.getColumnModel();
			column = colModel.getColumn(i);
			width += column.getWidth();
		}
		height = 0;
		if (tableTemplate.topNum >= 1) {
			height += table.getRowHeight() * tableTemplate.topNum;
		}
		if (dataList != null && dataList.size() >= 0) {
			height += table.getRowHeight() * dataList.size();
		}
		height += table.getRowHeight();
		tableInnerSize = new Dimension(width, height);
	}

	public void resetScrollPanelSize() {
		return;

	}

	public void setCanScrolling(boolean canScrolling) {
		// TODO Auto-generated method stub

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
		String nameString = evt.getPropertyName();
		if (!nameString.equals("lastDividerLocation"))
			return;
		// resetScrollPanelSize();
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

	public void resetTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		if (template == null)
			return;
		this.tableTemplate = (UTableTemplate) template;
		clearTemplateData();
		initMethods();
		initTableBase();
		if (tableTemplate.no != null) {
			userStartColNo = 1;
		} else {
			userStartColNo = 0;
		}
		initTableTitle();
		initActionBar();
		initColumns();

		initFinished = true;

	}

	public void clearTemplateData() {
		map.clear();
		getMethodMap.clear();
		setMethodMap.clear();
		userStartColNo = 0;
		// tableModelChangeCanSend = false;
		// mouseClicked = false;
		// mouseEventCanSend = false;
		// tableModelChangeCanUse = false;
		// actionEventCanSend = false;
		// keyEventCanSend = false;
		initFinished = false;
		filterMap.clear();
		comMap.clear();
		colNoMap.clear();
	}

	public void restTableBase() {
		tableModel = new UTableModel();
		table.setModel(tableModel);
		table = new JTable(tableModel);
		tableHeader = table.getTableHeader();
		tableHeader
				.setToolTipText("Click to sort; Shift-Click to sort in reverse order");
		tableHeader.addMouseListener(tableEventProcessorAdaptor);
		tableHeader.setReorderingAllowed(false);
		tableModel.addTableModelListener(tableEventProcessorAdaptor);
	}

	public void displayCurrentRowDetail(UHandlerI h) {
		// TODO Auto-generated method stub
		displayCurrentRowDetail(table.getSelectedRow(), h);
	}

	public void displayCurrentRowDetail(int selectionIndex, UHandlerI h) {
		// TODO Auto-generated method stub
		if (tableTemplate == null || !(tableTemplate.isDisplayRowDetail))
			return;
		if (selectionIndex < 0)
			return;
		int i = 0;
		int size;
		UTableForm tableForm = new UTableForm();
		size = tableTemplate.columnNum;
		TableAttributeValueForm items[] = new TableAttributeValueForm[size];
		TableAttributeValueForm itemForm;
		Object o;
		int startNum = 0;
		if (tableTemplate.no != null)
			startNum++;
		for (i = 0; i < size; i++) {
			itemForm = new TableAttributeValueForm();
			if (i < tableTemplate.topStrings.length)
				itemForm.setTitle(tableTemplate.topStrings[i]);
			String str = "";
			o = tableModel.getValueAt(selectionIndex, i + startNum);
			if (o != null)
				str = o.toString();
			itemForm.setValue(str);
			items[i] = itemForm;
		}
		tableForm.setItems(items);
		if (!(h instanceof UFormHandlerI))
			return;
		UFormHandlerI fh = (UFormHandlerI) h;
		UDialogPanel dlg = (UDialogPanel) fh.startDialog(
				"uimsTableDetailInfoDialog", tableForm);
	}

	public UTableModel getTableModel() {
		return tableModel;
	}

	public JTable getTable() {
		return table;
	}

	public int[] getSelectColumnsIndex() {
		int selectColumnsIndex[] = table.getSelectedColumns();
		return selectColumnsIndex;
	}

	public List getCheckedDataList(String name) {
		Integer o = (Integer) colNoMap.get(name);
		if (o == null)
			return null;
		int col = o;
		Boolean b;
		Object ot;
		List retList = new ArrayList();
		for (int i = 0; i < dataList.size(); i++) {
			ot = tableModel.getValueAt(i, col);
			if (ot instanceof Boolean) {
				b = (Boolean) ot;
				if (b.booleanValue()) {
					retList.add(dataList.get(i));
				}
			}
		}
		return retList;
	}

	public JPanel initPageQueryBar(JPanel panels) {
		JPanel panel = panels;
		if (tableTemplate.pageRowNum <= 0)
			return panel;
		pageActionPanel = new UPageActionPanel(this, tableTemplate.pageRowNum,panel);
		return pageActionPanel.getJPanel();
	}

	public void displayTableByPage() {
		displayPageData();
	}
	public void displayPageData() {
		if(pageActionPanel == null)
			return;
		dataList = this.pageActionPanel.getPageDataList();
		dataToModel();
		getTableInnerSize();
		resetScrollPanelSize();
	}

	public void doTablePageDataQuery() {
		TablePageDataQueryHandlerI handler = (TablePageDataQueryHandlerI) getUParent()
				.getHandler();
		if (handler == null)
			return;
		if(this.pageActionPanel == null)
			return;
		handler.getTablePageData(this.pageActionPanel.getTableQueryDataForm());
		displayTableByPage();
	}
	
	public void doTablePageDataQuery(UTableQueryDataForm form) {
		TablePageDataQueryHandlerI handler = (TablePageDataQueryHandlerI) getUParent()
				.getHandler();
		if (handler == null)
			return;
		handler.getTablePageData(form);
		displayTableByPage();
	}


	public void repaintTableData() {
		dataToModel();
	}

	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return getSelectRowsIndex();
	}

	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		int selectRowsIndex[] = this.getSelectRowsIndex();
		if (selectRowsIndex == null || selectRowsIndex.length == 0)
			return null;
		else
			return dataList.get(selectRowsIndex[0]);
	}

	public Object getRowDataFormByStringArrays(Integer[] cols, String[] oa) {
		if (oa == null)
			return null;
		if (tableTemplate == null)
			return null;
		String className = tableTemplate.itemFormClassName;
		if (className == null)
			return null;
		try {
			Object retObject = Class.forName(className).newInstance();
			Object o = null;
			int i;
			Method ms, mg;
			int col;
			for (i = 0; i < oa.length; i++) {
				if (cols[i] == null)
					continue;
				col = cols[i];
				ms = (Method) (setMethodMap
						.get(tableTemplate.columnTemplates[col].itemFormMember));
				mg = (Method) (setMethodMap
						.get(tableTemplate.columnTemplates[col].itemFormMember));
				o = DataProcessUtils.changeStringToObject(mg.getReturnType(),
						oa[i]);
				if (tableTemplate.columnTemplates[col].itemIndex == null) {
					ms.invoke(retObject, o);
				} else {
					ms.invoke(retObject,
							tableTemplate.columnTemplates[col].itemIndex, o);
				}
			}
			return retObject;
		} catch (Exception e) {
			return null;
		}
	}

	public void inserDataToTable(String[] heads, List<String[]> inList) {
		if (tableTemplate == null)
			return;
		int i, j;
		boolean b;
		Integer[] index = new Integer[heads.length];
		for (i = 0; i < heads.length; i++) {
			b = false;
			j = 0;
			while (!b && j < tableTemplate.columnNum) {
				if (tableTemplate.topStrings[j].equals(heads[i])) {
					b = true;
					index[i] = j;
				}
				j++;
			}
		}
		Object o;
		for (i = 0; i < dataList.size(); i++) {
			o = getRowDataFormByStringArrays(index, inList.get(i));
			dataList.add(o);
		}
		dataToModel();
		getTableInnerSize();
		resetScrollPanelSize();
	}

	public boolean readDataFromExcel() {
		try {
			File file = GetFile.getOpenFile("xls");
			if (file == null)
				return false;
			int i, j;
			String oa[];
			List<String[]> inList = null;
			FileInputStream in = new FileInputStream(file);
			Workbook wb = Workbook.getWorkbook(in);
			Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			int cols = sheet.getColumns();
			String heads[] = new String[cols];
			Cell cell;
			String str;
			for (j = 0; j < cols; j++) {
				cell = sheet.getCell(0, j);
				str = cell.getContents().trim();
				heads[j] = str;
			}
			if (rows <= 1)
				return false;
			inList = new ArrayList<String[]>();
			for (i = 1; i < rows; i++) {
				oa = new String[cols];
				for (j = 0; j < cols; j++) {
					cell = sheet.getCell(j, i);
					str = cell.getContents().trim();
					oa[j] = str;
				}
				inList.add(oa);
			}
			in.close();
			inserDataToTable(heads, inList);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void doDownloadTemplate() {
		if (tableTemplate == null) {
			return;
		}
		int indexs[] = getcolumnIndexs();
		try {
			File file = GetFile.getSaveFile("xls");
			WritableWorkbook book;
			WritableSheet sheet1;
			int i, j;
			book = Workbook.createWorkbook(file);
			sheet1 = book.createSheet("第一页", 0);
			jxl.write.Label labelColumnName;
			for (i = 0; i < table.getColumnCount(); i++) {
				if (indexs[i] < 0)
					continue;
				labelColumnName = new jxl.write.Label(indexs[i], 0,
						table.getColumnName(i));
				// 将定义好的单元格添加到工作表中
				sheet1.addCell(labelColumnName);
			}
			book.write();
			book.close();

		} catch (Exception e) {
			return;
		}
	}

	public void pushComponentToForm() {
		UPanel p = (UPanel) this.getUParent();
		if (p != null)
			p.getSubData(this.componentName);
	}

	public void update() {
		UPanel p = (UPanel) this.getUParent();
		if (p != null)
			p.getSubData(this.componentName);
	}

	public int[] getSelectRowColumn() {
		int row = table.getSelectedRow();
		int col = table.getSelectedColumn() - userStartColNo;
		int ra[] = new int[2];
		ra[0] = row;
		ra[1] = col;
		return ra;
	}

	public void initColumnComboxEvent() {

		if (tableTemplate == null)
			return;
		int i, index;
		String templateName;
		UComponentI com;
		for (i = 0; i < tableTemplate.columnTemplates.length; i++) {
			index = i + userStartColNo;
			UComboBox cb;
			com = (UComponentI) comMap
					.get(tableTemplate.columnTemplates[i].name);
			if (com == null || !(com instanceof UComboBox))
				continue;
			cb = (UComboBox) com;
			cb.addMouseListener(eventProcessor);
			canGetCellValueByPanel = true;
			table.setColumnSelectionInterval(index, index);
		}
		if (canGetCellValueByPanel || tableTemplate.mustCellSelect) {
			table.setCellSelectionEnabled(true);
		}
	}

	public void initColumnPopupPanel() {

		if (tableTemplate == null)
			return;
		int i, index;
		String templateName;
		UPopupPanel popupPanel;
		canGetCellValueByPanel = false;
		Dialog dlg = null;
		if (this.getUParent() instanceof UDialogPanel) {
			UDialogPanel pp = (UDialogPanel) this.getUParent();
			dlg = pp.getContainer();
		}
		if (getUParent() == null)
			return;
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		UHandlerI th = getUParent().getHandler();
		PopupListSelectionListener listListener = new PopupListSelectionListener();
		columnPopupPanel = new Object[tableTemplate.columnTemplates.length];
		for (i = 0; i < tableTemplate.columnTemplates.length; i++) {
			index = i + userStartColNo;
			if (tableTemplate.columnTemplates[i].panelTemplateName != null) {
				canGetCellValueByPanel = true;
				table.setColumnSelectionInterval(index, index);
				if (tableTemplate.columnTemplates[i].popupMode.equals("panel")) {
					templateName = tableTemplate.columnTemplates[i].panelTemplateName;
					Object o = UFactory.getModelSession().getTemplate(
							UConstants.MAPKEY_PANEL_FORM, templateName);
					UPanelTemplate panelTemplate = (UPanelTemplate) o;
					if (panelTemplate == null)
						continue;
					popupPanel = (UPopupPanel) panelTemplate.newComponent();
					popupPanel.setCanPopupPanelComponent(this);
					popupPanel.setStartMenuName("table"
							+ tableTemplate.columnTemplates[i].name
							+ templateName);
					popupPanel.setPathNameString(panelTemplate.name);
					popupPanel.setTemplate(panelTemplate);
					popupPanel.init();
					popupPanel.getContainer().setSize(panelTemplate.width,
							panelTemplate.height);
					columnPopupPanel[i] = new PopupPanelShell(popupPanel, dlg);
				}
			} else if (tableTemplate.columnTemplates[i].popupable) {
				canGetCellValueByPanel = true;
				table.setColumnSelectionInterval(index, index);
				UListPopup popup = new UListPopup();
				if (tableTemplate.columnTemplates[i].addedDatas != null)
					popup.setList(tableTemplate.columnTemplates[i].addedDatas);
				else if (tableTemplate.columnTemplates[i].dictionary != null) {
					List list = util
							.getComboxListByCode(tableTemplate.columnTemplates[i].dictionary);
					if (list == null || list.size() == 0)
						list = th
								.getInitAddedDataListByName(tableTemplate.columnTemplates[i].dictionary);
					if (list != null)
						popup.setList(list.toArray());
				} else
					popup.setList(new ArrayList().toArray());
				popup.addListSelectionListener(listListener);
				columnPopupPanel[i] = popup;
			}
		}
		if (canGetCellValueByPanel) {
			table.setCellSelectionEnabled(true);
		}
	}

	private class PopupListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if (columnPopupPanel[currentPopupCol] != null) {
				if (columnPopupPanel[currentPopupCol] instanceof UListPopup) {
					UListPopup lp = (UListPopup) columnPopupPanel[currentPopupCol];
					JList list = (JList) e.getSource();
					int index = e.getFirstIndex();
					Object o = list.getSelectedValue();
					if (o != null) {
						setIoDataOfCell(o, currentPopupRow, currentPopupCol);
					}
					lp.setVisible(false);
				}
			}
		}

	}

	public Object getIoDataOfCell(int row, int col) {
		return getCellObjectFromForm(row, col + userStartColNo);
	}

	public void setIoDataOfCell(Object info, int row, int col) {
		// setValueAt(info, row, col + userStartColNo);
		setValueofDataList(info, row, col + userStartColNo);
		updateCell(row, col + userStartColNo);
		setModifySatatus(row, row);
	}

	public void setColumnPopUpDataList(String name, List list) {
		for (int i = 0; i < tableTemplate.columnTemplates.length; i++) {
			if (tableTemplate.columnTemplates[i].dictionary != null
					&& tableTemplate.columnTemplates[i].dictionary.equals(name)
					&& columnPopupPanel[i] != null) {
				UListPopup lp = (UListPopup) columnPopupPanel[i];
				if (list != null)
					lp.setList(list.toArray());
				return;
			}
		}

	}

	public boolean getCellValueByPanel(MouseEvent e) {
		if (!canGetCellValueByPanel)
			return false;
		int[] pos = getSelectRowColumn();
		int row = pos[0];
		int col = pos[1];
		if (col < 0 || col >= tableTemplate.columnNum || row < 0
				|| row >= dataList.size()) {
			return false;
		}
		Object o = dataList.get(row);
		if (o instanceof UCellLockedDataI) {
			UCellLockedDataI f = (UCellLockedDataI) o;
			if (f.isLocked(col))
				return false;
		}
		HashMap map = null;
		UHandlerI th = getUParent().getHandler();
		if (th != null)
			map = th.getUserDataHashMap();
		if (columnPopupPanel[col] != null) {
			currentPopupCol = col;
			currentPopupRow = row;
			int colt = col + userStartColNo;
			if (columnPopupPanel[col] instanceof PopupPanelShell) {
				PopupPanelShell ps = (PopupPanelShell) columnPopupPanel[col];
				Point p = e.getLocationOnScreen();
				UFormI form = ps.getDataForm();
				if (form instanceof UPopupIoDataI) {
					UPopupIoDataI listForm = (UPopupIoDataI) form;
					listForm.setIoData(getIoDataOfCell(row, col));
				}
				UFormHandler h = ps.getInnerHandler();
				if (map != null)
					ps.setParameters(map);
				if (h != null)
					h.resetPanelData();
				ps.setPopupLocation(p);
				ps.show();
			} else {
				UListPopup lp = (UListPopup) columnPopupPanel[col];
				List list = null;
				if (tableTemplate.columnTemplates[col].dictionary != null)
					list = th.getRowAddedDataListByName(row, getData(row),
							tableTemplate.columnTemplates[col].dictionary);
				else
					list = th.getRowAddedDataListByName(row, getData(row),
							tableTemplate.columnTemplates[col].name);
				if (list != null)
					lp.setList(list.toArray());
				lp.setSelectObject(getIoDataOfCell(row, col));
				lp.setPopupPreferredSize();
				lp.show(table, e.getX(), e.getY());
			}
			return true;
		} else {
			String tName = tableTemplate.columnTemplates[col].panelTemplateName;
			if (tName == null)
				return false;

			currentPopupCol = col;
			currentPopupRow = row;
			UPanelTemplate temp;
			temp = (UPanelTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_DIALOG, tName);
			UFormI form = null;
			try {
				if (temp.dataFormClassName != null) {
					form = (UFormI) Class.forName(temp.dataFormClassName)
							.newInstance();
				}
				if (form instanceof UPopupIoDataI) {
					UPopupIoDataI listForm = (UPopupIoDataI) form;
					listForm.setIoData(getIoDataOfCell(row, col));
				}
			} catch (Exception et) {
				et.printStackTrace();
			}
			UDialogPanel item = (UDialogPanel) temp.newComponent();
			if (item != null) {
				if (this.getUParent().getAWTComponent() instanceof Dialog)
					item.SetOwner(this.getUParent());
				else
					item.SetOwner(null);
				item.setOwnerHandler(th);
				item.setComponentName(tName);
				item.setTemplate(temp);
				item.setParameters(map);
				item.setDialogForm(form);
				item.setLocatePoint(e.getLocationOnScreen());
				item.init();
				if (item.getReturnValue().equals(item.RETURN_OK)) {
					UPopupIoDataI listForm = (UPopupIoDataI) item.getData();
					setIoDataOfCell(listForm.getIoData(), currentPopupRow,
							currentPopupCol);
				}
			}
			return true;
		}
	}

	@Override
	public void closePopupPanel(int returnType) {
		// TODO Auto-generated method stub
		if (columnPopupPanel[currentPopupCol] != null) {
			if (columnPopupPanel[currentPopupCol] instanceof PopupPanelShell) {
				PopupPanelShell ps = (PopupPanelShell) columnPopupPanel[currentPopupCol];
				if (returnType > 0) {
					UFormI form = ps.getDataForm();
					if (form != null) {
						if (form instanceof UPopupIoDataI) {
							UPopupIoDataI listForm = (UPopupIoDataI) form;
							setIoDataOfCell(listForm.getIoData(),
									currentPopupRow, currentPopupCol);
						}
					}
				}
				ps.hide();
			}
		}
		this.setFocusable(false);

	}

	@Override
	public void setPanelTemplateName(String name) {
		// TODO Auto-generated method stub
	}

	public void addNewRow() {
		if (tableTemplate == null)
			return;
		if (tableTemplate.itemFormClassName == null)
			return;
		try {
			UFormI o = (UFormI) Class.forName(tableTemplate.itemFormClassName)
					.newInstance();
			this.insertTabRow(o, dataList.size());
		} catch (Exception e) {
			return;
		}
	}

	public void deleteSelectedRow() {
		int index[] = this.getSelectRowsIndex();
		if (index == null || index.length == 0)
			return;
		deleteTabRow(index[0]);
	}

	public void closeOwnerPanel(int returnType) {
		UPanelI p = this.getUParent();
		if (returnType > 0) {
			UFormHandler fh = (UFormHandler) p.getHandler();
			if (fh != null)
				fh.componentToForm();
		}
		p.closePopUpPanel(returnType);
	}

	private class InnerButtonActionProcessor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String cmd = e.getActionCommand();
			if (cmd.equals("innerCommandAddNewRowButton")) {
				addNewRow();
			} else if (cmd.equals("innerCommandDeleteRowButton")) {
				deleteSelectedRow();
			} else if (cmd.equals("innerCommandClearButton")) {
				clearTableData();
			} else if (cmd.equals("innerCommandokButton")) {
				closeOwnerPanel(1);
			} else if (cmd.equals("innerCommandokButton")) {
				closeOwnerPanel(-1);
			}

		}

	}

	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		int n = 0;
		if (queryDataField != null)
			n++;
		if (n == 0)
			return null;
		Object ret[] = new Object[n];
		n = 0;
		if (queryDataField != null) {
			ret[n] = queryDataField.getData();
			n++;
		}
		return ret;
	}

	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		if (queryDataField != null) {
			queryData.clear();
			queryDataField.setData(queryData);
		}
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub

	}

	public Point getTablePopupPoint() {
		int c[] = getSelectRowColumn();
		currentPopupRow = c[0];
		currentPopupCol = c[1];
		Rectangle r = table.getCellRect(currentPopupRow, currentPopupCol
				+ userStartColNo, true);
		Point p = new Point(r.x, r.y + r.height * 2);
		return p;
	}

	public void setCurrentCellObject(Object o) {
		setIoDataOfCell(o, currentPopupRow, currentPopupCol);
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

	public void tableColumnSortSet(UHandlerI h) {
		if (!(h instanceof UFormHandlerI))
			return;
		HashMap map = new HashMap();
		if (tableTemplate == null)
			return;
		int i;
		String str = "";
		for (i = 0; i < tableTemplate.columnTemplates.length; i++) {
			str += tableTemplate.topStrings[i];
			if (i < tableTemplate.columnTemplates.length - 1)
				str += ";";
		}
		map.put("itemStr", str);
		UFormHandler fh = (UFormHandler) h;
		UDialogPanel dlg = (UDialogPanel) fh.startDialog(
				"uimsTableColumnSortSetDialog", null, null, map);
		if (dlg.getReturnValue().equals(UDialogPanel.RETURN_OK)) {
			UTableForm tForm = (UTableForm) dlg.getDialogForm();
			Object items[] = tForm.getItems();
			if (items == null || items.length == 0)
				return;
			sortList = new ArrayList<SortColumnAttribute>();
			UTableColumnSortItemForm f;
			SortColumnAttribute a;
			int col;
			boolean asc;
			Comparator com;
			for (i = 0; i < items.length; i++) {
				f = (UTableColumnSortItemForm) items[i];
				if (f.getCol() != null && f.getCol().length() != 0)
					col = Integer.parseInt(f.getCol());
				else
					col = 0;
				if (f.getAsc() == null || f.getAsc().equals("1"))
					asc = true;
				else
					asc = false;
				com = this.getColumnComparator(col);
				sortList.add(new SortColumnAttribute(col, asc, com));
			}
			this.sortByColumnList();
		}
	}

	public void initValidatorMap() {
		String name;
		UElementTemplate ele;
		String className;
		DataValidatorI vi = null;
		if (tableTemplate == null)
			return;
		for (int i = 0; i < tableTemplate.columnTemplates.length; i++) {
			if (!(tableTemplate.columnTemplates[i].isEditable()))
				continue;
			className = tableTemplate.columnTemplates[i].validatorClassName;
			if (className == null
					&& tableTemplate.columnTemplates[i].validatorName != null) {
				className = UFactory.getModelSession().getDefaultClassByType(
						tableTemplate.columnTemplates[i].validatorName);
			}
			if (className == null)
				continue;
			try {
				vi = (DataValidatorI) Class.forName(className).newInstance();
				vi.init(tableTemplate.columnTemplates[i].validatorMsg,
						UimsUtils
								.stringToHashMap(tableTemplate.columnTemplates[i].validatorParas));
				validatorMap.put(tableTemplate.columnTemplates[i].name, vi);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean testInvalidateData() {
		String name;
		UComponentI com;
		DataValidatorI vi = null;
		if (tableTemplate == null)
			return false;
		int[] index = getDataChangedRows();
		if (index == null)
			return false;
		int i, col, row;
		Object obj = null;
		for (col = 0; col < tableTemplate.columnTemplates.length; col++) {
			vi = (DataValidatorI) this.validatorMap
					.get(tableTemplate.columnTemplates[col].name);
			if (vi != null) {
				for (i = 0; i < index.length; i++) {
					row = index[i];
					obj = tableModel.getValueAt(row, col + userStartColNo);
					if (vi.isInvalidate(obj)) {
						UimsUtils.messageBoxInfo(vi.getInvalidateMessage());
						return true;
					}
				}
			}
		}
		return false;
	}

	public int getSingleChoiceRow(String name) {
		for (int col = 0; col < tableTemplate.columnNum; col++) {
			if (tableTemplate.columnTemplates[col].name.equals(name)) {
				for (int row = 0; row < table.getRowCount(); row++) {
					Object o = tableModel.getValueAt(row, col + userStartColNo);
					if (!(o instanceof Boolean))
						return -1;
					if ((Boolean) o.equals("true")) {
						return row;
					}
				}
			}
		}
		return -1;
	}

	public JTable getJTable() {
		return table;
	}

	public String getReportTitle() {
		if (tableTemplate != null && tableTemplate.reportTitle != null)
			return tableTemplate.reportTitle;
		else
			return "";
	}

	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		enablePopupMenu = enable;
	}

	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub

	}

	public void setActionBarButtonEditable(String name, boolean b) {
		if (buttons == null || buttons.length == 0)
			return;
		for (int i = 0; i < buttons.length; i++) {
			if (name.equals(buttons[i].getComponentName())) {
				buttons[i].setEditable(b);
				return;
			}
		}
	}

	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getRowDataObject(int row) {
		if (row < 0 || dataList == null || row >= dataList.size())
			return null;
		else
			return dataList.get(row);
	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub

	}

	public Object getColumnSumValue(String member, Integer index) {
		if (dataList == null || dataList.size() == 0)
			return 0;
		Method m = (Method) getMethodMap.get(member);
		double d = 0;
		int n = 0;
		Integer nn = null;
		Double dd = null;
		Object o;
		try {
			for (int i = 0; i < dataList.size(); i++) {
				if (index == null)
					o = m.invoke(dataList.get(i));
				else
					o = m.invoke(dataList.get(i), index);
				if (o != null) {
					if (o instanceof Integer) {
						nn = (Integer) o;
						n += nn.intValue();
					} else if (o instanceof Double) {
						dd = (Double) o;
						d += dd.doubleValue();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		if (dd != null)
			return d;
		else
			return n;
	}

	public void initStatisticsBar() {
		if (tableTemplate.statisticsItemList == null
				|| tableTemplate.statisticsItemList.size() == 0)
			return;
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JLabel label;
		TableStatisticsItemTemplate t;
		statisticsTextField = new JTextField[tableTemplate.statisticsItemList
				.size()];
		for (int i = 0; i < tableTemplate.statisticsItemList.size(); i++) {
			t = tableTemplate.statisticsItemList.get(i);
			label = new JLabel(t.label);
			statisticsTextField[i] = new JTextField(10);
			panel.add(label);
			panel.add(statisticsTextField[i]);
		}
		add(panel, BorderLayout.SOUTH);
	}

	public void setStatisticsBarData() {
		if (tableTemplate.statisticsItemList == null
				|| tableTemplate.statisticsItemList.size() == 0)
			return;
		TableStatisticsItemTemplate t;
		List<Variable> variables;
		Object result;
		StringTokenizer sz;
		String v;
		int ind;
		String member;
		Integer index;
		Object o;
		for (int i = 0; i < tableTemplate.statisticsItemList.size(); i++) {
			t = tableTemplate.statisticsItemList.get(i);
			variables = null;
			if (t.variables != null && t.variables.length() != 0) {
				variables = new ArrayList<Variable>();
				sz = new StringTokenizer(t.variables, ";");
				while (sz.hasMoreTokens()) {
					v = sz.nextToken();
					ind = v.indexOf("_");
					if (ind > 0) {
						member = v.substring(0, ind);
						index = new Integer(v.substring(ind + 1, v.length()));
					} else {
						member = v;
						index = null;
					}
//					variables.add(Variable.createVariable(v,getColumnSumValue(member, index)));
				}
			}
//			result = Double.parseDouble(new DecimalFormat("#.##").format(ExpressionEvaluator.evaluate(t.expression, variables)));
//			if (result != null)
//				statisticsTextField[i].setText(result.toString());
//			else
//				statisticsTextField[i].setText("");
		}
	}

	public void setDynamicColumnVisible() {

	}

	public void initViewControl() {
		if (tableTemplate == null || tableTemplate.viewControl == null) {
			tableScrollPane = new UScrollPane();
			viewContainer = this;
		} else if (tableTemplate.viewControl.type == TableViewControlTemplate.CONTROL_TYPE_COLUMN_CONTROL) {
			tableScrollPane = new UScrollPane();
			viewContainer = this;
			initViewControlColumn();
		} else if (tableTemplate.viewControl.type == TableViewControlTemplate.CONTROL_TYPE_MULTI_VIEW) {
			if (tableTemplate.viewControl.views == null
					|| tableTemplate.viewControl.views.length == 0) {
				tableScrollPane = new UScrollPane();
			} else {
				viewScrollPane = new UScrollPane[tableTemplate.viewControl.views.length];
				for (int i = 0; i < tableTemplate.viewControl.views.length; i++) {
					viewScrollPane[i] = new UScrollPane();
					if (tableTemplate.viewControl.views[i] == TableViewControlTemplate.VIEW_TYPE_DATA) {
						tableScrollPane = viewScrollPane[i];
					}
				}
				viewContainer = new JPanel();
				add(viewContainer, BorderLayout.CENTER);
				viewContainer.setLayout(new CardLayout());
				initViewControlMulti();
			}
		}
	}

	public void initViewControlColumn() {
		listCheck = new UListCheck();
		listCheck.initContents();
		ColumnControlActionProcessor al = new ColumnControlActionProcessor();
		int num = tableTemplate.columnNum;
		columnCheckObject = new CheckObject[tableTemplate.columnNum];
		int i;
		for (i = 0; i < num; i++) {
			columnCheckObject[i] = new CheckObject(true, new ListOptionInfo(
					tableTemplate.columnTemplates[i].name,
					tableTemplate.topStrings[i]));
		}
		Dimension d = new Dimension(tableTemplate.viewControl.width, num
				* (tableTemplate.viewControl.rowHeight));
		listCheck.setPreferredSize(d);
		listCheck.setSize(d);
		listCheck.setData(columnCheckObject);
		listCheck.setListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (initFinished) {
					resetColumnDisplayStatus();
					// getTableInnerSize();
					// resetScrollPanelSize();
				}

			}
		});
		add(listCheck.getAWTComponent(), BorderLayout.WEST);
	}

	public void initViewControlMulti() {
		int i;
		JPanel control = new JPanel();
		control.setLayout(new BorderLayout());
		if (tableTemplate.viewControl.views != null) {
			JComboBox com = new JComboBox();
			for (i = 0; i < tableTemplate.viewControl.views.length; i++) {
				if (tableTemplate.viewControl.views[i] == TableViewControlTemplate.VIEW_TYPE_DATA) {
					com.addItem(new ListOptionInfo(
							tableTemplate.viewControl.views[i] + "", "表格"));
				} else if (tableTemplate.viewControl.views[i] == TableViewControlTemplate.VIEW_TYPE_HISTOGRAM) {
					com.addItem(new ListOptionInfo(
							tableTemplate.viewControl.views[i] + "", "直方图"));
				} else if (tableTemplate.viewControl.views[i] == TableViewControlTemplate.VIEW_TYPE_PIE) {
					com.addItem(new ListOptionInfo(
							tableTemplate.viewControl.views[i] + "", "饼图"));
				} else if (tableTemplate.viewControl.views[i] == TableViewControlTemplate.VIEW_TYPE_CUSTOM) {
					com.addItem(new ListOptionInfo(
							tableTemplate.viewControl.views[i] + "", "自定义"));
				}
			}
			control.add(com, BorderLayout.SOUTH);
		}
		DefaultTableModel tModel = new DefaultTableModel();
		JTable t = new JTable(tModel);
		t.setShowGrid(true);
		t.setBackground(tableBackground);
		t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		t.setRowHeight(tableTemplate.viewControl.rowHeight);
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(t);
		this.setPreferredSize(new Dimension(tableTemplate.viewControl.width,
				tableTemplate.viewControl.rowHeight
						* (tableTemplate.columnNum + 1)));
		JTableHeader th = t.getTableHeader();
		tModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
			}

		});
		tModel.addColumn("项名");
		tModel.addColumn("维度");
		tModel.addColumn("显示");
		tModel.addColumn("不显示");
		JCheckBox ckb;
		TableColumnModel colModel = t.getColumnModel();
		TableColumn column;
		TableRowSpecificRender re = new TableRowSpecificRender();
		ckb = new JCheckBox("");
		column = colModel.getColumn(1);
		column.setCellEditor(new DefaultCellEditor(ckb));
		column.setCellRenderer(re);
		ckb = new JCheckBox("");
		column = colModel.getColumn(2);
		column.setCellEditor(new DefaultCellEditor(ckb));
		column.setCellRenderer(re);
		ckb = new JCheckBox("");
		column = colModel.getColumn(3);
		column.setCellEditor(new DefaultCellEditor(ckb));
		column.setCellRenderer(re);
		Object[] a;
		for (i = 0; i < tableTemplate.columnNum; i++) {
			a = new Object[4];
			a[0] = tableTemplate.topStrings[i];
			a[1] = false;
			a[2] = false;
			a[3] = false;
			tModel.insertRow(i, a);
		}
		control.add(sp, BorderLayout.CENTER);
		add(control, BorderLayout.WEST);
	}

	public String getActionBarInputString(String name) {
		if (tableTemplate == null || tableTemplate.actionBar == null)
			return null;
		int i;
		if (tableTemplate.actionBar.labels != null
				&& tableTemplate.actionBar.labels.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.labels.length; i++) {
				if (name.equals(tableTemplate.actionBar.labels[i].name)) {
					return labels[i].getText();
				}
			}
		}
		if (tableTemplate.actionBar.fields != null
				&& tableTemplate.actionBar.fields.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.fields.length; i++) {
				if (name.equals(tableTemplate.actionBar.fields[i].name)) {
					return fields[i].getText();
				}
			}
		}
		if (tableTemplate.actionBar.spinners != null
				&& tableTemplate.actionBar.spinners.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.spinners.length; i++) {
				if (name.equals(tableTemplate.actionBar.spinners[i].name)) {
					if(spinners[i].getData() != null)
						return spinners[i].getData().toString();
					else
						return  null;
				}
			}
		}
		if (tableTemplate.actionBar.comboBoxs != null
				&& tableTemplate.actionBar.comboBoxs.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.comboBoxs.length; i++) {
				if (name.equals(tableTemplate.actionBar.comboBoxs[i].name)) {
					Object o = comboBoxs[i].getSelectedItem();
					if (o == null)
						return null;
					if (o instanceof ListOptionInfo) {
						return ((ListOptionInfo) o).getValue();
					} else
						return o.toString();
				}
			}
		}
		if (tableTemplate.actionBar.comboBoxDates != null
				&& tableTemplate.actionBar.comboBoxDates.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.comboBoxDates.length; i++) {
				if (name.equals(tableTemplate.actionBar.comboBoxDates[i].name)) {
					Object o = comboBoxDates[i].getData();
					if (o == null)
						return null;
					else
						return DateTimeTool.parseDateTime((Date)o, "yyyy-MM-dd");
				}
			}
		}
		return null;
	}

	public void setActionBarInputString(String name, String value) {
		if (tableTemplate == null || tableTemplate.actionBar == null)
			return;
		int i, j;
		if (tableTemplate.actionBar.labels != null
				&& tableTemplate.actionBar.labels.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.labels.length; i++) {
				if (name.equals(tableTemplate.actionBar.labels[i].name)) {
					labels[i].setText(value);
				}
			}
		}
		if (tableTemplate.actionBar.fields != null
				&& tableTemplate.actionBar.fields.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.fields.length; i++) {
				if (name.equals(tableTemplate.actionBar.fields[i].name)) {
					fields[i].setText(value);
					return;
				}
			}
		}
		if (tableTemplate.actionBar.spinners != null
				&& tableTemplate.actionBar.spinners.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.spinners.length; i++) {
				if (name.equals(tableTemplate.actionBar.spinners[i].name)) {
					spinners[i].setText(value);
					return;
				}
			}
		}
		if (tableTemplate.actionBar.comboBoxs != null
				&& tableTemplate.actionBar.comboBoxs.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.comboBoxs.length; i++) {
				if (name.equals(tableTemplate.actionBar.comboBoxs[i].name)) {
					int count = comboBoxs[i].getItemCount();
					for (j = 0; j < count; j++) {
						Object o = comboBoxs[i].getItemAt(j);
						if (o instanceof ListOptionInfo) {
							ListOptionInfo lo = (ListOptionInfo) o;
							if (lo.getValue().equals(value)) {
								comboBoxs[i].setSelectedIndex(j);
								break;
							}
						} else {
							if (value.equals(o.toString())) {
								comboBoxs[i].setSelectedIndex(j);
								break;
							}
						}
					}
					return;
				}
			}
		}
		if (tableTemplate.actionBar.comboBoxDates != null
				&& tableTemplate.actionBar.comboBoxDates.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.comboBoxDates.length; i++) {
				if (name.equals(tableTemplate.actionBar.comboBoxDates[i].name)) {
					comboBoxDates[i].setData(DateTimeTool.formatDateTime(value, "yyyy-MM-dd"));
					return;
				}
			}
		}
	}

	public UComboBox getActionBarComboBox(int index) {
		if (comboBoxs != null && index < comboBoxs.length)
			return comboBoxs[index];
		else
			return null;
	}

	public void setCannotEvent(boolean cannotEvent) {
		this.cannotEvent = cannotEvent;
	}

	public boolean editCellAt(int row, int column) {
		return table.editCellAt(row, column);
	}
	
	public UTextField getActionBarField(String name){
		if (tableTemplate == null || tableTemplate.actionBar == null)
			return null;
		int i;
		if (tableTemplate.actionBar.fields != null	&& tableTemplate.actionBar.fields.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.fields.length; i++) {
				if (name.equals(tableTemplate.actionBar.fields[i].name)) {
					return fields[i];
				}
			}
		}
		return null;
	}

	public USpinner getActionBarSpinner(String name){
		if (tableTemplate == null || tableTemplate.actionBar == null)
			return null;
		int i;
		if (tableTemplate.actionBar.spinners != null	&& tableTemplate.actionBar.spinners.length != 0) {
			for (i = 0; i < tableTemplate.actionBar.spinners.length; i++) {
				if (name.equals(tableTemplate.actionBar.spinners[i].name)) {
					return spinners[i];
				}
			}
		}
		return null;
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

	@Override
	public void setTableQueryDataForm(UTableQueryDataForm form) {
		// TODO Auto-generated method stub
		if(this.pageActionPanel != null)
			pageActionPanel.setTableQueryDataForm(form);
	}

	@Override
	public UTableQueryDataForm getTableQueryDataForm() {
		// TODO Auto-generated method stub
		if(pageActionPanel != null)
			return pageActionPanel.getTableQueryDataForm();
		else
			return null;
	}
	
	public void setTabelColumnName(Integer columnIndex, String name){
		tableModel.setColumnName(userStartColNo+columnIndex, name);
	}
	public void setDisplayColumnCount(int leng){
		int num = displayColumnCount;
		if(num == 0)
			num = this.tableTemplate.columnNum;
		if(leng == num)
			return;
		int i;
		if(leng < num) {
			for(i = leng; i < num;i++) {
				this.hideColumn(userStartColNo+i);
			}
		}else {
			for(i = num;i < leng;i++) {
				this.showColumn(userStartColNo+i, tableTemplate.columnTemplates[i].width);
			}
		}
		displayColumnCount = leng;
	}
	
	public Object getQueryDataFieldData() {
		// TODO Auto-generated method stub
		if (queryDataField != null) {
			return queryDataField.getData();
		}else
			return null;
	}
	public void setQueryDataFieldData(Object data) {
		// TODO Auto-generated method stub
		if (queryDataField != null) {
			queryDataField.setData(data);
		}
	}

	public void clearQueryDataFieldData() {
		// TODO Auto-generated method stub
		if (queryDataField != null) {
			 queryDataField.setData(null);;
		}
	}
	public void updatetpActionBarComboBoxAdddData(int index, List list ){
		if (comboBoxs == null || index >= comboBoxs.length)
			return;
		if(list != null) {
			Object o[] = list.toArray();
			comboBoxs[index].setAddedDatas( o);
		}
		else
			comboBoxs[index].setAddedDatas((Object[])null) ;
		comboBoxs[index].updateAddedDatas();
	}
	public void setQueryDataFieldInputQueryParaProcessor(InputQueryParaProcessorI pi){
		if(queryDataField != null)
			queryDataField.setInputQueryParaProcessor(pi);
	}
	public void setTableHeaderValue(Integer index, String name){
		table.getColumnModel().getColumn(index).setHeaderValue(name);
	}
}
