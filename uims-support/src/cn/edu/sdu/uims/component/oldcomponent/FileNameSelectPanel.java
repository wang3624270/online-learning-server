package cn.edu.sdu.uims.component.oldcomponent;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.sdu.uims.component.method.GetFile;

public class FileNameSelectPanel extends JPanel {

	static public final int FILE_TYPE_OPEN = 0;
	static public final int FILE_TYPE_SAVE = 1;
	static public final int FILE_TYPE_DIR = 2;
	private String title;

	final private JButton browserButton;

	final private JLabel titleLabel;

	final private JTextField input;
	private String ext = "*";
	
	private int nType;

	public FileNameSelectPanel() {
		this("test","*", FILE_TYPE_OPEN);
	}
	public FileNameSelectPanel(String title) {
		this(title,"*", FILE_TYPE_OPEN);
	}
	public FileNameSelectPanel(String title, String ext) {
		this(title, ext, FILE_TYPE_OPEN);
	}
	public FileNameSelectPanel(String title, String ext, int nType) {
		super();
		this.title = title;
		this.nType = nType;
		this.ext = ext;
		titleLabel = new JLabel(title);
		input = new JTextField(25);
		browserButton = new JButton("浏览");
		browserButton.setActionCommand("browser");
		this.setLayout(new FlowLayout());
		this.add(titleLabel);
		this.add(input);
		this.add(browserButton);
		browserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSelectFileName();
			}
		});
	}

	public void getSelectFileName() {
		File  file = null;
		switch(nType){
		case FILE_TYPE_OPEN:
			file = GetFile.getOpenFile(ext);
			break;
		case FILE_TYPE_SAVE:
			file = GetFile.getSaveFile(ext);
			break;
		case FILE_TYPE_DIR:
			file = GetFile.getDirName();
			break;
		}
		if(file != null)
			input.setText(file.getAbsolutePath());
	}	
	public void showMessage(String s) {
		JOptionPane.showMessageDialog(this, s);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileName(){
		return input.getText();
	}
}