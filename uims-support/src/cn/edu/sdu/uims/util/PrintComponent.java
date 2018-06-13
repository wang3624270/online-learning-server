package cn.edu.sdu.uims.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

///////目前只实现了打印A4纸张///////
public class PrintComponent implements Printable {

	private Component mComponent;

	public PrintComponent(Component c) {
		mComponent = c;		
	}

	public int print(Graphics g, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		g2.scale(0.72, 0.65);
		int pageI = (mComponent.getWidth()+ 979)/980;
		int pageJ = (mComponent.getHeight()+689)/690 ;
		//System.out.println(pageI+"  ...........  "+pageJ);
		
		if (pageIndex >= pageI * pageJ)
			return NO_SUCH_PAGE; 
		int i=pageIndex%pageI;
		int j=pageIndex/pageI;
		g2.translate(-(i * 980), -(j * 690));
		mComponent.printAll(g2);
		return PAGE_EXISTS;

	}
}