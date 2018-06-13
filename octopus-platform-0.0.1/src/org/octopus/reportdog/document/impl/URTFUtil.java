package org.octopus.reportdog.document.impl;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.document.UDocumentUtilI;
import org.octopus.reportdog.module.CellInstance;
import org.octopus.reportdog.module.ComplexTextPhraseForRtf;
import org.octopus.reportdog.module.DocLattice_P;
import org.octopus.reportdog.module.DocLattice_Paper;
import org.octopus.reportdog.module.DocLattice_PaperOldModel;
import org.octopus.reportdog.state.BlockState;

import com.lowagie_rtf.text.Chunk;
import com.lowagie_rtf.text.Document;
import com.lowagie_rtf.text.Element;
import com.lowagie_rtf.text.Font;
import com.lowagie_rtf.text.Image;
import com.lowagie_rtf.text.PageSize;
import com.lowagie_rtf.text.Paragraph;
import com.lowagie_rtf.text.Phrase;
import com.lowagie_rtf.text.Rectangle;
import com.lowagie_rtf.text.html.simpleparser.HTMLWorker;
import com.lowagie_rtf.text.pdf.BaseFont;
import com.lowagie_rtf.text.pdf.PdfPCell;
import com.lowagie_rtf.text.pdf.PdfPTable;
import com.lowagie_rtf.text.pdf.PdfWriter;
import com.lowagie_rtf.text.rtf.RtfWriter2;

import cn.edu.sdu.common.pi.ModelSessionBaseI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.common.reportdog.UTableAttribute;

public class URTFUtil implements UDocumentUtilI {

	private static ModelSessionBaseI modelSessionBase = null; 
	public static URTFUtil instance;

	public static URTFUtil getInstance() {
		if (instance == null) {
			instance = new URTFUtil();
		}
		return instance;

	}

