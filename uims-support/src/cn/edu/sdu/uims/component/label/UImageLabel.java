package cn.edu.sdu.uims.component.label;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UImageLabelTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.pi.ImageDataDriverI;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UimsUtils;

public class UImageLabel extends JPanel implements UComponentI,MouseWheelListener{
	
	JLabel jlPic; //图片，用于拖动
	protected UTemplate template;
	private BufferedImage  img = null;
	private String componentName = null;
	private UPanelI parentI = null;
	protected UElementTemplate elementTemplate;
	private int count = 0;
	private int j = 1;
	private  Point p=new Point(0,0);
	
	public BufferedImage getImg() {
		return img;
	}
	public void initContents() {
		if(template == null){
			return;
		}
		UImageLabelTemplate t = (UImageLabelTemplate)template;
		this.addMouseWheelListener(this);
		this.setVisible(true); 
		if(t == null || t.fileName == null)
			return ;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, this.getWidth(), this.getHeight());
		if (img == null)
			return;
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
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return this.template;
	}
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.template = template;

	}
	
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		
	}
	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if(elementTemplate != null)
			return new URect(elementTemplate.x,elementTemplate.y,elementTemplate.w, elementTemplate.h);
		else
			return null;
	}
	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}
	public String getComponentName() {
		// TODO Auto-generated method stub
		return this.componentName;
	}
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}
	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return this.parentI;
	}
	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}
	public void init() {
		// TODO Auto-generated method stub
		
	}
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub
		
	}
	public void setArrangeType(int type) {
		// TODO Auto-generated method stub
		
	}
	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
//		if(border !=null ){
//			this.setBorder(border.getObject());
		this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
		
	}
	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub
		
	}
	public void setColor(UColor c) {
		// TODO Auto-generated method stub
		
	}
	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		this.componentName = name;
	}
	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null) 
			img = null;
		else {
			if(obj instanceof Integer) {
				ImageDataDriverI driver = UimsFactory.getImageDataDriverI();
				if(driver != null ){				
					img = (BufferedImage)driver.getImageData((Integer)obj);
				}
			}
			else
				if(obj instanceof byte[]){
				try{
					img = ImageIO.read(new ByteArrayInputStream((byte [])obj));
				}catch(Exception e){
					img = null;
				}
			}
			else if(obj instanceof BufferedImage) {
				img = (BufferedImage)obj;
			}else if(obj instanceof SerialBlob){
				SerialBlob blob = (SerialBlob)obj;
				try{
						img = ImageIO.read(blob.getBinaryStream());
				}catch(Exception e){
						img = null;
				}
			}else if(obj instanceof String){
				String name = (String) obj;
				try {
					FileInputStream in = new FileInputStream(name);
					img = ImageIO.read(in);
					
				}catch(Exception e){
					img = null;
				}
			}
		}
		this.repaint();
	}
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		
	}
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		
	}
	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub
		
	}
	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		
	}
	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub
		
	}
	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setInitComponentData(Object data) {
		// TODO Auto-generated method stub
		
	}
	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		
	}
	public void setText(String arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		parentI = parent;
	}
	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}
	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
//		this.setLocation(x, y);
//		this.setPreferredSize(new Dimension(w,h));
	}
	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}
	public void onClose(){
		
	}
	public void repaintComponent() {
	}
	public void setParameters(HashMap paras){
		
	}
	public HashMap getParameters(){
		return null;
	}
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}



	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setdisplayText(String text) {
		// TODO Auto-generated method stub
		
	}
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setActionComandString(String str) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setUserData(Object obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub	
	}
	
	public void rotate( int rotate){
		img = UimsUtils.rotateImage( img, rotate);
		repaint();
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		int num = e.getWheelRotation()*20;
		count += num;
		Graphics g = this.getGraphics();
		if(img == null)
			return;
		
		int dw = this.getWidth();
		int dh = this.getHeight();
		int iw = img.getWidth()+count;
		int ih = img.getHeight()+count;
		int x,y,w,h;
		//double is = (double)iw/ih;
		//double ds = (double)dw/dh;
		/*if(is > ds) {
			if(iw < dw) {
				w = iw;
				h = ih;
			}else {
				w = dw;
				h = ih*w/iw;
			}
			w = iw;
			h = ih;
		}else {
			if(ih < dh) {
				h = ih;
				w = iw;
			}else {
				h = dh;
				w = iw*h/ih;
			}
			h = ih;
			w = iw;
		}*/
		w = iw;
		h = ih;
		x = (dw-iw)/2;
	    y = (dh-ih)/2;
		//y = (dh-ih)/2;
		//int num = e.getWheelRotation();
		//this.setIcon(new ImageIcon( image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));   //重点1：按所给的大小来设置图片，不管图片有多大，总是全图显示，可能导致失真！你可以加一个大小限制，超过图片大小了则不再放大。
	   /* if(iw > dw ||ih > dh) {
	    	g.drawImage(img, x-(e.getX()-dw/2), y-(e.getY()-dh/2), w, h, this);
	    }else {
	    	g.drawImage(img, x, y, w, h, this);
	    	 
	    }*/
	    g.drawImage(img, x-(e.getX()-dw/2), y-(e.getY()-dh/2), w, h, this);
		if(j %3 ==0) {
			this.repaint();  //刷新屏幕
		}
		j++;
	}
	
}

