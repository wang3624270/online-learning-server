package cn.edu.sdu.uims.graph.form;

import java.util.List;

import cn.edu.sdu.uims.def.UCheckBoxGroupTemplate;
import cn.edu.sdu.uims.graph.model.GraphModel2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.itms.form.IForm;

public class GraphViewForm extends IForm implements GraphViewFormI{

	protected transient GraphModelI currentGraphObject;

	public Object getThisObject() {
		return this;
	}

	public void setThisObject(Object o) {
	}

	public GraphModelI getCurrentGraphObject() {
		return currentGraphObject;
	}

	public void setCurrentGraphObject(GraphModelI currentGraphObject) {
		this.currentGraphObject = currentGraphObject;
	}

	public UCheckBoxGroupTemplate getLayersDisplayStatus() {
		return null;
//		if (currentGraphObject == null)
//			return null;
//		else
//			return currentGraphObject.getLayersDisplayStatus();
	}

	public void setLayersDisplayStatus(Boolean data[]) {
//		if (currentGraphObject != null)
//			currentGraphObject.setLayersDisplayStatus(data);
	}

	public Object getCurrentLayerNo() {
		return null;
//		if (currentGraphObject == null)
//			return null;
//		Integer i = currentGraphObject.getCurrentLayerNo();
//		return i;
	}

	public void setCurrentLayerInfo(Object o) {
//		if (o == null || currentGraphObject == null)
//			return;
//		currentGraphObject.setCurrentLayerNo((Integer) o);
	}

	public List getLayersInfoList() {
		return null;
//		if (currentGraphObject == null)
//			return null;
//		else
//			return currentGraphObject.getLayersInfoList();
	}

	public void setDefaultXmlContent(String modelName, GraphModel2D graphModel2D) {
		// TODO Auto-generated method stub
		// String content = currentGraphObject.getDefaultXmlContent();
//		String xmlContent = null;
//		graphModel2D.setXmlContent(xmlContent);
	}

	@Override
	public String getGraphInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
