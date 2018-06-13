package cn.edu.sdu.uims.graph.rule;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.graph.model.GraphLayer;
import cn.edu.sdu.uims.graph.model.GraphLayerI;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.GraphModelViewParas;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;

public class GraphPrintPDFProcessRule  {
	public List<GraphLayerI> newTempGraphLayerList(GraphLayerI layer, int count) {
		List<GraphLayerI> retList = new ArrayList<GraphLayerI>(count);
		GraphLayer newLayer;
		for (int i = 0; i < count; i++) {
			newLayer = new GraphLayer();
			newLayer.copy((GraphLayer)layer);
			retList.add(newLayer);
		}
		return retList;
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
	public void startPrint(List formList,String modelName, String fileName, HttpServletResponse httpServletResponse) {
		if(formList == null || formList.size() == 0)
			return; 
		
		GraphModelI g2d = UFactory.getModelSession().getGraphModel2DObject(modelName);
		if (g2d == null)
			return;
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
//		File file = ExportService.getService().getTempPDFFile();
		File file = null;
		Document document = new Document(new Rectangle(imageWidth, imageHeight));
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(file));
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
//		ExportService.getService().downloadFile(file.getAbsolutePath(),
//				fileName, httpServletResponse);
		g2d.setLayerList(oldList);
	}


}
