package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.component.UTextArea;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.label.ULabel;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.def.dataimport.DataImportTemplateFromXLS;
import cn.edu.sdu.uims.def.dataimport.ItemTemplate;
import cn.edu.sdu.uims.form.DataImportFormRowFormI;
import cn.edu.sdu.uims.form.impl.DataIoProcessForm;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DataImportValidateDialogHandler extends UDialogHandler implements
		Runnable {

	private Thread progress = null;
	private boolean cancelMark = false;
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
		cancelMark = true;
	}

	public void start() {
		progress = new Thread(this);
		progress.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ULabel countLabel = (ULabel) this.getComponent().getSubComponent(
					"countLabel");
			UTextArea infoArea = (UTextArea) this.getComponent()
					.getSubComponent("infoArea");
			DataIoProcessForm dForm = (DataIoProcessForm) dataForm;
			DataImportTemplateFromXLS template = (DataImportTemplateFromXLS) UFactory
					.getModelSession().getBsTemplate("dataImport",
							dForm.getDataIoStruct().getModelName());
			if (template == null) {
				JOptionPane.showMessageDialog(this.getComponent()
						.getAWTComponent(), "校验模板不存在！");
				doOk();
				return;
			}
			int rowNum = template.getRowNum();
			HashMap<String, Method> setMethodMap = UimsUtils
					.getSetMethodLabelAsKey(template.getFormClassName(),
							template.getItemList());
			HashMap<String, ItemTemplate> itemMap = UimsUtils
					.getItemMapLableAsKey(template.getItemList());
			HashMap<String, String> poMap = new HashMap<String, String>();
			HashMap<String, String> initMap = new HashMap<String, String>();
			HashMap<String, String> methodMap = new HashMap<String, String>();
			HashMap<String, String> dataMap = new HashMap<String, String>();
			HashMap<String, String> valueMap = new HashMap<String, String>();
			for (int i = 0; i < template.getItemList().size(); i++) {
				if (template.getItemList().get(i).getTarget() != null)
					poMap.put(template.getItemList().get(i).getName(), template
							.getItemList().get(i).getTarget());
				if (template.getItemList().get(i).getInit() != null)
					initMap.put(template.getItemList().get(i).getName(),
							template.getItemList().get(i).getInit());
				if (template.getItemList().get(i).getMap() != null)
					methodMap.put(template.getItemList().get(i).getName(),
							template.getItemList().get(i).getMap());
				if (template.getItemList().get(i).getDataMap() != null)
					dataMap.put(template.getItemList().get(i).getName(),
							template.getItemList().get(i).getDataMap());
				if (template.getItemList().get(i).getDataValue() != null)
					valueMap.put(template.getItemList().get(i).getName(),
							template.getItemList().get(i).getDataValue());
			}

			File file = new File(dForm.getFileName());
			if (!file.exists()) {
				JOptionPane.showMessageDialog(this.getComponent()
						.getAWTComponent(), "校验文件不存在！");
				doOk();
				return;
			}
			if (template.getRuleBean() == null) {
				JOptionPane.showMessageDialog(this.getComponent()
						.getAWTComponent(), "无可用的校验方法！");
				doOk();
				return;
			}

			InputStream fis = new FileInputStream(file);
			Workbook rwb = Workbook.getWorkbook(fis);
			Sheet rs = rwb.getSheet(0);
			int rsColumns = rs.getColumns();
			int rsRows = rs.getRows();
			String head[] = new String[rsColumns];
			Cell cell;
			int row = 1, col;
			String keyInfo[] = new String[rowNum];
			DataImportFormRowFormI rowForms[] = new DataImportFormRowFormI[rowNum];
			for (row = 0; row < rowNum; row++) {
				rowForms[row] = (DataImportFormRowFormI) Class.forName(
						template.getFormClassName()).newInstance();
			}
			for (col = 0; col < rsColumns; col++) {
				cell = rs.getCell(col, 0);
				head[col] = cell.getContents().trim();
			}
			countLabel.setText("记录总数： " + (rsRows - 1) + "条");
			String cont;
			Method m;
			Object o;
			ItemTemplate item;
			String retInfo;
			String pKey = template.getPrimaryKey();
			dForm.setDispLayInfo("");
			sb = new StringBuffer(1024);
			int k;
			HashMap retMap = null;
			int dispCount = 0;
			row = 1;

			while (!cancelMark && row < rsRows) {
				k = 0;
				while (k < rowNum && k + row < rsRows) {
					rowForms[k] = rowForms[k].clear();
					for (col = 0; col < rsColumns; col++) {
						cell = rs.getCell(col, row + k);
						cont = cell.getContents().trim();
						if (cont == null || cont.length() == 0)
							continue;
						item = itemMap.get(head[col]);
						if (item != null && item.getName().equals(pKey))
							keyInfo[k] = cont;
						m = setMethodMap.get(head[col]);
						if (m != null) {
							o = item.StringToObject(cont);
							m.invoke(rowForms[k], o);
						}
					}
					k++;
				}
				retMap = sendDataToServerByBean(template.getRuleBean(),
						rowForms, k, dForm.getUserMap(), dForm
								.getDataIoStruct().getParas(), poMap, initMap,
						methodMap, dataMap, valueMap);
				if (retMap != null
						&& retMap.get(RmiKeyConstants.KEY_ERRORMSG) != null) {
					JOptionPane.showMessageDialog(this.getComponent()
							.getAWTComponent(), retMap
							.get(RmiKeyConstants.KEY_ERRORMSG));
					doOk();
					return;
				}

				for (int j = 0; j < k; j++) {
					retInfo = null;
					if (retMap != null) {
						retInfo = (String) retMap.get(keyInfo[j]);
					}
					if (retInfo == null)
						retInfo = "ok";
					else {
						sb.append("\r\n" + retInfo);
					}
					if (dispCount > 30) {
						dForm.setDispLayInfo("序号" + (row + j) + ":"
								+ keyInfo[j] + ":" + retInfo);
						dispCount = 0;
					} else {
						dForm.setDispLayInfo(dForm.getDispLayInfo() + "\n"
								+ "序号" + (row + j) + ":" + keyInfo[j] + ":"
								+ retInfo);
						dispCount++;
					}
					infoArea.setText(dForm.getDispLayInfo());
				}
				row += k;
			}
			fis.close();
			if (sb == null || sb.length() == 0) {
				JOptionPane.showMessageDialog(this.getComponent()
						.getAWTComponent(), "数据校验成功！");
			} else {
				int ret = JOptionPane.showConfirmDialog(this.getComponent()
						.getAWTComponent(), "数据校验存在错误，请确认保存日志");
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

	void saveLogFile(StringBuffer sb) {
		File file = GetFile.getSaveFile(".txt");
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

	public HashMap sendDataToServerByBean(String ruleName,
			DataImportFormRowFormI form[], int count, HashMap userMap,
			String paras, HashMap poMap, HashMap initMap, HashMap methodMap,
			HashMap dataMap, HashMap valueMap) {
		List list = new ArrayList();
		for (int i = 0; i < count; i++) {
			list.add(form[i]);
		}
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING1, paras);
		request.add(RmiKeyConstants.KEY_FORMLIST, list);
		request.add(RmiKeyConstants.KEY_STRING, ruleName);
		request.add(RmiKeyConstants.KEY_HASH, userMap);
		request.add(RmiKeyConstants.KEY_HASH1, poMap);
		request.add(RmiKeyConstants.KEY_HASH2, methodMap);
		request.add(RmiKeyConstants.KEY_HASH3, dataMap);
		request.add(RmiKeyConstants.KEY_HASH4, initMap);
		request.add(RmiKeyConstants.KEY_HASH5, valueMap);
		request.setCmd("uimsSetFormDataListAndMapToServerValidate");
		RmiResponse response = UimsUtils.requestProcesser(
				request);
		return (HashMap) response.get(RmiKeyConstants.KEY_HASH);
	}

}