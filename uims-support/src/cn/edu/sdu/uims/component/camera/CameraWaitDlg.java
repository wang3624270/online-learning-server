package cn.edu.sdu.uims.component.camera;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRootPane;

public class CameraWaitDlg extends JDialog {

	JLabel messageLabel;// = new JLabel();

	// ////////////////////////////////////////////
	public CameraWaitDlg() {
		// super();
		messageLabel = new JLabel("视频设备初试化...");
		messageLabel.setBorder(BorderFactory.createTitledBorder(""));
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		this.setLocation(screenWidth / 2 - 400 / 2, screenHeight / 2 - 75 / 2);

		Container mainPane = this.getContentPane();
		mainPane.setLayout(new BorderLayout());
		mainPane.add(messageLabel, BorderLayout.CENTER);

		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setSize(400, 70);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return;
		}
	}

	public void run() {

	}

}