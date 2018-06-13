package cn.edu.sdu.uims.component.panel;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JTextField;

public class UTestPanel extends SuperPanel {
	private JTextField t = new JTextField("test");
	private JButton b = new JButton("ok");
	public UTestPanel(){
		setLayout(new FlowLayout());
		add(t);
		add(b);
//		this.setBounds(50, 50, 200, 100);
	}
}
