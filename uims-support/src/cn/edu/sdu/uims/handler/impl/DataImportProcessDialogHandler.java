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

public class DataImportProcessDialogHandler extends UDialogHandler implements
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
			DataIoProcessForm dForm = (DataIoProcessForm) dataForm;
			DataImportTemplateFromXLS template = (DataImportTemplateFromXLS) UFactory
					.getModelSession().getBsTemplate("dataImport",
							dForm.getDataIoStruct().getModelName());
			if (template == null) {
				JOptionPane.showMessageDialog(this.getComponent()
						.getAWTComponent(), "导入模板不存在！");
				doOk();
				return;
			}
			HashMap<String, Method> setMethodMap = UimsUtils
					.getSetMethodLabelAsKey(template.getFormClassName(),
							template.getItemList());
			HashMap<String, ItemTemplate> itemMap = UimsUtils
					.getItemMapLableAsKey(template.getItemList());
			HashMap<String, String> sourceMap = new HashMap<String, String>();
			HashMap<String, String> poMap = new HashMap<String, String>();
			HashMap<String, String> initMap = new HashMap<String, String>();
			HashMap<String, String> methodMap = new HashMap<String, String>();
			HashMap<String, String> dataMap = new HashMap<String, String>();
			HashMap<String, String> valueMap = new HashMap<String, String>();
			for (int i = 0; i < template.getItemList().size(); i++) {
				if (template.getItemList().get(i).getSource() != null)
					sourceMap.put(template.getItemList().get(i).getName(),
							template.getItemList().get(i).getSource());
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
						.getAWTComponent(), "导入模板不存在！");
				doOk();
				return;
			}
			String filename = file.getName();
			String[] b= filename.split("\\.");

			int row;
			Cell cell;
			String cont;
			Method m;
			Object o;
			String retInfo;
			Integer level =0;//当前循环等级
			int dispCount = 0;
			ArrayList<DataImportFormRowFormI> rowForms = new ArrayList<DataImportFormRowFormI>();
			String keyInfo[] = new String[rowForms.size()];

			if(b.length>1 && b[1].equals("xlsx")){
//				ExampleEventUserModel model = new ExampleEventUserModel();
//				rowForms = model.processOneSheet(file.getAbsolutePath(),template);
			}else if(b.length>1 && b[1].equals("xls")){
				InputStream fis = new FileInputStream(file);
				Workbook rwb = Workbook.getWorkbook(fis);
				String page =  null;
				if(dForm != null && dForm.getUserMap() != null)
					page = (String)dForm.getUserMap().get("page");
				Sheet rs=null;
				if(page!=null && !page.equals("")){
					Integer xpage = Integer.parseInt(page)-1;
					rs = rwb.getSheet(xpage);
				}else{
					rs = rwb.getSheet(0);
				}
				Integer rsColumns = rs.getColumns();
				Integer rsRows = rs.getRows();
				
				String head[] = new String[rsColumns];
				for (int col = 0; col < rsColumns; col++) {
				cell = rs.getCell(col, 0);
				head[col] = cell.getContents().trim();
				}
				for (row = 0; row < rsRows-1; row++) {
					DataImportFormRowFormI tform=(DataImportFormRowFormI) Class.forName(
							template.getFormClassName()).newInstance();
					rowForms.add(tform);
			}
				String pKey = template.getPrimaryKey();
				dForm.setDispLayInfo("");
				sb = new StringBuffer(1024);
				row = 1;
					int k = 0;
						while (k < rsRows && k + row < rsRows) {
							for (int col = 0; col < rsColumns; col++) {
								cell = rs.getCell(col, row + k);
								cont = cell.getContents().trim();
								if (cont == null || cont.length() == 0)
									continue;
								ItemTemplate item = itemMap.get(head[col]);
							//	if (item != null && item.getName().equals(pKey))
								//	keyInfo[k] = cont;
								m = setMethodMap.get(head[col]);
								if (m != null) {
									o = item.StringToObject(cont);
									m.invoke(rowForms.get(k), o);
								}
							}
							k++;
			}
				countLable.setText("记录总数： " + (rsRows - 1) + "条");
			}else{
				UimsUtils.messageBoxInfo("导入文件类型必须为xls/xlsx!");
				doOk();
				return;
			}	
			HashMap retMap = null;
			while(rowForms.size() - 1000*level >0){
				
				if (template.getRuleBean() != null && poMap.size() > 0)
					retMap = sendDataToServerByBean(template.getRuleBean(),
							rowForms, rowForms.size(), dForm.getUserMap(), dForm
									.getDataIoStruct().getParas(), sourceMap,
							poMap, initMap, methodMap, dataMap, valueMap,level);
				else if (template.getRuleBean() != null)
					retMap = sendDataToServerByBean(template.getRuleBean(),
							rowForms, rowForms.size(), dForm.getUserMap(), dForm
									.getDataIoStruct().getParas(),level);
				else
					retMap = sendDataToServer(template.getCmd(), rowForms, rowForms.size(),
							dForm.getUserMap(), dForm.getDataIoStruct()
									.getParas(),level);
				if(rowForms.size() - 1000*(level+1) >0)
					countLable.setText("已完成： " + 1000*level + "条");
				else
					countLable.setText("已完成： " + rowForms.size() + "条");
				level++;
			}
