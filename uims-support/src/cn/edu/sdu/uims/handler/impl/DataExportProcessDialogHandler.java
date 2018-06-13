package cn.edu.sdu.uims.handler.impl;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.component.UProgressBar;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.label.ULabel;
import cn.edu.sdu.uims.def.dataexport.DataExportItemTemplate;
import cn.edu.sdu.uims.def.dataexport.DataExportTemplateToXLS;
import cn.edu.sdu.uims.form.impl.DataIoProcessForm;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DataExportProcessDialogHandler extends UDialogHandler implements
		Runnable {

	private Thread progress = null;
	private boolean cancelMark = false;

	
	public void start() {
		progress = new Thread(this);
		progress.start();
	}
	
	
	public List getDataExportFormDataKeyList(DataIoProcessForm dlgForm){
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, dlgForm.getDataIoStruct().getModelName());
		request.add(RmiKeyConstants.KEY_HASH, dlgForm.getUserMap());
		request.setCmd("uimsGetDataExportFormDataKeyList");
		RmiResponse response = UimsUtils.requestProcesser(request);
		return (List)response.get(RmiKeyConstants.KEY_FORMLIST);
	}
	public List getDataExportFormDataPartList(DataIoProcessForm dlgForm, List list ){
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_STRING, dlgForm.getDataIoStruct().getModelName());
		request.add(RmiKeyConstants.KEY_HASH, dlgForm.getUserMap());
		request.add(RmiKeyConstants.KEY_FORMLIST, list);
		request.setCmd("uimsGetDataExportFormDataPartList");
		RmiResponse response = UimsUtils.requestProcesser(request);
		return (List)response.get(RmiKeyConstants.KEY_FORMLIST);
	}
	public void changeFormListToBytes(int pos, WritableSheet sheet1, DataExportTemplateToXLS template,List list) throws Exception{
		jxl.write.Label labelColumnName;
		List<DataExportItemTemplate> itemList = template.getItemList();
		int row;
		Object o;
		String str;
		int col;
		Object dataObject;
		HashMap<String, Method> getMethodMap = template.getMethodMap();
		if (getMethodMap == null)
			return;
		Method m;
		for (row = 0; row < list.size(); row++) {
			dataObject = list.get(row);
			if (dataObject == null)
				continue;
			for (col = 0; col < itemList.size(); col++) {
				m = getMethodMap.get(itemList.get(col).getName());
				if (m == null)
					str = "";
				o = m.invoke(dataObject);
				if (o == null)
					str = "";
				else {
					str = o.toString();
				}
				labelColumnName = new jxl.write.Label(col, row+1+pos, str);
				sheet1.addCell(labelColumnName);
			}
		}
	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		UDialogPanel dlg = (UDialogPanel) component;
		DataIoProcessForm dlgForm = (DataIoProcessForm)dataForm;
		UProgressBar progressBar = (UProgressBar)this.component.getSubComponent("progressBar");	
		ULabel progressInfo = (ULabel)this.component.getSubComponent("progressInfo");

		List keyList = getDataExportFormDataKeyList(dlgForm);
		if(keyList == null || keyList.size() < 1){
			JOptionPane.showMessageDialog(dlg.getAWTComponent(), "数据导出错！");
			dlg.doClose();			
			return;
		}
		DataExportTemplateToXLS template = (DataExportTemplateToXLS)UFactory.getModelSession().getBsTemplate("dataExport", dlgForm.getDataIoStruct().getModelName());
		try {
			jxl.write.Label labelColumnName;
			FileOutputStream out = new FileOutputStream(dlgForm.getDataIoStruct().getFileName());
			WritableWorkbook book;
			WritableSheet sheet1;
			book = Workbook.createWorkbook(out);
			sheet1 = book.createSheet("第一页", 0);
			List<DataExportItemTemplate> itemList = template.getItemList();
			for (int col = 0; col < itemList.size(); col++) {
				labelColumnName = new jxl.write.Label(col, 0, itemList.get(col)
						.getLabel());
				// 将定义好的单元格添加到工作表中
				sheet1.addCell(labelColumnName);
			}
			int size = keyList.size();
			int pos = 0;
			int rowNum= template.getRowNum();
			List oList = new ArrayList(rowNum);
			int i;
			List rList;
			while(pos < size && !cancelMark) {
				oList.clear();
				i = 0;
				while (i<rowNum && pos + i < size) {
					oList.add(keyList.get(pos+i));
					i++;
				}
				rList = getDataExportFormDataPartList(dlgForm, oList);
				if(rList != null) {
					changeFormListToBytes(pos, sheet1, template, rList);
					pos += rList.size();
					if (progressBar != null)
						progressBar.setValue(pos*100/size);
					if(progressInfo != null)
						progressInfo.setText("共计  "+ size + " 条，已导出 " + pos + " 条");
				}else {
					cancelMark = true;
				}
			}
			book.write();
			book.close();
			out.close();
			if(!cancelMark) 
				JOptionPane.showMessageDialog(dlg.getAWTComponent(), "数据导出成功！");
			else {
				JOptionPane.showMessageDialog(dlg.getAWTComponent(), "数据导出错误！");
			}
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(dlg.getAWTComponent(), "数据导出错误！");
		}
		dlg.doClose();
	}

	public void processWindowEvent(Object o, String cmd){
		if(cmd.equals(EventConstants.CMD_CLOSING)){
			cancelMark = true;
		}
	}
	
}
