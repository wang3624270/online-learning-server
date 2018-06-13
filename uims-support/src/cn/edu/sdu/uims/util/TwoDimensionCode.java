package cn.edu.sdu.uims.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;

public class TwoDimensionCode {
	
	public TwoDimensionCode() {
		
	}
	
	/**
	 * 生成二维码
	 * */
	public void encoderQRCode(String content, String imgPath, String imgType,int size) {
		BufferedImage bufImg = this.qRcodeCommon(content, imgType, size);
		File imgFile = new File(imgPath);
		try {
			ImageIO.write(bufImg, imgType, imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码图片
	 * */
	public void encoderQRCode(String content, OutputStream output,String imgType, int size) {
		BufferedImage bufImg = this.qRcodeCommon(content, imgType, size);
		try {
			// 生成二维码图片
			ImageIO.write(bufImg, imgType, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成二维码公共方法
	 * */
	private BufferedImage qRcodeCommon(String content,String imgType,int size) {
		BufferedImage bufImg = null;

		try {
			Qrcode qrcodeHandler = new Qrcode();

			// 设置二维码排错率,可选L(7%)、M(15%)、Q(25%)、H(30%),排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');

			// 设置设置二维码尺寸,取值范围1-40,值越大尺寸越大,可存储的信息越大
			qrcodeHandler.setQrcodeVersion(size);

			// 获得内容的字节数组，设置编码格式
			byte[] bytes = content.getBytes("utf-8");

			// 图片尺寸
			int imgSize = 67 + 12 * (size - 1);

			bufImg = new BufferedImage(imgSize, imgSize,BufferedImage.TYPE_INT_RGB);

			Graphics2D gs = bufImg.createGraphics();
			// 设置背景颜色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// 设定图像颜色BLACK
			gs.setColor(Color.BLACK);

			// 设置偏移量，不设置可能导致解析出错
			int pixoff = 2;

			// 生成二维码
			if (bytes.length > 0 && bytes.length < 800) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(bytes);
				for (int i = 0; i < codeOut.length; ++i) {
					for (int j = 0; j < codeOut.length; ++j) {
						if (codeOut[j][i])
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
					}
				}
			} else
				throw new Exception("QRCode content bytes length = " + bytes.length + " not in [0, 800].");

			gs.dispose();
			bufImg.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bufImg;
	}
	
	/**
	 * 解析二维码
	 * */
	public String decoderQRcode(String imagePath) {
		// QRCode 二维码图片文件
		File imageFile = new File(imagePath);
		BufferedImage bufImg = null;
		String content = null;
		try {
			bufImg = ImageIO.read(imageFile);
			QRCodeDecoder decoder = new QRCodeDecoder();
			content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)),"utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 解析二维码
	 * */
	public String decoderQRcode(InputStream input) {
		BufferedImage bufImg = null;
		String content = null;
		
		try {
			bufImg = ImageIO.read(input);
			QRCodeDecoder decoder = new QRCodeDecoder();
			content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
