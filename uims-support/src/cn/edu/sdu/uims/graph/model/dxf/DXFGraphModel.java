package cn.edu.sdu.uims.graph.model.dxf;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.kabeja.dxf.Bounds;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLayer;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.base.CheckObject;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UCheckBoxGroupTemplate;
import cn.edu.sdu.uims.graph.form.GraphDataForm;
import cn.edu.sdu.uims.graph.model.Graph2D;
import cn.edu.sdu.uims.graph.model.Graph2DI;
import cn.edu.sdu.uims.graph.model.GraphLayerI;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.SelectedData;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.graph.view.GraphViewParasI;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;

public class DXFGraphModel implements GraphModelI, Serializable {

	private DXFDocument doc = null;
	private GraphDataForm graphDataForm = null;
	private int currentLayerNo = 0;
	private Date timeStamp;
	private static double BOUND_WIDTH = 2;
	private static double DEFAUT_IMAGE_WIDTH =2000;
	private double gminx, gminy,gmaxx,gmaxy;
	private double scaleXY = 1;
	private Integer graphId;
	private WVTrans wv = new WVTrans();

	public DXFDocument getDoc() {
		return doc;
	}

	public void setDoc(DXFDocument doc) {
		this.doc = doc;
	}

	public DXFGraphModel(DXFDocument doc){
		this.doc = doc;
		setPaperTemplate((UPaperTemplate) UFactory.getModelSession()
				.getTemplate(UConstants.MAPKEY_PAPER, "A4"));
		resetViewParameter();
		wv.rightHanded = true;
	}
	public void resetViewParameter(){
		Bounds b = doc.getBounds();
		gminx = b.getMinimumX() - BOUND_WIDTH;
		gmaxx = b.getMaximumX() + BOUND_WIDTH;
		gminy = b.getMinimumY() - BOUND_WIDTH;
		gmaxy = b.getMaximumY() + BOUND_WIDTH;
//		double w = (gmaxx-gminx);
//		if(w > DEFAUT_IMAGE_WIDTH) {
//			scaleXY = DEFAUT_IMAGE_WIDTH/w;
//		}
		scaleXY = 1;
		makeWVTrans();
	}

	@Override
	public UPaperTemplate getPaperTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getGraphWidth() {
		// TODO Auto-generated method stub
		return gmaxx-gminx;
	}

	@Override
	public double getGraphHeight() {
		// TODO Auto-generated method stub
		return gmaxy-gminy;
	}




	@Override
	public double getScaleXY() {
		// TODO Auto-generated method stub
		return scaleXY;
	}

	@Override
	public void setScaleXY(double xy) {
		// TODO Auto-generated method stub
		scaleXY = xy;
		makeWVTrans();
	}


	@Override
	public Object getGraphData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphDataForm getGraphDataForm() {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public void makeGraphDataForm() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLayerDataForm(int index, UFormI form) {
		// TODO Auto-generated method stub

	}

	@Override
	public Graph2D getGraph2DObject(int i) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Graph2D getGraph2DByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DrageDataI getDrageData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SelectedData getSelectedDataByPoint(UFPoint fp) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void read(DataInputStream in) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLayerSize() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub

	}

	public void setPaperTemplate(UPaperTemplate paperTemplate) {
	}

	
	public double getImageWidth() {
		return (gmaxx-gminx)*scaleXY;
	}

	public double getImageHeight() {
		return  (gmaxy-gminy)*scaleXY; 
	}



	@Override
	public void setLayerList(List layerList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GraphLayerI> getLayerList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g, GraphViewParasI para) {
		// TODO Auto-generated method stub
		int size, i;
		if(doc == null)
			return;
		UFormI formI = null;
		Iterator it = doc.getDXFLayerIterator();
		DXFLayer layer;
		while(it.hasNext()) {
			layer = (DXFLayer) it.next();
//			if(layer != null && layer.isVisible())
//				layer.draw(g,para);
		}
	
	}

