package cn.edu.sdu.uims.graph.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;

import org.dom4j.Element;

import com.google.gson.JsonObject;
import com.itextpdf.awt.PdfGraphics2DForUims;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.uims.constant.UMethodConstants;
import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.model.util.XmlGraphMoelUtils;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.view.UVUtil;

public class GElementText extends GElement2D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UFPoint po = new UFPoint();
	public String text;
	public String fontName;
	public int rotate = 0;

	// 刘洋add
	private int horizontalAlignment;

	private int verticalAlignment;

	private boolean bordered = false;

	public GElementText() {
	}

	public GElementText(double xo, double yo, String text, String fontName) {
		po.x = xo;
		po.y = yo;
		this.text = text;
		this.fontName = fontName;
	}

	public GElementText(double xo, double yo, int w, int h, String text,
			String fontName) {
		this(xo, yo, text, fontName);
		this.box = new UFRect(po.x, po.y, w, h);
	}
	public GElementText(double xo, double yo, double w, double h, String text,
			String fontName) {
		this(xo, yo, text, fontName);
		this.box = new UFRect(po.x, po.y, w, h);
		bordered = true;
		colorName = "colorBlack";
	}
	
	public GElementText(double xo, double yo, double w, double h, String text,
			String fontName, String colorName, boolean bordered) {
		this(xo, yo, text, fontName);
		this.box = new UFRect(po.x, po.y, w, h);
		this.bordered = bordered;
		this.colorName = colorName;
	}
	
	public GElementText(double xo, double yo, double minx,double miny , double w, double h, String text,
			String fontName, String colorName,boolean bordered) {
		this(xo, yo, text, fontName);
		this.box = new UFRect(minx, miny, w, h);
		this.bordered = bordered;
		this.colorName = colorName;
	}
	
	public GElementText(double xo, double yo, double minx,double miny , double w, double h, String text,
			String fontName, String colorName,Integer hAlign,Integer vAlign,boolean bordered) {
		this(xo, yo, text, fontName);
		this.box = new UFRect(minx, miny, w, h);
		this.bordered = bordered;
		this.colorName = colorName;
		this.horizontalAlignment = hAlign;
		this.verticalAlignment = vAlign;
	}
	

	/**
	 * @author 洋
	 */
	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object data,UPoint shiftPoint) {
		UFPoint poi = new UFPoint();
		poi.x = po.x;
		poi.y = po.y;
		if(c!= null && c.shiftMap !=null) {
			UFPoint spo = c.shiftMap.get(name);
			if(spo != null) {
				poi.x += spo.x;
				poi.y += spo.y;
			}
		}
		UFPoint pi = new UFPoint();
		pi.x = poi.x + box.w;
		pi.y = poi.y + box.h;
		UPoint p1, p2;
		Font oldFont = null;
		Color oldColor = null;
		UFont uFont = null;
		UColor uColor = null;
		String par = null;
		if (data != null) {
			Object o = getAttributeObject(data);
			if (o != null)
				par = o.toString();
		}
		if (fontName != null)
			oldFont = dc.getFont();
		uFont = UFactory.getModelSession().getFontByName(fontName);
		dc.setFont(uFont.font);
		AttributedString attributeString;
		if (colorName != null)
			oldColor = dc.getColor();
		uColor = UFactory.getModelSession().getColorByName(colorName);
		dc.setColor(uColor.color);
		p1 = p.m.logicToView(poi);
		p2 = p.m.logicToView(pi);		
		if (par == null) {
			if (text == null) {
				text = "";
			}
			if (box.w + box.h < 1) {
				if(c.isServer && text.length() > 0) {
				attributeString = new AttributedString(text);
				attributeString.addAttribute(TextAttribute.FONT, new Font(uFont.fontName,
						Font.PLAIN, uFont.size));
				((PdfGraphics2DForUims) dc).setCHNFont(uFont.fileName);
				((PdfGraphics2DForUims) dc).setCHNFontSize(uFont.size);
				dc.drawString(attributeString.getIterator(), p1.x, p1.y);
				}
				else {
					dc.drawString(text, p1.x, p1.y);					
				}
			} else {
				if (rotate == 0) {
					UVUtil.drawStringBox(dc, text, uColor, uFont, p1.x, p1.y,
							p2.x - p1.x, p2.y - p1.y, horizontalAlignment,
							verticalAlignment, 0, c.isServer);
				} else {
					UVUtil
							.drawStringRotate(dc, text, uColor, uFont, p1.x,
									p1.y, p2.x - p1.x, p2.y - p1.y, rotate,
									Color.white);
				}
			}
		} else {
			if (!(c != null && c.useDataParameter)) {
				if (box.w + box.h < 1) {
					if(c.isServer &&((String)par).length() > 0) {
						attributeString = new AttributedString((String)par);
						attributeString.addAttribute(TextAttribute.FONT, uFont.font);					
						((PdfGraphics2DForUims) dc).setCHNFont(uFont.fileName);
						((PdfGraphics2DForUims) dc).setCHNFontSize(uFont.size);
					dc.drawString(attributeString.getIterator(), p1.x, p1.y);
					}
					else {
						dc.drawString((String) par, p1.x, p1.y);						
					}
				} else {
					if (rotate == 0) {
						UVUtil.drawStringBox(dc, (String) par, uColor, uFont,
								p1.x, p1.y, p2.x - p1.x, p2.y - p1.y,
								horizontalAlignment, verticalAlignment, 0,c.isServer);
					} else {
						UVUtil.drawStringRotate(dc, (String) par, uColor,
								uFont, p1.x, p1.y, p2.x - p1.x, p2.y - p1.y,
								rotate, Color.white);
					}
				}
			}
		}
		if (isBordered()) {
			UVUtil.drawBorderBox(dc, p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
		}
		if (fontName != null)
			dc.setFont(oldFont);
		if (colorName != null)
			dc.setColor(oldColor);
	}

	public int getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(int horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public int getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(int verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public boolean isBordered() {
		return bordered;
	}

	public void setBordered(boolean bordered) {
		this.bordered = bordered;
	}

	public UFPoint getPo() {
		return po;
	}

	public void setPo(UFPoint po) {
		this.po = po;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int testPointOnElement(UFPoint fp) {
		double x1 = po.x;
		double y1 = po.y;
		double x2 = po.x + box.w;
		double y2 = po.y + box.h;
		if ((x1 < fp.x && fp.x <= x2) && (y1 < fp.y && fp.y < y2)) {
			return GraphConstants.GRAPH_SELECT_STATUS_INNER;
		} else if ((x1 >= (fp.x - 1) && x1 <= (fp.x + 1))
				&& (y1 >= (fp.y - 1) && y1 <= (fp.y + 1))) {
			UMethodConstants.GRAPH_SELECT_STATUS_BOUND_TYPE = 1;
			return GraphConstants.GRAPH_SELECT_STATUS_BOUND;
		} else if ((x1 >= (fp.x - 1) && x1 <= (fp.x + 1))
				&& (y2 >= (fp.y - 1) && y2 <= (fp.y + 1))) {
			UMethodConstants.GRAPH_SELECT_STATUS_BOUND_TYPE = 4;
			return GraphConstants.GRAPH_SELECT_STATUS_BOUND;
		} else if ((x2 >= (fp.x - 1) && x2 <= (fp.x + 1))
				&& (y1 >= (fp.y - 1) && y1 <= (fp.y + 1))) {
			UMethodConstants.GRAPH_SELECT_STATUS_BOUND_TYPE = 2;
			return GraphConstants.GRAPH_SELECT_STATUS_BOUND;
		} else if ((x2 >= (fp.x - 1) && x2 <= (fp.x + 1))
				&& (y2 >= (fp.y - 1) && y2 <= (fp.y + 1))) {
			UMethodConstants.GRAPH_SELECT_STATUS_BOUND_TYPE = 3;
			return GraphConstants.GRAPH_SELECT_STATUS_BOUND;
		}
		return GraphConstants.GRAPH_SELECT_STATUS_NO;
	}

	/***
	 * @function 获得拖动操作不变的点
	 * @param selectStatus
	 *            选中点的状态
	 * @return 点的集合
	 */
	@SuppressWarnings("unchecked")
	public ArrayList getDragPoints(int selectStatus) {
		double x = box.x;
		double y = box.y;
		double w = box.w;
		double h = box.h;
		UFPoint po;
		ArrayList pointList = new ArrayList();
		switch (selectStatus) {
		case 1:
			po = new UFPoint(x + w, y + h);
			pointList.add(po);
			break;
		case 2:
			po = new UFPoint(x, y + h);
			pointList.add(po);
			break;
		case 3:
			po = new UFPoint(x, y);
			pointList.add(po);
			break;
		case 4:
			po = new UFPoint(x + w, y);
			pointList.add(po);
			break;
		}
		return pointList;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		readUFPoint(in,po);
		text = readString(in);
		fontName = readString(in);
		rotate = in.readInt();
		horizontalAlignment = in.readInt();
		verticalAlignment= in.readInt();
		bordered = in.readBoolean();
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeUFPoint(out,po);
		writeString(out, text);
		writeString(out,fontName);
		out.writeInt(rotate);;
		out.writeInt(horizontalAlignment);
		out.writeInt(verticalAlignment);
		out.writeBoolean(bordered);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		if(text != null && !text.equals(""))
			ge.addAttribute("text",text );
		ge.addAttribute("font", fontName);
		ge.addAttribute("rotate", ""+rotate);
		if(horizontalAlignment != 0)
		ge.addAttribute("horizontalAlignment", XmlGraphMoelUtils.changeAlignmentValueToString(horizontalAlignment));
		if(verticalAlignment != 0)
			ge.addAttribute("verticalAlignment",  XmlGraphMoelUtils.changeAlignmentValueToString(verticalAlignment));
		if(!bordered)
		ge.addAttribute("pen", "null");
		ge.addAttribute("h", ""+box.h);
		ge.addAttribute("w", ""+box.w);
		ge.addAttribute("x", ""+po.x);
		ge.addAttribute("type","text");
		super.exportElementToDoc(ge);
	}

	@Override
	public Object getJSonObject(ViewParameter viewParameter) {
		// TODO Auto-generated method stub
		String text = this.getText();
		if(!text.equals(""))
		{
			
			JsonObject obj = new JsonObject();
			JsonObject content = new JsonObject();
			
			JsonObject bObj = new JsonObject();
			bObj.addProperty("x0", this.box.x);
			bObj.addProperty("y0", this.box.y);
			bObj.addProperty("w", this.box.w);
			bObj.addProperty("h", this.box.h);
			
			content.addProperty("text", this.getText());
			content.addProperty("rotate", this.rotate);
			content.add("bound", bObj);
			obj.add("GElementText", content);
			return obj;
		}
		return null;
	}	
	
}
