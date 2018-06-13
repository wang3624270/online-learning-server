package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import cn.edu.sdu.uims.component.complex.def.UImageIconTemplate;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.form.impl.UImageIconForm;


public class UImageIconPanel extends UComplexPanel implements MouseListener{
	private  List <UImageIconForm> imageList;
	private JPanel innerPanel;
	private JScrollPane scrollPanel;
	private boolean canActionEvent = false;
	public void initContents() {
		// TODO Auto-generated method stub
		innerPanel = new JPanel();
		scrollPanel = new JScrollPane(innerPanel);
		this.setLayout(new BorderLayout());
		add(scrollPanel, BorderLayout.CENTER);
		innerPanel.addMouseListener(this);
	}

	public void resetPanelSize(){
		
	}
	public void dispImageIconByData(){
		innerPanel.removeAll();
		innerPanel.setLayout(null);
		UImageIconTemplate  template = (UImageIconTemplate)elementTemplate;
		if(template == null)
			return;
		if(imageList == null) {
			return;
		}
		int colNum, rowNum, col, row;
		if(template.colNum < 0 && template.rowNum < 0){
			colNum = imageList.size();
			rowNum = 1;
		}else if(template.colNum > 0){
			colNum = template.colNum;
			rowNum =  imageList.size() / colNum;
			if(imageList.size() % colNum > 0)
				rowNum ++;
		}else {
			rowNum = template.rowNum;
			colNum =  imageList.size() / rowNum;
			if(imageList.size() % rowNum > 0)
				colNum ++;
		}	
		int tdw, tdh, ishiftx, isfhity,lshiftx, lshifty;
		if(template.titleLayout == null) {
			tdw = 0;
			tdh = 0;
			ishiftx = 0;
			isfhity = 0;
			lshiftx = 0;
			lshifty = 0;
		}else if(template.titleLayout.equals("north") || template.titleLayout.equals("south")){
			tdw = 0;
			tdh = template.titileHeight;
			lshiftx = 0;
			if(template.titleLayout.equals("north")) {
				ishiftx = 0;
				isfhity =  template.titileHeight;
				lshifty =  0;
			}else {
				ishiftx = 0;
				isfhity = 0;
				lshifty = template.imageHeight;
			}
		}else {
			tdw = template.titileHeight;
			tdh = template.titileHeight;
			lshifty = 0;
			if(template.titleLayout.equals("west")) {
				isfhity = 0;
				ishiftx =  template.titileHeight;
				lshiftx = 0;
			}else {
				ishiftx = 0;
				isfhity = 0;
				lshiftx = template.imageWidth;
			}
		}
		int x, y = template.blankWidth;
		int count = 0; 
		InnerImagePanel imgP;
		JLabel label;
		UImageIconForm imageForm;
		for(row  = 0; row  <  rowNum; row ++) {
			x = template.blankWidth;
			for(col = 0; col< colNum;col++, count++) {
				if(count >= imageList.size())
					continue;
				imageForm = imageList.get(count); 
				imgP = new InnerImagePanel(getImgByBytes((byte[])imageForm.getImageData()));
				imgP.setBounds(x+ishiftx, y+isfhity, template.imageWidth, template.imageHeight);
				imageForm.setX(x+ishiftx);
				imageForm.setY(y+isfhity);
				innerPanel.add(imgP);
				if(tdw!= 0 || tdh != 0) {
					label = new JLabel(imageForm.getImageTitle());
					if(tdw != 0){
						label.setBounds(x+ishiftx+lshiftx, y + isfhity + lshifty, tdw, template.imageHeight);
						label.setVerticalAlignment(SwingConstants.CENTER);
					}
					else{
						label.setBounds(x+ishiftx+lshiftx, y + isfhity + lshifty, template.imageWidth, tdh);
						label.setHorizontalAlignment(SwingConstants.CENTER);
					}
					innerPanel.add(label);
				}
				x += template.imageWidth + tdw + template.blankWidth;
			}
			y += template.imageHeight +tdh + template.blankWidth;
		}
		int tw = template.blankWidth *2 + template.intervalW*(colNum-1) + (template.imageWidth + tdw)*colNum;
		int th = template.blankWidth *2 + template.intervalW*(rowNum-1) + (template.imageHeight + tdh)*rowNum;
		innerPanel.setSize(new Dimension(tw,th));
		innerPanel.setPreferredSize(new Dimension(tw,th));
		this.updateUI();
	}
	public Object getData() {
		return null;
	}

	public void setData(Object obj) {
		imageList = (List <UImageIconForm> )obj;
		dispImageIconByData();
	}
	public BufferedImage getImgByBytes(byte[] bytes) {
		if(bytes == null || bytes.length == 0)
			return null;
		BufferedImage img = null;
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			img = ImageIO.read(in);
		} catch (Exception e) {
			img = null;
		}
		return img;
	}
	public void addEvents(UEventAttribute[] events) {
		int i;

		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				canActionEvent = true;
			} 
		}
	}
	
	private class InnerImagePanel extends JPanel{
		private BufferedImage img;

		public InnerImagePanel(BufferedImage img){
			this.img = img;
		}
		public BufferedImage getImg() {
			return img;
		}

		public void setImg(BufferedImage img) {
			this.img = img;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawRect(1, 1, this.getWidth()-2, this.getHeight()-2);
			if (img != null)
				g.drawImage(img, 2, 2, this.getWidth()-4, this
						.getHeight()-4, this);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		UImageIconTemplate  template = (UImageIconTemplate)elementTemplate;
		if(template == null)
			return;
		if(imageList == null)
			return;
		int x = e.getX();
		int y = e.getY();
		int i = 0;
		UImageIconForm form, selectForm= null;
		while(selectForm== null && i < imageList.size()) {
			form = (UImageIconForm)imageList.get(i);
			if(x > form.getX() && x < form.getX() + template.imageWidth  &&
					y > form.getY() && y < template.imageHeight) {
				selectForm = form;
			}else
				i++;
		}
		if(selectForm != null) {
			if(canActionEvent) {
				ActionEvent ae = new ActionEvent(this, 0, selectForm.getImageId());
				getUParent().getEventAdaptor().processEvent(ae,EventConstants.EVENT_ACTION ,"action");
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
