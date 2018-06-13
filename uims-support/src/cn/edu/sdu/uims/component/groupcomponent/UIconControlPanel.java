package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cn.edu.sdu.uims.form.impl.UImageIconForm;
import cn.edu.sdu.uims.util.UimsUtils;

public class UIconControlPanel extends JPanel implements MouseListener{

	private UGroupComponentPageIcon owner;
	private List<UImageIconForm> iconList;
	private List<Rectangle> rectList;
	private BufferedImage backImage, forwardImage;
	public static final int buttonW = 40, buttonH = 40;
	
	public UIconControlPanel(UGroupComponentPageIcon owner){
		this.owner = owner;
		addMouseListener(this);
		backImage = UimsUtils.getIconImageByFileName("/icon/back.gif");
		forwardImage = UimsUtils.getIconImageByFileName("/icon/forward.gif");
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawControlImage(g);
	}
	
	
	public void drawIcon(Graphics g, UImageIconForm form, int x, int y, int w, int h, int th){
		BufferedImage img = (BufferedImage)form.getImageData();
		g.drawRect(x+1, y+1, w-2, h-2 -th);
		if (img != null)
			g.drawImage(img, x+2, y+2, w-4, h-4-th, this);
		if(th > 0)
			g.drawString(form.getImageTitle(), x, y+h-2);
		if(form.getNote() != null) {
			g.drawString(form.getNote(), x+w-20, y+10);
		}
	}
	
	
	public void drawControlImage(Graphics g) {
		int model = owner.getCurrentDispMode();
		int index = owner.getCurrentIndex();
		int width, height,dww, dhh,xo,yo, x, y, c,r;
		int dw = 40;
		int dh = 40;
		int w = 160;
		int h = 190;
		int th = 30;
		int col = 3;
		int row = 2;
		Dimension size = this.getSize();
		width = size.width;
		height = size.height;
		if(model == UGroupComponentPageIcon.DISP_MODE_ICON_PANEL) {
			col = 1;
			row = iconList.size();
			dw = 0;
			th = 0;
			if(w > size.width) {
				w = size.width;
				h = w;
			}
			if(h * row >= size.height-buttonH) {
				h =  (size.height-buttonH) /row;
				w = h;
			}
			dh = 0;
			xo = (size.width - w)/2;
			yo = 0;
		}else {
			dww = w*col + dw*(col-1);
			dhh = h*row + dh*(row-1);
			xo = (width-dww)/2;
			yo = (height - dhh)/2;
		}
		x = xo;
		y = yo;
		Rectangle rect;
		int n;
		Color oldColor;
		for(r = 0; r < row; r++) {
			for(c = 0; c < col; c++) {
				n =r*col+c; 
				if( n<iconList.size()) {
					rect = rectList.get(n);
					rect.x = xo + (w+dw)*c;
					rect.y = yo+(h+dh)*r;
					rect.width = w;
					rect.height = h;
					drawIcon(g, iconList.get(n), rect.x, rect.y, rect.width,rect.height, th);
				}
			}
		}
		if(model == 1){
			g.drawImage(backImage, width-buttonW, height-buttonH, buttonW, buttonH, this);
		}else {
			g.drawImage(forwardImage, width-buttonW, height-buttonH, buttonW, buttonH, this);
		}
	}
	void changeView(int x, int y) {
		Dimension size = this.getSize();
		if(x >= size.width - buttonW && x <= size.width && y >= size.height -buttonH && y < size.height){
			owner.exchangeDispMode();
		}else {
			Rectangle r;
			for(int i = 0; i <rectList.size();i++) {
				r = rectList.get(i);
				if(x >= r.x && x <= r.x + r.width && y >= r.y && y < r.y + r.height){
					if(owner.getCurrentDispMode() == owner.DISP_MODE_ICON_ONLY)
						owner.exchangeDispMode();
					owner.setCurrentPagePanel(i);
					return;
				}
			}
		}
	}
	public void initIconData(List<UPageComponentDescription> comList){
		rectList = new ArrayList<Rectangle>();
		iconList = new ArrayList<UImageIconForm>();
		UImageIconForm f;
		UPageComponentDescription des;
		for(int i = 0; i < comList.size();i++){
			f = new UImageIconForm();
			des = comList.get(i);
			f.setImageId(des.name);
			f.setImageTitle(des.title);
			f.setImageData(UimsUtils.getIconImageByFileName(des.iconName));
			iconList.add(f);
			rectList.add(new Rectangle());
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		changeView(e.getX(),e.getY());
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
	
	public void setPageIconDisplayInfo(List<String> infoList) {
		// TODO Auto-generated method stub
		if(infoList == null || iconList == null)
			return;
		for(int i = 0; i < infoList.size();i++) {
			if(i >= iconList.size())
				break;
			iconList.get(i).setNote(infoList.get(i));
		}
	}

}
