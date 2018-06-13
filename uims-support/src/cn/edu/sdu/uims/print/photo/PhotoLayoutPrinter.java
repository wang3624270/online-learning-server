package cn.edu.sdu.uims.print.photo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.view.UVUtil;

public class PhotoLayoutPrinter implements Printable {


	private PhotoLayoutTemplate photoTemplate;
	private PhotoLayoutDataFormI dataForm;
	private UPaperTemplate paperTemplate;
	public static String colorName = "colorBlack";
	public static String ft0 = "boldfont18";
	public static String ft1 ="font16";
	public static String fc = "font8";
	public static String fcc = "font12";	
	public static String sft0 = "boldfont14";
	public static String sft1 = "font12";
	public static String sfc = "font6";
	public static String sfcc = "font10";
	
	public static float titleHeight = 80;
	public static float platformWidth = 60;
	public static float platformHeight = 30;
	public static float platformDHeight = 40;
	public static float innerEdgeDist = 4.0f;
	public static float pScale = 0.4f;
	public static float seatDistx = 2.0f;
	public static float seatDisty = 3.0f;
	public static float whScale = 0.6f;
	public static float whiteWidth = 5.0f;
	public static float seatHeight = 30;
	public static float pageIndexHeight = 15.0f;
	private int arrangeType = 1;
	
	public PhotoLayoutPrinter(PhotoLayoutDataFormI dForm) {
		if(dForm == null || dForm.getTemplateName() == null)
			return;
		this.dataForm = dForm;
		this.photoTemplate =  UFactory.getModelSession().getPhotoLayoutTemplate(dForm.getTemplateName());
	}

	public int getArrangeType() {
		return arrangeType;
	}

	public void setArrangeType(int arrangeType) {
		this.arrangeType = arrangeType;
	}

	public UPaperTemplate getPaperTemplate(
			PhotoLayoutTemplate photoTemplate) {
		if (photoTemplate == null)
			return null;
		UPaperTemplate paperTemplate = new UPaperTemplate();
		paperTemplate.width = 595.27559f;
		paperTemplate.height = 841.88976f;
		paperTemplate.left = 8.0f;
		paperTemplate.right = 8.0f;
		paperTemplate.top = 8.0f;
		paperTemplate.bottom = 8.0f;
		paperTemplate.dpi = 72;
		paperTemplate.orientation = photoTemplate.getPaperOrientation();
		if(paperTemplate.orientation == 1)
			paperTemplate.scale = 728.787f /1031.8109f;
		else 
			paperTemplate.scale = 1.0f;
		return paperTemplate;
	}

	public ViewParameter makeViewParameter(){
			WVTrans wv = new WVTrans();
			double width, height;
			if(paperTemplate.orientation == 1) {
				width = paperTemplate.height;
				height = paperTemplate.width;
			}else {
				width = paperTemplate.width;
				height = paperTemplate.height;
			}
			wv.setWindows(0,0, width,height);
			wv.setViewport(0,0,width,height);
			wv.makeWVMatrix();
			UMatrix mt = new UMatrix();
			return new ViewParameter(wv.m,mt);
			
	}
	

