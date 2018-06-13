package cn.edu.sdu.uims.component.complex;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.component.complex.def.UFileUploadTemplate;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.textfield.UTextField;
import cn.edu.sdu.uims.form.impl.FileUploadProcessForm;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.util.UimsUtils;

public class UFileUploadPanel extends UComplexPanel {
	protected JTextField dirNameField;
	protected JComboBox typeComboBox;
	protected JButton browserButton;
	protected JButton uploadButton;

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

		uploadButton = new JButton("上传");
		uploadButton.setActionCommand("uploadButton");
		uploadButton.addActionListener(this);

		add(uploadButton);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == browserButton) {
			doBrowser();
		} else if (e.getSource() == uploadButton) {
			doUpLoad();
		}
	}

	public void doBrowser() {
		File file;
		UFileUploadTemplate temp = (UFileUploadTemplate) elementTemplate;
		if (temp.singleFile) {
			if(temp.fileType != null && !temp.fileType.equals(""))
				file = GetFile.getOpenFile(temp.fileType);
			else
				file = GetFile.getOpenFile();			
		} else {
			file = GetFile.getDirName();
		}
		if (file != null) {
			dirNameField.setText(file.getAbsolutePath());
		}
	}

	public String getUploadType() {
		if (typeComboBox == null)
			return null;
		else {
			ListOptionInfo info = (ListOptionInfo) typeComboBox
					.getSelectedItem();
			if (info == null)
				return null;
			else
				return info.getValue();
		}
	}

	public void doUpLoad() {
		String dirName = dirNameField.getText();
		if (dirName == null || dirName.length() == 0) {
			JOptionPane.showMessageDialog(null, "导入目录或者文件为空！");
			return;
		}
		File file = new File(dirName);
		UFileUploadTemplate temp = (UFileUploadTemplate) elementTemplate;
		if (temp.singleFile) {
			if (!file.isFile()) {
				JOptionPane.showMessageDialog(null, "请选中上传的文件！");
				return;
			}
			if(!temp.needProcess) {
				doUplaodSingleFile(file);
			}else {
				doUplaodSingleFileWithProcess(file);				
			}
		} else {
			String fileNames[] = file.list();
			if (fileNames == null || fileNames.length == 0) {
				JOptionPane.showMessageDialog(null, "导入文件不存在！");
				return;
			}
			FileUploadProcessForm form = new FileUploadProcessForm();
			UHandlerI h = this.getUParent().getHandler();
			HashMap userMap = null;
			if (h != null)
				userMap = h.getBusinessParaMap(componentName);
			form.setDirName(dirName);
			form.setUserMap(userMap);
			if (h instanceof UFormHandler) {
				((UFormHandler) h).startDialog("uimsFileUploadProcessDialog",
						form);
			} else {
				JOptionPane.showMessageDialog(null, "系统无法导入！");
			}
		}
	}

	public void doUplaodSingleFile(File file) {
		try {
			long length = file.length();
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) length];
			fis.read(data);
			fis.close();
			UHandlerI h = this.getUParent().getHandler();
			HashMap userMap = null;
			if (h != null)
				userMap = h.getBusinessParaMap(componentName);
			RmiRequest request = new RmiRequest();
			request.add("paraMap", userMap);
			request.add("fileName", file.getName());
			request.add("fileData", data);
			request.setCmd((String) (userMap.get("cmd")));
			RmiResponse response = UimsUtils.requestProcesser(
					request);
			if (response.getErrorMsg() == null) {
				JOptionPane.showMessageDialog(null, "文件上传成功！");
				if(h !=null)
					h.componentProcessFished(this.getComponentName());
			} else {
				JOptionPane.showMessageDialog(null, "文件上传失败！");
			}
		} catch (Exception e) {
		}
	}
	void doUplaodSingleFileWithProcess(File file){
		try {
			long length = file.length();
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) length];
			fis.read(data);
			fis.close();
			UHandlerI h = this.getUParent().getHandler();
			HashMap userMap = null;
			if (h != null)
				userMap = h.getBusinessParaMap(componentName);
			RmiRequest request = new RmiRequest();
			request.add("paraMap", userMap);
			request.add("fileName", file.getName());
			request.add("fileData", data);
			request.setCmd((String) (userMap.get("cmd")));
			RmiResponse response = UimsUtils.requestProcesser(
					request);
			if (response.getErrorMsg() == null) {
				JOptionPane.showMessageDialog(null, "文件上传成功！");
			} else {
				JOptionPane.showMessageDialog(null, "文件上传失败！");
			}
		} catch (Exception e) {
		}		
	}
}
