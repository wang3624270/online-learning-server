package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.def.UCheckBoxGroupTemplate;
import cn.edu.sdu.uims.graph.form.GraphDataForm;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.graph.view.GraphViewParasI;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.trans.UFPoint;

public interface GraphModelI {
	
	void resetViewParameter();
	void exportElementToDoc(Element ge);
	double getGraphWidth();
	double getGraphHeight();
	double getImageWidth();
	double getImageHeight();
	UPaperTemplate getPaperTemplate();
	void makeGraphDataForm();
	Object getGraphData();
	List getSelectElementByPoint(UFPoint fp);
	WVTrans getWindowViewport();
	double getScaleXY();
	void setScaleXY(double xy);
	GraphDataForm getGraphDataForm();
	void setGraphDataForm(GraphDataForm graphDataForm);
	Graph2DI getGraph2DObject(int i);
	Graph2DI getGraph2DByName(String name);
	void draw(Graphics dc, GraphViewParasI para);
	void drawLayer(Graphics dc, GraphViewParasI para, int index);
	DrageDataI getDrageData();
	int getLayerSize();
	void setLayerList(List layerList);
	void setLayerDataForm(int index, UFormI form);
	List<GraphLayerI> getLayerList();
	Date getTimestamp();
	void write(DataOutputStream out) throws IOException;
	void read(DataInputStream in) throws IOException;
	SelectedData getSelectedDataByPoint(UFPoint fp);
	void scaleGraph(boolean b);
	void setLayerDisplayStatus(int index, Boolean b);
	List getLayerElementList(int index);
	List getSelectElementList();
	Graph2DI getGraph2D(GraphLayerI layer);
	UCheckBoxGroupTemplate getLayersVisibleStatus();
	List getLayersVisibleStatusList();
	void setLayerVisible(String name, boolean visible);
	boolean getLayerVisible(String name);
	void setLayerControlData(int index, Object form);
	void setGraphId(Integer graphId);
	Integer getGraphId();
	Object outJSONObject(int imageWidth,int imageHeight);
	void clearModifyMark();
}
