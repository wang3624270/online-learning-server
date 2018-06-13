package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.form.InfoPhotoForm;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.view.UVUtil;

public class GElementDataImage extends GElementImage{
	private BufferedImage img;
	private boolean bordered = true;
	private String perName;
	private String perNum;
	private Integer personId;
	private String remark;
	private double width;
	private double height;
	private String fontName="宋体";
	
	private int horizontalAlignment;

	private int verticalAlignment;
	

	public GElementDataImage(float x, float y, float w, float h, InfoPhotoForm photoForm)
	{
		box = new UFRect(x, y, w, h);
		this.addedAttributeForm=photoForm;
		this.perName=photoForm.getPerName();
		this.perNum=photoForm.getPerNum();
		this.personId=photoForm.getPersonId();
		SerialBlob b=photoForm.getPhoto();
		if(b!=null)
		{
			try {
				this.img=ImageIO.read(b.getBinaryStream());
			} catch (SerialException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			img=null;
		}
		
	}
	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object data,UPoint shiftPoint) {
		URect re = p.m.logicToView(box);
		BufferedImage tempImg = null;
		UFont uFont=(UFont)UFactory.getModelSession().getFontByName(fontName);
		UColor uColor=(UColor)UFactory.getModelSession().getColorByName("colorBlack");
		int nameY=(int)box.y+155;
		int numW=(int)box.y+170;
		Object o = null;
		if (data != null) {
			o = getAttributeObject(data);
		}
		if (o == null) {
			if (img == null) {
				UVUtil.drawStringBox(dc, "没有要审核图片！", uColor, uFont, (int)box.x, nameY-100, 100, 15, horizontalAlignment, verticalAlignment,c.isServer);
				UVUtil.drawStringBox(dc, perNum, uColor, uFont, (int)box.x, numW, 100, 15, horizontalAlignment, verticalAlignment,c.isServer);
			} else {
				tempImg = img;
				dc.drawImage(tempImg, re.x, re.y, re.w, re.h, null);
				UVUtil.drawStringBox(dc, img.getWidth()+" * "+img.getHeight(), uColor, uFont, (int)box.x, numW, 100, 15, horizontalAlignment, verticalAlignment,c.isServer);
			}
			
			UVUtil.drawStringBox(dc, perName, uColor, uFont, (int)box.x, nameY, 100, 15, horizontalAlignment, verticalAlignment,c.isServer);
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
			dc.drawImage(tempImg, re.x, re.y, re.w, re.h, null);
			UVUtil.drawStringBox(dc, tempImg.getWidth()+" * "+tempImg.getHeight(), uColor, uFont, (int)box.x, numW, 100, 15, horizontalAlignment, verticalAlignment,c.isServer);
		}
		
		
		
		
		
//		UVUtil.drawStringBox(dc, perNum, uColor, uFont, (int)rect.x, numY, 100, 15, horizontalAlignment, verticalAlignment);
		
	}
	
	public int testPointOnElement(UFPoint fp) {
		if (fp.x >= box.x && fp.x <= box.x+box.w && fp.y >= box.y && fp.y <= box.y + box.h) {
			return GraphConstants.GRAPH_SELECT_STATUS_INNER;
		}
		else
		{
			return GraphConstants.GRAPH_SELECT_STATUS_NO;
		}
		
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		bordered = in.readBoolean();
		perName = readString(in);
		perNum = readString(in);
		int ii = in.readInt();
		if(ii < 0)
			personId = null;
		else 
			personId = ii;
		remark = readString(in);
		width = in.readDouble();
		height = in.readDouble();
		fontName= readString(in);
		horizontalAlignment = in.readInt();
		verticalAlignment = in.readInt();
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeBoolean(bordered);
		writeString(out,perName);
		writeString(out,perNum);
		if(personId== null)
			out.writeInt(-1);
		else 
			out.writeInt(personId.intValue());
		writeString(out,remark);
		out.writeDouble(width);
		out.writeDouble(height);
		writeString(out,fontName);
		out.writeInt(horizontalAlignment);
		out.writeInt(verticalAlignment);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("", "");
		super.exportElementToDoc(ge);
	}	
	
}
