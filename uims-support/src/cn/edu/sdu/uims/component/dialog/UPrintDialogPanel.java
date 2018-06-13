package cn.edu.sdu.uims.component.dialog;

import cn.edu.sdu.common.form.UFormI;

public class UPrintDialogPanel extends UDialogPanel{
	public void setDialogForm(UFormI form) {
		dialogForm = form;
//		Integer id = service.getGraphIdByName(((GraphPrintDialogForm)dialogForm).getGraphName());
//		GraphPrintForm printform=((GraphPrintDialogForm)dialogForm).getGraphPrintForm();
//		printform.setGraphObject(service.loadGraph(id));
//		printform.setInputParameterMap();
	}
}
