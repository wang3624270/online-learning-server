package cn.edu.sdu.uims.component.camera;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PicturePanel extends JPanel {
	private BufferedImage image;

	public PicturePanel(BufferedImage image) {
		super();
		this.image = image;
	}

	public PicturePanel() {
		super();

	}

	public void setBufferedImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);

		int pictureWidth;
		int pictureHeight;

		if (image != null) {
			if ((double) this.getWidth() / (double) this.getHeight() >= (double) image
					.getWidth()
					/ (double) image.getHeight()) {
				pictureHeight = this.getHeight();
				pictureWidth = (int) ((double) pictureHeight
						* (double) image.getWidth() / (double) image
						.getHeight());
			} else {
				pictureWidth = this.getWidth();
				pictureHeight = (int) ((double) pictureWidth / ((double) image
						.getWidth() / (double) image.getHeight()));
			}

			g.drawImage(resizeImage(image, pictureWidth, pictureHeight), 0, 0,
					null);

		}
	}

	public static BufferedImage resizeImage(BufferedImage bufferedimage,
			final int w, final int h) {
		int type = bufferedimage.getColorModel().getTransparency();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
				.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0, bufferedimage
				.getWidth(), bufferedimage.getHeight(), null);
		graphics2d.dispose();
		return img;
	}

}