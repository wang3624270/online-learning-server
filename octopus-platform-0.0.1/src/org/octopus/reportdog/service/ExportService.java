package org.octopus.reportdog.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.octopus.reportdog.configParser.ReportParser;
import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.document.UDocumentUtilI;
import org.octopus.reportdog.document.element.ReportElementForm;
import org.octopus.reportdog.document.element.sheetElement.SimpleSheetTable;
import org.octopus.reportdog.document.impl.StreamPDFUtil;
import org.octopus.reportdog.document.impl.UPDFUtil;
import org.octopus.reportdog.document.impl.USheetUtil;
import org.octopus.reportdog.processHandler.exportHandler.PDFStreamExportHandler;
import org.octopus.reportdog.processHandler.exportHandler.RTFStreamExportHandler;
import org.octopus.reportdog.processHandler.exportHandler.SheetStreamExportHandler;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.service.itext_reportdog_ext.ReportContext;

public class ExportService {
	private static ExportService service = new ExportService();

	public static ExportService getService() {
		return service;
	}

	public void startExportService(RmiRequest request, RmiResponse response) {
		try {
        			HashMap aMap = new HashMap();
			request.add(ReportdogConstants.GLOBAL_VARIABLE_MAP, aMap);
			process(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			UDocument document1 = (UDocument) request
					.get(ReportdogConstants.DOCUMENT_PDF_KEY);
			UDocument document2 = (UDocument) request
					.get(ReportdogConstants.DOCUMENT_XLS_KEY);
			UDocumentUtilI documentUtil1 = UPDFUtil.getInstance();
			UDocumentUtilI documentUtil2 = USheetUtil.getInstance();
			try {
				if (document1 != null)
					documentUtil1.enforceClose(document1);
				if (document2 != null)
					documentUtil2.enforceClose(document2);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}

	public void startExportServiceForDataStructure(RmiRequest request,
			RmiResponse response) {
		try {
			HashMap aMap = new HashMap();
			request.add(ReportdogConstants.GLOBAL_VARIABLE_MAP, aMap);
			request.add("reportContent", "false");
			request.add("dataStructureList", new ArrayList());
			process(request, response);
			response.add("reportDataStructure",
					request.get("dataStructureList"));
		} catch (Exception e) {
			e.printStackTrace();
			UDocument document1 = (UDocument) request
					.get(ReportdogConstants.DOCUMENT_PDF_KEY);
			UDocument document2 = (UDocument) request
					.get(ReportdogConstants.DOCUMENT_XLS_KEY);
			UDocumentUtilI documentUtil1 = UPDFUtil.getInstance();
			UDocumentUtilI documentUtil2 = USheetUtil.getInstance();
			try {
				if (document1 != null)
					documentUtil1.enforceClose(document1);
				if (document2 != null)
					documentUtil2.enforceClose(document2);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}

	public void report(HashMap paraMap) {
		RmiRequest request = new RmiRequest();
		RmiResponse response = new RmiResponse();
		ReportParser parser = new ReportParser();

		String panelName = null;
		String panelPath = null;
		if (paraMap.get(ReportdogConstants.PANELNAME_KEY) != null)
			panelName = paraMap.get(ReportdogConstants.PANELNAME_KEY)
					.toString();
		if (paraMap.get(ReportdogConstants.PANELNAME_PATH) != null)
			panelPath = paraMap.get(ReportdogConstants.PANELNAME_PATH)
					.toString();
		HashMap configMap;
		Reportdog a = new Reportdog();
		if (panelName != null)
			configMap = a.parseConfig(panelName);
		else
			configMap = a.parseConfigFromFile(panelPath);

		request.add(ReportdogConstants.REPORTDOG_WRAPPER,
				configMap.get(ReportdogConstants.REPORTDOG_WRAPPER));
		request.add(ReportdogConstants.REPORTDOG_DATA,
				configMap.get(ReportdogConstants.REPORTDOG_DATA));
		request.add(ReportdogConstants.REPORTDOG_PAGE,
				configMap.get(ReportdogConstants.REPORTDOG_PAGE));

		paraMap.put("data-point-map", configMap.get("data-point-map"));

		try {
			request.add("parseFinish", "true");
			request.putAll(paraMap);
			request.add("contextMap", paraMap);
			process(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reportForDataStructure(HashMap paraMap) {
		RmiRequest request = new RmiRequest();
		RmiResponse response = new RmiResponse();
		ReportParser parser = new ReportParser();

		String panelName = null;
		String panelPath = null;
		if (paraMap.get(ReportdogConstants.PANELNAME_KEY) != null)
			panelName = paraMap.get(ReportdogConstants.PANELNAME_KEY)
					.toString();
		if (paraMap.get(ReportdogConstants.PANELNAME_PATH) != null)
			panelPath = paraMap.get(ReportdogConstants.PANELNAME_PATH)
					.toString();
		HashMap configMap;
		Reportdog a = new Reportdog();
		if (panelName != null)
			configMap = a.parseConfig(panelName);
		else
			configMap = a.parseConfigFromFile(panelPath);

		request.add(ReportdogConstants.REPORTDOG_WRAPPER,
				configMap.get(ReportdogConstants.REPORTDOG_WRAPPER));
		request.add(ReportdogConstants.REPORTDOG_DATA,
				configMap.get(ReportdogConstants.REPORTDOG_DATA));
		request.add(ReportdogConstants.REPORTDOG_PAGE,
				configMap.get(ReportdogConstants.REPORTDOG_PAGE));

		try {
			request.add("parseFinish", "true");
			request.putAll(paraMap);
			request.add("contextMap", paraMap);
			request.add("reportContent", "false");
			request.add("dataStructureList", new ArrayList());
			process(request, response);
			response.add("reportDataStructure",
					request.get("dataStructureList"));
			paraMap.put("reportDataStructure", request.get("dataStructureList"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void process(RmiRequest request, RmiResponse response)
			throws Exception {

		Reportdog.defaultPreProcessHandler.process(request, response);
		do {
			Reportdog.defaultLoadDataHandler.process(request, response);
			Reportdog.defaultMidDataGenerateHandler.process(request, response);
			Reportdog.defaultLoadShowModelHandler.process(request, response);

			Reportdog.defaultDataInjectShowModelHandler.process(request,
					response);
			if (request.get("reportContent") == null
					|| request.get("reportContent").toString().equals("true"))
				Reportdog.defaultExportReportHandler.process(request, response);
		} while (generateForwardStepLabel(request));
		if (request.get(ReportdogConstants.REPORTDOG_REGENERATE) != null)
			if (Boolean.parseBoolean(request.get(
					ReportdogConstants.REPORTDOG_REGENERATE).toString()) == true) {
				// Only single page supported.

				UDocument ud = (UDocument) request
						.get(ReportdogConstants.DOCUMENT_PDF_KEY);
				ReportContext rc = ud.reportContext;
				request.add("totalPage", rc.pageNum - 1);
				((HashMap) request.get("contextMap")).put("totalPage",
						rc.pageNum - 1);
				if (request.get("reportContent") == null
						|| request.get("reportContent").toString()
								.equals("true"))
					Reportdog.defaultExportReportHandler.process(request,
							response);

			}
		Reportdog.defaultEndHandler.process(request, response);

	}

	private boolean generateForwardStepLabel(RmiRequest request) {
		Integer nowPageNum = Integer.parseInt(request.get(
				ReportdogConstants.NOW_PAGE_NUM_KEY).toString());

		HashMap splitedPageConfigs = (HashMap) request
				.get(ReportdogConstants.SPLIT_PAGE_CONFIGS_KEY);

		nowPageNum++;
		Integer totalPage = splitedPageConfigs.size();
		request.add(ReportdogConstants.NOW_PAGE_NUM_KEY, nowPageNum);
		if (nowPageNum >= totalPage)
			return false;

		else
			return true;
	}

	public static void main(String[] args) {

		ReportElementForm form = new ReportElementForm();
		SimpleSheetTable table = new SimpleSheetTable();
		List<String> list = new ArrayList<String>();
		List<Integer> list2 = new ArrayList<Integer>();

		list.add("cc");
		list2.add(59);
		String[][] s = { { "d" }, { "d" } };
		table.columnName = list;
		table.columnWidth = list2;
		table.data = s;
		form.addReportElement(table);
		ExportService a = new ExportService();
		a.exportSheetStream(form, "dddd", null);

	}

	public void exportPDFStream(ReportElementForm form, String exportFileName,
			HttpServletResponse httpServletResponse, HashMap paraMap) {

		try {
			HashMap aMap = new HashMap();

			aMap.put(ReportdogConstants.REPORT_ELEMENT_STREAM, form);
			PDFStreamExportHandler handler = new PDFStreamExportHandler();
			handler.process(aMap);
			if (exportFileName == null)
				exportFileName = "exportData.pdf";
			downloadFile(aMap.get(ReportdogConstants.TMP_FILE_NAME_KEY)
					.toString(), exportFileName, httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public byte[] exportRTFStream(ReportElementForm form,
			String exportFileName, HashMap paraMap) {

		try {
			HashMap aMap = new HashMap();

			aMap.put(ReportdogConstants.REPORT_ELEMENT_STREAM, form);
			RTFStreamExportHandler handler = new RTFStreamExportHandler();
			handler.process(aMap);
			if (exportFileName == null)
				exportFileName = "exportData.rtf";

			return getFileByte(aMap.get(ReportdogConstants.TMP_FILE_NAME_KEY)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	public File getTempPDFFile() {
		StreamPDFUtil pdfUtil = new StreamPDFUtil();
		try {
			return pdfUtil.getNewTempFile();
		} catch (Exception e) {
			return null;
		}
	}

	public void exportSheetStream(ReportElementForm form,
			String exportFileName, HttpServletResponse httpServletResponse) {

		try {
			HashMap aMap = new HashMap();

			aMap.put(ReportdogConstants.REPORT_ELEMENT_STREAM, form);
			SheetStreamExportHandler handler = new SheetStreamExportHandler();
			handler.process(aMap);
			if (exportFileName == null)
				exportFileName = "exportData.xls";
			downloadFile(aMap.get(ReportdogConstants.TMP_FILE_NAME_KEY)
					.toString(), exportFileName, httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void downloadFile(String fileName, String downloadFileName,
			HttpServletResponse httpServletResponse) {
		String fileType = "pdf";

		if (fileName != null && fileName.indexOf('.') != -1) {
			fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		if (fileType.equals("pdf")) {
			httpServletResponse.setContentType("APPLICATION/pdf");
		} else if (fileType.equals("xls")) {
			httpServletResponse.setContentType("application/vnd.ms-excel");
		} else if (fileType.equals("xlsx")) {
			httpServletResponse
					.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		} else if (fileType.equals("doc")) {
			httpServletResponse
					.setContentType("application/vnd.ms-word;charset=UTF-8");
		}

		if (downloadFileName == null)
			downloadFileName = "export." + fileType;
		try {
			httpServletResponse.setHeader(
					"Content-Disposition",
					"attachment;filename=\""
							+ new String(downloadFileName.getBytes("gb2312"),
									"ISO8859-1") + "\"");
		} catch (Exception e) {
		}

		File file = new File(fileName);
		httpServletResponse.setContentLength((int) file.length());
		byte[] data = null;
		try {
			InputStream input = new FileInputStream(file);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = input.read()) != -1) {
				bytestream.write(ch);
			}
			data = bytestream.toByteArray();
			bytestream.close();
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServletOutputStream seveletOuputStream;
		try {
			seveletOuputStream = httpServletResponse.getOutputStream();

			seveletOuputStream.write(data, 0, data.length);
			seveletOuputStream.flush();
			seveletOuputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	public byte[] getFileByte(String fileName) {

		File file = new File(fileName);
		byte[] data = null;
		try {
			InputStream input = new FileInputStream(file);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = input.read()) != -1) {
				bytestream.write(ch);
			}
			data = bytestream.toByteArray();
			bytestream.close();
			input.close();
			return data;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteTempFile(String fileName) {

	}

}