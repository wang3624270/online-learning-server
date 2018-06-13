package cn.edu.sdu.uims.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.BarCode;


public class UVBarCode extends UVElement {
	private Image img = null;
	public UVBarCode() {
		super();
	}
	public BufferedImage getBarCode(String str,boolean showMessage){
		BarCode barcode=new BarCode();
		barcode.code=str;
		barcode.showMessage = showMessage;
		barcode.barType=BarCode.CODE128;
		barcode.setSize(barcode.width,barcode.height);
		BufferedImage bufferedimage_temp=new BufferedImage(barcode.getSize().width,barcode.getSize().height,BufferedImage.TYPE_BYTE_INDEXED);
		Graphics2D graphics2d_temp=bufferedimage_temp.createGraphics();
		barcode.paint(graphics2d_temp);
		barcode.invalidate();
		graphics2d_temp.dispose();
		BufferedImage bufferedimage=new BufferedImage(barcode.getSize().width,barcode.getSize().height,BufferedImage.TYPE_INT_RGB);
		
		Graphics2D graphics2d=bufferedimage.createGraphics();
		barcode.paint(graphics2d);
		return  bufferedimage;
	}
	public void setData(Object obj) {
		if(obj == null)
			text = "";
		else
			text = obj.toString();
		img = getBarCode(text,false);
	}
	public void draw(Graphics g, UMatrix m) {
		super.draw(g,m);
		URect rd= m.logicToView(new URect(x,y,w,h));
		g.drawImage(img, x, y, img.getWidth(null), img.getHeight(null),null);
	}
}
