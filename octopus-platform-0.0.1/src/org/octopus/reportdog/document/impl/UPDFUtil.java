package org.octopus.reportdog.document.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.document.UDocumentUtilI;
import org.octopus.reportdog.module.CellInstance;
import org.octopus.reportdog.module.ComplexTextPhrase;
import org.octopus.reportdog.module.DocLattice_P;
import org.octopus.reportdog.module.DocLattice_Paper;
import org.octopus.reportdog.module.DocLattice_PaperOldModel;
import org.octopus.reportdog.module.data_provider.DataPoint;
import org.octopus.reportdog.state.BlockState;
import org.octopus.reportdog.util.RequestUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import cn.edu.sdu.common.pi.ModelSessionBaseI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.common.reportdog.UTableAttribute;

public class UPDFUtil implements UDocumentUtilI {
	private static BaseFont bfChineseSong = null;
	private static BaseFont bfChineseKai = null;
	private static BaseFont bfVerdana = null;
	public static UPDFUtil instance;
	private static ModelSessionBaseI modelSessionBase = null; 

	public static ModelSessionBaseI getModelSessionBase() {
		return modelSessionBase;
	}

	public static void setModelSessionBase(ModelSessionBaseI modelSessionBase) {
		UPDFUtil.modelSessionBase = modelSessionBase;
	}

