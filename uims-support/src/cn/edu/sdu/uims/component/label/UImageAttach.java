package cn.edu.sdu.uims.component.label;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.pi.ImageDataDriverI;
import cn.edu.sdu.uims.util.UimsUtils;

public class UImageAttach extends UImageLabel implements MouseListener {
	private Integer attachId = null;
	private boolean isCanAction = false;
	public void initContents() {
		if(template == null){
			return;
		}
		this.addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage img = null;
		if(attachId == null)
			return;
		g.drawRect(0, 0, this.getWidth(), this.getHeight());
		ImageDataDriverI driver = UimsFactory.getImageDataDriverI();
		if(driver != null ){				
			img = (BufferedImage)driver.getImageData(attachId);
		}
		if (img == null) {
			img = UimsUtils.getImageBufferOfAttachFile(attachId);
		}
		if(img == null) {
			return;
		}
		int dw = this.getWidth();
		int dh = this.getHeight();
		int iw = img.getWidth();
		int ih = img.getHeight();
		int x,y,w,h;
		double is = (double)iw/ih;
		double ds = (double)dw/dh;
		if(is > ds) {
			if(iw < dw) {
				w = iw;
				h = ih;
			}else {
				w = dw;
				h = ih*w/iw;
			}
		}else {
			if(ih < dh) {
				h = ih;
				w = iw;
			}else {
				h = dh;
				w = iw*h/ih;
			}
		}
		x = (dw-w)/2;
		y = (dh-h)/2;
		g.drawImage(img, x, y, w, h, this);
	}
	
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		for (int i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				isCanAction = true;
			}
		}
		
	}
	public Object getData() {
		// TODO Auto-generated method stub
		return attachId;
	}
	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null)
			attachId = null;
		else
			if(obj instanceof Integer) {
				attachId = (Integer) obj;
			}
		this.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isCanAction) {
			ActionEvent e1 = new ActionEvent(this,e.getID(),this.getComponentName());
			getUParent().getEventAdaptor().actionPerformed(e1);
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
	public void repaintComponent() {
		repaint();
	}

}
