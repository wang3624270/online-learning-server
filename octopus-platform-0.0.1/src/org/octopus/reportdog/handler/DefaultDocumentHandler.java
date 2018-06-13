package org.octopus.reportdog.handler;

import java.io.File;

import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.document.UDocumentUtilI;
import org.octopus.reportdog.document.impl.UPDFUtil;
import org.octopus.reportdog.module.DocDescription;
import org.octopus.reportdog.module.DocLattice_P;
import org.octopus.reportdog.module.DocLattice_Paper;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.service.itext_reportdog_ext.ReportContext;

@Service
public class DefaultDocumentHandler implements DocumentHandlerI {

	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		DocDescription docDescription = (DocDescription) request
				.get(ReportdogConstants.DOC_DESCRIPTION);
		UDocument document = (UDocument) request
				.get(ReportdogConstants.DOCUMENT_PDF_KEY);

		PdfWriter pdfWriter = (PdfWriter) document.paraMap.get("pdfWriter");

		PageEventHelper helper = new PageEventHelper(request, response);
		if (pdfWriter != null)
			pdfWriter.setPageEvent(helper);

		List lattice_pList = docDescription.getp_List();

		UDocumentUtilI documentUtil = UPDFUtil.getInstance();

		int i;
		DocLattice_P pInstance;
		String type;
		for (i = 0; i < lattice_pList.size(); i++) {
			pInstance = (DocLattice_P) lattice_pList.get(i);

			if (pInstance != null) {
				type = pInstance.getType();
				if (type.equals("text")) {
					// 写文档内容
					documentUtil.addParagraph(document, pInstance);
				} else if (type.equals("table")) {
					// 写table
					documentUtil.addTable(document, pInstance, 0);
				} else if (type.equals("embedMainTable")) {
					documentUtil.addEmbedTable(document, pInstance, 0,
							lattice_pList);
				} else if (type.equals("image")) {
					// 写Image
					documentUtil.addImage(document, pInstance);
				} else if (type.equals("block")) {
					documentUtil.addBlock(document, pInstance);
				} else if (type.equals("formatText")) {
					documentUtil.addFormatText(document, pInstance, 0);
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

		UDocumentUtilI documentUtil = UPDFUtil.getInstance();
		documentUtil.close(document);
	}

	public void processBefore(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		DocWrapper docWrapper = (DocWrapper) request
				.get(ReportdogConstants.DOC_WRAPPER);

		UDocumentUtilI documentUtil = UPDFUtil.getInstance();
		File tmpFile = documentUtil.getNewTempFile();
		request.add(ReportdogConstants.TMP_FILE_NAME_KEY, tmpFile.getPath());

		tmpFile.deleteOnExit();

		UDocument document = documentUtil.openDocumentFile(tmpFile, docWrapper
				.getPaperInfo().transToBasicLatticeElement(), null);
		if (document.reportContext != null
				&& document.reportContext.paraMap != null)
			document.reportContext.paraMap = (HashMap) request
					.get("contextMap");
		document.request = request;
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

	class PageEventHelper extends PdfPageEventHelper {
		protected RmiRequest request;
		protected RmiResponse response;
		protected HashMap lines = new HashMap();

		public PageEventHelper(RmiRequest request, RmiResponse response) {
			this.request = request;
			this.response = response;
		}

		public void onStartPage(PdfWriter writer, Document document) {
			UDocument ud = (UDocument) request
					.get(ReportdogConstants.DOCUMENT_PDF_KEY);
			ReportContext rc = ud.reportContext;
			rc.currentPage++;
			ud.reportContext.triggerNewPageAction();
		}

		public void onEndPage(PdfWriter writer, Document document) {
			UDocument ud = (UDocument) request
					.get(ReportdogConstants.DOCUMENT_PDF_KEY);
			ReportContext rc = ud.reportContext;
			rc.pageNum++;
			DocDescription docDescription = (DocDescription) request
					.get(ReportdogConstants.DOC_DESCRIPTION);

			if (docDescription.getBackgroundImagePath() != null) {
				try {
					Image image = com.itextpdf.text.Image.getInstance(Thread
							.currentThread()
							.getContextClassLoader()
							.getResource(
									docDescription.getBackgroundImagePath()));

					float pageWidth = document.getPageSize().getWidth();
					float pageHeight = document.getPageSize().getHeight();
					float imageWidth = image.getWidth();
					float imageHeight = image.getHeight();
					if (imageWidth > pageWidth) {
						imageHeight = imageHeight * pageWidth / imageWidth;
						imageWidth = pageWidth;

					}

					if (imageHeight > pageHeight) {

						imageWidth = imageWidth * pageHeight / imageHeight;
						imageHeight = pageHeight;
					}
					image.scaleAbsolute(imageWidth, imageHeight);
					image.setAbsolutePosition((pageWidth - imageWidth) / 2,
							(pageHeight - imageHeight) / 2);

					document.add(image);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// /////////////////////

			if (docDescription.getPageFooter() != null) {
				DocLattice_Paper lPaper = docDescription.getPaperInfo();
				Rectangle footerRe = new Rectangle(0, 30,
						lPaper.getPaperWidth(), 30);
				String value = docDescription.getPageFooter().getValue();
				String con2 = replaceVar(value);

				ColumnText.showTextAligned(writer.getDirectContent(),
						Element.ALIGN_CENTER, new Phrase(con2,
								(Font) rc.newPageValueChangeParas.get("font")),
						(footerRe.getLeft() + footerRe.getRight()) / 2,
						footerRe.getBottom() - 18, 0);

			}
		}

		private String replaceVar(String content) {
			int i, t;
			String content2 = "";
			String var;
			while (true) {
				i = content.indexOf("${");
				if (i <= 0) {
					content2 = content2 + content;
					break;
				}
				content2 = content2 + content.substring(0, i);
				t = content.indexOf("}");
				var = content.substring(i + 2, t);
				content2 = content2 + getVarValue(var);
				content = content.substring(t + 1);
				if (content.length() == 0)
					break;
			}
			return content2;
		}

		private String getVarValue(String var) {
			String str;
			if (var.indexOf("REPORTDOG.") == 0) {
				str = var.substring(var.indexOf("REPORTDOG.") + 10);
				if (str.equals("currentPage")) {
					UDocument ud = (UDocument) request
							.get(ReportdogConstants.DOCUMENT_PDF_KEY);
					ReportContext rc = ud.reportContext;
					return rc.currentPage.toString();
				} else if (str.equals("totalPage")) {
					if (request.get("totalPage") != null)
						return request.get("totalPage").toString();
					else {
						request.add(ReportdogConstants.REPORTDOG_REGENERATE,
								true);
					}
					return "";
				}
			}
			return "";
		}
	}
}