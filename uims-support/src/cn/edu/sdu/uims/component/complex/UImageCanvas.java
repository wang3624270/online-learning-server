package cn.edu.sdu.uims.component.complex;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.complex.def.UPageImageViewTemplate;
import cn.edu.sdu.uims.component.complex.form.DataLabeDrawI;
import cn.edu.sdu.uims.component.complex.form.ImageDataFormI;
import cn.edu.sdu.uims.component.complex.form.ImageDrawData;
import cn.edu.sdu.uims.pi.ImageDataDriverI;

public class UImageCanvas extends JScrollPane implements MouseListener, ImageCanvasProcessI{
	private JPanel view;
	private UPageImageViewPanel owner;
	private List<ImageDrawData>imgList;
	private DataLabeDrawI drawer;
	public UImageCanvas(UPageImageViewPanel owner){
		this.owner = owner;
		init();
	}
	UPageImageViewTemplate getTemplate(){
		return (UPageImageViewTemplate)owner.getElementTemplate();
	}
	public void init(){
		UPageImageViewTemplate t = getTemplate();
		try {
			drawer = (DataLabeDrawI)Class.forName(t.labelDrawClass).newInstance();
		}catch(Exception e) {
			drawer = null;
		}
		view = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawGraph(g);
			}
		};
		view.addMouseListener(this);
		view.setPreferredSize(new Dimension(1,1));
		setViewportView(view);
		this.updateUI();
	}
	public void drawGraph(Graphics g){
		if(imgList == null || imgList.size() == 0)
			return;
		int i,j;
		UPageImageViewTemplate t = this.getTemplate();
		ImageDrawData d;
		for(i = 0; i < imgList.size();i++) {
			d = imgList.get(i);
			if(drawer != null) {
				drawer.drawLabel(g, d);
			}else {
				if (d.img != null) {
					int dw = d.w;
					int dh = d.h;
					int iw = d.img.getWidth();
					int ih = d.img.getHeight();
					int x, y, w, h;
					double is = (double) iw / ih;
					double ds = (double) dw / dh;
					if (is > ds) {
						if (iw < dw) {
							w = iw;
							h = ih;
						} else {
							w = dw;
							h = ih * w / iw;
						}
					} else {
						if (ih < dh) {
							h = ih;
							w = iw;
						} else {
							h = dh;
							w = iw * h / ih;
						}
					}
					x = (dw - w) / 2;
					y = (dh - h) / 2;
					g.drawImage(d.img, d.x + x, d.y + y, w, h, null);
				}			
			}
		}
	}
	public void setDrawData(List dataList){
		UPageImageViewTemplate t = this.getTemplate();
		int w, h;
//		if(drawer == null)
//			return;
		if(dataList == null || dataList.size() == 0) {
			imgList = null;
			w = 1;
			h = 1;
		}else {
			ImageDataDriverI driver = UimsFactory.getImageDataDriverI();
			imgList = new ArrayList<ImageDrawData>();
			ImageDrawData d;
			ImageDataFormI f;
			byte [] b;
			int x, y;
			x = t.B;
			y = t.B;
			for(int i = 0; i < dataList.size();i++){
				d = new ImageDrawData();
				f = (ImageDataFormI)dataList.get(i);
				d.img = null;
				if(f.getImageData() != null) {
					b = f.getImageData();
					if(b == null)
						d.img = null;
					else {
						try{
							d.img = ImageIO.read(new ByteArrayInputStream(b));
						}catch(Exception e){
							d.img = null;
						}
					}					
				}
				if(d.img == null && driver != null ){
					d.img = (BufferedImage)driver.getImageData(f.getAttachId());
				}
				d.data = f;
				d.x = x;
				d.y = y;
				d.w = t.imgW;
				d.h = t.imgH;
				d.tw = t.textW;
				d.th = t.textH;
				imgList.add(d);
				if((i+1) % t.colNum == 0) {
					x = t.B;
					y += t.imgH+ t.textH + t.DH;
				}else {
					x += t.imgW + t.textW + t.DW;
				}
			}
			int n = imgList.size();
			t.rowNum = n / t.colNum;
			int c = n % t.colNum;
			if(c != 0)
				t.rowNum ++;
			w = (t.imgW + t.textW) * t.colNum  + t.DW *(t.colNum -1) + 2* t.B;
			h = (t.imgH + t.textH) * t.rowNum  + t.DH *(t.rowNum -1) + 2* t.B;
		}
		view.setPreferredSize(new Dimension(w,h));
		this.updateUI();
		view.repaint();
	}
	
	public void repaintDrawImage(){		
		if(drawer == null)
			return;
		if(imgList == null)
			return;
		ImageDataDriverI driver = UimsFactory.getImageDataDriverI();
		if(driver == null)
			return;
		ImageDrawData d;
		ImageDataFormI f;
		for(int i = 0;i < imgList.size();i++) {
			d = imgList.get(i);
			if(d == null)
				continue;
			f = d.data;
			if(f == null)
				continue;
			d.img = (BufferedImage)driver.getImageData(f.getAttachId());
		}			
		view.repaint();
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p = e.getPoint();
		if(e.getButton()== MouseEvent.BUTTON1) {
			doSelectImage(p);
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			owner.dispPopMenu(e);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void doSelectImage(Point p){
		if(imgList == null || imgList.size() == 0)
			return;
		UPageImageViewTemplate t = this.getTemplate();
		boolean b= false;
		int i= 0;
		ImageDrawData d;
		while(!b && i < imgList.size()) {
			d = imgList.get(i);
			if( p.x > d.x  && p.x < d.x + d.w + d.tw && p.y > d.y && p.y < d.y + d.h + d.th) {
				b = true;
			}else
				i++;
		}
		if(!b)
			return;
		owner.doSelectObject(imgList.get(i).data);
	}

}