	public File getNewTempFile() throws Exception {
		// TODO Auto-generated method stub

		return File.createTempFile("tmp", ".doc");
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
		// TODO Auto-generated method stub
		FileOutputStream os = new FileOutputStream(file);
		UDocument ud = null;
		Document document = null;
		String name;
		if (paperInfo == null) {
			throw new Exception("PDFUtil类openDocumentFile方法中paperTemplate为null");
		}

		try {
			Rectangle r = null;
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
			RtfWriter2.getInstance(document, os);
			// 添加PDF文档的某些信息
			document.addAuthor("sdu");
			document.addSubject("sdu_report_rtf");
			// document.setPageSize(PageSize.A4);
			// 打开文档
			document.open();
		} catch (Exception e) {
			return null;
		}
		ud = new UDocument();
		ud.name = file.getAbsolutePath();
		ud.object = document;
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
			Rectangle r = null;
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
			document.addAuthor("arui");
			document.addSubject("test pdf.");
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
		BaseFont bfChinese = BaseFont.createFont(
				"cn/edu/sdu/reportfont/simsun.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		String templateName = paragraphInstance.getTemplateName();
		UParagraphTemplate template = modelSessionBase.getParagraphTemplateByName(templateName);
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
			fontChinese = new Font(bfChinese, templateFont.size,
					templateFont.stly, new Color(templateColor.color.getRed(),
							templateColor.color.getGreen(),
							templateColor.color.getBlue()));
		} else {
			fontChinese = new Font(bfChinese, templateFont.size,
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
		BaseFont bfChinese = BaseFont.createFont(
				"cn/edu/sdu/reportfont/simsun.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

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
			fontChinese = new Font(bfChinese, templateFont.size,
					templateFont.stly, new Color(templateColor.color.getRed(),
							templateColor.color.getGreen(),
							templateColor.color.getBlue()));
		else
			fontChinese = new Font(bfChinese, templateFont.size,
					templateFont.stly);

		PdfPTable table;
		PdfPCell cell;

		table = new PdfPTable(paragraphInstance.getCols());
		table.setWidths(paragraphInstance.getWidth());

		// table.setPadding(2f);
		// table.setBorderWidth(0f);
		int j;
		// String content;
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
			// cell.setWidth(cellInstance.getWidth());

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
		BaseFont bfChinese = BaseFont.createFont(
				"cn/edu/sdu/reportfont/simsun.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

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
		Font fontChinese = generateFont(bfChinese, template);

		PdfPTable table;
		PdfPCell cell;
		table = new PdfPTable(paragraphInstance.getCols());
		Paragraph con;
		table.setSplitLate(false);
		table.setSplitRows(true);
		if (paragraphInstance.getWidth() == null)
			throw new Exception("表格" + paragraphInstance.getName() + "的宽度为空");
		table.setTotalWidth(paragraphInstance.getWidth());
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
					if (cellInstance.getStreamValue() != null)
						image = Image
								.getInstance(cellInstance.getStreamValue());
					cell = new PdfPCell();
					if (image != null)
						cell.addElement(image);
				} else if (cellInstance.getType().equals("imagePath")) {
					Image image = Image.getInstance(Thread.currentThread()
							.getContextClassLoader()
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

						Font aFontChinese = generateFont(bfChinese, aTemplate);
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
						content += "";
						if (cellInstance.getIsComplexProcess() == 0) {
							con = new Paragraph(content, fontChinese);
							if (cellInstance.getHAlign() == 0)
								con.setAlignment(Paragraph.ALIGN_CENTER);
							else if (cellInstance.getHAlign() == 2)
								con.setAlignment(Paragraph.ALIGN_LEFT);
							else if (cellInstance.getHAlign() == 4)
								con.setAlignment(Paragraph.ALIGN_RIGHT);
							cell = new PdfPCell(con);
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
				// cell.setBorderWidth(paragraphInstance.getTableBorderWidth());
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
					cell.setBorderColorBottom(Color.BLACK);
				}
				cell.setLeading(0f, cellInstance.getLineLeading());
				table.addCell(cell);

			}
		}
		table.setWidthPercentage(paragraphInstance.getTableWidthPercentage());
		// Paragraph p = new Paragraph();
		// p.add(table);
		// p.setSpacingBefore(0f);
		// p.setSpacingAfter(0f);
		table.setSpacingBefore(9f);
		doc.add(table);

	}

	// /////////////////////////////////////////////////////////////////
	public PdfPTable generateChildTable(String tableName,
			List paragraphInstances, DocLattice_P paragraphInstance)
			throws Exception {

		if (paragraphInstance.getCols() <= 0)
			return null;
		BaseFont bfChinese = BaseFont.createFont(
				"cn/edu/sdu/reportfont/simsun.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

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
		fontChinese = generateFont(bfChinese, template);

		PdfPTable table;
		PdfPCell cell;
		table = new PdfPTable(paragraphInstance.getCols());
		table.setSplitLate(false);
		table.setSplitRows(true);
		table.setTotalWidth(paragraphInstance.getWidth());

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
					if (image != null)
						cell.addElement(image);
				} else if (cellInstance.getType().equals("imagePath")) {
					Image image = Image.getInstance(Thread.currentThread()
							.getContextClassLoader()
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
						Font aFontChinese = generateFont(bfChinese, aTemplate);
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
					cell.setBorderColorBottom(Color.BLACK);
				}
				cell.setLeading(0f, cellInstance.getLineLeading());
				table.addCell(cell);

			}
		}
		table.setWidthPercentage(paragraphInstance.getTableWidthPercentage());
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
		Image image = Image.getInstance(Thread.currentThread()
				.getContextClassLoader().getResource(content));
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
		BaseFont bfChinese = BaseFont.createFont(
				"cn/edu/sdu/reportfont/simsun.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
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
						fontChinese = new Font(bfChinese, templateFont.size,
								templateFont.stly,

								new Color(templateColor.color.getRed(),
										templateColor.color.getGreen(),
										templateColor.color.getBlue()));
					else
						fontChinese = new Font(bfChinese, templateFont.size,
								templateFont.stly);
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
					Image image = Image.getInstance(Thread.currentThread()
							.getContextClassLoader().getResource(content));
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
		ComplexTextPhraseForRtf aPhrase = new ComplexTextPhraseForRtf();
		aPhrase.setContent(content);
		List<ComplexTextPhraseForRtf> contentList = new ArrayList();
		List<ComplexTextPhraseForRtf> contentList2 = null;
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

	private void generateParagraph(List<ComplexTextPhraseForRtf> contentList,
			Paragraph pa, Font font, UParagraphTemplate template) {
		int i;
		Phrase aPhrase;
		Font aFont = generateFont(font.getBaseFont(), template);
		aFont.setStyle(Font.UNDERLINE);
		for (i = 0; i < contentList.size(); i++) {
			if (contentList.get(i).getIsUnderline() == true) {

				aPhrase = new Phrase(contentList.get(i).getContent(), aFont);
				pa.add(aPhrase);
			}
			if (contentList.get(i).getImage() != null) {
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
	private List<ComplexTextPhraseForRtf> processUnderlineText(
			List<ComplexTextPhraseForRtf> contentList) throws Exception {
		int i, j, k, e = 0;
		List<ComplexTextPhraseForRtf> contentList1 = new ArrayList();
		String str1 = null;
		ComplexTextPhraseForRtf aPhrase;
		for (i = 0; i < contentList.size(); i++) {
			while (e != -1) {
				e = contentList.get(i).getContent().indexOf("[underline]");

				if (e != -1) {
					str1 = contentList.get(i).getContent().substring(0, e);
					aPhrase = new ComplexTextPhraseForRtf();
					aPhrase.setContent(str1);
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(e + 11));

					k = contentList.get(i).getContent().indexOf("[~underline]");
					if (k == -1) {
						throw new Exception(
								"文本解析过程中没有\"[underline]\"与\"[~underline]\"匹配");
					}
					str1 = contentList.get(i).getContent().substring(0, k);
					aPhrase = new ComplexTextPhraseForRtf();
					aPhrase.setContent(str1);
					aPhrase.setIsUnderline(true);
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(k + 12));
				} else {
					aPhrase = new ComplexTextPhraseForRtf();
					aPhrase.setContent(contentList.get(i).getContent());
					contentList1.add(aPhrase);

				}

			}
		}
		return contentList1;
	}

	// ///////////////////////////////////////
	private List<ComplexTextPhraseForRtf> processImageInText(
			List<ComplexTextPhraseForRtf> contentList) throws Exception {
		int i, j, k, e = 0;
		List<ComplexTextPhraseForRtf> contentList1 = new ArrayList();
		String str1 = null;
		ComplexTextPhraseForRtf aPhrase;
		for (i = 0; i < contentList.size(); i++) {
			while (e != -1) {
				e = contentList.get(i).getContent().indexOf("[imagepath]");

				if (e != -1) {
					str1 = contentList.get(i).getContent().substring(0, e);
					aPhrase = new ComplexTextPhraseForRtf();
					aPhrase.setContent(str1);
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(e + 11));

					k = contentList.get(i).getContent().indexOf("[~imagepath]");
					if (k == -1) {
						throw new Exception(
								"文本解析过程中没有\"[imagepath]\"与\"[~imagepath]\"匹配");
					}
					str1 = contentList.get(i).getContent().substring(0, k);
					Image image = Image.getInstance(Thread.currentThread()
							.getContextClassLoader().getResource(str1));
					aPhrase = new ComplexTextPhraseForRtf();
					// aPhrase.setImagePath(str1);
//					aPhrase.setImage(image);
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(k + 12));
				} else {
					aPhrase = new ComplexTextPhraseForRtf();
					aPhrase.setContent(contentList.get(i).getContent());
					contentList1.add(aPhrase);

				}

			}
		}
		return contentList1;
	}

	// //////////////////////////////////////////
	private Font generateFont(BaseFont baseFont, UParagraphTemplate template) {
		Font aFont = null;
		UFont templateFont;
		UColor templateColor;
		templateFont = modelSessionBase.getFontByName(
				template.fontName);
		templateColor = modelSessionBase.getColorByName(
				template.colorName);
		if (templateColor != null)
			aFont = new Font(baseFont, templateFont.size, templateFont.stly,
					new Color(templateColor.color.getRed(),
							templateColor.color.getGreen(),
							templateColor.color.getBlue()));
		else
			aFont = new Font(baseFont, templateFont.size, templateFont.stly);

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