	public void startPrint() {
		
		PrinterJob printJob;

		PrintService[] services;

		PrintService selectedService;

		PrintService defaultService;

		Book book = new Book();

		Paper paper = new Paper();

		PageFormat format;

		PrintRequestAttributeSet aset;

		paperTemplate= getPaperTemplate(photoTemplate);
		try {

			aset = new HashPrintRequestAttributeSet();
			printJob = PrinterJob.getPrinterJob();
			format = printJob.defaultPage();
			paper.setSize(paperTemplate.width, paperTemplate.height);
			paper.setImageableArea(paperTemplate.left, paperTemplate.top,
					paperTemplate.width - paperTemplate.left
							- paperTemplate.right, paperTemplate.height
							- paperTemplate.top - paperTemplate.bottom);
			format.setOrientation(0);
			format.setPaper(paper);

			printJob.setPageable(book);
			book.append(this, format, 20);
			printJob.setPrintable(this, format);
			aset = new HashPrintRequestAttributeSet();
			aset.add(new JobName(dataForm.getPrintFileName(), null));
			if (paperTemplate.orientation == 1)
				aset.add(OrientationRequested.LANDSCAPE);
			else
				aset.add(OrientationRequested.PORTRAIT);
			services = PrinterJob.lookupPrintServices();
			defaultService = PrintServiceLookup.lookupDefaultPrintService();

			if (services != null && services.length > 0) {
				selectedService = services[0];

			} else {
				javax.swing.JOptionPane.showMessageDialog(null, "打印失败", "提示",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			printJob.setPrintService(defaultService);
			printJob.print(aset);
		} catch (Exception pe) {
			String msg = "打印错误\n" + pe.getMessage();
			javax.swing.JOptionPane.showMessageDialog(null, msg, "提示",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public int print(Graphics g, PageFormat format, int pageIndex)
			throws PrinterException {
		if(photoTemplate == null)
			return Printable.NO_SUCH_PAGE;
		
//		if (pageIndex < 0 || pageIndex >= dataForm.getPrintPages())
//			return Printable.NO_SUCH_PAGE;
		ViewParameter p = makeViewParameter();
		ControlParameter cc = new ControlParameter();
		UColor uColor = UFactory.getModelSession().getColorByName(colorName);
		g.setColor(uColor.color);
//		drawPageNum(g,pageIndex,p,uColor,cc);
		
		return Printable.PAGE_EXISTS;

	}
	public void drawRectBox(Graphics g,ViewParameter p, float x, float y, float w, float h ){
		UPoint p0 = p.m.logicToView(x, y);
		UPoint p1 = p.m.logicToView(x+w, y+h);
		g.drawRect(p0.x, p0.y, p1.x-p0.x, p1.y-p0.y);
	}
	public void drawText(Graphics g,ViewParameter p, float x, float y, float w, float h, String text, UFont f, UColor c, int horizontalAlignment,ControlParameter cc ){
		UPoint p1 = p.m.logicToView(x, y);
		UPoint p2 = p.m.logicToView(x+w, y+h);
		UVUtil.drawStringBox(g, text, c, f, p1.x, p1.y,
				p2.x - p1.x, p2.y - p1.y, horizontalAlignment,
				UConstants.ALIGNMENT_CENTER, 0, cc.isServer);		
	}
	public UFRect getRoomRectBox() {
		double x,y,w,h;
		if(paperTemplate.orientation == 1) {
			x = paperTemplate.bottom;
			w = paperTemplate.height - paperTemplate.top -paperTemplate.bottom;
			y = paperTemplate.right  + titleHeight * paperTemplate.scale;
			h = paperTemplate.width - paperTemplate.right - y;
		}
		else {
			x = paperTemplate.left;
			w = paperTemplate.width - paperTemplate.left -paperTemplate.right;
			y = paperTemplate.bottom + titleHeight * paperTemplate.scale;
			h = paperTemplate.height - paperTemplate.top - y;
		}
		h -= pageIndexHeight;
		x += whiteWidth;
		w -= whiteWidth *2;
		y += whiteWidth;
		h -= whiteWidth*2;
		return new UFRect(x,y,w,h);
	}
	public byte[] getGraphPrintPdfByteArray() {
		paperTemplate =getPaperTemplate(photoTemplate);
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		int imageWidth, imageHeight;
		if(paperTemplate.orientation == 1) {
			imageHeight = (int) (paperTemplate.width );
			imageWidth  = (int) (paperTemplate.height);
		}
		else {
			imageWidth = (int) (paperTemplate.width );
			imageHeight = (int) (paperTemplate.height);			
		}
		ControlParameter c = new ControlParameter();
		c.isServer = true;
		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
		Document document = new Document(new Rectangle(imageWidth, imageHeight));
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					out);
			document.open();
			PdfContentByte canvas = writer.getDirectContent();
			DefaultFontMapper mapper1 = UFactory.getModelSession()
					.getFontMapper();
			Graphics2D g2;
//			RoomPageTemplate page;
			ViewParameter p	= makeViewParameter();
//			for (int pageIndex = 0; pageIndex < photoTemplate.getPrintPages(); pageIndex++) {
			for (int pageIndex = 0; pageIndex < 1; pageIndex++) {
				document.newPage();
				g2 = canvas.createGraphics(imageWidth,
						imageHeight, mapper1);				
		
				UColor uColor = UFactory.getModelSession().getColorByName(colorName);
				g2.setColor(uColor.color);
				g2.dispose();
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return out.toByteArray();
	}

}
