package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.BarCode;

public class GElementBarCode extends GElement2D {
	public String text;
	public BufferedImage img;

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
	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object data,UPoint shiftPoint) {
		Object o = null;
		if (data != null) {
			o = getAttributeObject(data);
		}
		if (o != null) {
			text = (String)o;
		}
		if (text == null) 
			return;
		BufferedImage tempImg =  getBarCode(text,false);
		URect re = p.m.logicToView(box);
		dc.drawImage(tempImg, re.x, re.y, tempImg.getWidth(), tempImg.getHeight(), null);
	}


	public int testPointOnElement(UFPoint fp) {
		if (fp.x >= box.x && fp.x <= box.x + box.w && fp.y >= box.y
				&& fp.y <= box.y + box.h) {
			return GraphConstants.GRAPH_SELECT_STATUS_INNER;
		}
		return GraphConstants.GRAPH_SELECT_STATUS_NO;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		text = readString(in);

	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, text);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("h", ""+box.h);
		ge.addAttribute("w", ""+box.w);
		ge.addAttribute("y", ""+box.y);
		ge.addAttribute("x", ""+box.x);
		if(text != null && text.length() != 0)
		ge.addAttribute("text", text);
		super.exportElementToDoc(ge);
	}	
	
}
