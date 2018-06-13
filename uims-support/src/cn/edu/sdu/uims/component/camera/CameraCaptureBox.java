package cn.edu.sdu.uims.component.camera;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.humatic.dsj.DSFiltergraph;
import de.humatic.dsj.rc.RendererControls;

public class CameraCaptureBox extends JPanel implements ActionListener,
		MouseListener, MouseMotionListener {

	JLabel defaultLabel = new JLabel("无图像");

	int captureWidth = 0;
	int captureHeight = 0;
	double photoRatio = 0.75;
	double mediaRatio = 0;
	int lineRectX = 0;
	int lineRectY = 0;
	int photoWidth = 0;
	int photoHeight = 0;
	Box baseBox = null;
	DSFiltergraph dsfg;
	JPanel mediaPanel = new JPanel();
	JPanel glassPanel = new JPanel();
	// JButton captureButton = new JButton("拍照");
	BufferedImage currentCaptureImage;
	PicturePanel distinationPanel;

	Point clickBeginPointer = new Point(0, 0);

	public CameraCaptureBox(DSFiltergraph dsfg, int width, int height,
			double mediaRatio) {

		super();
		this.setSize(width, height);
		this.mediaRatio = mediaRatio;
		this.setLayout(new GridLayout(1, 1));
		// baseBox = Box.createVerticalBox();
		// this.add(baseBox);
		this.dsfg = dsfg;
		dsfg.addMouseListener(this);
		dsfg.addMouseMotionListener(this);
		generateCaptureBoxSize(mediaRatio);
		defaultLabel.setHorizontalAlignment(JLabel.CENTER);
		defaultLabel.setBorder(BorderFactory.createTitledBorder(""));
		defaultLabel.setBounds(0, 0, width, height);
		// mediaPanel.setLayout(new GridLayout(1,1));
		mediaPanel.setLayout(null);
		mediaPanel.add(defaultLabel);
		this.add(mediaPanel);

	}

	public void setCaptureLines() {
		int x, y;
		x = (int) dsfg.getMediaDimension().getWidth();
		y = (int) dsfg.getMediaDimension().getHeight();
		RendererControls rc = dsfg.getRendererControls();
		BufferedImage overlay = new BufferedImage(x, y,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = overlay.createGraphics();
		g2d.setBackground(new Color(0, 0, 0, 0));
		BasicStroke bs = new BasicStroke(3f);// width���߿�,float��
		g2d.setColor(Color.green.darker());
		g2d.setStroke(bs);

		if (x > y) {
			photoHeight = (int) ((double) y * 0.9);
			photoWidth = (int) ((double) photoHeight * photoRatio);

		} else {
			photoWidth = (int) ((double) x * 0.95);
			photoHeight = (int) ((double) photoWidth / photoRatio);
		}
		lineRectX = (int) ((double) x / 2 - (double) photoWidth / 2);
		lineRectY = (int) ((double) y / 2 - (double) photoHeight / 2);
		g2d.drawRect(lineRectX, lineRectY, photoWidth, photoHeight);
		g2d.drawLine(lineRectX + photoWidth / 2 - 50, lineRectY + photoHeight
				/ 2, lineRectX + photoWidth / 2 + 50, lineRectY + photoHeight
				/ 2);
		g2d.drawLine(lineRectX + photoWidth / 2, lineRectY + photoHeight / 2
				- 90, lineRectX + photoWidth / 2, lineRectY + photoHeight / 2
				+ 90);
		rc.setOverlayImage(overlay, null, Color.black, 1f);
		// this.revalidate();
	}

	public void moveRect() {
		int x, y;
		x = (int) dsfg.getMediaDimension().getWidth();
		y = (int) dsfg.getMediaDimension().getHeight();
		RendererControls rc = dsfg.getRendererControls();
		BufferedImage overlay = new BufferedImage(x, y,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = overlay.createGraphics();
		g2d.setBackground(new Color(0, 0, 0, 0));
		BasicStroke bs = new BasicStroke(3f);// width���߿�,float��
		g2d.setColor(Color.green.darker());
		g2d.setStroke(bs);
		g2d.drawRect(lineRectX, lineRectY, photoWidth, photoHeight);
		g2d.drawLine(lineRectX + photoWidth / 2 - 50, lineRectY + photoHeight
				/ 2, lineRectX + photoWidth / 2 + 50, lineRectY + photoHeight
				/ 2);
		g2d.drawLine(lineRectX + photoWidth / 2, lineRectY + photoHeight / 2
				- 90, lineRectX + photoWidth / 2, lineRectY + photoHeight / 2
				+ 90);
		rc.setOverlayImage(overlay, null, Color.black, 1f);
		this.revalidate();
	}

	public void flashPicture() {
		int x, y;
		x = (int) dsfg.getMediaDimension().getWidth();
		y = (int) dsfg.getMediaDimension().getHeight();
		RendererControls rc = dsfg.getRendererControls();
		BufferedImage overlay = new BufferedImage(x, y,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = overlay.createGraphics();
		g2d.setBackground(new Color(0, 0, 0, 0));
		BasicStroke bs = new BasicStroke(3f);//
		g2d.setColor(Color.green.darker());
		g2d.setStroke(bs);
		g2d.fillRect(lineRectX, lineRectY, photoWidth, photoHeight);
		rc.setOverlayImage(overlay, null, Color.black, 1f);
		try {
			Thread.sleep(100);
			this.revalidate();
		} catch (Exception e) {
		}
		g2d.clearRect(lineRectX, lineRectY, photoWidth, photoHeight);

		g2d.clearRect(lineRectX, lineRectY, photoWidth, photoHeight);
		moveRect();
	}

	public BufferedImage clipImage(BufferedImage bufferedimage) {
		BufferedImage img;
		// ///////////////////////////
		img = bufferedimage.getSubimage(lineRectX, lineRectY, photoWidth,
				photoHeight);
		return img;
	}

	public void reloadCamera(DSFiltergraph dsfg) {
		this.dsfg = dsfg;
		dsfg.addMouseListener(this);
		dsfg.addMouseMotionListener(this);
		showCamera();
	}

	public void showCamera() {
		// this.removeAll();
		// //
		CameraWaitDlg dlg = new CameraWaitDlg();
		dlg.dispose();
		this.remove(0);

		// dsfg.setMaximumSize(new Dimension(captureWidth, captureHeight));
		// dsfg.setSize(captureWidth, captureHeight);
		mediaPanel.removeAll();
		mediaPanel.add(dsfg);

		dsfg.setBounds((this.getWidth() - captureWidth) / 2, 4, captureWidth,
				captureHeight);
		this.add(mediaPanel, 0);

		setCaptureLines();
		this.revalidate();
	}

	private void generateCaptureBoxSize(double mediaRatio) {
		double containerRatio = (double) this.getWidth()
				/ (double) this.getHeight();

		if (containerRatio > mediaRatio) {
			captureHeight = this.getHeight();
			captureWidth = (int) ((double) captureHeight * mediaRatio);

		} else {
			captureWidth = this.getWidth();
			captureHeight = (int) ((double) captureWidth / mediaRatio);
		}
		//
		// if (mediaRatio >= 1) {
		// if (mediaRatio > containerRatio) {
		// captureWidth = this.getWidth();
		// captureHeight = (int) ((double) captureWidth / mediaRatio);
		// } else {
		// captureHeight = this.getHeight();
		// captureWidth = (int) ((double) captureHeight * mediaRatio);
		// }
		//
		// } else {
		// if (mediaRatio > containerRatio) {
		// captureHeight = this.getHeight();
		// captureWidth = (int) ((double) captureHeight * mediaRatio);
		// } else {
		// captureWidth = this.getWidth();
		// captureHeight = (int) ((double) captureWidth / mediaRatio);
		// }
		//
		// }

	}

	public void paintRect() {
	}

	public BufferedImage getCurrentCaptureImage() {
		if (dsfg != null)
			return dsfg.getImage();
		else
			return null;
	}

	public BufferedImage getCurrentClipCaptureImage() {
		return clipImage(dsfg.getImage());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("拍照")) {
			currentCaptureImage = dsfg.getImage();
			// 
			try {
				flashPicture();
				// BufferedImage image1 = ImageIO.read(new File("d:\\aaa.jpg"));
				distinationPanel
						.setBufferedImage(clipImage(currentCaptureImage));

				// distinationPanel.revalidate();
				distinationPanel.repaint();
			} catch (Exception ee) {
			}

		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int a = e.getX() - clickBeginPointer.x;

		int b = e.getY() - clickBeginPointer.y;
		clickBeginPointer.x = e.getX();
		clickBeginPointer.y = e.getY();
		// clickBeginPointer.x+=a;
		// clickBeginPointer.y+=b;

		double m, n;
		m = (double) a / (mediaRatio * 0.4);
		n = (double) b / (mediaRatio * 0.4);

		if (m >= 0.7 && m < 1)
			m = m + 1;
		if (m < -0.7 && m > -1)
			m = m - 1;
		if (n >= 0.7 && n < 1)
			n = n + 1;
		if (n < -0.7 && n > -1)
			n = n - 1;
		lineRectX = lineRectX + (int) m;
		lineRectY = lineRectY + (int) n;
		moveRect();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		currentCaptureImage = dsfg.getImage();
		// 
		try {
			flashPicture();
			// BufferedImage image1 = ImageIO.read(new File("d:\\aaa.jpg"));
			distinationPanel.setBufferedImage(clipImage(currentCaptureImage));

			// distinationPanel.revalidate();
			distinationPanel.repaint();
		} catch (Exception ee) {
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		clickBeginPointer = new Point();
		clickBeginPointer.x = e.getX();
		clickBeginPointer.y = e.getY();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	//
	// public void paint(Graphics g) {
	// super.paint(g);
	// // this.paintComponent(g);
	//
	// try {
	// g.drawLine(0, 0, 400, 300);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
}