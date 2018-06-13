package cn.edu.sdu.uims.graph.model.excl;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.def.UCheckBoxGroupTemplate;
import cn.edu.sdu.uims.graph.form.GraphDataForm;
import cn.edu.sdu.uims.graph.model.Graph2DI;
import cn.edu.sdu.uims.graph.model.GraphLayerI;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.SelectedData;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.graph.view.GraphViewParasI;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.trans.UFPoint;

public class EGraphModel implements GraphModelI {

//	private PageModelModuleConfig pageModel;
	private HashMap dataMap;

	public void setDataMap(HashMap dataMap) {
	}

//	public PageModelModuleConfig getPageModel() {
//		return pageModel;
//	}

//	public void setPageModel(PageModelModuleConfig pageModel) {
//		this.pageModel = pageModel;
//	}

	public HashMap getDataMap() {
		return dataMap;
	}

//	public EGraphModel(PageModelModuleConfig pageModel) {
//		this.pageModel = pageModel;
//	}

	@Override
	public void draw(Graphics dc, GraphViewParasI para) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawLayer(Graphics dc, GraphViewParasI para, int index) {
		// TODO Auto-generated method stub

	}


	@Override
	public DrageDataI getDrageData() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UPaperTemplate getPaperTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getScaleXY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getSelectElementByPoint(UFPoint fp) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SelectedData getSelectedDataByPoint(UFPoint fp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WVTrans getWindowViewport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makeGraphDataForm() {
		// TODO Auto-generated method stub

	}


	@Override
	public void read(DataInputStream in) throws IOException {
		// TODO Auto-generated method stub

	}


	@Override
	public void setLayerDataForm(int index, UFormI form) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLayerList(List layerList) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setScaleXY(double xy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}


	@Override
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public double getGraphWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getGraphHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getImageWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getImageHeight() {
		// TODO Auto-generated method stub
		return 0;
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
	public Graph2DI getGraph2DObject(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graph2DI getGraph2DByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLayerSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<GraphLayerI> getLayerList() {
		// TODO Auto-generated method stub
		return null;
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



	@Override
	public void setGraphDataForm(GraphDataForm graphDataForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetViewParameter() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public UCheckBoxGroupTemplate getLayersVisibleStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLayerVisible(String name, boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getLayerVisible(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLayerControlData(int index, Object form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGraphId(Integer graphId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getGraphId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object outJSONObject(int imageWidth, int imageHeight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getLayersVisibleStatusList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearModifyMark() {
		// TODO Auto-generated method stub
		
	}



}
