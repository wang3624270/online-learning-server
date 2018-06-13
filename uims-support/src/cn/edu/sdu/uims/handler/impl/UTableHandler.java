package cn.edu.sdu.uims.handler.impl;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.form.UFormIdI;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.graph.form.GraphPrintForm;
import cn.edu.sdu.uims.graph.form.UPdfViewDataForm;
import cn.edu.sdu.uims.graph.model.GraphLayer;
import cn.edu.sdu.uims.graph.model.GraphLayerI;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.GraphModelViewParas;
import cn.edu.sdu.uims.graph.view.GraphViewI;
import cn.edu.sdu.uims.graph.view.UViewPrinter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.util.UimsUtils;

public class UTableHandler extends UToolHandler  {

	public Integer[] getIds(){
		return getIds(this.getUTablePanel());
	}
	public Integer[] getIds(UTablePanel table){
		if(table == null)
			return null;
		List list = table.getSelectRowsList();
		if (list == null || list.size() == 0) {
			return null;
		}
		int i;
		Integer ids[] = new Integer[list.size()];
		UFormIdI idform;
		if (!(list.get(0) instanceof UFormIdI))
			return null;
		for (i = 0; i < ids.length; i++) {
			idform = (UFormIdI) list.get(i);
			ids[i] = idform.getId();
		}
		return ids;
	}
		
	public UTablePanel getUTablePanel(String name){
		UComponentI com = component.getSubComponent(name);
		if (!(com instanceof UTablePanel))
			return null;
		return (UTablePanel) com;
	}

	public UTablePanel getUTablePanel(){
		UComponentI[] coms =this.component.getAllSubComponents();
		if(coms == null || coms.length == 0)
			return null;
		for(int i = 0; i < coms.length;i++) {
			if(coms[i] instanceof UTablePanel) {
				return (UTablePanel)coms[i];
			}
		}
		return null;
	}
	
