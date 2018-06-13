package cn.edu.sdu.uims.component.complex;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cn.edu.sdu.uims.attribute.UFileDataAttribute;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.textfield.UTextField;

public class UFileChooserPanel extends UComplexPanel {
	protected JTextField dirNameField;
	protected JButton browserButton;
	protected UFileDataAttribute fileData;
	@Override
	public void initContents() {
		super.initContents();
		List list;
		int h = 20;
		if (elementTemplate == null)
			return;
		if (elementTemplate.text != null && elementTemplate.text.length() != 0)
			add(new JLabel(elementTemplate.text));
		dirNameField = new UTextField(20);
		dirNameField.setPreferredSize(new Dimension(200, h));
		add(dirNameField);

		browserButton = new JButton("浏览");
		browserButton.setActionCommand("browserButton");
		browserButton.addActionListener(this);
		add(browserButton);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == browserButton) {
			doBrowser();
		}
	}

	public void doBrowser() {
		File file;
		file = GetFile.getOpenFile();
		if (file != null) {
			dirNameField.setText(file.getAbsolutePath());
			makeFileData();
		}
		else 
			fileData = null;
	}

	public Object getData(){
		return fileData;
	}
	public void setData(Object o){
		
	}
	public void makeFileData() {
		fileData = null;
		String fileName = dirNameField.getText();
		try {
			File file = new File(fileName);
			long length = file.length();
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) length];
			fis.read(data);
			fis.close();
			fileData = new UFileDataAttribute();
			fileData.setFileName(file.getName());
			fileData.setData(data);
		}catch(Exception e){
			fileData = null;
		}
	}

}