//
				/*for (int j = 0; j < k; j++) {
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
				}*/
//				row += k;
//			}
//			fis.close();
			sb = new StringBuffer();
			/*if(retMap!=null)
				sb.append(retMap.get(RmiKeyConstants.KEY_MESSAGE).toString());*/
			for (int j = 0; j < rowForms.size(); j++) {
				retInfo = null;
				if (retMap != null) {
					retInfo = (String) retMap.get(j+"");
				}
				if(retInfo!=null){
					sb.append("\r\n" + retInfo);
					if (dispCount > 30) {
						dForm.setDispLayInfo(retInfo);
						dispCount = 0;
					} else {
						dForm.setDispLayInfo(dForm.getDispLayInfo() + "\n"
								+ retInfo);
						dispCount++;
					}
					infoArea.setText(dForm.getDispLayInfo());
				}
			}
			if(retMap!=null && retMap.get(RmiKeyConstants.KEY_MESSAGE) != null)
				infoArea.setText((retMap.get(RmiKeyConstants.KEY_MESSAGE).toString()) );
			
			if (sb == null || sb.length() == 0) {
				JOptionPane.showMessageDialog(this.getComponent()
						.getAWTComponent(), "数据导入成功！");
			} else {
				int ret = JOptionPane.showConfirmDialog(this.getComponent()
						.getAWTComponent(), "数据导入存在错误，请确认保存日志");
				if (ret == JOptionPane.YES_OPTION) {
					saveLogFile(sb);
				}
			}
			doOk();
		}catch (Exception e) {
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

	public HashMap sendDataToServer(String cmd, ArrayList<DataImportFormRowFormI> form,
			int count, HashMap userMap, String paras,Integer level) {
		List list = new ArrayList();
		if(form.size() < 1000*(level+1)){
			for (int i = 1000*level; i < form.size(); i++) {
				list.add(form.get(i));
			}
		}else{
			for (int i = 1000*level; i < 1000*(level+1); i++) {
				list.add(form.get(i));
			}
		}
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING1, paras);
		request.add(RmiKeyConstants.KEY_FORMLIST, list);
		request.add(RmiKeyConstants.KEY_HASH, userMap);
		request.setCmd(cmd);
		RmiResponse response = UimsUtils.requestProcesser(
				request);
		return (HashMap) response.get(RmiKeyConstants.KEY_HASH);
	}

	public HashMap sendDataToServerByBean(String ruleName,
			ArrayList<DataImportFormRowFormI> form, int count, HashMap userMap,
			String paras,Integer level) {
		List list = new ArrayList();
		if(form.size() < 1000*(level+1)){
			for (int i = 1000*level; i < form.size(); i++) {
				list.add(form.get(i));
			}
		}else{
			for (int i = 1000*level; i < 1000*(level+1); i++) {
				list.add(form.get(i));
			}
		}
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING1, paras);
		request.add(RmiKeyConstants.KEY_FORMLIST, list);
		request.add(RmiKeyConstants.KEY_STRING, ruleName);
		request.add(RmiKeyConstants.KEY_HASH, userMap);
		request.setCmd("uimsSetFormDataListToServer");
		RmiResponse response = UimsUtils.requestProcesser(
				request);
		return (HashMap) response.get(RmiKeyConstants.KEY_HASH);
	}

	public HashMap sendDataToServerByBean(String ruleName,
			ArrayList<DataImportFormRowFormI> form, int count, HashMap userMap,
			String paras, HashMap sourceMap, HashMap poMap, HashMap initMap,
			HashMap methodMap, HashMap dataMap, HashMap valueMap,Integer level) {
		List list = new ArrayList();
		if(form.size() < 1000*(level+1)){
			for (int i = 1000*level; i < form.size(); i++) {
				list.add(form.get(i));
			}
		}else{
			for (int i = 1000*level; i < 1000*(level+1); i++) {
				list.add(form.get(i));
			}
		}
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING1, paras);
		request.add(RmiKeyConstants.KEY_FORMLIST, list);
		request.add(RmiKeyConstants.KEY_STRING, ruleName);
		request.add(RmiKeyConstants.KEY_HASH, userMap);
		request.add(RmiKeyConstants.KEY_HASH6, sourceMap);
		request.add(RmiKeyConstants.KEY_HASH1, poMap);
		request.add(RmiKeyConstants.KEY_HASH2, methodMap);
		request.add(RmiKeyConstants.KEY_HASH3, dataMap);
		request.add(RmiKeyConstants.KEY_HASH4, initMap);
		request.add(RmiKeyConstants.KEY_HASH5, valueMap);
		request.setCmd("uimsSetFormDataListAndMapToServer");
		RmiResponse response = UimsUtils.requestProcesser(request);
		return (HashMap) response.get(RmiKeyConstants.KEY_HASH);
	}

}