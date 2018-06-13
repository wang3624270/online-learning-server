package cn.edu.sdu.uims.handler.impl;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.FormI;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.CallBackStruct;
import cn.edu.sdu.uims.base.RequestStruct;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.USetpProcessDataDialog;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.event.UEventObject;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.flex.FNameObjectPar;
import cn.edu.sdu.uims.form.impl.CommonProgressDataForm;
import cn.edu.sdu.uims.form.impl.USetpProcessDataForm;
import cn.edu.sdu.uims.graph.form.UMutilePdfViewDataForm;
import cn.edu.sdu.uims.graph.form.UPdfViewDataForm;
import cn.edu.sdu.uims.graph.form.USinglePdfViewDataForm;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.handler.UFormHandlerI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.progress.ProgressElementObject;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class UFormHandlerBase extends UHandler implements UFormHandlerI,
		UProgressProcessorI {

	protected HashMap addedMap = new HashMap();

	public boolean componentToForm() {
		// TODO Auto-generated method stub
		return true;
	}

	public void formToComponent() {
		// TODO Auto-generated method stub

	}

	public void formToComponent(FormI form) {
		// TODO Auto-generated method stub

	}

	public HashMap getComponentInitDataMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getSubComponentData(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void initTableAddedData(List list) {
		// TODO Auto-generated method stub

	}

	public void setComponent(UComponentI com) {
	}

	public Object getAddedDate(String name) {
		if (name == null)
			return null;
		return addedMap.get(name);
	}

	public void sendActionEventToParent(String cmd) {
	}

	public void initAddedData() {

	}

	public void updateAddedData() {
	}

	public void updateAddedData(String name) {

	}

	public void putAddedData(String name, Object[] list) {
		addedMap.put(name, list);
	}

	public void setInitData(UDialogPanel p) {
	}

	public UPanelI startDialog(String name, UFormI form) {
		return startDialog(name, form, (UComponentI) null);
	}

	public UPanelI startDialog(String name, UFormI form, UComponentI ownerI,
			HashMap paras) {
		return null;
	}

	public UPanelI startDialog(String name, UFormI form, UComponentI ownerI) {
		return null;
	}

	public UPanelI startDialog(String name, UFormI form, UHandlerI ownerHandlerI) {
		return null;
	}

	public void mutiInfoShowDialog(List list, UComponentI ownerI) {
	}

	public void startPanel(String name, String content) {
	}

	public HashMap getParameters() {
		return null;
	}

	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub
	}

	public UComponentI getComponentByActionCommandString(String str) {
		return null;
	}

	public void setSubComponentData(String name, Object o) {
		// TODO Auto-generated method stub

	}

	public void processPopupMenuEvent(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	public void processActionEvent(Object o, String cmd) {

	}
	public void processFocusEvent(Object o, String cmd) {

	}

	public void processItemEvent(Object o, String cmd) {
		// TODO Auto-generated method stub

	}

	public void processMouseEvent(Object o, String cmd) {
		// TODO Auto-generated method stub

	}

	public void processMouseMotionEvent(Object o, String cmd) {

	}

	public void processAdjustmentEvent(Object o, String cmd) {

	}

	public void processWindowEvent(Object o, String cmd) {

	}

	public void processKeyEvent(Object o, String cmd) {

	}

	public void processChangeEvent(Object o, String cmd) {

	}

	public void processTableModelEvent(Object o, String cmd) {
		if(o instanceof TableModelEvent){
			TableModelEvent e = (TableModelEvent)o;
			UTablePanel table = (UTablePanel)e.getSource();
		}
	}

	public void processTreeSelectionEvent(Object o, String cmd) {

	}

	public void processGraphEvent(Object o, String cmd) {

	}

	public void processCalendarEvent(Object o, String cmd) {

	}

	public void processSelectObjectEvent(Object o, String cmd) {

	}

	public void processGraphInteractionEvent(Object o, String cmd) {

	}

	public void processGraphFocusEvent(Object o, String cmd) {

	}

	public void processListSelectionEvent(Object o, String cmd) {

	}

	public void processTimerEvent(Object o, String cmd) {

	}

	public void processGroupComponentEvent(Object o, String cmd) {

	}

	public void processGroupComponentEvent(UEventObject o, String cmd) {
		processGroupComponentEvent(o.getEventObject(), cmd);
	}

	public List processPopupMenuEvent(UEventObject o, String cmd) {
		// TODO Auto-generated method stub
		processPopupMenuEvent((ActionEvent) o.getEventObject());
		return null;
	}

	public List processActionEvent(UEventObject o, String cmd) {
		// TODO Auto-generated method stub
		processActionEvent(o.getEventObject(), cmd);
		return null;
	}
	public List processFocusEvent(UEventObject o, String cmd) {
		// TODO Auto-generated method stub
		processFocusEvent(o.getEventObject(), cmd);
		return null;
	}

	public List processMouseEvent(UEventObject o, String cmd) {
		// TODO Auto-generated method stub
		processMouseEvent(o.getEventObject(), cmd);
		return null;
	}

	public List processKeyEvent(UEventObject o, String cmd) {
		processKeyEvent(o.getEventObject(), cmd);
		return null;
	}

	public List processWindowEvent(UEventObject o, String cmd) {
		processWindowEvent(o.getEventObject(), cmd);
		return null;
	}

	public List processChangeEvent(UEventObject o, String cmd) {
		processChangeEvent(o.getEventObject(), cmd);
		return null;
	}

	public List processMouseMotionEvent(UEventObject o, String cmd) {
		processMouseMotionEvent(o.getEventObject(), cmd);
		return null;

	}

	public List processAdjustmentEvent(UEventObject o, String cmd) {

		processMouseMotionEvent(o.getEventObject(), cmd);
		return null;

	}

	public List processTableModelEvent(UEventObject o, String cmd) {
		processTableModelEvent(o.getEventObject(), cmd);
		return null;

	}

	public List processTreeSelectionEvent(UEventObject o, String cmd) {
		processMouseMotionEvent(o.getEventObject(), cmd);
		return null;

	}

	public List processListSelectionEvent(UEventObject o, String cmd) {
		processListSelectionEvent(o.getEventObject(), cmd);
		return null;

	}

	public List processGraphEvent(UEventObject o, String cmd) {
		processGraphEvent(o.getEventObject(), cmd);
		return null;

	}

	public List processCalendarEvent(UEventObject o, String cmd) {
		processCalendarEvent(o.getEventObject(), cmd);
		return null;
	}

	public List processSelectObjectEvent(UEventObject o, String cmd) {
		processSelectObjectEvent(o.getEventObject(), cmd);
		return null;
	}

	public List processGraphInteractionEvent(UEventObject o, String cmd) {
		processGraphInteractionEvent(o.getEventObject(), cmd);
		return null;

	}

	public List processGraphFocusEvent(UEventObject o, String cmd) {
		processGraphFocusEvent(o.getEventObject(), cmd);
		return null;
	}

	public List processActionEventFromSubHandler(String actionCommand) {
		return null;
	}

	public List sendHandlerRequestData(HashMap map) {
		return null;
	}

	public void processInputData(UEventObject eventObject) {
		Object result = eventObject.getResult();
		if (result instanceof UFormI) {
			dataForm = (UFormI) result;
		}
	}

	public List getcallBackLiset() {
		List retList = new ArrayList();
		CallBackStruct stru = new CallBackStruct();
		stru.handlerId = this.getId();
		stru.commandList = new ArrayList();
		retList.add(stru);
		return retList;
	}

	public List processSetData() {
		List retList = getcallBackLiset();
		CallBackStruct stru = (CallBackStruct) retList.get(0);
		addSetDataCommand(stru);
		return retList;
	}

	public void addSetDataCommand(CallBackStruct stru) {
		FNameObjectPar obj = new FNameObjectPar();
		obj.name = "setData";
		obj.ob = dataForm;
		stru.commandList.add(obj);
	}

	public void addInitAddedDataCommand(CallBackStruct stru, String name,
			List dataList) {
		FNameObjectPar obj = new FNameObjectPar();
		obj.name = "initAddedData";
		List parList = new ArrayList();
		parList.add(name);
		parList.add(dataList);
		obj.ob = parList;
		stru.commandList.add(obj);
	}

	public void addRequestDataCommand(CallBackStruct stru, List requestList) {
		FNameObjectPar obj = new FNameObjectPar();
		obj.name = "requestData";
		obj.ob = requestList;
		stru.commandList.add(obj);
	}

	public void addStartDialogCommand(CallBackStruct stru, String templateName,
			List parDataList) {
		FNameObjectPar obj = new FNameObjectPar();
		obj.name = "statDialog";
		List parList = new ArrayList();
		parList.add(templateName);
		parList.add(parDataList);
		obj.ob = parList;
		stru.commandList.add(obj);
	}

	public List processRequestData(String handlerId, String comName,
			String methodName, Object paras) {
		RequestStruct request = new RequestStruct();
		List retList = getcallBackLiset();
		CallBackStruct stru = (CallBackStruct) retList.get(0);
		request.handlerId = handlerId;
		request.comName = comName;
		request.methodName = methodName;
		request.dataObject = paras;
		List requestList = new ArrayList();
		requestList.add(request);
		addRequestDataCommand(stru, requestList);
		return retList;
	}

	public List messageBoxInfo(String s) {
		List retList = getcallBackLiset();
		CallBackStruct stru = (CallBackStruct) retList.get(0);
		FNameObjectPar obj = new FNameObjectPar();
		obj.name = "messageBoxInfo";
		obj.ob = s;
		stru.commandList.add(obj);
		return retList;
	}

	public GraphModelI getGraphModel2DObject(String name) {
		// TODO Auto-generated method stub
		return UFactory.getModelSession().getGraphModel2DObject(name);
	}

	public void saveImageToFile(Object tag, String fileName) {
		saveImageToFile(tag, new File(fileName));
	}

	public void saveImageToFile(Object obj) {
		File file = GetFile.getSaveFile("jpg");
		saveImageToFile(obj, file);
	}

	public void saveImageToFile(Object obj, File file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			BufferedImage img;
			if (obj instanceof SerialBlob) {
				SerialBlob blob = (SerialBlob) obj;
				img = ImageIO.read(blob.getBinaryStream());
			} else {
				img = (BufferedImage) obj;
			}
			ImageIO.write(img, "jpg", out);
			out.close();
		} catch (Exception e) {
		}
	}

	public void initAllAddedData() {
		UHandlerI[] hs = getAllSubHandler();
		if (hs != null && hs.length != 0) {
			for (int i = 0; i < hs.length; i++) {
				if (hs[i] != null) {
					hs[i].initAllAddedData();
				}
			}
		}
	}

	public void printPDf(UPdfViewDataForm form) {
		USinglePdfViewDataForm sForm = new USinglePdfViewDataForm();
		sForm.setData(form);
		this.startDialog("uimsPdfPrintPreviewDialog", sForm);
	}

	public void printPDf(List pdfList) {
		if (pdfList == null || pdfList.size() == 0)
			return;
		if (pdfList.size() == 1)
			printPDf((UPdfViewDataForm) pdfList.get(0));
		else {
			UMutilePdfViewDataForm form = new UMutilePdfViewDataForm();
			form.setPdfList(pdfList);
			this.startDialog("uimsMutilePdfPrintPreviewDialog", form);
		}
	}

	public String getTableCellValue(Object obj, Method m) {
		if (obj == null || m == null)
			return "";
		try {
			Object o = m.invoke(obj);
			if (o == null)
				return "";
			else
				return o.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public Method getFormGetMethod(Class c, String member) {
		String methodName = "get" + member.substring(0, 1).toUpperCase()
				+ member.substring(1);
		try {
			return c.getMethod(methodName);
		} catch (Exception e) {
			return null;
		}
	}

	public Object getExportDataForm(Object key) {
		return null;
	}

	public boolean saveFormListToExcel(List keyList,
			UTableTemplate tableTemplate) {

		if (keyList == null || keyList.size() == 0) {
			JOptionPane.showMessageDialog(null, "空表不能转出");
			return false;
		}
		File file = GetFile.getSaveFile("xls");
		if (file == null)
			return false;
		HashMap<String, Method> map = new HashMap<String, Method>();
		int i, j;
		Method m;
		try {
			Class c = Class.forName(tableTemplate.itemFormClassName);
			for (i = 0; i < tableTemplate.columnNum; i++) {
				m = getFormGetMethod(c, tableTemplate.columnTemplates[i].name);
				map.put(tableTemplate.columnTemplates[i].name, m);
			}
		} catch (Exception e) {
			return false;
		}
		WritableWorkbook book = null;
		WritableSheet sheet = null;
		try {
			book = Workbook.createWorkbook(file);
			// 生成名为 第一页 的工作表，参数0表示这是第一页
			sheet = book.createSheet("第一页", 0);
			// 写入表头 在Label对象的构造子中指名单元格位置是第一列 第一行(0,0)
			// 注意构造的顺序为先列 后行
			jxl.write.Label labelColumnName;
			for (i = 0; i < tableTemplate.columnNum; i++) {
				labelColumnName = new jxl.write.Label(i, 0,
						tableTemplate.columnTemplates[i].name);
				// 将定义好的单元格添加到工作表中
				sheet.addCell(labelColumnName);
			}
		} catch (Exception e) {

		}
		CommonProgressDataForm dlgForm = new CommonProgressDataForm();
		dlgForm.setUserObject(keyList);
		dlgForm.setCount(keyList.size());
		HashMap parasMap = new HashMap();
		parasMap.put("methodMap", map);
		parasMap.put("sheet", sheet);
		parasMap.put("tableTemplate", tableTemplate);
		dlgForm.setParaMap(parasMap);
		this.startDialog("uimsCommonProgressClientDialog", dlgForm, this);
		try {
			book.write();
			book.close();
		} catch (Exception e) {
			
		}
		return true;
	}

	@Override
	public void requestServerProcess(CommonProgressDataForm form,
			ProgressElementObject progress) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestServerProcessOne(CommonProgressDataForm form, int pos) {
		// TODO Auto-generated method stub
		int i, j;
		List keyList = (List) form.getUserObject();
		UTableTemplate tableTemplate = (UTableTemplate)form.getParasObject("tableTemplate");
		WritableSheet sheet = (WritableSheet)form.getParasObject("sheet");
		HashMap<String, Method> map = (HashMap<String, Method>)form.getParasObject("methodMap");
		Object o = getExportDataForm(keyList.get(pos));
		for (j = 0; j < tableTemplate.columnNum; j++) {
			jxl.write.Label label1 = new jxl.write.Label(j, pos + 1,
					getTableCellValue(o,map.get(tableTemplate.columnTemplates[j].name)));
			try {
				sheet.addCell(label1);
			} catch (Exception e) {

			}
		}
	}
	public void printAttachFile(Integer attachId,String fileName) {
		if(attachId == null) {
			UimsUtils.messageBoxInfo("文件不存在!,不能下载打印！");
			return;
		}
		RmiRequest request = new RmiRequest();
		request.add("attachId", attachId+"");
		request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
		request.setCmd("attachmentDownloadAttachmentFile");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null) {
			UimsUtils.messageBoxError(respond.getErrorMsg());
			return;
		}
		byte [] data = (byte[]) respond.get(RmiKeyConstants.KEY_BYTES);
		String fileName1 = fileName;
		if(fileName1 == null) {
			fileName1 = (String) respond.get("fileName");
		}
		printPdfData(data, fileName1);
	}
	
	public void printPdfData(byte data[], String fileName) {
		if(data == null) {
			UimsUtils.messageBoxInfo("数据不存在，不能下载打印！");
			return;
		}
		UPdfViewDataForm form = new UPdfViewDataForm();
		form.setData(data);
		USinglePdfViewDataForm sForm = new USinglePdfViewDataForm();
		sForm.setData(form);
		this.startDialog("uimsPdfPrintPreviewDialog", sForm);
	}
	public void openDocment(byte[]data, String fileName) {
		if(data == null) {
			UimsUtils.messageBoxInfo("数据不存在，不能打开！");
			return;
		}
		if(fileName == null) {
			UimsUtils.messageBoxInfo("文件类型错，不能打开！");
			return;			
		}
		int index = fileName.lastIndexOf('.');
		if(index < 0)
			return;
		String str = fileName.substring(index+1,fileName.length()).toUpperCase();
		if(str.equals("PDF")) {
			printPdfData(data,fileName);
		}else {
			try {
				File file   = new File("c:\\" + fileName);
				OutputStream out = new FileOutputStream(file);
				out.write(data);
				out.close();
				Desktop.getDesktop().open(file);
			}catch(Exception e){
				UimsUtils.messageBoxInfo("文件类型错，不能打开！");
				return;			
			}
		}
	}
	public void startSetpProcessDataDialog(String title, USetpProcessDataForm form){
		USetpProcessDataDialog dlg = new USetpProcessDataDialog(title, form);
	}
	
	
	public BufferedImage getImageBufferOfAttachFile(Integer attachId) {
		return UimsUtils.getImageBufferOfAttachFile(attachId);
	}
	public void saveByteDataToFile(byte[] data) {
		File file = GetFile.getSaveFile("xls");
		if(file==null)
			return;
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(data);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Object [] changeListToArray(List list){
		if(list == null || list.size() == 0)
			return null;
		Object items [] = new Object[list.size()];
		for(int i = 0; i < list.size();i++) {
			items[i] = list.get(i);
		}
		return items;
	}
}