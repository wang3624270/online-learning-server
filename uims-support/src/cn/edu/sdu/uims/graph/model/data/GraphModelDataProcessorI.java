package cn.edu.sdu.uims.graph.model.data;

import java.util.HashMap;

import cn.edu.sdu.uims.graph.model.Graph2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;

public interface GraphModelDataProcessorI {
	public void getInitDefaultGraphData();

	public void makeObject();

	public void setGraphModelI(GraphModelI graphModel2D);

	public void setGraphData(Object o);

	public Object getGraphData();

	public Graph2D insertNewGraph2D(String graph2DName);

	public void insertNewLayer(Graph2D g2d, String layerName);

	public void updateLayer(int index, HashMap map);
}
