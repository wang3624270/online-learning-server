package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.graph.model.GraphModel2D;

public class FGraphForm extends UForm {
	protected GraphModel2D graphModel;

	public GraphModel2D getGraphModel() {
		return graphModel;
	}

	public void setGraphModel(GraphModel2D graphModel) {
		this.graphModel = graphModel;
	}

}
