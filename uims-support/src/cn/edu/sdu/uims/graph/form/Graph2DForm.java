package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.graph.model.Graph2D;

public class Graph2DForm extends UForm {
	protected Graph2D graphObject;

	public Graph2DForm() {
		graphObject = null;
	}

	public Graph2DForm(Graph2D graph) {
		graphObject = graph;
	}

	public Graph2D getGraphObject() {
		return graphObject;
	}

	public void setGraphObject(Graph2D graphObject) {
		this.graphObject = graphObject;
	}

}