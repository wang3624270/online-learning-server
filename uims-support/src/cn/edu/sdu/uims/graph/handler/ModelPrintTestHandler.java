package cn.edu.sdu.uims.graph.handler;

import java.awt.event.ActionEvent;

import cn.edu.sdu.uims.graph.form.GraphModelTestDataForm;
import cn.edu.sdu.uims.graph.form.ModelPrintTestForm;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.service.UFactory;

public class ModelPrintTestHandler extends GraphPrintHandler {

	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent )o;
		if(e.getActionCommand().equals("printButton")) {
			this.componentToForm();
			doPrint();
		}
	}
	public void doPrint(){
		ModelPrintTestForm form = (ModelPrintTestForm)dataForm;
		String modelName = form.getModelName();
		if(modelName == null || modelName.length() == 0)
			return ;
		GraphModelTestDataForm gForm = new GraphModelTestDataForm();
		GraphModelI g2d = UFactory.getModelSession().getGraphModel2DObject(modelName);
		gForm.setCurrentGraphObject(g2d);
		this.startPrint(gForm);
	}

}
