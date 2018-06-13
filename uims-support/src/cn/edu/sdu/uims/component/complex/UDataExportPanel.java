package cn.edu.sdu.uims.component.complex;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.component.button.UButton;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.def.DataIoStruct;
import cn.edu.sdu.uims.form.impl.DataIoProcessForm;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.progress.ProgressElementObject;
import cn.edu.sdu.uims.util.UimsUtils;

public class UDataExportPanel extends UDataIoPanel {

	private UButton exportButton;

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		super.initContents();
		exportButton = new UButton();
		exportButton.setText("数据导出");
		add(exportButton.getAWTComponent());
		exportButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exportButton) {
			doDataExport();
		}
	}

	public void doDataExport() {
		DataIoStruct info = (DataIoStruct) typeComboBox.getSelectedItem();
		if (info == null || info.getModelName() == null) {
			JOptionPane.showMessageDialog(null, "请选择导出类型！");
			return;
		}
		String suffix;
		suffix = info.getSuffix();
		if (suffix == null || suffix.length() == 0)
			suffix = ".xls";
		File file = GetFile.getSaveFile(suffix);
		if (file == null) {
			JOptionPane.showMessageDialog(null, "请选择要保存文件名！");
			return;
		}
		info.setFileName(file.getAbsolutePath());
		String ioType = "server";
		if (info.getIoType() != null)
			ioType = info.getIoType();
		if (ioType.equals("server")) {
			doDataExportServer(info, null);
		} else if (ioType.equals("serverWithProcess")) {
			doDataExportServerWithProcess(info);
		} else {
			doDataExportClient(info);
		}
	}

	public void doDataExportServer(DataIoStruct info,
			ProgressElementObject proObject) {
		UHandlerI h = this.getUParent().getHandler();
		HashMap userMap = null;
		if (h != null)
			userMap = h.getBusinessParaMap(componentName);
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, info.getModelName());
		if (userMap != null)
			request.add(RmiKeyConstants.KEY_HASH, userMap);
		if (proObject != null)
			request.add(RmiKeyConstants.KEY_DE_PROGRESS_OBJ, proObject);
		request.setCmd("uimsGetDataExportByFormList");
		RmiResponse response = UimsUtils.requestProcesser(
				request);
		byte data[] = (byte[]) response.get(RmiKeyConstants.KEY_BYTES);
		if (data == null || data.length == 0) {
			JOptionPane.showMessageDialog(null, "数据导出错！");
			return;
		}
		if (data != null) {
			try {
				FileOutputStream out = new FileOutputStream(info.getFileName());
				out.write(data);
				out.close();
				JOptionPane.showMessageDialog(null, "数据导出成功！");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "数据导出错误！");
			}
		}
	}

	void doDataExportClient(DataIoStruct info) {
		UHandlerI h = this.getUParent().getHandler();
		HashMap userMap = null;
		if (h != null)
			userMap = h.getBusinessParaMap(componentName);
		DataIoProcessForm form = new DataIoProcessForm();
		form.setDataIoStruct(info);
		form.setUserMap(userMap);
		if (h instanceof UFormHandler)
			((UFormHandler) h).startDialog("uimsDataExportProcessDialog", form);
	}

	void doDataExportServerWithProcess(DataIoStruct info) {
		UHandlerI h = this.getUParent().getHandler();
		HashMap userMap = null;
		if (h != null)
			userMap = h.getBusinessParaMap(componentName);
		DataIoProcessForm form = new DataIoProcessForm();
		form.setDataIoStruct(info);
		form.setUserMap(userMap);
		form.setComName(this.componentName);
		if (h instanceof UFormHandler)
			((UFormHandler) h).startDialog(
					"uimsDataExportServerWithProcessDialog", form,h);
	}
}
