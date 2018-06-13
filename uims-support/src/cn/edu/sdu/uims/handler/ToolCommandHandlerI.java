package cn.edu.sdu.uims.handler;

import java.util.List;

import cn.edu.sdu.uims.component.panel.MyExcelSheet;

/*
 * 操作：关闭当前窗口，Close
 刷新，Refresh
 添加，add
 修改,modify
 删除  delete
 打印设置，PrintSetup
 打印，Printer
 直接打印，Print
 导入数据，Import
 导出数据＄1�7  Export
 编辑功能＄1�7
 复制，Copy
 剪切，Cut
 粘贴 (包括 数据行，单元格，丄1�7段�1�7�中的文本等) ，Paste
 全�1�7�，SelectAll
 不�1�7�，SelectNone
 反�1�7�，SelectReverse
 批量置数，Batch
 查看功能＄1�7
 查询，Query
 过滤，Filter
 排序，Order
 选择刄1�7, SelectColumn
 数据导航＄1�7
 第一行，FirstRow
 上一行，PreviousRow
 下一行，NextRow
 朄1�7后一行，LastRow
 第一页，FirstPage
 上一页，PreviousPage
 下一页，NextPage
 朄1�7后一页�1�7�LastPage
 */
public interface ToolCommandHandlerI {
	public Object doNew();
	
	public void doSave();
	
	public void doClose();

	public Object doAdd();
	
	public void doModify();
	
	public void doDelete();
	
	public void doRefresh();

	public void doPrintSetup();

	public void doPriter();

	public void doPrint();

    public void doImport(MyExcelSheet myExcelSheet);

	public void doExport();

	public void doCopy();

	public void doCut();

	public void doPaste();

	public void doSelectAll();

	public void doSelectNone();

	public void doSelectReverse();

	public void doBatch();

	public void doQuery();
	
	public void doQuery(String where);

	public void doFilter();

	public void doOrder();

	public void doSelectColumn();

	public void doFirstRow();

	public void doNextRow();

	public void doPreviousRow();

	public void doLastRow();

	public void doFirstPage();

	public void doPreviousPage();

	public void doNextPage();

	public void doLastPage();

	public List getAttributeListForQuery();
	
	public void doBeforePanelClosed();
	
	public void doInfoSend();
	
	public void doTableDetail();
	
	public void doCheck();
	public void doSort();
	public void doPrintPreview();
}
