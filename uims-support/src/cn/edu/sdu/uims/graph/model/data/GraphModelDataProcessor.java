package cn.edu.sdu.uims.graph.model.data;

import java.io.Serializable;
import java.util.HashMap;

import cn.edu.sdu.uims.graph.model.Graph2D;
import cn.edu.sdu.uims.graph.model.GraphModel2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;

public class GraphModelDataProcessor implements GraphModelDataProcessorI, Serializable {
	protected GraphModel2D graphModel2D = null;
	protected Object graphData;
	
	public GraphModelDataProcessor(){
		
	}
	public  GraphModelDataProcessor(GraphModel2D graphModel2D) {
		this.graphModel2D = graphModel2D; 
	}
	public void getInitDefaultGraphData(){
		if(graphModel2D != null)
			graphModel2D.getInitDefaultGraph();
	}
	public void makeObject() {
	}

	public void setDefaultXmlContent() {
		// TODO Auto-generated method stub		
	}
	public GraphModelI getGraphModelI() {
		return graphModel2D;
	}
	
	public Object getGraphData() {
		// TODO Auto-generated method stub
		return graphData;
	}
	
	public void setGraphData(Object o) {
		// TODO Auto-generated method stub
		graphData = o;
	}
	@Override
	public Graph2D insertNewGraph2D(String graph2DName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insertNewLayer(Graph2D g2d,String layerName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateLayer(int index, HashMap map) {
		// TODO Auto-generated method stub
		
	}
	public void setGraphModelI(GraphModelI graphModel2D) {
		// TODO Auto-generated method stub
		if(graphModel2D instanceof GraphModel2D)
		this.graphModel2D = (GraphModel2D)graphModel2D;
		
	}


}
