package cn.edu.sdu.uims.component.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UImagePanel extends JPanel implements UComponentI {
	private String componentName;
	private UPanelI parent = null;
	private BufferedImage image = null;
	protected UElementTemplate elementTemplate;

	public void UImagePanel() {
//		this.setOpaque(true);
		this.setBorder(new LineBorder(Color.black, 1, false));
	}

	public void paint(Graphics g) {
//		super.paint(g);
		Dimension d;
		int x, y, w, h;
		double a;
		d = this.getSize();
		Color oldColor = g.getColor();
		if (image != null) {
			a = (double) image.getWidth() / image.getHeight();
			if (a * d.height > d.width) {
				x = 0;
				w = d.width;
				h = (int) (d.width / a);
				y = (d.height - h) / 2;
			} else {
				y = 0;
				h = d.height;
				w = (int) (d.height * a);
				x = (d.width - w) / 2;
			}
			g.drawImage(image, x, y, w, h, this);
			g.setColor(new Color(0,0,0));
			g.drawRect(0, 0, d.width+1, d.height+1);
			g.setColor(oldColor);
		}
		else {
//			g.setColor(this.getBackground());
//			g.fillRect(0, 0, d.width-2, d.height-2);
		}
	}

	public void addEvents(UEventAttribute[] events) {
	}

	public URect getBoundRect() {
		if(elementTemplate != null)
			return new URect(elementTemplate.x,elementTemplate.y,elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public Component getAWTComponent() {
		return this;
	}

	public String getComponentName() {
		return componentName;
	}

	public Object getData() {
		return null;
	}

	public FilterI getFilter() {
		return null;
	}


	public UTemplate getTemplate() {
		return null;
	}

	public UPanelI getUParent() {
		return parent;
	}

	public boolean hasEmptyFileds() {
		return false;
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public void initContents() {
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

	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		componentName = name;
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) {
			image = null;
			Rectangle r = this.getBounds();
			this.getParent().repaint(r.x, r.y, r.width, r.height);
			return;
		}
		try {
			if (obj instanceof Image) {
				image = (BufferedImage) obj;
			} else if (obj instanceof String) {
				String fileName = (String) obj;
//				CommonMethod.getIconByFileName(fileName);
				InputStream inputStream = this.getClass().getResourceAsStream(
						fileName);
				//image = ImageIO.read(new File(fileName));
				if(inputStream != null) {
					image=ImageIO.read(inputStream);
				}
				repaint();
			} else if (obj instanceof Blob) {
				Blob b = (Blob) obj;
				image = ImageIO.read(b.getBinaryStream());
				repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub

	}

	public void setText(String arg0) {
		setData(arg0);
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}
	public void setInitComponentData(Object data){
		
	}
	public String getText() {
		// TODO Auto-generated method stub
		return null;
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



}
