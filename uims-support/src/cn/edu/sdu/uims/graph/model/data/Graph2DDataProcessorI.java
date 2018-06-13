package cn.edu.sdu.uims.graph.model.data;

import java.util.HashMap;

import cn.edu.sdu.uims.graph.model.GElement;

public interface Graph2DDataProcessorI extends ElementDataProcessorI{
	public GElement insertNewElement(String type, HashMap map);
	public void deleteElement(GElement e); 
	public void modifyElement(GElement ge, HashMap map);

}
