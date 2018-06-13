package org.octopus.reportdog.handler;

import java.io.ByteArrayOutputStream;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.document.UDocumentUtilI;
import org.octopus.reportdog.document.impl.UPDFUtil;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.stereotype.Service;

import cn.edu.sdu.common.reportdog.UDocument;

@Service
public class StreamDocumentHandler extends DefaultDocumentHandler {

	public void processAfter(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		UDocument document = (UDocument) request
				.get(ReportdogConstants.DOCUMENT_PDF_KEY);
		UDocumentUtilI documentUtil = UPDFUtil.getInstance();
		documentUtil.close(document);
	}

	public void processBefore(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		DocWrapper docWrapper = (DocWrapper) request
				.get(ReportdogConstants.DOC_WRAPPER);
		UDocumentUtilI documentUtil = UPDFUtil.getInstance();
		ByteArrayOutputStream aStream = new ByteArrayOutputStream();
		request.add(ReportdogConstants.TMP_FILE_OUT_STREAM, aStream);
		UDocument document = documentUtil.openDocumentStream(aStream,
				docWrapper.getPaperInfo().transToBasicLatticeElement(), null);
		request.add(ReportdogConstants.DOCUMENT_PDF_KEY, document);
	}

}
