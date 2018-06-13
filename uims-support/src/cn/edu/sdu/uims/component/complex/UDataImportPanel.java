package cn.edu.sdu.uims.component.complex;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.component.button.UButton;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.textfield.UTextField;
import cn.edu.sdu.uims.def.DataIoStruct;
import cn.edu.sdu.uims.def.dataimport.DataImportTemplateFromXLS;
import cn.edu.sdu.uims.form.impl.DataIoProcessForm;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.ClientAccessoriesUtility;
import cn.edu.sdu.uims.util.UimsUtils;

public class UDataImportPanel extends UDataIoPanel {
	protected UTextField fileNameField;
	protected UButton browserButton;
	protected UButton validateButton;
	protected UButton importButton;
	protected UButton downLoadButton;

	@Override
	public void initContents() {
		super.initContents();
		fileNameField = new UTextField(20);
		browserButton = new UButton();
		validateButton = new UButton();
		importButton = new UButton();
		downLoadButton = new UButton();
		fileNameField.setText("");
		browserButton.setText("浏览");
		validateButton.setText("数据校验");
		importButton.setText("导入");
		downLoadButton.setText("下载导入模板");
		add(fileNameField.getAWTComponent());
		add(browserButton.getAWTComponent());
		add(validateButton.getAWTComponent());
		add(importButton.getAWTComponent());
		add(downLoadButton.getAWTComponent());
		browserButton.addActionListener(this);
		validateButton.addActionListener(this);
		importButton.addActionListener(this);
		downLoadButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == browserButton) {
			doBrowser();
		} else if (e.getSource() == downLoadButton) {
			doDownLoad();
		} else if (e.getSource() == validateButton) {
			doDataValidate();
		} else if (e.getSource() == importButton) {
			doDataImport();
		}
	}

	public void doBrowser() {
		File file = GetFile.getOpenFile();
		if (file != null) {
			fileNameField.setText(file.getAbsolutePath());
		}
	}

	public void doDownLoad() {
		DataIoStruct info = (DataIoStruct) typeComboBox.getSelectedItem();
		if (info == null) {
			JOptionPane.showMessageDialog(null, "请选择导入类型！");
			return;
		}
		String modelName = info.getModelName();
		DataImportTemplateFromXLS temp = (DataImportTemplateFromXLS) UFactory
				.getModelSession().getBsTemplate("dataImport", modelName);
		if (temp == null) {
			JOptionPane.showMessageDialog(null, "模板类型不存在！");
		}
		String fileName = temp.getTemplateFileName();
		String[] b= fileName.split("\\.");
		File file = GetFile.getSaveFile(b[1]);
		if (file == null) {
			JOptionPane.showMessageDialog(null, "请选择保存文件名！");
		} else {

			try {
				byte[] array = ClientAccessoriesUtility
						.downloadServerContextFile("/public_downloads/xlsimport/"
								+ fileName);
				if (array != null) {
					FileOutputStream out = new FileOutputStream(file);
					out.write(array);
					out.close();
					JOptionPane.showMessageDialog(null, "模板下载成功！");
				} else {
					JOptionPane.showMessageDialog(null, "模板下载错误！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "模板下载错误！");
			}
		}
	}

	public void doDataImport() {
		DataIoStruct info = (DataIoStruct) typeComboBox.getSelectedItem();
		if (info == null) {
			JOptionPane.showMessageDialog(null, "请选择导入类型！");
			return;
		}
		String fileName = fileNameField.getText();
		if (fileName == null || fileName.length() == 0) {
			JOptionPane.showMessageDialog(null, "导入文件名为空！");
			return;
		}
		File file = new File(fileName);
		int length = (int) file.length();
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "导入文件不存在！");
			return;
		}
		DataIoProcessForm form = new DataIoProcessForm();
		UHandlerI h = this.getUParent().getHandler();
		HashMap userMap = null;
		if (h != null) {
			userMap = h.getBusinessParaMap(componentName);
			if (userMap != null) {
				String errorInfo = (String) userMap.get("DataErrorInfo");
				if (errorInfo != null) {
					JOptionPane.showMessageDialog(null, errorInfo);
					return;
				}
			}
		}
		form.setDataIoStruct(info);
		form.setFileName(fileName);
		form.setUserMap(userMap);
		String ioType = "client";
		if (info.getIoType() != null)
			ioType = info.getIoType();
		if (ioType.equals("client")) {
			if (h instanceof UFormHandler) {
				int ret = JOptionPane.showConfirmDialog(null,
						"确认要导入吗？（如有可用的数据校验方法，请先进行数据校验以检查数据的合法性）");
				if (ret == JOptionPane.YES_OPTION) {
					((UFormHandler) h).startDialog(
							"uimsDataImportProcessDialog", form);
				}
			}
		} else {
			try {
				FileInputStream in = new FileInputStream(file);
				byte a[] = new byte[length];
				in.read(a);
				in.close();
				RmiRequest request = new RmiRequest();
				request.add(RmiKeyConstants.KEY_BYTES, a);
				request.add(RmiKeyConstants.KEY_FORM, form);
				if (userMap != null)
					request.add(RmiKeyConstants.KEY_HASH, userMap);
				request.setCmd("uimsDataImportToDatabaseFromXLSFile");
				RmiResponse response = UimsUtils.requestProcesser(request);
				String retInfo = (String) response.get(RmiKeyConstants.KEY_STRING);
				if (retInfo == null || retInfo.length() == 0) {
					JOptionPane.showMessageDialog(null, "数据导入成功！");
				} else {
					JOptionPane.showMessageDialog(null, retInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doDataValidate() {
		DataIoStruct info = (DataIoStruct) typeComboBox.getSelectedItem();
		if (info == null) {
			JOptionPane.showMessageDialog(null, "请选择数据校验类型！");
			return;
		}
		String fileName = fileNameField.getText();
		if (fileName == null || fileName.length() == 0) {
			JOptionPane.showMessageDialog(null, "数据校验文件名为空！");
			return;
		}
		File file = new File(fileName);
		int length = (int) file.length();
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "数据校验文件不存在！");
			return;
		}
		DataIoProcessForm form = new DataIoProcessForm();
		UHandlerI h = this.getUParent().getHandler();
		HashMap userMap = null;
		if (h != null) {
			userMap = h.getBusinessParaMap(componentName);
			if (userMap != null) {
				String errorInfo = (String) userMap.get("DataErrorInfo");
				if (errorInfo != null) {
					JOptionPane.showMessageDialog(null, errorInfo);
					return;
				}
			}
		}
		form.setDataIoStruct(info);
		form.setFileName(fileName);
		form.setUserMap(userMap);
		String ioType = "client";
		if (info.getIoType() != null)
			ioType = info.getIoType();
		if (ioType.equals("client")) {
			if (h instanceof UFormHandler)
				((UFormHandler) h).startDialog("uimsDataImportValidateDialog",
						form);
		} else {
			try {
				FileInputStream in = new FileInputStream(file);
				byte a[] = new byte[length];
				in.read(a);
				in.close();
				RmiRequest request = new RmiRequest();
				request.add(RmiKeyConstants.KEY_BYTES, a);
				request.add(RmiKeyConstants.KEY_FORM, form);
				if (userMap != null)
					request.add(RmiKeyConstants.KEY_HASH, userMap);
				request.setCmd("uimsDataImportValidateFromXLSFile");
				RmiResponse response = UimsUtils.requestProcesser(request);
				String retInfo = (String) response.get(RmiKeyConstants.KEY_STRING);
				if (retInfo == null || retInfo.length() == 0) {
					JOptionPane.showMessageDialog(null, "数据校验成功！");
				} else {
					JOptionPane.showMessageDialog(null, retInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
