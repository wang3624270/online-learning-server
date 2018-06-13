package cn.edu.sdu.uims.graph.handler;

import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.form.impl.USForm;
import cn.edu.sdu.uims.graph.form.GraphItemsPrintForm;
import cn.edu.sdu.uims.graph.form.GraphPrintForm;
import cn.edu.sdu.uims.graph.form.GraphViewForm;
import cn.edu.sdu.uims.graph.model.GraphLayer;
import cn.edu.sdu.uims.graph.model.GraphLayerI;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.GraphModelViewParas;
import cn.edu.sdu.uims.graph.view.GraphViewI;
import cn.edu.sdu.uims.graph.view.UViewPrinter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.handler.impl.UToolHandler;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.util.PrintPreview;

public class GraphPrintHandler extends UToolHandler {
	public GraphPrintHandler() {
		// initContents();
	}

	public GraphModelI getCurrentGraph() {
		if (dataForm == null)
			return null;
		return ((GraphViewForm) dataForm).getCurrentGraphObject();
	}

	public void startPrint() {
		startPrint(true,false);
	}
	public GraphViewI getGraphView(UPaperTemplate paperTemplate){
		return new UViewPrinter(paperTemplate);
	}
	public GraphViewI getGraphView(UPaperTemplate paperTemplate,String deviceName){
		return new UViewPrinter(paperTemplate, deviceName);
	}
	public void startPrint(boolean isSetData, boolean isPrintPreview) {
		startPrint(isSetData, isPrintPreview, null);
	}
	public void startPrint(boolean isSetData, boolean isPrintPreview, String deviceName) {
		GraphPrintForm form = (GraphPrintForm) dataForm;
		GraphModelI g2d = getCurrentGraph();
		if (g2d == null)
			return;
		form.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		GraphViewI pt = getGraphView(paperTemplate,deviceName);
		if (isSetData){
			g2d.makeGraphDataForm();
			g2d.setLayerDataForm(0, form);
		}
		if(isPrintPreview) {
			pt.setGraphPrintForm(form);
			new PrintPreview(pt.getPrintable(),pt.getFormat());
		}else
			pt.invokeJob(form);
	}
	public void startPrint(UFormI form, String modelName) {
		startPrint(form,modelName, false);
	}
	
	public void startPrint(UFormI form, String modelName, boolean isPrintPreview) {
		GraphModelI g2d = UFactory.getModelSession().getGraphModel2DObject(modelName);
		if(g2d == null)
			return;
		GraphPrintForm graphForm =  new GraphPrintForm();
		graphForm.setCurrentGraphObject(g2d);
		graphForm.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		GraphViewI pt = getGraphView(paperTemplate);
		g2d.makeGraphDataForm();
		for(int i  = 0;i < g2d.getLayerSize();i++)
		g2d.setLayerDataForm(i, form);
		if(isPrintPreview) {
			pt.setGraphPrintForm(graphForm);
			new PrintPreview(pt.getPrintable(),pt.getFormat());
		}else
			pt.invokeJob(graphForm);
	}
	public void startPrint(UFormI form, String modelName,HashMap controlDataMap) {
		startPrint(form, modelName,controlDataMap,false); 
	}

	public void startPrint(UFormI form, String modelName,HashMap controlDataMap, boolean isPrintPreview) {
		startPrint(form, modelName,controlDataMap,isPrintPreview, null);
	}	
	public void startPrint(UFormI form, String modelName,HashMap controlDataMap, boolean isPrintPreview,String deviceName) {
		GraphModelI g2d = UFactory.getModelSession().getGraphModel2DObject(modelName);
		if(g2d == null)
			return;
		GraphPrintForm graphForm =  new GraphPrintForm();
		graphForm.setCurrentGraphObject(g2d);
		graphForm.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		GraphViewI pt = getGraphView(paperTemplate,deviceName);
		g2d.makeGraphDataForm();
		for(int i  = 0;i < g2d.getLayerSize();i++)
		g2d.setLayerDataForm(i, form);
		if(isPrintPreview) {
			pt.setGraphPrintForm(graphForm);
			new PrintPreview(pt.getPrintable(),pt.getFormat());
		}else
		pt.invokeJob(graphForm);
	}
	public void startPrint(GraphPrintForm form){
		startPrint( form, false);		
	}

	public void startPrint(GraphPrintForm form, boolean isPrintPreview) {
		GraphModelI g2d = form.getCurrentGraphObject();
		if (g2d == null)
			return;
		form.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		GraphViewI pt = getGraphView(paperTemplate);
		g2d.makeGraphDataForm();
		
		for(int i  = 0;i < g2d.getLayerSize();i++)
		g2d.setLayerDataForm(i, form);
	
		if(isPrintPreview) {
			pt.setGraphPrintForm(form);
			new PrintPreview(pt.getPrintable(),pt.getFormat());
		}else
			pt.invokeJob(form);
	}

