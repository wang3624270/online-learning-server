package org.octopus.reportdog.processHandler.exportHandler;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.document.BaseStreamDocumentUtil;
import org.octopus.reportdog.document.ExportObjectOperator;
import org.octopus.reportdog.document.element.ReportElement;
import org.octopus.reportdog.document.element.ReportElementForm;
import org.octopus.reportdog.document.impl.StreamSheetUtil;

public class SheetStreamExportHandler {
	public void process(HashMap paraMap) throws Exception {

		BaseStreamDocumentUtil documentUtil = StreamSheetUtil.getInstance();
		File tmpFile = documentUtil.getNewTempFile();

		tmpFile.deleteOnExit();
		paraMap.put(ReportdogConstants.TMP_FILE_NAME_KEY, tmpFile
				.getPath());
		ExportObjectOperator document = documentUtil.getExportDocument(tmpFile);
		((StreamSheetUtil) documentUtil).openSheetFile((File) document
				.getDocTarget());
		((StreamSheetUtil) documentUtil).addSheet();

		document.paraMap.put("documentUtil", documentUtil);
		// /////////////////////////////
		ReportElementForm form = (ReportElementForm) paraMap
				.get(ReportdogConstants.REPORT_ELEMENT_STREAM);
		List<ReportElement> list = form.getElementList();
		int i;
		for (i = 0; i < list.size(); i++) {
			list.get(i).renderElement(document);
		}

		((StreamSheetUtil) documentUtil).close();

	}
}