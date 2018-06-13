package cn.edu.sdu.uims.component.workbench;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	BufferedImage img = null;

	public ImagePanel() {
		try {
			img = ImageIO.read(new File("gradms/image/bg.jpg"));
		} catch (IOException e) {
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}



}