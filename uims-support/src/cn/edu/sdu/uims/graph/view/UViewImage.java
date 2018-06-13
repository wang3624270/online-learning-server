package cn.edu.sdu.uims.graph.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.graph.form.GraphPrintForm;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.trans.UPoint;

public class UViewImage implements GraphViewI {
	private int imageWidth, imageHeight;
	private String imgFileName = null;

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public UViewImage(UPaperTemplate paperTemplate, String imgFileNam) {
		init(paperTemplate);
		this.imgFileName = imgFileName;
	}

	public void init(UPaperTemplate paperTemplate) {
		imageWidth = (int) (paperTemplate.width * paperTemplate.dpi / 25.4f);
		imageHeight = (int) (paperTemplate.height * paperTemplate.dpi / 25.4f);
	}

	public boolean invokeJob(GraphPrintForm graphPrintForm) {
		// TODO Auto-generated method stub
		GraphModelI g2d = graphPrintForm.getCurrentGraphObject();
		if (g2d == null)
			return false;
		String fileName, tempFileName;
		if (imgFileName != null)
			tempFileName = imgFileName;
		else
			tempFileName = graphPrintForm.getOutputFileName();
		if (tempFileName == null)
			return false;
		ViewParameter p = graphPrintForm.getViewParameter();
		ControlParameter c = graphPrintForm.getControlParameter();
		BufferedImage img;
		Graphics g;
		try {
			for (int i = 0; i < g2d.getLayerSize(); i++) {
				img = new BufferedImage(imageWidth, imageHeight,
						BufferedImage.TYPE_3BYTE_BGR);
				g = img.getGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, imageWidth, imageHeight);
				g2d.drawLayer(g, new GraphModelViewParas(p, c, g2d.getGraphDataForm(),
						new UPoint(0, 0)), i);
				File outfile = new File(tempFileName + i + ".jpeg");
				FileOutputStream fos = new FileOutputStream(outfile);
				JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fos);
				JPEGEncodeParam jpegEncodeParam = jpegEncoder
						.getDefaultJPEGEncodeParam(img);
				jpegEncodeParam
						.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
				jpegEncodeParam.setXDensity(300);
				jpegEncodeParam.setYDensity(300);
				jpegEncoder.encode(img, jpegEncodeParam);
				fos.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public BufferedImage getGraphImg(GraphPrintForm graphPrintForm, int index) {
		// TODO Auto-generated method stub
		GraphModelI g2d = graphPrintForm.getCurrentGraphObject();
		if (g2d == null)
			return null;
		ViewParameter p = graphPrintForm.getViewParameter();
		ControlParameter c = graphPrintForm.getControlParameter();
		BufferedImage img;
		Graphics g;
		img = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_3BYTE_BGR);
		g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imageWidth, imageHeight);
		g2d.drawLayer(g, new GraphModelViewParas(p, c, g2d.getGraphDataForm(), new UPoint(0, 0)), index);
		return img;
	}

	@Override
	public void setGraphPrintForm(GraphPrintForm gForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageFormat getFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Printable getPrintable() {
		// TODO Auto-generated method stub
		return null;
	}

}
