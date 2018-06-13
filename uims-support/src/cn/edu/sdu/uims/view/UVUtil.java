package cn.edu.sdu.uims.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.text.AttributedString;
import java.util.List;

import javax.swing.ImageIcon;

import com.itextpdf.awt.PdfGraphics2DForUims;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.util.UTextUtil;

public class UVUtil {
	public static void drawStringBox(Graphics g, String s, UColor color,
			UFont font, int x, int y, int w, int h, int horizontalAlignment,
			int verticalAlignment, boolean fontMark) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D b;
		int fh, fw, xo = 0, t1 = 0, t2 = 0, yo = 0;
		b = font.font.getStringBounds(s, 0, s.length(), g2
				.getFontRenderContext());
		fh = (int) b.getHeight();
		fw = (int) b.getWidth();
		switch (horizontalAlignment) {
		case UConstants.ALIGNMENT_LEFT:
			xo = x;
			break;
		case UConstants.ALIGNMENT_CENTER:
			xo = x + (w - fw) / 2;
			break;
		case UConstants.ALIGNMENT_RIGHT:
			xo = x + w - fw;
			break;
		}
		switch (verticalAlignment) {
		case UConstants.ALIGNMENT_TOP:
			yo = y + fh;
			break;
		case UConstants.ALIGNMENT_CENTER:
			t1 = y + (h + fh) / 2;
			t2 = (int) (b.getMinY()) / 2;
			yo = t1 + t2;
			break;
		case UConstants.ALIGNMENT_BOTTOM:
			yo = y + h - fh;
		}
		Font tmpFont = g2.getFont();
		g2.setFont(font.font);
		if (!fontMark) {
			g.drawString(s, xo, yo);
		} else {
			AttributedString attributeString;
			attributeString = new AttributedString(s);
			((PdfGraphics2DForUims) g2).setCHNFont(font.fileName);
			((PdfGraphics2DForUims) g2).setCHNFontSize(font.size);
/*			attributeString.addAttribute(TextAttribute.FONT, new Font(
					font.fontName, Font.PLAIN, font.size));
*/			g.drawString(attributeString.getIterator(), xo, yo);
		}
		g2.setFont(tmpFont);

	}
	public static void drawStringBox(Graphics g, String s, UColor color,
			UFont font, int x, int y, int w, int h, int horizontalAlignment,
			int verticalAlignment, int firstSpace, boolean fontMark) {
		drawStringBox(g,s,color.color,font.font,x,y,w,h,horizontalAlignment,verticalAlignment,firstSpace,fontMark,font.fileName,font.size);
		
	}
	public static void drawStringBox(Graphics g, String s, Color color,
			Font font, int x, int y, int w, int h, int horizontalAlignment,
			int verticalAlignment, int firstSpace, boolean fontMark,String fileName, int fontSize) {
		List<String> lineList = UTextUtil.getRowLine(s,font, g, w,
				firstSpace);
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D b;
		int fh, fw, xo = 0, t1 = 0, t2 = 0, yo = 0;
		b = getTextRectangle2D(lineList, font, g, x, y, w, h);
		fh = (int) (b.getHeight() + 0.5);// textdoctorRegisterQueryByNumOrIdCardPaneldoctorRegisterQueryByNumOrIdCardPaneldoctorRegisterQueryByNumOrIdCardPanel所需要高度
		fw = (int) (b.getWidth() + 0.5); // text所需要宽度
		switch (horizontalAlignment) {
		case UConstants.ALIGNMENT_LEFT:
			xo = x;
			break;
		case UConstants.ALIGNMENT_CENTER:
			if (fw <= w) {
				xo = x + (w - fw) / 2;
			} else {
				xo = x;
			}
			break;
		case UConstants.ALIGNMENT_RIGHT:
			if (fw < w)
				xo = x + w - fw;
			else
				xo = x + w;
			break;
		}
		switch (verticalAlignment) {
		case UConstants.ALIGNMENT_TOP:
			yo = y;
			break;
		case UConstants.ALIGNMENT_CENTER:
			if (fh < h) {
				t1 = y + (h - fh) / 2;
				if (lineList != null && lineList.size() > 0)
					t2 = (int) (b.getHeight()) * 3 / (lineList.size() * 4);
				else {
					t2 = (int) (b.getHeight()) * 3 / (4);
				}
				yo = t1 + t2;
			} else {
				yo = y;
			}
			break;
		case UConstants.ALIGNMENT_BOTTOM:
			if (fh < h)
				yo = y + h - fh;
			else
				yo = y + h;
		}
		Font tmpFont = g2.getFont();
		g2.setFont(font);
		g2.setColor(color);
		int i;
		String line;
		Rectangle2D lr;
		AttributedString attributeString;
		for (i = 0; i < lineList.size(); i++) {
			if (yo < (h + y)) {
				line = lineList.get(i);
				if (fontMark) {
					attributeString = new AttributedString(line);
					// attributeString.addAttribute(TextAttribute.FONT, new
					// Font(
					// font.fontName, Font.PLAIN, font.size));
					try {
						((PdfGraphics2DForUims) g2).setCHNFont(fileName);
						((PdfGraphics2DForUims) g2).setCHNFontSize(fontSize);
					} catch (Exception e) {
						// TODO: handle exception
					}
					g.drawString(attributeString.getIterator(), xo, yo);
				} else {
					g.drawString(line, xo, yo);
				}
				lr = font.getStringBounds(line, 0, line.length(), g2
						.getFontRenderContext());
				yo += (int) lr.getHeight();
			}
		}
		g2.setFont(tmpFont);

	}

	public static Rectangle2D getTextRectangle2D(Graphics g, String s,
			UFont font, int x, int y, int w, int h, int firstSpace) {
		List<String> lineList = UTextUtil.getRowLine(s, font.font, g, w,
				firstSpace);
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D b;
		int fh, fw, xo = 0, t1 = 0, t2 = 0, yo = 0;
		b = getTextRectangle2D(lineList, font, g, x, y, w, h);
		return b;
	}
	public static Rectangle2D getTextRectangle2D(List<String> lineList,
			UFont font, Graphics g, int x, int y, int w, int h) {
		return getTextRectangle2D(lineList, font.font, g,x,y,w,h);
	}
	public static Rectangle2D getTextRectangle2D(List<String> lineList,
			Font font, Graphics g, int x, int y, int w, int h) {
		Rectangle2D rectangle = null;
		Graphics2D g2 = (Graphics2D) g;
		//
		int i;
		int th = 0, tw = 0; // th 表示中的高度，tw表示总的宽度
		String line;
		Rectangle2D lr; // 表示每行的小矩形
		for (i = 0; i < lineList.size(); i++) {
			line = lineList.get(i);
			lr = font.getStringBounds(line, 0, line.length(), g2
					.getFontRenderContext());
			th += (lr.getHeight() + 0.5);
			if (lr.getWidth() > tw) {
				tw = (int) lr.getWidth();
			}
		}
		if (th > h) {
			th = h;
		}
		rectangle = new Rectangle(x, y, tw, th);

		return rectangle;
	}

	public static void drawStringRotate(Graphics g, String str, UColor color,
			UFont font, int imgx, int imgy, int imagewidth, int imageheight,
			int degree, Color bgColor) {
		FontRenderContext context = ((Graphics2D) g).getFontRenderContext();
		Rectangle2D bounds = font.font.getStringBounds(str, context);
		int height = (int) bounds.getHeight();
		int width = (int) bounds.getWidth();
		BufferedImage image = transStrToImage(str, width, height, imagewidth,
				imageheight, font.font, bgColor, color.color);
		image = rotateImg(image, degree, bgColor);
		// 计算角度
		degree = degree % 360;
		if (degree < 0)
			degree += 360;
		double ang = degree * 0.0174532925;
		int rimagewidth = 0;
		int rimageheight = 0;
		if (degree == 180 || degree == 0 || degree == 360) {
			rimagewidth = imagewidth;
			rimageheight = imageheight;
		} else if (degree == 90 || degree == 270) {
			rimagewidth = imageheight;
			rimageheight = imagewidth;
		} else {
			int d = imagewidth + imageheight;
			rimagewidth = (int) (d * Math.abs(Math.cos(ang)));
			rimageheight = (int) (d * Math.abs(Math.sin(ang)));
		}
		g.drawImage(image, imgx, imgy, rimagewidth, rimageheight, null);
	}

	public static BufferedImage transStrToImage(String str, int width,
			int height, int imgwidth, int imgheight, Font font, Color bgColor,
			Color color) {
		BufferedImage image = null;
		int offsetx = (imgwidth - width) / 2;
		int offsety = (imgheight - height) / 2;
		image = new BufferedImage(imgwidth, imgheight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gimg = (Graphics2D) image.getGraphics();
		// gimg.setColor(bgColor);
		// gimg.fillRect(0, 0, imgwidth, imgheight);
		gimg.setBackground(new Color(0, 0, 0, 0.0f));
		// gimg.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
		// RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		// gimg.setComposite(AlphaComposite
		// .getInstance(AlphaComposite.CLEAR, 0.0f));
		gimg.fillRect(0, 0, imgwidth, imgheight);
		gimg.setFont(font);
		gimg.setColor(color);
		gimg.drawString(str, offsetx, offsety);
		return image;
	}

	public static BufferedImage rotateImg(BufferedImage image, int degree,
			Color bgColor) {
		BufferedImage rimage = null;
		int iw = image.getWidth();
		int ih = image.getHeight();
		int w = 0;
		int h = 0;
		int x = 0;
		int y = 0;
		degree = degree % 360;
		if (degree < 0)
			degree = 360 + degree;// 将角度转换到0-360度之间
		double ang = degree * 0.0174532925;
		if (degree == 180 || degree == 0 || degree == 360) {
			w = iw;
			h = ih;
		} else if (degree == 90 || degree == 270) {
			w = ih;
			h = iw;
		} else {
			int d = iw + ih;
			w = (int) (d * Math.abs(Math.cos(ang)));
			h = (int) (d * Math.abs(Math.sin(ang)));
		}
		x = (w - iw) / 2;
		y = (h - ih) / 2;
		rimage = new BufferedImage(w, h, image.getType());
		Graphics g = rimage.getGraphics();
		// g.setColor(bgColor);
		// g.fillRect(0, 0, w, h);
		AffineTransform at = new AffineTransform();
		at.rotate(ang, w / 2, h / 2);
		at.translate(x, y);
		AffineTransformOp op = new AffineTransformOp(at,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		op.filter(image, rimage);
		return rimage;
	}

	public static BufferedImage transParency(Image image, int alpha, int r,
			int g, int b) {
		BufferedImage transImage = null;
		transImage = transImageToBufferedImage(image);
		transImage = transParency(transImage, alpha, r, g, b);
		return transImage;
	}

	public static BufferedImage transImageToBufferedImage(Image image) {
		BufferedImage bimage = null;
		if (image instanceof BufferedImage) {
			bimage = (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		ImageIcon imageIcon = new ImageIcon(image);

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_4BYTE_ABGR;
			bimage = new BufferedImage(imageIcon.getIconWidth(), imageIcon
					.getIconHeight(), type);
		}
		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
		g.dispose();

		return bimage;
	}

	public static BufferedImage transParency(BufferedImage image, int alpha,
			int r, int g, int b) {
		BufferedImage transImage = null;
		transImage = image;
		int rgb;
		DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();
		for (int i = transImage.getMinY(); i < transImage.getHeight(); i++) {
			for (int j = transImage.getMinX(); j < transImage.getWidth(); j++) {
				rgb = transImage.getRGB(j, i);
				int red = dcm.getRed(rgb);
				int green = dcm.getGreen(rgb);
				int blue = dcm.getBlue(rgb);
				if (red == r && green == g && blue == b)// 如果像素为白色，则让它透明
					rgb = (alpha << 24) | (rgb & 0x00ffffff);
				transImage.setRGB(j, i, rgb);
			}
		}
		return transImage;

	}

	/**
	 * @author 洋
	 * @param dc
	 * @param p
	 * @param box
	 */
	public static void drawBorderBox(Graphics dc, int x, int y, int w, int h) {
		// System.out.println(x+":"+y+":"+w+":"+h);
		if (w < 0 || h < 0)
			return;
		dc.drawRect(x, y, w, h);
	}

	public static void drawStringBox(Graphics g, int xo, int yo, int l) {
		int w, h;
		double pi6 = Math.PI / 6;
		g.drawLine(xo, yo, xo + l, yo);
		g.drawLine(xo + l, yo, xo + l, yo + l);
		g.drawLine(xo + l, yo + l, xo, yo + l);
		g.drawLine(xo, yo + l, xo, yo);
		w = (int) (l * 0.5 * Math.cos(pi6));
		h = (int) (l * 0.5 * Math.sin(pi6));
		g.drawLine(xo, yo, xo + w, yo - h);
		g.drawLine(xo + w, yo - h, xo + w + l, yo - h);
		g.drawLine(xo + w + l, yo - h, xo + l, yo);
		g.drawLine(xo + w + l, yo - h, xo + w + l, yo + l - h);
		g.drawLine(xo + w + l, yo + l - h, xo + l, yo + l);
	}
	
	public static Rectangle2D getTextRectangle2D(String line,
			Font font, Graphics g, int x, int y, int w, int h) {
		Rectangle2D rectangle = null;
		Graphics2D g2 = (Graphics2D) g;
		//
		int i;
		int th = 0, tw = 0; // th 表示中的高度，tw表示总的宽度
		Rectangle2D lr; // 表示每行的小矩形
			lr = font.getStringBounds(line, 0, line.length(), g2
					.getFontRenderContext());
			th += (lr.getHeight() + 0.5);
			if (lr.getWidth() > tw) {
				tw = (int) lr.getWidth();
			}
		if (th > h) {
			th = h;
		}
		rectangle = new Rectangle(x, y, tw, th);

		return rectangle;
	}
	public static void drawStringBox(Graphics g, String line, Font font, int x, int y, int w, int h) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D b;
		int fh, fw, xo = 0, t1 = 0, t2 = 0, yo = 0;
		b = getTextRectangle2D(line, font, g, x, y, w, h);
		fh = (int) (b.getHeight() + 0.5);// textdoctorRegisterQueryByNumOrIdCardPaneldoctorRegisterQueryByNumOrIdCardPaneldoctorRegisterQueryByNumOrIdCardPanel所需要高度
		fw = (int) (b.getWidth() + 0.5); // text所需要宽度
			if (fw <= w) {
				xo = x + (w - fw) / 2;
			} else {
				xo = x;
			}
			if (fh < h) {
				t1 = y + (h - fh) / 2;
				t2 = (int) (b.getHeight()) * 3 / (4);
				yo = t1 + t2;
			} else {
				yo = y;
			}
		g.drawString(line, xo, yo);

	}
	
}
