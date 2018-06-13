package cn.edu.sdu.uims.graph.util;

import java.util.HashMap;

import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.graph.model.GraphModelI;

public class GraphModelFactory {
	private static HashMap<Integer , GraphModelInfo> modelMap = new HashMap<Integer, GraphModelInfo>(); 
	public static GraphModelI getGraphModel(Integer id){
		GraphModelInfo info = modelMap.get(id);
		if(info == null)
			return null;
		else {
			info.setTimeStamep(DateTimeTool.getNowTime());
			return info.getGraphModel();
		}
	}
	public static void setGraphModel(Integer graphId, GraphModelI graphModel){
		GraphModelInfo info =new GraphModelInfo();
		info.setGraphId(graphId);
		info.setGraphModel(graphModel);
		info.setTimeStamep(DateTimeTool.getNowTime());
		modelMap.put(graphId, info);
	}
}
