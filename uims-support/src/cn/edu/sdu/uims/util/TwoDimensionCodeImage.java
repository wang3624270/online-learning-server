package cn.edu.sdu.uims.util;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

public class TwoDimensionCodeImage implements QRCodeImage {

	private BufferedImage bufImg; 
	
	public TwoDimensionCodeImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}
	
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return bufImg.getHeight();
	}

	@Override
	public int getPixel(int x, int y) {
		// TODO Auto-generated method stub
		return bufImg.getRGB(x, y);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return bufImg.getWidth();
	}

}
