package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.UTextArea;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.label.ULabel;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.form.impl.FileUploadDataInfoForm;
import cn.edu.sdu.uims.form.impl.FileUploadProcessForm;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class FileUploadProcessDialogHandler extends UDialogHandler implements
		Runnable {

	private Thread progress = null;
	private boolean canleMark = false;
	private StringBuffer sb = null;

	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent) o;
		String command = e.getActionCommand();
		UDialogPanel dlg = (UDialogPanel) component;
		if (command.equals("okCommand")) {
			this.componentToForm();
			dlg.doOk();
		} else if (command.equals("cancelCommand")) {
			doCancel();
		} else {
			dlg.doUserAction(command);
		}
	}

	public void doCancel() {
		canleMark = true;
	}

	public void start() {
		progress = new Thread(this);
		progress.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ULabel countLable = (ULabel) this.getComponent().getSubComponent(
					"countLable");
			UTextArea infoArea = (UTextArea) this.getComponent()
					.getSubComponent("infoArea");
			FileUploadProcessForm dForm = (FileUploadProcessForm) dataForm;
			Map<Integer, String> map = readfile(dForm.getDirName(), null);
			File files[] = new File[map.size()];
			InputStream fis;
			long length;
			byte[] data;
			String retInfo;
			int dispCount = 0;
			boolean isEnglish = UimsFactory.getClientMainI().isEnglishVersion();
			String noStr = UFactory.getModelSession().getPromptInfoByName("序号", isEnglish);
			dForm.setDispLayInfo("");
			sb = new StringBuffer(1024);
			RmiRequest request = new RmiRequest();
			for (int i = 0; i < map.size(); i++) {
				files[i] = new File(map.get(i));
				if (!files[i].exists() || !files[i].isFile())
					continue;
				if (files[i].getName().endsWith(".db"))
					continue;
				length = files[i].length();
				fis = new FileInputStream(files[i]);
				data = new byte[(int) length];
				fis.read(data);
				fis.close();
				FileUploadDataInfoForm dataForm = new FileUploadDataInfoForm();
				dataForm.setParaMap(dForm.getUserMap());
				dataForm.setFileName(files[i].getName());
				dataForm.setFileData(data);
				request.add(RmiKeyConstants.KEY_FORM, dataForm);
				request.setCmd((String) (dForm.getUserMap().get("cmd")));
				RmiResponse response = UimsUtils.requestProcesser(request);
				if (response.getErrorMsg() != null) {
					retInfo = response.getErrorMsg();
					retInfo = UFactory.getModelSession().getPromptInfoByName(retInfo, isEnglish);
					sb.append(files[i].getName() + ":" + retInfo + "\n");
				} else {
					retInfo = (String) response.get(RmiKeyConstants.KEY_STRING);
					if (retInfo == null)
						retInfo = "ok";
					else {
						retInfo = UFactory.getModelSession().getPromptInfoByName(retInfo, isEnglish);
						sb.append(files[i].getName() + ":" + retInfo + "\n");
					}
				}
				if (dispCount > 30) {
					dForm.setDispLayInfo(noStr + (i) + ":" + files[i].getName()
							+ ":" + retInfo);
					dispCount = 0;
				} else {
					dForm.setDispLayInfo(dForm.getDispLayInfo() + "\n" + noStr
							+ (i) + ":" + files[i].getName() + ":" + retInfo);
					dispCount++;
				}
				infoArea.setText(dForm.getDispLayInfo());
			}
			if (sb == null || sb.length() == 0) {
				UimsUtils.messageBoxInfo("数据导入成功");
			} else {
				int ret = UimsUtils.messageBoxChoice("数据导入存在错误，请确认保存日志");
				if (ret == JOptionPane.YES_OPTION) {
					saveLogFile(sb);
				}
			}
			doOk();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doOk() {
		UDialogPanel dlg = (UDialogPanel) component;
		dlg.doOk();
	}

	public static Map<Integer, String> readfile(String filepath,
			Map<Integer, String> pathMap) throws Exception {
		if (pathMap == null) {
			pathMap = new HashMap<Integer, String>();
		}

		File file = new File(filepath);

		// 文件
		if (!file.isDirectory()) {
			pathMap.put(pathMap.size(), file.getPath());

		} else if (file.isDirectory()) { // 如果是目录， 遍历所有子目录取出所有文件名
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "/" + filelist[i]);
				if (!readfile.isDirectory()) {
					pathMap.put(pathMap.size(), readfile.getPath());

				} else if (readfile.isDirectory()) { // 子目录的目录
					readfile(filepath + "/" + filelist[i], pathMap);
				}
			}
		}
		return pathMap;
	}

	void saveLogFile(StringBuffer sb) {
		File file = GetFile.getSaveFile(".txt", this.getComponent()
				.getAWTComponent());
		if (file == null)
			return;
		FileWriter out;
		try {
			out = new FileWriter(file);
			out.write(sb.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayInfo(String message) {

	}

}
