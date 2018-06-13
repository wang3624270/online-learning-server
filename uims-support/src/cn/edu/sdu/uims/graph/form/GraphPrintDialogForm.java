package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class GraphPrintDialogForm extends UForm {
	protected String graphName;

	protected GraphPrintForm graphPrintForm;

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public GraphPrintForm getGraphPrintForm() {
		return graphPrintForm;
	}

	public void setGraphPrintForm(GraphPrintForm graphPrintForm) {
		this.graphPrintForm = graphPrintForm;
	}
}
