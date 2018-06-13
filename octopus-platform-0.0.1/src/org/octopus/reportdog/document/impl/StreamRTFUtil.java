package org.octopus.reportdog.document.impl;

import java.io.File;

import java.io.FileOutputStream;

import com.lowagie_rtf.text.Document;
import com.lowagie_rtf.text.PageSize;
import com.lowagie_rtf.text.Rectangle;
import com.lowagie_rtf.text.pdf.PdfWriter;
import com.lowagie_rtf.text.rtf.RtfWriter2;

import org.octopus.reportdog.document.BaseStreamDocumentUtil;
import org.octopus.reportdog.document.ExportObjectOperator;

public class StreamRTFUtil extends BaseStreamDocumentUtil {

	// public static USheetUtil instance;

	public static StreamRTFUtil getInstance() {
		return new StreamRTFUtil();

	}

	@Override
	public File getNewTempFile() throws Exception {
		return File.createTempFile("tmp", ".rtf");
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
			Rectangle r = null;
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
			RtfWriter2 a = RtfWriter2.getInstance(document, os);
			// 添加PDF文档的某些信息
			document.addAuthor("sdu_office");
			document.addSubject("sdu_office rtf.");
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
