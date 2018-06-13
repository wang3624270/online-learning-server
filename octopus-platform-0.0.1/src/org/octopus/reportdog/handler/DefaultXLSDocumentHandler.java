package org.octopus.reportdog.handler;

import java.io.File;
import java.util.List;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.document.UDocumentUtilI;
import org.octopus.reportdog.document.impl.USheetUtil;
import org.octopus.reportdog.module.DocDescription;
import org.octopus.reportdog.module.DocLattice_P;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.stereotype.Service;

import cn.edu.sdu.common.reportdog.UDocument;

@Service
public class DefaultXLSDocumentHandler implements DocumentHandlerI {

	public void processBefore(RmiRequest request, RmiResponse response)
			throws Exception {
		DocWrapper docWrapper = (DocWrapper) request
				.get(ReportdogConstants.DOC_WRAPPER);

		UDocumentUtilI documentUtil = USheetUtil.getInstance();
		File tmpFile = documentUtil.getNewTempFile();

		tmpFile.deleteOnExit();
		request.add(ReportdogConstants.TMP_FILE_NAME_KEY, tmpFile.getPath());
		UDocument document = documentUtil.openDocumentFile(tmpFile, docWrapper
				.getPaperInfo().transToBasicLatticeElement(), null);
		((USheetUtil) documentUtil).openSheetFile((File) document.object);
		request.add(ReportdogConstants.NOW_USHEETUTIL, documentUtil);
		request.add(ReportdogConstants.DOCUMENT_XLS_KEY, document);

	}

	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		DocDescription modelModuleInstance = (DocDescription) request
				.get(ReportdogConstants.DOC_DESCRIPTION);

		UDocument document = (UDocument) request
				.get(ReportdogConstants.DOCUMENT_XLS_KEY);
		List paragraphInstances = modelModuleInstance.getp_List();

		UDocumentUtilI documentUtil = (UDocumentUtilI) request
				.get(ReportdogConstants.NOW_USHEETUTIL);

		int i;
		DocLattice_P paragraphInstance;
		String type;

		Integer nowXLSRowNum = 0;
		((USheetUtil) documentUtil).nowSheetId = Integer.parseInt(request.get(
				ReportdogConstants.NOW_PAGE_NUM_KEY).toString());
		((USheetUtil) documentUtil).SHEET_NAME = (List) (request
				.get("sheetNameList"));
		((USheetUtil) documentUtil).addSheet();
		for (i = 0; i < paragraphInstances.size(); i++) {
			((USheetUtil) documentUtil).theSheetParameter.row = ((USheetUtil) documentUtil).theSheetParameter.sheet
					.getRows();

			paragraphInstance = (DocLattice_P) paragraphInstances.get(i);

			if (paragraphInstance != null) {
				type = paragraphInstance.getType();
				if (type.equals("text")) {
					// 写文档内容
					documentUtil.addParagraph(document, paragraphInstance);
				} else if (type.equals("table")
						|| type.equals("embedMainTable")) {
					// 写table
					documentUtil.addTable(document, paragraphInstance, 0);
				} else if (type.equals("image")) {
					// 写Image
					documentUtil.addImage(document, paragraphInstance);
				} else if (type.equals("block")) {
					documentUtil.addBlock(document, paragraphInstance);
				}
			}

			// nowXLSRowNum = nowXLSRowNum + paragraphInstances.size();

		}
		documentUtil.newPage(document);

	}

	public void processAfter(RmiRequest request, RmiResponse response)
			throws Exception {

		UDocument document = (UDocument) request
				.get(ReportdogConstants.DOCUMENT_XLS_KEY);
		UDocumentUtilI documentUtil = (UDocumentUtilI) request
				.get(ReportdogConstants.NOW_USHEETUTIL);
		if (request.get("dataStructureList") == null)
			documentUtil.close(document);
		else
			documentUtil.enforceClose(document);

	}

}