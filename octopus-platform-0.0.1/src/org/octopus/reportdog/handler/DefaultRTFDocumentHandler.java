package org.octopus.reportdog.handler;

import java.io.File;
import java.util.List;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.document.UDocumentUtilI;
import org.octopus.reportdog.document.impl.URTFUtil;
import org.octopus.reportdog.module.DocDescription;
import org.octopus.reportdog.module.DocLattice_P;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.stereotype.Service;

import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UDocument;

@Service
public class DefaultRTFDocumentHandler implements DocumentHandlerI {

	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		DocDescription modelModuleInstance = (DocDescription) request
				.get(ReportdogConstants.DOC_DESCRIPTION);

		UDocument document = (UDocument) request
				.get(ReportdogConstants.DOCUMENT_PDF_KEY);
		List paragraphInstances = modelModuleInstance.getp_List();

		UDocumentUtilI documentUtil = URTFUtil.getInstance();

		int i;
		DocLattice_P paragraphInstance;
		String type;
		for (i = 0; i < paragraphInstances.size(); i++) {
			paragraphInstance = (DocLattice_P) paragraphInstances.get(i);

			if (paragraphInstance != null) {
				type = paragraphInstance.getType();
				if (type.equals("text")) {
					// 写文档内容
					documentUtil.addParagraph(document, paragraphInstance);
				} else if (type.equals("table")) {
					// 写table
					documentUtil.addTable(document, paragraphInstance, 0);
				} else if (type.equals("embedMainTable")) {
					documentUtil.addEmbedTable(document, paragraphInstance, 0,
							paragraphInstances);
				} else if (type.equals("image")) {
					// 写Image
					documentUtil.addImage(document, paragraphInstance);
				} else if (type.equals("block")) {
					documentUtil.addBlock(document, paragraphInstance);
				} else if (type.equals("formatText")) {
					documentUtil.addFormatText(document, paragraphInstance, 0);
				}
			}
		}
		documentUtil.newPage(document);

	}

	public void processAfter(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		UDocument document = (UDocument) request
				.get(ReportdogConstants.DOCUMENT_PDF_KEY);
		UDocumentUtilI documentUtil = URTFUtil.getInstance();
		documentUtil.close(document);
	}

	public void processBefore(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		DocWrapper docWrapper = (DocWrapper) request
				.get(ReportdogConstants.DOC_WRAPPER);
		;

		UDocumentUtilI documentUtil = URTFUtil.getInstance();
		File tmpFile = documentUtil.getNewTempFile();
		request.add(ReportdogConstants.TMP_FILE_NAME_KEY, tmpFile.getPath());

		tmpFile.deleteOnExit();
		UDocument document = documentUtil.openDocumentFile(tmpFile, docWrapper
				.getPaperInfo().transToBasicLatticeElement(), null);
		request.add(ReportdogConstants.DOCUMENT_PDF_KEY, document);
	}

	public String[] getImageInfo(String content) {
		if (content != null && !content.equals("")) {
			String strs[];
			int i, p1, p2;
			String tmp;
			content = content.replace('#', ';');
			strs = content.split(";");
			for (i = 1; i < strs.length; i++) {
				p1 = strs[i].indexOf('{');
				p2 = strs[i].indexOf('}');
				tmp = strs[i].substring(p1 + 1, p2);
			}
		}
		return null;
	}

}
