package cn.edu.sdu.uims.component.panel;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.JPanel;

import cn.edu.sdu.uims.handler.ToolCommandHandlerI;

public class SuperPanel extends JPanel implements ToolCommandHandlerI {

	private static final long serialVersionUID = 111111L;
	public static final int WIDTH = 830;
	public static final int HEIGHT = 610;


	private String toolAtctions[] = null;

	public String[] getToolActions() {
		return toolAtctions;
	}

	public void setToolActions(String[] acts) {
		toolAtctions = acts;
	}

	protected TextFieldChangeListener textChangeListener = new TextFieldChangeListener(
			this);

	public SuperPanel() {
		super();
		this.setOpaque(true);
		this.addFocusListener(new SuperPanel_this_focusAdapter(this));
	}
	public void init(){
		
	}
	public Component getPrintComponent() {
		return this;
	}

	public void activated() {
//		System.out.println("Panel actived");
	}

	/**
	 * 当panel被不濄1�7活时此方法激洄1�7
	 */
	public void deactivated() {
//		System.out.println("Panel deactivate");
	}

	/**
	 * 当panel被关闭时此方法激洄1�7
	 */
	public void closed() {
		System.out.println("Panel closed");
	}

	/*
	 * public String getSelectedText(){ return caretListener.getSlectedText(); }
	 * 
	 * private class DragMouseAdapter extends MouseAdapter { public void
	 * mousePressed(MouseEvent e) { JComponent c = (JComponent) e.getSource();
	 * TransferHandler handler = c.getTransferHandler(); handler.exportAsDrag(c,
	 * e, TransferHandler.COPY); } }
	 * 
	 * protected class MyCaretListener implements CaretListener { String
	 * selectedText=""; public MyCaretListener() {}
	 * 
	 * //Might not be invoked from the event dispatching thread. public void
	 * caretUpdate(CaretEvent e) { Object o = e.getSource(); if(o instanceof
	 * JTextField){ selectedText=((JTextField)o).getSelectedText();
	 * System.out.println("textfield "+((JTextField)o).getSelectedText()); }else
	 * if(o instanceof JTextArea){
	 * selectedText=((JTextField)o).getSelectedText();
	 * System.out.println("textarea"+((JTextField)o).getSelectedText()); } else
	 * if(o instanceof JTextComponent){
	 * selectedText=((JTextComponent)o).getSelectedText();
	 * System.out.println("textarea"+((JTextComponent)o).getSelectedText()); }
	 * 
	 * //displaySelectionInfo(e.getDot(), e.getMark()); }
	 * 
	 * public String getSlectedText(){ return selectedText; } }
	 */
	public void  doSave() {

	}

	public Object doAdd() {
		return null;
	}

	public void doModify() {
	}

	public void doBatch() {
	}

	public void doClose() {
	}

	public void doCopy() {
	}

	public void doCut() {
	}

	public void doDelete() {
	}

	public void doExport() {
	}

	public void doExport(File file) {
	}

	public void doFilter() {
	}

	public void doFirstPage() {
	}

	public void doFirstRow() {
	}

	public void doLastPage() {
	}

	public void doLastRow() {
	}

	public void doNextPage() {
	}

	public void doNextRow() {
	}

	public void doOrder() {
	}

	public void doPaste() {
	}

	public void doPreviousPage() {
	}

	public void doPreviousRow() {
	}

	public void doPrint() {
		/*
		 * try { PrinterJob prnJob = PrinterJob.getPrinterJob(); PrintComponent
		 * cp = new PrintComponent(getPrintComponent()); PageFormat pageformat =
		 * prnJob.defaultPage(); prnJob.setPrintable(cp, pageformat);
		 * javax.print.attribute.HashPrintRequestAttributeSet attrs = new
		 * javax.print.attribute.HashPrintRequestAttributeSet(); // int width =
		 * getPrintComponent().getWidth(); // int height =
		 * getPrintComponent().getHeight(); attrs
		 * .add(javax.print.attribute.standard.OrientationRequested.REVERSE_LANDSCAPE);
		 * prnJob.print(attrs); } catch (PrinterException pe) {
		 * pe.printStackTrace(); JOptionPane.showMessageDialog(null,
		 * "打印错误＄1�7"); }
		 */
	}

	public void doPrintSetup() {
		/*
		 * try { PrinterJob prnJob = PrinterJob.getPrinterJob(); PrintComponent
		 * cp = new PrintComponent(getPrintComponent()); PageFormat pageformat =
		 * prnJob.defaultPage(); prnJob.setPrintable(cp, pageformat);
		 * javax.print.attribute.HashPrintRequestAttributeSet attrs = new
		 * javax.print.attribute.HashPrintRequestAttributeSet(); // int width =
		 * getPrintComponent().getWidth(); // int height =
		 * getPrintComponent().getHeight(); attrs
		 * .add(javax.print.attribute.standard.OrientationRequested.REVERSE_LANDSCAPE);
		 * if (!prnJob.printDialog()) { return; } else prnJob.print(attrs); }
		 * catch (PrinterException pe) { pe.printStackTrace();
		 * JOptionPane.showMessageDialog(null, "打印错误＄1�7"); }
		 */
	}

	public void doPriter() {
	}

	public void doQuery() {
	}

	public void doQuery(String where) {

	}

	public void doRefresh() {
	}

	public void doSelectAll() {
	}

	public void doSelectColumn() {
	}

	public void doSelectNone() {
	}

	public void doSelectReverse() {
	}

	@SuppressWarnings("unchecked")
	public void keyPressed(KeyEvent e) {
//		toolbarRight.put("Save", true);
	}


	public List getAttributeListForQuery() {
		return null;
	}

	void this_focusGained(FocusEvent e) {
		System.out.println("Focus gained");
	}

	public void doBeforePanelClosed() {
		// TODO Auto-generated method stub

	}

	public void doImport(MyExcelSheet myExcelSheet) {
		// TODO Auto-generated method stub
		
	}


	public void doInfoSend() {
		// TODO Auto-generated method stub
		
	}
	public void onClose(){
		
	}

	@Override
	public void doTableDetail() {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public void doPrintPreview() {
		// TODO Auto-generated method stub
		
	}
}

class TextFieldChangeListener extends java.awt.event.KeyAdapter {

	SuperPanel adaptee;

	TextFieldChangeListener(SuperPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.keyPressed(e);
	}
}

class SuperPanel_this_focusAdapter extends java.awt.event.FocusAdapter {
	SuperPanel adaptee;

	SuperPanel_this_focusAdapter(SuperPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void focusGained(FocusEvent e) {
		adaptee.this_focusGained(e);
	}
}