	public List<GraphLayerI> newTempGraphLayerList(GraphLayerI layer, int count){
		List<GraphLayerI> retList = new ArrayList<GraphLayerI>(count);
		GraphLayer newLayer;
		for(int i = 0; i < count; i++) {
			newLayer = new GraphLayer();
			newLayer.copy(layer);
			retList.add(newLayer);
		}
		
		return retList; 
	}
	public void startPrint(List<GraphPrintForm> formList) {
		startPrint(formList,false);
		
	}

	public void startPrint(List<GraphPrintForm> formList, boolean isPrintPreview) {
		if(formList == null || formList.size() == 0)
			return;
		GraphPrintForm form = formList.get(0);
		GraphModelI g2d = form.getCurrentGraphObject();
		if (g2d == null)
			return;
		form.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		GraphViewI pt = getGraphView(paperTemplate);
		List<GraphLayerI> oldList = g2d.getLayerList();
		List<GraphLayerI> newList = newTempGraphLayerList(oldList.get(0), formList.size()) ;
		g2d.setLayerList(newList);
		g2d.makeGraphDataForm();
		for(int i  = 0;i < g2d.getLayerSize();i++)
		g2d.setLayerDataForm(i, formList.get(i));	
		if(isPrintPreview) {
			pt.setGraphPrintForm(form);
			new PrintPreview(pt.getPrintable(),pt.getFormat());
		}else
			pt.invokeJob(form);
		g2d.setLayerList(oldList);
		
	}
	public void startPrint(String modelName, List<UFormI> dataFormList){
		startPrint(modelName, dataFormList, false);
	}
	public void startPrint(String modelName, List<UFormI> dataFormList, boolean isPrintPreview) {
		if(dataFormList == null || dataFormList.size() == 0)
			return;
		GraphPrintForm form = new GraphPrintForm();
		GraphModelI g2d = UFactory.getModelSession()
				.getGraphModel2DObject(modelName);
		form.setCurrentGraphObject(g2d);
		form.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		GraphViewI pt = getGraphView(paperTemplate);
		List<GraphLayerI> oldList = g2d.getLayerList();
		List<GraphLayerI> newList = newTempGraphLayerList(oldList.get(0), dataFormList.size()) ;
		g2d.setLayerList(newList);
		g2d.makeGraphDataForm();
		for(int i  = 0;i < g2d.getLayerSize();i++)
			g2d.setLayerDataForm(i, dataFormList.get(i));	
		if(isPrintPreview) {
			pt.setGraphPrintForm(form);
			new PrintPreview(pt.getPrintable(),pt.getFormat());
		}else
			pt.invokeJob(form);
		g2d.setLayerList(oldList);
		
	}
	
	public void startItemsPrint() {
		GraphItemsPrintForm form = (GraphItemsPrintForm) dataForm;
		Object items[] = form.getItems();
		GraphModelI g2d = getCurrentGraph();
		if (g2d == null)
			return;
		form.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		GraphViewI pt = getGraphView(paperTemplate);
		USForm sForm;
		g2d.makeGraphDataForm();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i] instanceof UFormI
					&& items[i] instanceof USForm) {
				sForm = (USForm) items[i];
				if (sForm.getSelect() != null
						&& sForm.getSelect().booleanValue()) {
					for(int k=0;k<g2d.getLayerList().size();k++) 
						g2d.setLayerDataForm(k, (UFormI) items[i]);
					pt.invokeJob(form);
				}
			}
		}
	}

	public ViewParameter getViewParameterByGraphModel(GraphModelI graphObject){
		ViewParameter viewParameter;
			if(graphObject == null) {
				viewParameter = new ViewParameter();
			}
			else {
				double graphWidth = graphObject.getGraphWidth();
				double graphHeight = graphObject.getGraphHeight();
//				float graphDpi = graphObject.getGraphDpi();
				double imageWidth = graphObject.getImageWidth();
				double imageHeight = graphObject.getImageHeight();
//				float imageDpi = graphObject.getImageDpi();
				WVTrans wv = new WVTrans();
				wv.setWindows(0,0, graphWidth,graphHeight);
				wv.setViewport(0,0,imageWidth,imageHeight);
				wv.makeWVMatrix();
				UMatrix mt = new UMatrix();
				viewParameter = new ViewParameter(wv.m,mt);
			}
		return viewParameter;
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
		List<GraphLayerI> newList = newTempGraphLayerList(oldList.get(0), formList.size()) ;
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
