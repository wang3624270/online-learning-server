package org.octopus.reportdog.document.impl;

import java.io.File;
import java.io.FileOutputStream;

import org.octopus.reportdog.document.BaseStreamDocumentUtil;
import org.octopus.reportdog.document.ExportObjectOperator;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class StreamPDFUtil extends BaseStreamDocumentUtil {

	// public static USheetUtil instance;

	public static StreamPDFUtil getInstance() {
		return new StreamPDFUtil();

	}

	@Override
	public File getNewTempFile() throws Exception {
		return File.createTempFile("tmp", ".pdf");
	}

	public void initExportTarget(ExportObjectOperator ud) {
		File file = (File) ud.getDocTarget();
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);
			ud.paraMap.put("docTargetStream", os);
		} catch (Exception e) {
		}
		Document document = null;
		String name = ud.paraMap.get("paperType").toString();

		try {
			com.itextpdf.text.Rectangle r = null;
			// r.w
			if (name.equals("A4")) {
				r = PageSize.A4;
			} else if (name.equals("A3")) {
				r = PageSize.A3;
			} else if (name.equals("B5")) {
				r = PageSize.B5;
			}
			if (r == null)
				r = PageSize.A4;

			if (Boolean
					.parseBoolean(ud.paraMap.get("isOrientation").toString()) == true) {
				r = r.rotate();
			}
			document = new Document(r);
			PdfWriter a = PdfWriter.getInstance(document, os);
			// 添加PDF文档的某些信息
			document.addAuthor("arui");
			document.addSubject("test pdf.");
			// document.setPageSize(PageSize.A4);
			// 打开文档
			document.open();
			ud.setDocOperator(document);
		} catch (Exception e) {
			return;
		}
		return;
	}

	public void close(ExportObjectOperator op) {
		Document document = (Document) op.getDocOperator();
		document.close();
		try {
			if (op.paraMap.get("docTargetStream") != null)
				((FileOutputStream) op.paraMap.get("docTargetStream")).close();
		} catch (Exception e) {
		}
	}

}