	@Override
	public void drawLayer(Graphics dc, GraphViewParasI para, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scaleGraph(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLayerDisplayStatus(int index, Boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getLayerElementList(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getSelectElementList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graph2DI getGraph2D(GraphLayerI layer) {
		// TODO Auto-generated method stub
		return null;
	}

	public void makeWVTrans() {
		double iw = (gmaxx-gminx)*scaleXY;
		double ih = (gmaxy-gminy)*scaleXY; 
		wv.setWindows(gminx, gminy, gmaxx,gmaxy);
		wv.setViewport(0, 0,iw, ih);
		wv.makeWVMatrix();		
	}

	@Override
	public WVTrans getWindowViewport(){
		// TODO Auto-generated method stub
		return wv;
	}

	@Override
	public void setGraphDataForm(GraphDataForm graphDataForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UCheckBoxGroupTemplate getLayersVisibleStatus() {
		// TODO Auto-generated method stub
		UCheckBoxGroupTemplate template = null;
		Iterator it = doc.getDXFLayerIterator();
		if(it == null)
			return null;
		template = new UCheckBoxGroupTemplate();
		template.column = 1;
		ListOptionInfo info;
		template.addedDatas = new ArrayList();
		DXFLayer layer;
		int count = 0;
	
		while(it.hasNext()) {
			layer = (DXFLayer) it.next();
			info = new ListOptionInfo(count + 1 + "", layer.getName());
			template.addedDatas.add(info);
			count ++;
		}
		template.row = count;
		template.checks = new boolean[count];
		for (int i = 0; i < count; i++) {
			info = (ListOptionInfo)template.addedDatas.get(i);
			layer = (DXFLayer) doc.getDXFLayer(info.getLabel());
			template.checks[i] = layer.isVisible();
		}
		return template;
	}

	@Override
	public List getLayersVisibleStatusList() {
		// TODO Auto-generated method stub
		UCheckBoxGroupTemplate template = null;
		Iterator it = doc.getDXFLayerIterator();
		if(it == null)
			return null;
		ListOptionInfo info;
		DXFLayer layer;
		int count = 0;
		List list = new ArrayList();
		while(it.hasNext()) {
			layer = (DXFLayer) it.next();
			info = new ListOptionInfo(count + 1 + "", layer.getName());
			list.add(new CheckObject(layer.isVisible(),info));
			count ++;
		}
		return list;
	}
	
	@Override
	public void setLayerVisible(String name, boolean visible) {
		// TODO Auto-generated method stub
		if(doc == null)
			return;
		DXFLayer layer = (DXFLayer) doc.getDXFLayer(name);
		if(layer ==  null)
			return;
//		layer.setVisible(visible);
	}

	@Override
	public List getSelectElementByPoint(UFPoint fp) {
		// TODO Auto-generated method stub
		int size, i;
		if(doc == null)
			return null;
		UFormI formI = null;
		Iterator it = doc.getDXFLayerIterator();
		DXFLayer layer;
		List list = new ArrayList();
		while(it.hasNext()) {
			layer = (DXFLayer) it.next();
//			if(layer != null && layer.isVisible())
//				layer.getSelectElementByPoint(fp.x, fp.y, fp.z, list);
		}
		if(list.size() != 0)
			return list;
		return null;
	}

	@Override
	public boolean getLayerVisible(String name) {
		// TODO Auto-generated method stub
		if(doc == null)
			return false;
		DXFLayer layer = (DXFLayer) doc.getDXFLayer(name);
		if(layer == null)
			return false;
		return layer.isVisible();
	}

	@Override
	public void setLayerControlData(int index, Object form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGraphId(Integer graphId) {
		// TODO Auto-generated method stub
		this.graphId = graphId;
	}

	@Override
	public Integer getGraphId() {
		// TODO Auto-generated method stub
		return graphId;
	}

	@Override
	public Object outJSONObject(int imageWidth, int imageHeight) {
		// TODO Auto-generated method stub
		int size, i;
		if(doc == null)
			return null;
		ViewParameter viewParameter = new ViewParameter();
		wv.rightHanded = true;
		viewParameter.wv = wv;
		viewParameter.m = wv.m;
//		JSONObject jo = new JSONObject();
		Iterator it = doc.getDXFLayerIterator();
		DXFLayer layer;
		while(it.hasNext()) {
			layer = (DXFLayer) it.next();
//			if(layer != null && layer.isVisible())
//				layer.putElementToJsonObject(jo, viewParameter);
		}
//		System.out.println(jo.toString());
//		return jo;
		return null;
	}

	@Override
	public void clearModifyMark() {
		// TODO Auto-generated method stub
		
	}


	
}