	static {

		try {
			bfChineseSong = BaseFont.createFont(
					"cn/edu/sdu/reportfont/simsun.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			bfChineseKai = BaseFont.createFont(
					"cn/edu/sdu/reportfont/simkai.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			bfVerdana = BaseFont.createFont(
					"cn/edu/sdu/reportfont/verdana.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static BaseFont getBaseFont(String fontName) {
		if (fontName == null)
			return bfChineseSong;
		else if (fontName.indexOf("楷体") >= 0)
			return bfChineseKai;
		else if (fontName.indexOf("verdana") >= 0)
			return bfVerdana;
		return bfChineseSong;
	}

	public static byte[] mergeFileStream(List<byte[]> byteList) {
		try {
			if (byteList == null && byteList.size() == 0)
				return new ByteArrayOutputStream().toByteArray();
			ByteArrayOutputStream aStream = new ByteArrayOutputStream();
			InputStream is = new ByteArrayInputStream(byteList.get(0));

			Document document = new Document(new PdfReader(is).getPageSize(1));

			PdfCopy copy = new PdfCopy(document, aStream);
			PdfImportedPage page;
			document.open();
			int i, n, j;
			PdfReader reader;
			for (i = 0; i < byteList.size(); i++) {
				reader = new PdfReader(
						new ByteArrayInputStream(byteList.get(i)));
				n = reader.getNumberOfPages();
				for (j = 1; j <= n; j++) {
					document.newPage();
					page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
			document.close();
			return aStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new ByteArrayOutputStream().toByteArray();
	}

	public static UPDFUtil getInstance() {
		if (instance == null) {
			instance = new UPDFUtil();
		}
		return instance;

	}

	public File getNewTempFile() throws Exception {
		// TODO Auto-generated method stub

		return File.createTempFile("tmp", ".pdf");
	}

	public void newPage(UDocument doc)  {
		// TODO Auto-generated method stub
		Document document = (Document) doc.object;
		document.newPage();

	}

	public void close(UDocument doc) throws Exception {
		// TODO Auto-generated method stub
		Document document = (Document) doc.object;
		document.close();

	}

	public void enforceClose(UDocument doc) {
		Document document = (Document) doc.object;
		document.close();

	}

	public UDocument openDocumentFile(File file, DocLattice_Paper paperInfo,
			UPageNumberTemplate pageNum) throws Exception {
		FileOutputStream os = new FileOutputStream(file);
		UDocument ud = null;
		ud = new UDocument();
		ud.name = file.getAbsolutePath();
		Document document = null;
		String name;
		if (paperInfo == null) {
			throw new Exception("PDFUtil类openDocumentFile方法中找不到纸类型信息.");
		}

		PdfWriter a;
		try {
			com.itextpdf.text.Rectangle r = null;

			if (paperInfo.getType().equals("A4")) {
				r = PageSize.A4;
			} else if (paperInfo.getType().equals("A3")) {
				r = PageSize.A3;
			} else if (paperInfo.getType().equals("B5")) {
				r = PageSize.B5;
			} else {
				if (DocLattice_PaperOldModel.oldModel.oldPaperInfoMap
						.get(paperInfo.getType()) != null) {
					HashMap<String, String> m1 = DocLattice_PaperOldModel.oldModel.oldPaperInfoMap
							.get(paperInfo.getType());
					paperInfo.setLeft(m1.get("left"));
					paperInfo.setRight(m1.get("right"));
					paperInfo.setTop(m1.get("top"));
					paperInfo.setBottom(m1.get("bottom"));
					paperInfo
							.setDirection(m1.get("orientation").equals("1") ? "h"
									: "v");
					r = new Rectangle(Float.parseFloat(m1.get("width")),
							Float.parseFloat(m1.get("height")));
				}

			}
			if (r == null)
				r = PageSize.A4;

			if (paperInfo.getDirection().equals("h")) {
				r = r.rotate();
			}
			document = new Document(r, Float.parseFloat(paperInfo.getLeft()),
					Float.parseFloat(paperInfo.getRight()),
					Float.parseFloat(paperInfo.getTop()),
					Float.parseFloat(paperInfo.getBottom()));
			a = PdfWriter.getInstance(document, os, ud.reportContext);
			// 添加PDF文档的某些信息
			document.addAuthor("Shangdong University Office");
			document.addSubject("test pdf.");
			// document.setPageSize(PageSize.A4);
			// 打开文档
			document.open();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		ud.object = document;
		ud.paraMap.put("pdfWriter", a);
		return ud;
	}

	public UDocument openDocumentStream(ByteArrayOutputStream aStream,
			DocLattice_Paper paperInfo, UPageNumberTemplate pageNum)
			throws Exception {
		UDocument ud = null;
		Document document = null;
		String name;
		if (paperInfo == null) {
			throw new Exception("PDFUtil类openDocumentFile方法中paperTemplate为null");
		}

		try {
			com.itextpdf.text.Rectangle r = null;

			if (paperInfo.getType().equals("A4")) {
				r = PageSize.A4;
			} else if (paperInfo.getType().equals("A3")) {
				r = PageSize.A3;
			} else if (paperInfo.getType().equals("B5")) {
				r = PageSize.B5;
			} else {
				if (DocLattice_PaperOldModel.oldModel.oldPaperInfoMap
						.get(paperInfo.getType()) != null) {
					HashMap<String, String> m1 = DocLattice_PaperOldModel.oldModel.oldPaperInfoMap
							.get(paperInfo.getType());
					paperInfo.setLeft(m1.get("left"));
					paperInfo.setRight(m1.get("right"));
					paperInfo.setTop(m1.get("top"));
					paperInfo.setBottom(m1.get("bottom"));
					paperInfo
							.setDirection(m1.get("orientation").equals("1") ? "h"
									: "v");
				}
			}
			if (r == null)
				r = PageSize.A4;

			if (paperInfo.getDirection().equals("h")) {
				r = r.rotate();
			}

			document = new Document(r, Float.parseFloat(paperInfo.getLeft()),
					Float.parseFloat(paperInfo.getRight()),
					Float.parseFloat(paperInfo.getTop()),
					Float.parseFloat(paperInfo.getBottom()));
			PdfWriter a = PdfWriter.getInstance(document, aStream);
			// 添加PDF文档的某些信息
			document.addAuthor("Shandong University Office");
			document.addSubject("pdf");
			// document.setPageSize(PageSize.A4);
			// 打开文档
			document.open();
		} catch (Exception e) {
			return null;
		}
		ud = new UDocument();
		ud.object = document;
		return ud;
	}

	public void addParagraph(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception {
		// TODO Auto-generated method stub
		Document doc = (Document) ud.object;

		String templateName = paragraphInstance.getTemplateName();
		UParagraphTemplate template = modelSessionBase
				.getParagraphTemplateByName(templateName);
		if (template == null) {
			template = new UParagraphTemplate();
		}
		String content = paragraphInstance.getContent();
		if (content == null || content.toLowerCase().equals("null")) {
			content = "";
		} else {
			content = content.replaceAll("null", "    ");
		}
		Font fontChinese = null;
		UFont templateFont;
		UColor templateColor;
		templateFont = modelSessionBase.getFontByName(
				template.fontName);
		templateColor = modelSessionBase.getColorByName(
				template.colorName);
		if (templateColor != null) {
			fontChinese = new com.itextpdf.text.Font(
					getBaseFont(templateFont.fontName), templateFont.size,
					templateFont.stly, new BaseColor(
							templateColor.color.getRed(),
							templateColor.color.getGreen(),
							templateColor.color.getBlue()));
		} else {
			fontChinese = new com.itextpdf.text.Font(
					getBaseFont(templateFont.fontName), templateFont.size,
					templateFont.stly);
		}
		Paragraph p = new Paragraph(content, fontChinese);
		if (template.horizontalAlignment == 0)
			p.setAlignment(Paragraph.ALIGN_CENTER);
		else if (template.horizontalAlignment == 2)
			p.setAlignment(Paragraph.ALIGN_LEFT);
		else if (template.horizontalAlignment == 4)
			p.setAlignment(Paragraph.ALIGN_RIGHT);

		if (template.verticalAlignment == 3)
			p.setAlignment(Paragraph.ALIGN_BOTTOM);

		p.setSpacingAfter(template.after);
		p.setSpacingBefore(template.before);
		doc.add(p);

	}

	public void addFormatText(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType) throws Exception {
		Document doc = (Document) ud.object;

		String templateName = paragraphInstance.getTemplateName();
		UParagraphTemplate template = modelSessionBase
				.getParagraphTemplateByName(templateName);
		if (template == null) {
			template = new UParagraphTemplate();
		}
		String content = paragraphInstance.getContent();
		if (content == null || content.toLowerCase().equals("null")) {
			content = "";
		} else {
			content = content.replaceAll("null", "    ");
		}
		Font fontChinese = null;
		UFont templateFont;
		UColor templateColor;
		templateFont = modelSessionBase.getFontByName(
				template.fontName);
		templateColor = modelSessionBase.getColorByName(
				template.colorName);
		if (templateColor != null)
			fontChinese = new com.itextpdf.text.Font(
					getBaseFont(templateFont.fontName), templateFont.size,
					templateFont.stly, new BaseColor(
							templateColor.color.getRed(),
							templateColor.color.getGreen(),
							templateColor.color.getBlue()));
		else
			fontChinese = new com.itextpdf.text.Font(
					getBaseFont(templateFont.fontName), templateFont.size,
					templateFont.stly);

		PdfPTable table;
		PdfPCell cell;

		table = new PdfPTable(paragraphInstance.getCols());
		table.setWidths(paragraphInstance.getWidth());

		int j;
		List<CellInstance> cellList = paragraphInstance.getCellList();
		CellInstance cellInstance;
		for (j = 0; j < cellList.size(); j++) {
			cellInstance = cellList.get(j);
			content = cellInstance.getContent();
			if (content == null || content.toLowerCase().equals("null")) {
				content = "";
			} else {
				content = content.replaceAll("null", "    ");
			}
			Paragraph a = new Paragraph(content, fontChinese);

			cell = new PdfPCell(new Paragraph(content, fontChinese));
			cell.setRowspan(cellInstance.getRowspan());
			cell.setColspan(cellInstance.getColspan());

			if (cellInstance.getHAlign() == 0)
				cell.setHorizontalAlignment(Paragraph.ALIGN_MIDDLE);
			else if (cellInstance.getHAlign() == 2)
				cell.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
			else if (cellInstance.getHAlign() == 4)
				cell.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);

			if (cellInstance.getVAlign() == 5)
				cell.setVerticalAlignment(Paragraph.ALIGN_MIDDLE);
			else if (cellInstance.getVAlign() == 1)
				cell.setVerticalAlignment(Paragraph.ALIGN_TOP);
			cell.setBorderWidth(0f);
			table.addCell(cell);
		}

		Paragraph p = new Paragraph();
		p.add(table);
		p.setSpacingBefore(0f);
		p.setSpacingAfter(0f);
		doc.add(p);

	}

	public void addTable(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType) throws Exception {
		addEmbedTable(ud, paragraphInstance, arrageType, null);
	}

	// ////////////////////////////////////////////////////////////////
	public void addEmbedTable(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType, List paragraphInstances) throws Exception {
		if (paragraphInstance.getNewPage().equals("true"))
			((Document) ud.object).newPage();
		Document doc = (Document) ud.object;

		String templateName = paragraphInstance.getTemplateName();
		UParagraphTemplate template = modelSessionBase
				.getParagraphTemplateByName(templateName);
		if (template == null) {
			template = new UParagraphTemplate();
		}
		String content = paragraphInstance.getContent();
		if (content == null || content.toLowerCase().equals("null")) {
			content = "";
		} else {
			content = content.replaceAll("null", "    ");
		}
		Font fontChinese = generateFont(template);

		PdfPTable table;
		PdfPCell cell;
		table = new PdfPTable(paragraphInstance.getCols());
		table.setSplitLate(false);

		table.setSplitRows(paragraphInstance.isSplitRows());

		if ("repeat".equals(paragraphInstance.getNewPageAction())) {
			ud.reportContext.addNewPageRepeatElement(table);
		}
		if (paragraphInstance.getWidth() == null)
			throw new Exception("表格" + paragraphInstance.getName() + "的宽度为空");
		table.setWidths(paragraphInstance.getWidth());
		// table.setBorderColor(new Color(220, 255, 100));
		// table.setPadding(0);
		// table.setSpacing(0);
		// table.setBorderWidth(0);

		// table.setPadding(4f);
		// table.setBorderWidth(0.5f);
		int j;
		List<CellInstance> cellList = paragraphInstance.getCellList();
		CellInstance cellInstance;
		PdfPTable aTable;
		for (j = 0; j < cellList.size(); j++) {
			cellInstance = cellList.get(j);
			if ("true".equals(cellInstance.getNewPage())) {
				if (j != 0) {

					table.setWidthPercentage(paragraphInstance
							.getTableWidthPercentage());
					// Paragraph p = new Paragraph();
					// p.add(table);
					// p.setSpacingBefore(0f);
					// p.setSpacingAfter(0f);

					table.setSpacingBefore(0f);
					table.setSpacingAfter(template.after);
					// table.
					doc.add(table);
					this.newPage(ud);
					table = new PdfPTable(paragraphInstance.getCols());
					table.setSplitLate(false);
					table.setSplitRows(true);
					if (paragraphInstance.getWidth() == null)
						throw new Exception("表格" + paragraphInstance.getName()
								+ "的宽度为空");
					table.setWidths(paragraphInstance.getWidth());
				}
			}
			content = cellInstance.getContent();
			if (content == null || content.toLowerCase().equals("null")) {
				content = "";
			} else {
				content = content.replaceAll("null", "    ");
			}
			if (cellInstance.getType().equals("childTable")) {

				String tableName = content;
				aTable = generateChildTable(tableName, paragraphInstances,
						cellInstance.getEmbedParagraphInstance());

				if (aTable == null)
					cell = new PdfPCell();
				else
					cell = new PdfPCell(aTable);

				cell.setColspan(cellInstance.getColspan());
				cell.setRowspan(cellInstance.getRowspan());
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				if (cellInstance.getIsFixedHeight() == 1)
					cell.setFixedHeight(Float.parseFloat(cellInstance
							.getHeight()));
				cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);

				cell.setBorderWidth(paragraphInstance.getTableBorderWidth());

				table.addCell(cell);

			} else {
				if (cellInstance.getType().equals("image")) {
					Image image = null;
					if (cellInstance.getStreamValue() != null) {
						try{
						image = Image
								.getInstance(cellInstance.getStreamValue());
						}catch(IOException e) {
							e.printStackTrace();
						}
					}
					cell = new PdfPCell();
					if (image != null
							&& (cellInstance.getDelayEvaluate() == null || cellInstance
									.getDelayEvaluate().length() == 0))
						cell.addElement(image);
				} else if (cellInstance.getType().equals("imagePath")) {
					Image image = com.itextpdf.text.Image.getInstance(Thread
							.currentThread().getContextClassLoader()
							.getResource(cellInstance.getContent()));
					cell = new PdfPCell();
					cell.addElement(image);

				} else {
					cell = null;
					if (cellInstance.getIsHtmlProcess() == 1) {
						List<Element> objects = HTMLWorker.parseToList(
								new StringReader(content), null, null);
						Paragraph a = new Paragraph();
						for (Element el : objects)
							a.add(el);
						cell = new PdfPCell(a);
					} else if (cellInstance.getTemplateName() != null) {

						UParagraphTemplate aTemplate = modelSessionBase.getParagraphTemplateByName(
										cellInstance.getTemplateName());

						Font aFontChinese = generateFont(aTemplate);
						content = content.replace("\\n", "\n");

						if (cellInstance.getIsUnicodeProcess() == 1)
							content = processUnicode(content);

						if (cellInstance.getIsComplexProcess() == 0) {
							if (cellInstance.getDelayEvaluate() == null
									|| cellInstance.getDelayEvaluate().length() == 0) {
								cell = new PdfPCell(new Paragraph(content,
										aFontChinese));
							} else {
								cell = new PdfPCell();
								ud.reportContext.newPageValueChangeParas.put(
										"font", aFontChinese);
							}
						} else {

							Paragraph aParagraph = new Paragraph();
							processComplexText(content, aParagraph,
									aFontChinese, aTemplate);
							cell = new PdfPCell(aParagraph);

						}

					} else {

						content = content.replace("\\n", "\n");
						if (cellInstance.getIsUnicodeProcess() == 1)
							content = processUnicode(content);
						content += "";
						if (cellInstance.getIsComplexProcess() == 0) {

							if (cellInstance.getDelayEvaluate() == null
									|| cellInstance.getDelayEvaluate().length() == 0) {
								cell = new PdfPCell();
								cell.addElement(new Paragraph(content,
										fontChinese));
							} else {
								cell = new PdfPCell();
								ud.reportContext.newPageValueChangeParas.put(
										"font", fontChinese);
							}

						} else {
							Paragraph aParagraph = new Paragraph();
							processComplexText(content, aParagraph,
									fontChinese, template);
							cell = new PdfPCell(aParagraph);

						}

					}

				}
				if (cellInstance.getIsFixedHeight() == 1)
					cell.setFixedHeight(Float.parseFloat(cellInstance
							.getHeight()));

				cell.setRowspan(cellInstance.getRowspan());
				// cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
				cell.setColspan(cellInstance.getColspan());
				// cell.setcellInstance.getHeight());

				// cell.setWidth(cellInstance.getWidth());
				// cell.setBorderWidth(0.8f);
				// cell.setUseAscender(true);
				// cell.setVerticalAlignment(Cell.ALIGN_CENTER);
				// cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
				if (cellInstance.getHAlign() == 0)
					cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
				else if (cellInstance.getHAlign() == 2)
					cell.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
				else if (cellInstance.getHAlign() == 4)
					cell.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);

				if (cellInstance.getVAlign() == 5)
					cell.setVerticalAlignment(Paragraph.ALIGN_MIDDLE);
				else if (cellInstance.getVAlign() == 1)
					cell.setVerticalAlignment(Paragraph.ALIGN_TOP);
				else if (cellInstance.getVAlign() == 3)
					cell.setVerticalAlignment(Paragraph.ALIGN_BOTTOM);
				cell.setBorderWidth(paragraphInstance.getTableBorderWidth());
				if (cellInstance.getUnderline() == 1) {
					cell.setBorderWidthBottom(0.5f);
					cell.setBorderColorBottom(BaseColor.BLACK);
				}
				cell.setLeading(0f, cellInstance.getLineLeading());
				PdfPCell cell1 = table.addCell(cell);
				if (cellInstance.getDelayEvaluate() != null
						&& cellInstance.getDelayEvaluate().length() > 0) {
					if (cellInstance.getDelayEvaluate().indexOf("newPage") == 0) {
						String str = cellInstance.getDelayEvaluate();
						str = str.substring(str.indexOf('|') + 1);
						DataPoint dp = getDataPointExec(ud, str);
						Object ob = RequestUtils.applicationBean(dp
								.getProvider());
						ud.reportContext.addNewPageValueChangeElement(
								cellInstance.getContentVariableStr(), cell1,
								ob, dp.getMethod(), cellInstance.getType());
						if (cellInstance.getContentVariableStr().indexOf(
								"${REPORTDOG.totalPage") >= 0) {
							ud.request.add(
									ReportdogConstants.REPORTDOG_REGENERATE,
									true);
						}
						ud.reportContext.currentPage = 1;
						ud.reportContext.triggerSingleComponentAction(
								cellInstance.getContentVariableStr(), cell1,
								ob, dp.getMethod(), cellInstance.getType());
					}
				}
			}
		}
		table.setWidthPercentage(paragraphInstance.getTableWidthPercentage());

		table.setSpacingBefore(template.getBefore());
		if (paragraphInstance.getHeaderRowCount() > 0)
			table.setHeaderRows(paragraphInstance.getHeaderRowCount());

		// 触发第一页开始的换页事件（iText中的onStartPage方法在第一页开始时不调用，补救方法）
		if (ud.reportContext.currentPage == 0) {
			ud.reportContext.currentPage++;
			// ud.reportContext.triggerNewPageAction();
		}
		doc.add(table);
	}

	private DataPoint getDataPointExec(UDocument ud, String dataPointExecStr) {
		HashMap<String, DataPoint> map = (HashMap<String, DataPoint>) ud.reportContext.paraMap
				.get("data-point-map");
		DataPoint ob = map.get(dataPointExecStr);
		return ob;
	}

	// /////////////////////////////////////////////////////////////////
	public PdfPTable generateChildTable(String tableName,
			List paragraphInstances, DocLattice_P paragraphInstance)
			throws Exception {

		if (paragraphInstance.getCols() <= 0)
			return null;

		int i;
		// ParagraphInstance paragraphInstance = null;
		// for (i = 0; i < paragraphInstances.size(); i++) {
		// if (((ParagraphInstance) paragraphInstances.get(i)).getName()
		// .equals(tableName)) {
		// paragraphInstance = (ParagraphInstance) paragraphInstances
		// .get(i);
		// }
		// }

		String templateName = paragraphInstance.getTemplateName();
		UParagraphTemplate template = modelSessionBase
				.getParagraphTemplateByName(templateName);
		if (template == null) {
			template = new UParagraphTemplate();
		}
		String content = paragraphInstance.getContent();
		if (content == null || content.toLowerCase().equals("null")) {
			content = "";
		} else {
			content = content.replaceAll("null", "    ");
		}
		Font fontChinese = null;
		fontChinese = generateFont(template);

		PdfPTable table;
		PdfPCell cell;
		table = new PdfPTable(paragraphInstance.getCols());
		table.setSplitLate(false);
		table.setSplitRows(paragraphInstance.isSplitRows());
		table.setWidths(paragraphInstance.getWidth());
		// table.setBorderColor(new Color(220, 255, 100));
		// table.setPadding(0);
		// table.setSpacing(0);
		// table.setBorderWidth(0);

		// table.setPadding(4f);
		// table.setBorderWidth(0.5f);

		int j;
		// String content;
		List<CellInstance> cellList = paragraphInstance.getCellList();
		CellInstance cellInstance;
		PdfPTable aTable;
		for (j = 0; j < cellList.size(); j++) {

			cellInstance = cellList.get(j);
			content = cellInstance.getContent();
			if (content == null || content.toLowerCase().equals("null")) {
				content = "";
			} else {
				content = content.replaceAll("null", "    ");
			}
			if (cellInstance.getType().equals("childTable")) {

				String tableName2 = content;

				aTable = generateChildTable(tableName2, paragraphInstances,
						cellInstance.getEmbedParagraphInstance());
				if (aTable == null)
					cell = new PdfPCell();
				else
					cell = new PdfPCell(aTable);
				// cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
				cell.setColspan(cellInstance.getColspan());
				if (cellInstance.getIsFixedHeight() == 1)
					cell.setFixedHeight(Float.parseFloat(cellInstance
							.getHeight()));

				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

				cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
				cell.setBorderWidth(paragraphInstance.getTableBorderWidth());
				table.addCell(cell);
			} else {
				if (cellInstance.getType() != null
						&& cellInstance.getType().equals("image")) {
					Image image = null;
					if (cellInstance.getStreamValue() != null)
						image = Image
								.getInstance(cellInstance.getStreamValue());
					cell = new PdfPCell();
					if (image != null
							&& (cellInstance.getDelayEvaluate() == null || cellInstance
									.getDelayEvaluate().length() == 0))
						cell.addElement(image);
				} else if (cellInstance.getType().equals("imagePath")) {
					Image image = null;
					try {
						image = com.itextpdf.text.Image.getInstance(Thread
								.currentThread().getContextClassLoader()
								.getResource(cellInstance.getContent()));
					} catch (Exception e) {
					}
					cell = new PdfPCell();
					cell.addElement(image);

				} else {
					cell = null;
					if (cellInstance.getIsHtmlProcess() == 1) {
						List<Element> objects = HTMLWorker.parseToList(
								new StringReader(content), null, null);
						Paragraph a = new Paragraph();
						for (Element el : objects)
							a.add(el);
						cell = new PdfPCell(a);
					} else if (cellInstance.getTemplateName() != null) {

						UParagraphTemplate aTemplate = modelSessionBase.getParagraphTemplateByName(
										cellInstance.getTemplateName());
						Font aFontChinese = generateFont(aTemplate);
						content = content.replace("\\n", "\n");
						if (cellInstance.getIsUnicodeProcess() == 1)
							content = processUnicode(content);
						if (cellInstance.getIsComplexProcess() == 0) {
							cell = new PdfPCell(new Paragraph(content,
									aFontChinese));
						} else {
							Paragraph aParagraph = new Paragraph();
							processComplexText(content, aParagraph,
									aFontChinese, aTemplate);
							cell = new PdfPCell(aParagraph);

						}

					} else {

						content = content.replace("\\n", "\n");
						if (cellInstance.getIsUnicodeProcess() == 1)
							content = processUnicode(content);

						if (cellInstance.getIsComplexProcess() == 0) {
							cell = new PdfPCell(new Paragraph(content,
									fontChinese));
						} else {
							Paragraph aParagraph = new Paragraph();
							processComplexText(content, aParagraph,
									fontChinese, template);
							cell = new PdfPCell(aParagraph);

						}

					}

				}
				if (cellInstance.getIsFixedHeight() == 1)
					cell.setFixedHeight(Float.parseFloat(cellInstance
							.getHeight()));

				// cell.setRowspan(cellInstance.getRowspan());
				cell.setColspan(cellInstance.getColspan());
				cell.setRowspan(cellInstance.getRowspan());

				if (cellInstance.getHAlign() == 0)
					cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
				else if (cellInstance.getHAlign() == 2)
					cell.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
				else if (cellInstance.getHAlign() == 4)
					cell.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);

				if (cellInstance.getVAlign() == 5)
					cell.setVerticalAlignment(Paragraph.ALIGN_MIDDLE);
				else if (cellInstance.getVAlign() == 1)
					cell.setVerticalAlignment(Paragraph.ALIGN_TOP);
				else if (cellInstance.getVAlign() == 3)
					cell.setVerticalAlignment(Paragraph.ALIGN_BOTTOM);
				cell.setBorderWidth(paragraphInstance.getTableBorderWidth());

				if (cellInstance.getUnderline() == 1) {
					cell.setBorderWidthBottom(0.5f);
					cell.setBorderColorBottom(BaseColor.BLACK);
				}
				cell.setLeading(0f, cellInstance.getLineLeading());

				table.addCell(cell);

			}
		}

		// Paragraph p = new Paragraph();
		// p.add(table);
		// p.setSpacingBefore(0f);
		// p.setSpacingAfter(0f);
		return table;

	}

	// ///////////////////////////////////////////////////////////////////

	public void addImage(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception {
		Document doc = (Document) ud.object;
		String templateName = paragraphInstance.getTemplateName();
		UParagraphTemplate template = modelSessionBase
				.getParagraphTemplateByName(templateName);
		if (template == null) {
			template = new UParagraphTemplate();
		}
		String content = paragraphInstance.getContent();
		if (content == null || content.toLowerCase().equals("null")) {
			return;
		}
		String x, y, height, width;
		com.itextpdf.text.Image image = com.itextpdf.text.Image
				.getInstance(Thread.currentThread().getContextClassLoader()
						.getResource(content));
		x = template.sX;
		y = template.sY;
		height = template.sHeight;
		width = template.sWidth;
		if (x != null && y != null) {
			image.setAbsolutePosition(Float.parseFloat(x), Float.parseFloat(y));
		}
		if (height != null && width != null) {
			image.scaleAbsolute(Float.parseFloat(width),
					Float.parseFloat(height));
		}
		doc.add(image);
	}

	public void addBlock(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception {
		// TODO Auto-generated method stub
		Document doc = (Document) ud.object;

		String templateName = paragraphInstance.getTemplateName();
		UParagraphTemplate template = modelSessionBase
				.getParagraphTemplateByName(templateName);
		if (template == null) {
			template = new UParagraphTemplate();
		}
		Font fontChinese = null;
		Paragraph p = new Paragraph();
		if (template.horizontalAlignment == 0)
			p.setAlignment(Paragraph.ALIGN_CENTER);
		else if (template.horizontalAlignment == 2)
			p.setAlignment(Paragraph.ALIGN_LEFT);
		else if (template.horizontalAlignment == 4)
			p.setAlignment(Paragraph.ALIGN_RIGHT);
		p.setSpacingAfter(template.after);
		p.setSpacingBefore(template.before);
		List<BlockState> blockStateList = paragraphInstance.getBlockStateList();
		int i;
		BlockState blockState;
		String content, type, blockTemplateName;
		UParagraphTemplate blockTemplate;
		String x, y;
		Chunk chunk;
		if (blockStateList != null && blockStateList.size() > 0) {
			for (i = 0; i < blockStateList.size(); i++) {
				blockState = blockStateList.get(i);
				content = blockState.getContent();
				type = blockState.getType();
				blockTemplateName = blockState.getTemplateName();
				if (type.equals("text")) {
					blockTemplate = modelSessionBase
							.getParagraphTemplateByName(blockTemplateName);
					if (blockTemplate == null) {
						blockTemplate = template;
					}
					UFont templateFont;
					UColor templateColor;
					templateFont = modelSessionBase.getFontByName(
							blockTemplate.fontName);
					templateColor = modelSessionBase.getColorByName(
							blockTemplate.colorName);
					if (templateColor != null)
						fontChinese = new com.itextpdf.text.Font(
								getBaseFont(templateFont.fontName),
								templateFont.size, templateFont.stly,

								new BaseColor(templateColor.color.getRed(),
										templateColor.color.getGreen(),
										templateColor.color.getBlue()));
					else
						fontChinese = new com.itextpdf.text.Font(
								getBaseFont(templateFont.fontName),
								templateFont.size, templateFont.stly);
					chunk = new Chunk(content);
					chunk.setFont(fontChinese);
					// /chunk
					// chunk.ALIGN_LEFT;
					// chunk.setHorizontalScaling(4);
					p.add(chunk);
				} else if (type.equals("image")) {
					blockTemplate = modelSessionBase
							.getParagraphTemplateByName(blockTemplateName);
					if (blockTemplate == null) {
						blockTemplate = template;
					}
					x = template.sX;
					y = template.sY;
					com.itextpdf.text.Image image = com.itextpdf.text.Image
							.getInstance(Thread.currentThread()
									.getContextClassLoader()
									.getResource(content));
					chunk = new Chunk(image, Float.parseFloat(x),
							Float.parseFloat(y));
					chunk.setFont(fontChinese);
					p.add(chunk);
				}
			}
		}
		doc.add(p);
	}

	// ///////////////////////////////////////////////////////////////
	public byte[] getFileBytes(String fileName) {
		File file = new File(fileName);
		FileInputStream inputStream = null;
		byte[] b = null;
		try {
			inputStream = new FileInputStream(file);
			b = new byte[inputStream.available()];
			inputStream.read(b);
			inputStream.close();
			file.delete();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	private void processComplexText(String content, Paragraph pa, Font font,
			UParagraphTemplate template) {
		int i, j, k;
		ComplexTextPhrase aPhrase = new ComplexTextPhrase();
		aPhrase.setContent(content);
		List<ComplexTextPhrase> contentList = new ArrayList();
		List<ComplexTextPhrase> contentList2 = null;
		contentList.add(aPhrase);
		try {
			contentList2 = processUnderlineText(contentList);
			contentList2 = processImageInText(contentList2);
			generateParagraph(contentList2, pa, font, template);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateParagraph(List<ComplexTextPhrase> contentList,
			Paragraph pa, Font font, UParagraphTemplate template) {
		int i;
		Phrase aPhrase;
		Font aFont = generateFont(template);
		// aFont.setStyle(Font.UNDERLINE);
		for (i = 0; i < contentList.size(); i++) {
			if (contentList.get(i).getIsUnderline() == true) {
				String str = contentList.get(i).getContent();
				if (contentList.get(i).getUnderlineParaMap()
						.get("minLength_cjk") != null) {
					int ii, cc, dd;
					cc = Integer.parseInt((String) contentList.get(i)
							.getUnderlineParaMap().get("minLength_cjk"));
					dd = str.length();
					if (cc > dd)
						for (ii = 0; ii < cc - dd; ii++) {
							str = str + "　";
						}
				}
				Chunk chunk = new Chunk(str, aFont);
				chunk.setUnderline(0.1f, -2f);// 暂时使用固定值
				aPhrase = new Phrase();
				aPhrase.add(chunk);
				pa.add(aPhrase);
			} else if (contentList.get(i).getImage() != null) {
				aPhrase = new Phrase();
				pa.add(contentList.get(i).getImage());
				// Image image = sdu_com.itextpdf.text.Image.getInstance(Thread
				// .currentThread().getContextClassLoader().getResource());

			} else {
				aPhrase = new Phrase(contentList.get(i).getContent(), font);
				pa.add(aPhrase);
			}
		}
	}

	// ///////////////////////////////////////////////////
	private List<ComplexTextPhrase> processUnderlineText(
			List<ComplexTextPhrase> contentList) throws Exception {
		int i, j, k, e = 0;
		List<ComplexTextPhrase> contentList1 = new ArrayList();
		String str1 = null;
		ComplexTextPhrase aPhrase;
		for (i = 0; i < contentList.size(); i++) {
			e = 0;
			while (e != -1) {
				e = contentList.get(i).getContent().indexOf("[underline]");

				if (e != -1) {
					str1 = contentList.get(i).getContent().substring(0, e);
					aPhrase = new ComplexTextPhrase();
					aPhrase.setContent(str1);
					contentList1.add(aPhrase);
					String str = contentList.get(i).getContent()
							.substring(e + 11);
					String str2;
					if (str.indexOf("[para]") == 0) {
						str = str.substring(6);
						str2 = str.substring(0, str.indexOf("[~para]"));
						str = str.substring(str.indexOf("[~para]") + 7);

						checkPara(str2, contentList.get(i)
								.getUnderlineParaMap());
					}

					contentList.get(i).setContent(str);

					k = contentList.get(i).getContent().indexOf("[~underline]");
					if (k == -1) {
						throw new Exception(
								"文本解析过程中没有\"[underline]\"与\"[~underline]\"匹配");
					}
					str1 = contentList.get(i).getContent().substring(0, k);
					aPhrase = new ComplexTextPhrase();
					aPhrase.setContent(str1);
					aPhrase.setIsUnderline(true);
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(k + 12));
				} else {
					aPhrase = new ComplexTextPhrase();
					aPhrase.setContent(contentList.get(i).getContent());
					contentList1.add(aPhrase);

				}

			}
		}
		return contentList1;
	}

	private void checkPara(String paraStr, HashMap map) {
		String[] arrs = paraStr.split(",");
		int i;
		String str1, str2, str3;
		for (i = 0; i < arrs.length; i++) {
			str3 = arrs[i];
			str1 = str3.substring(0, str3.indexOf('='));
			str2 = str3.substring(str3.indexOf('=') + 1);
			map.put(str1, str2);

		}

	}

	// ///////////////////////////////////////
	private List<ComplexTextPhrase> processImageInText(
			List<ComplexTextPhrase> contentList) throws Exception {
		int i, j, k, e = 0;
		List<ComplexTextPhrase> contentList1 = new ArrayList();
		String str1 = null;
		ComplexTextPhrase aPhrase;
		for (i = 0; i < contentList.size(); i++) {
			e = 0;
			while (e != -1) {
				e = contentList.get(i).getContent().indexOf("[imagepath]");

				if (e != -1) {
					str1 = contentList.get(i).getContent().substring(0, e);
					aPhrase = new ComplexTextPhrase();
					// ///////////////////////
					aPhrase.setContent(str1);
					aPhrase.setIsUnderline(contentList.get(i).getIsUnderline());
					aPhrase.setImage(contentList.get(i).getImage());
					// //////////////////////////
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(e + 11));

					k = contentList.get(i).getContent().indexOf("[~imagepath]");
					if (k == -1) {
						throw new Exception(
								"文本解析过程中没有\"[imagepath]\"与\"[~imagepath]\"匹配");
					}
					str1 = contentList.get(i).getContent().substring(0, k);
					Image image = com.itextpdf.text.Image.getInstance(Thread
							.currentThread().getContextClassLoader()
							.getResource(str1));
					aPhrase = new ComplexTextPhrase();
					// aPhrase.setImagePath(str1);
					aPhrase.setImage(image);
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(k + 12));
				} else {
					contentList1.add(contentList.get(i));
				}

			}
		}
		return contentList1;
	}

	// //////////////////////////////////////////
	private Font generateFont(UParagraphTemplate template) {
		Font aFont = null;
		UFont templateFont;
		UColor templateColor;
		templateFont = modelSessionBase.getFontByName(
				template.fontName);
		templateColor = modelSessionBase.getColorByName(
				template.colorName);
		if (templateColor != null)
			aFont = new com.itextpdf.text.Font(
					getBaseFont(templateFont.fontName), templateFont.size,
					templateFont.stly, new BaseColor(
							templateColor.color.getRed(),
							templateColor.color.getGreen(),
							templateColor.color.getBlue()));
		else
			aFont = new com.itextpdf.text.Font(
					getBaseFont(templateFont.fontName), templateFont.size,
					templateFont.stly);

		return aFont;
	}

	// /////////////////////////////////////////////////////////
	private String processUnicode(String content) throws Exception {
		int i, j, k, e = 0;
		String content1 = "";
		String str1;
		while (e != -1) {
			e = content.indexOf("[unicode]");
			if (e != -1) {
				str1 = content.substring(0, e);

				content1 = content1 + str1;
				content = content.substring(e + 9);

				k = content.indexOf("[~unicode]");
				if (k == -1) {
					throw new Exception(
							"文本解析过程中没有\"[unicode]\"与\"[~unicode]\"匹配");
				}
				str1 = content.substring(0, k);

				content1 = content1 + (char) Integer.parseInt(str1);

				content = content.substring(k + 10);
			} else {
				content1 = content1 + content;

			}

		}
		return content1;
	}

	@Override
	public UDocument openDocumentFile(File file, UPaperTemplate paperTemplate, UPageNumberTemplate pageNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaperInfo(UDocument doc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTable(UDocument doc, UTableAttribute tableAttr, UCellAttribute[] datas, int arrageType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addParagraph(UDocument doc, String content, UParagraphTemplate template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addImage(UDocument ud, String filePath, UParagraphTemplate template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeDocumentFile(UDocument doc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UDocument openDocumentStream(ByteArrayOutputStream stream, UPaperTemplate paperTemplate,
			UPageNumberTemplate pageNum) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
