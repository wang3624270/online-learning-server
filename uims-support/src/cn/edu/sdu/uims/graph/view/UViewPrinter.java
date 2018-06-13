package cn.edu.sdu.uims.graph.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.HashMap;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;

import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.graph.form.GraphPrintForm;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;


public class UViewPrinter implements Printable, GraphViewI {

	private GraphPrintForm graphPrintForm;
	
	private PrinterJob printJob;

	private PrintService[] services;

	private PrintService selectedService;

	private PrintService defaultService;
	private String deviceName;

	private Book book = new Book();

	private Paper paper = new Paper();

	private PageFormat format;

	private PrintRequestAttributeSet aset;

	private Font font = new Font("����", Font.BOLD, 10);

	private WVTrans wv;
	private UMatrix mt;

	
	int pages=0;//记录打印的总页数
	
	boolean ifRecruit=false;

	public  UViewPrinter() {
		initPrint(new UPaperTemplate());
	}
	public  UViewPrinter(UPaperTemplate paperTemplate) {
		initPrint(paperTemplate);
	}
	public  UViewPrinter(UPaperTemplate paperTemplate, String deviceName) {
		this.deviceName = deviceName;
		initPrint(paperTemplate);
	}

	public void initPrintPara(HashMap paraMeter){
	}
	public void initPrint(UPaperTemplate paperTemplate) {
		this.aset = new HashPrintRequestAttributeSet();
		if (this.printJob == null)
			this.printJob = PrinterJob.getPrinterJob();
		if (format == null) {
			format = printJob.defaultPage();
			printJob.setPrintable(this);
		}
		paper.setSize(paperTemplate.width, paperTemplate.height);
		paper.setImageableArea(paperTemplate.left, paperTemplate.top, 
				paperTemplate.width -paperTemplate.left-paperTemplate.right,
				paperTemplate.height-paperTemplate.top-paperTemplate.bottom); 
//		format.setOrientation(paperTemplate.orientation);
		format.setOrientation(0);
		format.setPaper(paper);

		this.printJob.setPageable(book);
		book.append(this, format, 20);
		this.printJob.setPrintable(this, format);
		
		try {
			InitPRASet(paperTemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.services = PrinterJob.lookupPrintServices();
		this.defaultService = PrintServiceLookup.lookupDefaultPrintService();
		if(deviceName != null && services!= null && services.length > 0) {
			for(int i = 0;i < services.length;i++) {
				if(services[i].getName().equals(deviceName)) {
					this.defaultService = services[i];
					break;
				}
			}
		}
//		wv = new WVTrans();
//		wv.setWindows(0,0, paperTemplate.width,paperTemplate.height);
//		wv.setViewport(0,0,paperTemplate.width,paperTemplate.height);
//		wv.makeWVMatrix();
//		mt = new UMatrix();
	}
	public ViewParameter getViewParameter(){
//		return 	new ViewParameter(wv.m.m,mt.m);
		return graphPrintForm.getViewParameter();
	}

	public void InitPRASet(UPaperTemplate paperTemplate ) throws Exception {
		if (this.aset == null)
			this.aset = new HashPrintRequestAttributeSet();
		aset.add(new JobName("Graph Printable's Job", null));
		if(paperTemplate.orientation == 1)
			aset.add(OrientationRequested.LANDSCAPE);
		else
			aset.add(OrientationRequested.PORTRAIT);

	}

	public int print(Graphics g, PageFormat format, int pageIndex) throws PrinterException {

		if (pageIndex < 0 || pageIndex >= this.pages)
				return Printable.NO_SUCH_PAGE;

		GraphModelI g2d = graphPrintForm.getCurrentGraphObject();
		
		ViewParameter p = graphPrintForm.getViewParameter();
		ControlParameter c = graphPrintForm.getControlParameter();
		if(c == null) {
			c = new ControlParameter();
		}
//		HashMap par = graphPrintForm.getInputParameterMap();
		Object par = null;
				
		if (g2d != null)
			g2d.drawLayer(g,new GraphModelViewParas(p, c,g2d.getGraphDataForm(),new UPoint(0,0)),pageIndex);
		
		return Printable.PAGE_EXISTS;
		
	}
	public boolean invokeJob(GraphPrintForm gForm){
		return invokePrinterJob(gForm);
	}
	public void setGraphPrintForm(GraphPrintForm gForm){
		this.graphPrintForm = gForm; 
		if(graphPrintForm == null)
			this.pages=1;
		else 
			this.pages = graphPrintForm.getPrintPages();		
	}
	public boolean invokePrinterJob(GraphPrintForm gForm) {
		this.graphPrintForm = gForm; 
		if(graphPrintForm == null)
			this.pages=1;
		else 
			this.pages = graphPrintForm.getPrintPages();
		if (this.selectedService == null) {
			if (services != null && services.length > 0) {
				this.selectedService = services[0];

			} else {
				System.out.println("û���ҵ����õĴ�ӡ���񣬽���ֹ���δ�ӡ��");
				String msg = "û���ҵ����õĴ�ӡ���񣬽���ֹ���δ�ӡ��";
				javax.swing.JOptionPane.showMessageDialog(null, msg, "����",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		try {
			this.printJob.setPrintService(this.defaultService);
			this.printJob.print(aset);
		} catch (java.awt.print.PrinterException pe) {
			System.out.println("��ӡ����з��ִ���\n" + pe.getMessage());
			pe.printStackTrace();
			String msg = "��ӡ����з��ִ���\n" + pe.getMessage();
			javax.swing.JOptionPane.showMessageDialog(null, msg, "����",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	public boolean showPrintDialog() {
		boolean isCanceled = false;
		this.selectedService = ServiceUI.printDialog(null, 150, 150, services,
				defaultService, null, aset);
		if (this.selectedService == null)
			isCanceled = true;
		return !isCanceled;
	}
	public PageFormat getFormat() {
		return format;
	}
	public void setFormat(PageFormat format) {
		this.format = format;
	}
	public Printable getPrintable(){
		return this;
	}

}
