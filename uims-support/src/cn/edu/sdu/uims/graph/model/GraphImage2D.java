package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UPoint;

public class GraphImage2D extends Graph2D{
	
	private String URL = null;
	private double scale = 1;
	private int drawX = 0;
	private int drawY = 0;
	private byte[] imageData= null;
	
	public GraphImage2D(String name) {
		super(name);
	}
	
	public GraphImage2D(String name, byte []imageData) {
		this(name);
		this.imageData = imageData;
	}
	
	public GraphImage2D(String name, byte []imageData,int scale) {
		this(name,imageData);
		this.scale = scale;
	}
	
	public String getURL() {
		return URL;
	}
	
	public void setURL(String url) {
		URL = url;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	public int getDrawX() {
		return drawX;
	}

	public void setDrawX(int drawX) {
		this.drawX = drawX;
	}

	public int getDrawY() {
		return drawY;
	}

	public void setDrawY(int drawY) {
		this.drawY = drawY;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
	
	private Image scaleImage(BufferedImage image) {
		int width = image.getWidth();                          // 得到源图宽
		int height = image.getHeight();                        // 得到源图长
		width = (int) (width * scale);
		height = (int) (height * scale);
		Image sImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(sImage, 0, 0, null); // 绘制缩小后的图
        g.dispose();
		return sImage;
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object data, UPoint shiftPoint) {
		if(imageData != null)
		{
			ByteArrayInputStream in = new ByteArrayInputStream(imageData);
			try {
				BufferedImage image = ImageIO.read(in);
				Image scaleImage = scaleImage(image);
				if(image != null)
					dc.drawImage(scaleImage, drawX, drawY,null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
