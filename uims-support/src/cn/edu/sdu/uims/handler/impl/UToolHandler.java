package cn.edu.sdu.uims.handler.impl;

import java.awt.event.MouseEvent;
import java.awt.print.PrinterJob;
import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UTableI;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.panel.MyExcelSheet;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.frame.UClientFrame;
import cn.edu.sdu.uims.handler.QueryListHandlerI;
import cn.edu.sdu.uims.handler.ToolCommandHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.PrintPreview;
import cn.edu.sdu.uims.util.UimsUtils;

public class UToolHandler extends UFormHandler implements ToolCommandHandlerI {


	public Object doAdd() {
		// TODO Auto-generated method stub
		System.out.println("Add");	
		return null;
	}

	
	public void doBeforePanelClosed() {
		// TODO Auto-generated method stub
//		System.out.println("close");	
		
	}

	
	public void doBatch() {
		// TODO Auto-generated method stub
		
	}

	
	public void doClose() {
		// TODO Auto-generated method stub
		
	}


	public void doCut() {
		// TODO Auto-generated method stub
		
	}

	
	public void doDelete() {
		// TODO Auto-generated method stub
		System.out.println("Delete");	
		
	}
	public UTableI getCurrentDataTable(){
		UPanelI panel = (UPanelI) component;
		UTableI table = panel.getTableComponent();
		return table;
	}
	public void doExport() {
		// TODO Auto-generated method stub
		UTableI table = getCurrentDataTable();
		doExport(table);
	}
	public void doExport(UTableI table) {
		// TODO Auto-generated method stub
		if (table == null)
			return;
		File file = GetFile.getSaveFile("xls");
		if(file==null)
			return;
		String filePath = file.getAbsolutePath();
		if (table.saveDataToExcel(filePath)) {
			UimsUtils.messageBoxInfo("数据导出成功！");
		} else {
			UimsUtils.messageBoxError("数据导出错误！");
		}
	}

	public void doExportDBF() {
		// TODO Auto-generated method stub
		UTableI table = getCurrentDataTable();
		if (table == null)
			return;
		File file = file = GetFile.getSaveFile("dbf");
		if(file==null)
			return;
		String filePath = file.getAbsolutePath();
		String fileName = file.getName();
		if (table.saveDataToDBF(filePath)) {
			UimsUtils.messageBoxInfo("数据导出成功！");
		} else {
			UimsUtils.messageBoxError("数据导出错误！");
		}
	}



	public void doFilter() {
		// TODO Auto-generated method stub
		
	}

	
	public void doFirstPage() {
		// TODO Auto-generated method stub
		
	}


	public void doFirstRow() {
		// TODO Auto-generated method stub
		
	}

	
	public void doImport(MyExcelSheet myExcelSheet) {
		// TODO Auto-generated method stub
		
	}


	public void doLastPage() {
		// TODO Auto-generated method stub
		
	}


	public void doLastRow() {
		// TODO Auto-generated method stub
		
	}

	public void doModify() {
		// TODO Auto-generated method stub
		System.out.println("Modify");			
	}


	public void doNextPage() {
		// TODO Auto-generated method stub
		
	}


	public void doNextRow() {
		// TODO Auto-generated method stub
		
	}


	public void doOrder() {
		// TODO Auto-generated method stub
		
	}




	public void doPreviousPage() {
		// TODO Auto-generated method stub
		
	}


	public void doPreviousRow() {
		// TODO Auto-generated method stub
		
	}

	
	public void doPrint() {
		// TODO Auto-generated method stub
		
	}


	public void doPrintSetup() {
		// TODO Auto-generated method stub
		
	}


