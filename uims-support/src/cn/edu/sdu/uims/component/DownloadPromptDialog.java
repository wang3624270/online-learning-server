package cn.edu.sdu.uims.component;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class DownloadPromptDialog extends JDialog implements ActionListener,KeyListener{
	private JButton cButton;
	private JButton eButton;
	private JLabel label0;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private boolean isDownload = false;
	
	public DownloadPromptDialog() {
		super();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();		
		int Swing1x;
		int Swing1y;
		Container c = getContentPane();
		c.setLayout(null);
		Swing1x = 380;
		Swing1y = 140;			
		label0 = new JLabel("有新版本客户端程序，增加了一些新的功能，请及时下载!");
		label0.setBounds(10, 10, 340, 30);
		label0.setHorizontalAlignment(label0.LEFT);
		eButton = new JButton("下载");
		eButton.setBounds(40, 50, 120, 30);
		cButton = new JButton("以后再说");
		cButton.setBounds(180, 50, 120, 30);
		c.add(label0);
		setBounds(screensize.width / 2 - Swing1x / 2, screensize.height / 2
				- Swing1y / 2, Swing1x, Swing1y);
		eButton.setActionCommand("eButton");
		eButton.addActionListener(this);
		eButton.addKeyListener(this);
		cButton.addActionListener(this);
		cButton.setActionCommand("cButton");
		cButton.addActionListener(this);
		cButton.addKeyListener(this);
		c.add(eButton);
		c.add(cButton);
		this.setModal(true);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		eButton.addKeyListener(this);
		eButton.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String amd = e.getActionCommand();
		if (amd.equals("cButton")) {
			isDownload = false;
		} else if (amd.equals("eButton")) {
			isDownload = true;
		}
		this.dispose();

	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// TODO Auto-generated method stub
		if (ke.getKeyChar() == ke.VK_ENTER) {
			if (ke.getSource() == cButton) {
				isDownload = false;
			}
			if (ke.getSource() == eButton) {
				isDownload = true;
			}
			this.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean isDownload() {
		return isDownload;
	}
}
