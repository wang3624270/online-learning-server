package cn.edu.sdu.uims.graph.form;

import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class GraphDataForm extends UForm {
	private List layerList;
	private List controlList;

	public List getLayerList() {
		return layerList;
	}

	public void setLayerList(List layerList) {
		this.layerList = layerList;
	}

	public List getControlList() {
		return controlList;
	}

	public void setControlList(List controlList) {
		this.controlList = controlList;
	}
	
}