	public void doPriter() {
		// TODO Auto-generated method stub
		
	}

	
	public void doQuery() {
		// TODO Auto-generated method stub
		
	}

	
	public void doQuery(String where) {
		// TODO Auto-generated method stub
		
	}

	
	public void doRefresh() {
		// TODO Auto-generated method stub
		
	}

	
	public void doSave() {
		// TODO Auto-generated method stub
		
	}

	
	public void doSelectAll() {
		// TODO Auto-generated method stub
		
	}

	
	public void doSelectColumn() {
		// TODO Auto-generated method stub
		
	}

	
	public void doSelectNone() {
		// TODO Auto-generated method stub
		
	}

	
	public void doSelectReverse() {
		// TODO Auto-generated method stub
		
	}


	public List getAttributeListForQuery() {
		// TODO Auto-generated method stub
		return null;
	}
	public void doInfoSend(){
	}

	public void processMouseEvent(Object o, String cmd) {
		MouseEvent e = (MouseEvent) o;
		if (e.getClickCount()==2) {
			doTableDetail();
		}
	}

	public void doTableDetail() {
		// TODO Auto-generated method stub
		UPanelI p = this.getComponent();
		if( p== null)
			return;
		UTableI t = p.getInnerTable();
		if(t == null)
			return ;
		t.displayCurrentRowDetail(this);
	}
	public boolean confirmDelete(){
		int ret = JOptionPane.showConfirmDialog(null, "请确认是否删除选择的记录！", "确认删除对话框",JOptionPane.YES_NO_OPTION);
		if(ret == JOptionPane.YES_OPTION)
			return true;
		return false;
	}
	public QueryListHandlerI getSystemQueryListHandler(){
		QueryListHandlerI h =null;
		String className = UFactory.getModelSession().getEnvironmentTemplate().systemQueryListHandlerName;
		try{
			h =  (QueryListHandlerI)Class.forName(className).newInstance();
			h.setOwnerHandler(this);
			return h;
		}catch(Exception e){
			
		}
		return null;
	}


	@Override
	public Object doNew() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void doCheck() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doSort() {
		// TODO Auto-generated method stub
		UPanelI p = this.getComponent();
		if( p== null)
			return;
		UTableI t = p.getInnerTable();
		if(t == null)
			return ;
		t.tableColumnSortSet(this);		
	}

	public boolean printUserGraphPage(){
		
/*		GraphModelI g2d = UFactory.getModelSession().getGraphModel2DObject("GreatForeignConferencePlan");
		if(g2d == null)
			return false;
		GraphPrintForm graphForm =  new GraphPrintForm();
		graphForm.setCurrentGraphObject(g2d);
		graphForm.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		UViewPrinter pt = new UViewPrinter(paperTemplate);
		g2d.makeGraphDataForm();
		for(int i  = 0;i < g2d.getLayerSize();i++)
		g2d.setLayerDataForm(i, null);
		pt.setGraphPrintForm(graphForm);
//		PrinterJob pj = PrinterJob.getPrinterJob();
//		javax.print.attribute.HashPrintRequestAttributeSet att = new javax.print.attribute.HashPrintRequestAttributeSet();
		new PrintPreview(pt,pt.getFormat());
		return true;
*/
		return false;
	}
	
	@Override
	public void doPrintPreview() {
		// TODO Auto-generated method stub
		if(printUserGraphPage())
			return;
		UTablePanel table = (UTablePanel) getCurrentDataTable();
		if (table == null)
			return;
		JTable tab = table.getJTable();
		String title = table.getReportTitle();
		PrinterJob pj = PrinterJob.getPrinterJob();
		javax.print.attribute.HashPrintRequestAttributeSet att = new javax.print.attribute.HashPrintRequestAttributeSet();
		new PrintPreview(tab.getPrintable(
				javax.swing.JTable.PrintMode.FIT_WIDTH, new MessageFormat(title), new MessageFormat("{0}")),
				pj.getPageFormat(att));
	}
	
	public UPanelI getClientToolPanel(){
		return UClientFrame.getFrame().getClientToolPanel();
	}
	public void doCopy(){

	}
	public void doPaste(){
		
	}
}
