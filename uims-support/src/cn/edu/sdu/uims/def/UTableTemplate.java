package cn.edu.sdu.uims.def;

import java.util.List;
import java.util.StringTokenizer;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UBlockTemplate;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.uims.component.table.SortColumnAttribute;

public class UTableTemplate extends UBlockTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8552573387113313992l;
	public int subTableNum = 1;
	public int columnNum;

	public int rowHeight = 0;

	public UCellAttribute[] cells = null;

	public UColumnTemplate[] columnTemplates = null;

	public UColumnTemplate no = null;

	public int rowNum;

	public int topNum;

	public String[] topStrings;
	public String[] topEnStrings;

	public int bottomNum;

	public String[] bottomStrings;
	public String[] bottomEnStrings;

	public int sumColumnNum;

	public String sumString;

	public UCellAttribute title = null;

	public String leftTopString = null;

	public int leftWidth = 0;

	public String leftStrings[] = null;
	public String leftEnStrings[] = null;

	public int[] leftIndexs = null;

	public String itemFormClassName = null;
	public UCellAttribute note = null;

	public int checkGroup[][];

	public UActionBarTemplate actionBar;

	// liuyang added
	public String ruleName;

	public String methodName;

	public String dataInitClassName;

	public String initMethodName;

	public String beanId;

	public UCellAttribute[] topItems = null;

	public UCellAttribute[] bottomItems = null;

	public UColumnTemplate rowSpanNo = null;

	public boolean showTable = true;
	
	public String detailTemplateName = null;
	public int pageDisplayNum;
	public String renderClassName;
	public String tableHead;
	public String rowSpecificRenderName;

	public boolean withTotal = false;
	public int pageRowNum;
	public boolean isDisplayRowDetail = false;
	public boolean mustRowSelect = false;
	public boolean mustCellSelect = false;
	public boolean isHeadCanSort = true;
	public String reportTitle="";
	public boolean autoResizeLastColumn = false;
	
	public List<TableStatisticsItemTemplate> statisticsItemList;
	public  List<SortColumnAttribute> sortList;

	public TableViewControlTemplate viewControl;
	
	public boolean isDisplayRowDetail() {
		return isDisplayRowDetail;
	}
	public void setDisplayRowDetail(boolean isDisplayRowDetail) {
		this.isDisplayRowDetail = isDisplayRowDetail;
	}
	public int isPageRowNum() {
		return pageRowNum;
	}
	public void setPageRowNum(int pageRowNum) {
		this.pageRowNum = pageRowNum;
	}
	public int getSubTableNum() {
		return subTableNum;
	}
	public void setSubTableNum(int subTableNum) {
		this.subTableNum = subTableNum;
	}
	public int getColumnNum() {
		return columnNum;
	}
	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}
	public int getRowHeight() {
		return rowHeight;
	}
	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}
	public UCellAttribute[] getCells() {
		return cells;
	}
	public void setCells(UCellAttribute[] cells) {
		this.cells = cells;
	}
	public UColumnTemplate[] getColumnTemplates() {
		return columnTemplates;
	}
	public void setColumnTemplates(UColumnTemplate[] columnTemplates) {
		this.columnTemplates = columnTemplates;
	}
	public UColumnTemplate getNo() {
		return no;
	}
	public void setNo(UColumnTemplate no) {
		this.no = no;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getTopNum() {
		return topNum;
	}
	public void setTopNum(int topNum) {
		this.topNum = topNum;
	}
	public String[] getTopStrings() {
		return topStrings;
	}
	public void setTopStrings(String[] topStrings) {
		this.topStrings = topStrings;
	}
	public int getBottomNum() {
		return bottomNum;
	}
	public void setBottomNum(int bottomNum) {
		this.bottomNum = bottomNum;
	}
	public String[] getBottomStrings() {
		return bottomStrings;
	}
	public void setBottomStrings(String[] bottomStrings) {
		this.bottomStrings = bottomStrings;
	}
	public int getSumColumnNum() {
		return sumColumnNum;
	}
	public void setSumColumnNum(int sumColumnNum) {
		this.sumColumnNum = sumColumnNum;
	}
	public String getSumString() {
		return sumString;
	}
	public void setSumString(String sumString) {
		this.sumString = sumString;
	}
	public UCellAttribute getTitle() {
		return title;
	}
	public void setTitle(UCellAttribute title) {
		this.title = title;
	}
	public String getLeftTopString() {
		return leftTopString;
	}
	public void setLeftTopString(String leftTopString) {
		this.leftTopString = leftTopString;
	}
	public int getLeftWidth() {
		return leftWidth;
	}
	public void setLeftWidth(int leftWidth) {
		this.leftWidth = leftWidth;
	}
	public String[] getLeftStrings() {
		return leftStrings;
	}
	public void setLeftStrings(String[] leftStrings) {
		this.leftStrings = leftStrings;
	}
	public int[] getLeftIndexs() {
		return leftIndexs;
	}
	public void setLeftIndexs(int[] leftIndexs) {
		this.leftIndexs = leftIndexs;
	}
	public String getItemFormClassName() {
		return itemFormClassName;
	}
	public void setItemFormClassName(String itemFormClassName) {
		this.itemFormClassName = itemFormClassName;
	}
	public UCellAttribute getNote() {
		return note;
	}
	public void setNote(UCellAttribute note) {
		this.note = note;
	}
	public int[][] getCheckGroup() {
		return checkGroup;
	}
	public void setCheckGroup(int[][] checkGroup) {
		this.checkGroup = checkGroup;
	}
	public UActionBarTemplate getActionBar() {
		return actionBar;
	}
	public void setActionBar(UActionBarTemplate actionBar) {
		this.actionBar = actionBar;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getDataInitClassName() {
		return dataInitClassName;
	}
	public void setDataInitClassName(String dataInitClassName) {
		this.dataInitClassName = dataInitClassName;
	}
	public String getInitMethodName() {
		return initMethodName;
	}
	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}
	public String getBeanId() {
		return beanId;
	}
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	public UCellAttribute[] getTopItems() {
		return topItems;
	}
	public void setTopItems(UCellAttribute[] topItems) {
		this.topItems = topItems;
	}
	public UCellAttribute[] getBottomItems() {
		return bottomItems;
	}
	public void setBottomItems(UCellAttribute[] bottomItems) {
		this.bottomItems = bottomItems;
	}
	public UColumnTemplate getRowSpanNo() {
		return rowSpanNo;
	}
	public void setRowSpanNo(UColumnTemplate rowSpanNo) {
		this.rowSpanNo = rowSpanNo;
	}
	public boolean isShowTable() {
		return showTable;
	}
	public void setShowTable(boolean showTable) {
		this.showTable = showTable;
	}
	public void getControlAttribute( Element tab){
		viewControl = null;
		String str = tab.attributeValue("columnControl");
		if (str != null && str.equals("true")) {
			viewControl = new TableViewControlTemplate();
			viewControl.type = TableViewControlTemplate.CONTROL_TYPE_COLUMN_CONTROL;
			str = tab.attributeValue("columnControlWidth");
			if (str != null) {
				viewControl.width = Integer.parseInt(str);
			}
			else
				viewControl.width = 80;
			str = tab.attributeValue("columnControlRowHeight");
			if (str != null) {
				viewControl.rowHeight = Integer.parseInt(str);
			}else
				viewControl.rowHeight = 24;
		}
		StringTokenizer sz;
		String s;
		int i;
		Element e = tab.element("viewControl");
		if(e != null) {
			viewControl = new TableViewControlTemplate();
			str = e.attributeValue("type");
			if (str != null && str.equals("columnControl")) {
				viewControl.type = TableViewControlTemplate.CONTROL_TYPE_COLUMN_CONTROL;
			}else
				viewControl.type = TableViewControlTemplate.CONTROL_TYPE_MULTI_VIEW;
			str = e.attributeValue("width");
			if (str != null) {
				viewControl.width = Integer.parseInt(str);
			}
			str = e.attributeValue("rowHeight");
			if (str != null) {
				viewControl.rowHeight = Integer.parseInt(str);
			}
			str = e.attributeValue("views");
			if(str == null) {
				viewControl.views = new int []{TableViewControlTemplate.VIEW_TYPE_DATA};
			}else {
				sz = new StringTokenizer(str, "|");
				viewControl.views = new int[sz.countTokens()];
				for(i = 0; i < viewControl.views.length;i++){
					s = sz.nextToken();
					if(s.equals("data"))
						viewControl.views[i] = TableViewControlTemplate.VIEW_TYPE_DATA;
					else if(s.equals("histogram")) {
						viewControl.views[i] = TableViewControlTemplate.VIEW_TYPE_HISTOGRAM;						
					}
					else if(s.equals("pie")) {
						viewControl.views[i] = TableViewControlTemplate.VIEW_TYPE_PIE;						
					}
					else if(s.equals("custom")) {
						viewControl.views[i] = TableViewControlTemplate.VIEW_TYPE_CUSTOM;						
					}	
				}
			}
			viewControl.customViewName = e.attributeValue("customViewName");
			viewControl.customViewLabel = e.attributeValue("customViewLabel");
			str = e.attributeValue("dims");
			if(str != null) {
				sz = new StringTokenizer(str, "|");
				viewControl.dims = new int[sz.countTokens()];
				for(i = 0; i < viewControl.dims.length;i++) {
					viewControl.dims[i] = Integer.parseInt(sz.nextToken()); 
				}
			}
		}
	}
}
