package cn.edu.sdu.uims.component.camera;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class CaptureCamera extends JDialog {
	CameraCaptureControl control;
	JPanel capturePanel;
	PicturePanel picture;

	public CaptureCamera() {
		// control = new CameraCaptureControl();
		// capturePanel.add(control.getCaptureBox());
		// control.setPictureDistinationJanel(picture);
		// control.showCamera();
		// control.getCurrentCaptureImage();
		//

		control = new CameraCaptureControl();
		capturePanel = new JPanel();
		capturePanel.setSize(400, 330);
		control.initCaptureBox(400, 330);

		capturePanel.setLayout(new GridLayout(1, 1));
		capturePanel.add(control.getCaptureBox());

		this.setLayout(null);
		control.setBounds(33, 33, 800, 40);
		capturePanel.setLocation(33, 90);
		picture = new PicturePanel();
		picture.setBounds(33, 420, 300, 300);

		control.setPictureDistinationJanel(picture);
		this.add(control);
		this.add(capturePanel);
		this.add(picture);

		// /////////////////
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void showCamera() {
		control.captureBox.showCamera();
		// capturePanel.add(control.getCaptureBox());
		capturePanel.revalidate();

	}

	public static void main(String[] args) {
		CaptureCamera ins = null;

		ins = new CaptureCamera();
		ins.setTitle("示li");

		ins.setSize(800, 600);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		ins.setLocation(screenWidth / 2 - 800 / 2, screenHeight / 2 - 600 / 2);
		ins.setVisible(true);
		ins.showCamera();

	}

	public void initCamera() {
		// captureDeviceInfo = CaptureDeviceManager.getDevice
		//
		// ("vfw:Microsoft WDM Image Capture (Win32):0"); // ������õ�����Ƶ��
		//
		// mediaLocator = new MediaLocator("vfw://0"); // ��������Ƶ��ַ
	}
}