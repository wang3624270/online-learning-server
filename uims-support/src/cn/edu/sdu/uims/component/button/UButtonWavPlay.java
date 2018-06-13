package cn.edu.sdu.uims.component.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;


public class UButtonWavPlay extends UButton implements ActionListener{
	private boolean isStart = true;
	private String fileName;
	private Integer attachId;
	private InputStream in;
	public void initContents() {
		// TODO Auto-generated method stub
		this.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		
	}
}
