package cn.edu.sdu.uims.graph.model;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import org.dom4j.Element;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UParagraphContent;
import cn.edu.sdu.uims.doc.UDElementI;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;

public class GElementImage extends GElement2D implements UDElementI{
	public BufferedImage img;
	public boolean bordered = true;
	public String imageUrl;

	public GElementImage() {
	}

	public GElementImage(float x, float y, float w, float h, BufferedImage img) {
		box = new UFRect(x, y, w, h);
		this.img = img;
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object data,UPoint shiftPoint) {
		URect re = p.m.logicToView(box);
		BufferedImage tempImg = null;
		Object o = null;
		if (data != null) {
			o = getAttributeObject(data);
		}
		if (o == null) {
			if (img == null) {
				if (imageUrl == null)
					return;
				else {
					img = getBufferImage(c.isServer);
					tempImg = img;
				}
			} else {
				tempImg = img;
			}
		} else {
			if (o instanceof BufferedImage) {
				tempImg = (BufferedImage) o;
			} else if (o instanceof SerialBlob) {
				try {
					SerialBlob blob = (SerialBlob) o;
					tempImg = ImageIO.read(blob.getBinaryStream());
				} catch (Exception e) {
					tempImg = null;
				}
			}
		}
//		dc.drawImage(tempImg, re.x, re.y, re.w, re.h, null);
		if(re.w < 1 || re.h <1)
			dc.drawImage(tempImg, re.x, re.y, tempImg.getWidth(), tempImg.getHeight(), null);
		else 
			dc.drawImage(tempImg, re.x, re.y, re.w, re.h, null);
	}

	public boolean isBordered() {
		return bordered;
	}

	public void setBordered(boolean bordered) {
		this.bordered = bordered;
	}

	public int testPointOnElement(UFPoint fp) {
		if (fp.x >= box.x && fp.x <= box.x + box.w && fp.y >= box.y
				&& fp.y <= box.y + box.h) {
			return GraphConstants.GRAPH_SELECT_STATUS_INNER;
		}
		return GraphConstants.GRAPH_SELECT_STATUS_NO;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public BufferedImage getBufferImage(boolean isServer) {

		if (imageUrl != null) {
			try {
				byte [] bytes= null;
//				if(isServer)
//					bytes = ServerAccessoriesUtility.downloadServerContextFile(imageUrl);
//				else 
//					bytes = ClientAccessoriesUtility.downloadServerContextFile(imageUrl);
			 InputStream in = new ByteArrayInputStream(bytes);
			 return ImageIO.read(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}


	public void setParameterData(HashMap map, UFormI form, UComponentI obj) {
		// TODO Auto-generated method stub
		
	}


	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		
	}




	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}


	public URect getBoundRect() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getComponentName() {
		// TODO Auto-generated method stub
		return null;
	}


	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}


	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public HashMap getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}


	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}


	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}


	public void initContents() {
		// TODO Auto-generated method stub
		
	}


	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void onClose() {
		// TODO Auto-generated method stub
		
	}


	public void repaintComponent() {
		// TODO Auto-generated method stub
		
	}


	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}


	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}

	
	public void setActionComandString(String str) {
		// TODO Auto-generated method stub
		
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
		
	}


	public void setData(Object obj) {
		// TODO Auto-generated method stub
		
	}


	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		
	}

	
	public void setElementTemplate(UElementTemplate elementTemplate) {
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


	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub
		
	}


	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		
	}


	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}


	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		
	}


	public void setText(String arg0) {
		// TODO Auto-generated method stub
		
	}


	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		
	}


	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}


	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}


	public void setdisplayText(String text) {
		// TODO Auto-generated method stub
		
	}


	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		
	}

	
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		bordered = in.readBoolean();
		imageUrl = readString(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeBoolean(bordered);
		writeString(out,imageUrl);
	}


	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	
	public void exDocument(UDocumentUtilBaseI util, UDocument doc,
			UParagraphContent constant) {
		// TODO Auto-generated method stub
		
	}


	public void initContent(UParagraphContent constant, HashMap requestMap,
			HashMap respondMap) {
		// TODO Auto-generated method stub
		
	}


	public DataInitHandlerI getDataInitHandler(int compNum) {
		// TODO Auto-generated method stub
		return null;
	}


	public DataInitHandlerI getDataInitHandler() {
		// TODO Auto-generated method stub
		return null;
	}


	public Object[] getDataItemById(HashMap requestMap, HashMap respondMap,
			Object dataId) {
		// TODO Auto-generated method stub
		return null;
	}


	public List getInitDataList(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		return null;
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
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("imageUrl", imageUrl);
		if(!bordered)
			ge.addAttribute("pen", "null");
		ge.addAttribute("h", ""+box.h);
		ge.addAttribute("w", ""+box.w);
		ge.addAttribute("y", ""+box.y);
		ge.addAttribute("x", ""+box.x);
		super.exportElementToDoc(ge);
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
