package cn.edu.sdu.uims.graph.handler;

import java.awt.event.ActionEvent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.graph.form.GraphPrintDialogForm;
import cn.edu.sdu.uims.graph.view.UGraphView;

public class GraphDialogHandler extends GraphPrintHandler {

	public GraphDialogHandler() {
		super();
		
	}

	public void processActionEvent(Object obj, String cmd) {
		UComponentI com = (UComponentI) ((ActionEvent) obj).getSource();
		UDialogPanel dlg = (UDialogPanel) component;
		if (com.getComponentName().equals("print")) {
			this.componentToForm();
			GraphPrintDialogForm form = (GraphPrintDialogForm) dataForm;
			if (form == null)
				return;
			UGraphView view = (UGraphView) component
					.getSubComponent("printGraphView");
			view.startPrint();
			dlg.doOk();
		} else if (com.getComponentName().equals("btnClose")) {
			dlg.doClose();
		}
	}
}
