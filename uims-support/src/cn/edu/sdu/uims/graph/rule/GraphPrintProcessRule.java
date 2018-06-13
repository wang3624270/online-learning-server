package cn.edu.sdu.uims.graph.rule;

import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.graph.form.GraphPrintForm;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.view.GraphViewI;
import cn.edu.sdu.uims.graph.view.UViewImage;

public class GraphPrintProcessRule  {

	public void startPrint(GraphPrintForm form) {
		GraphModelI g2d = form.getCurrentGraphObject();
		if (g2d == null)
			return;
		form.makeViewParameter();
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();

		GraphViewI pt = new UViewImage(paperTemplate, null);
		g2d.makeGraphDataForm();
		for (int i = 0; i < g2d.getLayerSize(); i++)
			g2d.setLayerDataForm(i, form);
		pt.invokeJob(form);
		// ////////////////////////////
		//
		// // ReportElementForm eForm = new ReportElementForm();
		// AbsoluteImage aImage = new AbsoluteImage();
		// byte[] bytes = null;
		// aImage.injectContent(bytes, null);
		// eForm.addReportElement(aImage);
		// ExportService.getService().exportPDFStream(eForm, "test.pdf",
		// httpServletResponse, null);
		// ////////////////////////////////

	}

}