	public void processPopupMenuEvent(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("infoSend")){
			this.doInfoSend();
		}else if(cmd.equals("exportExcl")){
			this.doExport();
		}
		else if(cmd.equals("exportPhoto")){
			doExportPhoto();
		}
		else if(cmd.equals("import")){
			doImport();
		}else if(cmd.equals("downloadTemplate")) {
			doDownloadTemplate();
		}
		else if(cmd.startsWith("print")) {
			doPrintData(cmd);
		}
		if(cmd.startsWith("exportData")) {
				doExportData(cmd);
		}
	}
	public void doInfoSend(Integer ids[], String typeName) {
		if(ids == null) {
			UimsUtils.messageBoxInfo("没有选中的人员不能发送短信！");
			return;			
		}
/*		
		MessageInfoSendForm form = new MessageInfoSendForm();
		form.setIds(ids);
		form.setTypeName(typeName);
		form.setMailCheck(true);
		form.setSmsCheck(true);
		startDialog("messageInfoSendDialog",form);
*/		
	}
	
	public void doInfoSend(String fieldName, String typeName) {
		Integer ids[] = this.getIds(getUTablePanel(fieldName));
		doInfoSend(ids,typeName);
	}

	public void doInfoSend(){
		doInfoSend(getIds(),"");
	}
	public void doExportPhoto(){
		doExportPhoto(getIds(),"");	
	}
	public void doExportPhoto(String fieldName, String typeName) {
		doExportPhoto(getIds(this.getUTablePanel(fieldName)),typeName);	
	}
	public void doExportPhoto(Integer ids[], String typeName){
	}
	public void doExportData(String cmd){
		String methodName = "do" + cmd.substring(0, 1).toUpperCase()
		+ cmd.substring(1, cmd.length());
		try {
			Method m = this.getClass().getMethod(methodName, Integer [].class);
			m.invoke(this, this.getIds());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void doImport(){
		doImport(this.getUTablePanel());
	}
	public void doImport(String name){
		doImport(this.getUTablePanel(name));
	}
	
	public void doImport(UTablePanel p){
		if(p == null)
			return;
		p.readDataFromExcel();
		this.componentToForm();
	}
	public void doDownloadTemplate(UTablePanel p){
		if(p != null)
			p.doDownloadTemplate();
	}
	public void doDownloadTemplate(){
		doDownloadTemplate(this.getUTablePanel());
	}
	public void doDownloadTemplate(String fieldName){
		doDownloadTemplate(this.getUTablePanel(fieldName));
	}
	public void doPrint() {
		// TODO Auto-generated method stub
	}
	public String getPrintTableFieldName(String menuName){
		return menuName;
	}
	public String getPrintModelName(String menuName){
		return menuName;
	}
	
	public List<GraphLayerI> newTempGraphLayerList(List<GraphLayerI> layers, int count){
		if(layers == null || layers.size()== 0)
			return null;
		List<GraphLayerI> retList = new ArrayList<GraphLayerI>(count);
		GraphLayer newLayer;
		for(int i = 0; i < count/layers.size(); i++) {
			for(int j = 0; j < layers.size();j++) {
				newLayer = new GraphLayer();
				newLayer.copy(layers.get(j));
				retList.add(newLayer);
			}
		}
		return retList; 
	}
	public List getPrintedFormDataLIst(List list , String cmd){
		return list;
	}
	public void  doPrintData(String cmd){
		String menuName =  cmd.substring(5, 6).toLowerCase() + cmd.substring(6, cmd.length());
		UTablePanel p = this.getUTablePanel(getPrintTableFieldName(menuName));
		List list = p.getSelectRowsList();
		if(list == null || list.size() == 0) {
			UimsUtils.messageBoxInfo("没有选中的数据不能打印！");
			return;	
		}
		List formList = getPrintedFormDataLIst(list,menuName);
		if(formList == null || formList.size() == 0)
			return;
		String modelName = getPrintModelName(menuName);
		GraphModelI g2d = UFactory.getModelSession().getGraphModel2DObject(modelName);
		if(g2d == null)
			return;
		GraphPrintForm form =  new GraphPrintForm();
		form.setCurrentGraphObject(g2d);
		form.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		GraphViewI pt = new UViewPrinter(paperTemplate);
		List<GraphLayerI> oldList = g2d.getLayerList();
		List<GraphLayerI> newList = newTempGraphLayerList(oldList, formList.size()) ;
		g2d.setLayerList(newList);
		g2d.makeGraphDataForm();
		for(int i  = 0;i < g2d.getLayerSize();i++)
		g2d.setLayerDataForm(i, (UFormI)formList.get(i));	
		pt.invokeJob(form);
		g2d.setLayerList(oldList);	
	}
	
	public void  doPrintPreviewData(String cmd){
		String menuName =  cmd.substring(5, 6).toLowerCase() + cmd.substring(6, cmd.length());
		UTablePanel p = this.getUTablePanel(getPrintTableFieldName(menuName));
		List list = p.getSelectRowsList();
		if(list == null || list.size() == 0) {
			UimsUtils.messageBoxInfo("没有选中的数据不能打印！");
			return;	
		}
		List formList = getPrintedFormDataLIst(list,menuName);
		if(formList == null || formList.size() == 0)
			return;
		String modelName = getPrintModelName(menuName);
		byte [] data = getGraphPrintPdfByteArray(formList, modelName);
		if(data == null)
			return;
		UPdfViewDataForm pForm = new UPdfViewDataForm();
		pForm.setData(data);
		this.startDialog("uimsPdfPrintPreviewDialog", pForm);
	}

	public ViewParameter getViewParameter(GraphModelI currentGraphObject) {
		double graphWidth = currentGraphObject.getGraphWidth();
		double graphHeight = currentGraphObject.getGraphHeight();
		double imageWidth = currentGraphObject.getImageWidth();
		double imageHeight = currentGraphObject.getImageHeight();
		WVTrans wv = new WVTrans();
		wv.setWindows(0, 0, graphWidth, graphHeight);
		wv.setViewport(0, 0, imageWidth, imageHeight);
		wv.makeWVMatrix();
		UMatrix mt = new UMatrix();
		ViewParameter viewParameter = new ViewParameter(wv.m, mt);
		return viewParameter;
	}
	
	public byte[] getGraphPrintPdfByteArray(List formList,String modelName) {
		if(formList == null || formList.size() == 0)
			return null;
		GraphModelI g2d = UFactory.getModelSession().getGraphModel2DObject(modelName);
		if (g2d == null)
			return null;
//		ReportElementForm eForm = new ReportElementForm();
		ViewParameter v = getViewParameter(g2d);
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		int imageWidth, imageHeight;
		if(paperTemplate.orientation == 1) {
			imageHeight = (int) (paperTemplate.width );
			imageWidth  = (int) (paperTemplate.height);
		}
		else {
			imageWidth = (int) (paperTemplate.width );
			imageHeight = (int) (paperTemplate.height);			
		}
		List<GraphLayerI> oldList = g2d.getLayerList();
		List<GraphLayerI> newList = newTempGraphLayerList(oldList, formList.size()) ;
		g2d.setLayerList(newList);
		g2d.makeGraphDataForm();
		ControlParameter c = new ControlParameter();
		c.isServer = true;
		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
		Document document = new Document(new Rectangle(imageWidth, imageHeight));
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					out);
			document.open();
			PdfContentByte canvas = writer.getDirectContent();
			DefaultFontMapper mapper1 = UFactory.getModelSession()
					.getFontMapper();
			for (int i = 0; i < g2d.getLayerSize(); i++) {
				document.newPage();
				Graphics2D g2 = canvas.createGraphicsForUims(imageWidth,
						imageHeight, mapper1);
				g2d.drawLayer(g2, new GraphModelViewParas(v, c, formList.get(i), new UPoint(0, 0)), i);
				g2.dispose();
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		g2d.setLayerList(oldList);
		return out.toByteArray();
	}
	
}
