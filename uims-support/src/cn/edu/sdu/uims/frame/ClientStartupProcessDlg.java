package cn.edu.sdu.uims.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import cn.edu.sdu.uims.util.UimsUtils;

public class ClientStartupProcessDlg extends JFrame implements Runnable {

	JLabel messageLabel;// = new JLabel();
	String messege = "";
	JLabel iconLabel;// = new JLabel();
	Thread t;
	JPanel aa;// = new JPanel();
	Icon icon1;
	Icon icon2;

	// ////////////////////////////////////////////
	public ClientStartupProcessDlg() {
		// super();
		messageLabel = new JLabel();
		iconLabel = new JLabel();
		aa = new JPanel();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		this.setLocation(screenWidth / 2 - 400 / 2, screenHeight / 2 - 75 / 2);

		Container mainPane = this.getContentPane();
		mainPane.setLayout(new BorderLayout());
		icon1 = UimsUtils.getCSClientIcon("greenBall.png");
		icon2 = UimsUtils.getCSClientIcon("redBall.png");
		messageLabel = new JLabel("");
		iconLabel = new JLabel(icon1);
		mainPane.add(iconLabel, BorderLayout.WEST);
		mainPane.add(messageLabel, BorderLayout.CENTER);

		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setSize(400, 70);
		this.setAlwaysOnTop(true);

		this.setVisible(true);
		t = new Thread(this);
		t.start();
	}

	public void run() {
		boolean flag = false;
		while (!this.messege.equals("endProcess")) {
			if (flag) {
				iconLabel.setIcon(icon1);
				flag = false;
			} else {
				iconLabel.setIcon(icon2);
				flag = true;
			}
			try {
				Thread.sleep(300);
			} catch (Exception e) {
			}
		}
		this.dispose();
	}

	public static void main(String[] args) {
		// new ClientStartupProcessDlg();
	}

}