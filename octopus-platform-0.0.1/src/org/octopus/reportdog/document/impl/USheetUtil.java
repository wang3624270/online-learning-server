package org.octopus.reportdog.document.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.octopus.reportdog.document.UDocumentUtilI;
import org.octopus.reportdog.module.CellInstance;
import org.octopus.reportdog.module.ComplexTextPhrase;
import org.octopus.reportdog.module.DocLattice_P;
import org.octopus.reportdog.module.DocLattice_Paper;

import cn.edu.sdu.common.pi.ModelSessionBaseI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.common.reportdog.USheetParameter;
import cn.edu.sdu.common.reportdog.UTableAttribute;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WriteException;


public class USheetUtil implements UDocumentUtilI {

	// USheetUtil类每次使用，都会生成一个USheetUtil对象，并非一个公共方法类。（临时方案，应该尽快调整USheetUtil类的使用方法）

	private static ModelSessionBaseI modelSessionBase = null; 
	public int nowSheetId = 0;

	public USheetParameter theSheetParameter = null;
	public String nowTemplateName = null;
	public DocLattice_Paper paper;
	public List SHEET_NAME = null;

	public static USheetUtil getInstance() {
		// // if (instance == null) {
		// instance = new USheetUtil();
		// }
		return new USheetUtil();

	}

	public UDocument openDocumentStream(ByteArrayOutputStream aStream,
			DocLattice_Paper paperInfo, UPageNumberTemplate pageNum)
			throws Exception {
		return null;
	}

	public void addParagraph(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception {
		String fileName = ((File) ud.object).getName();
		try {

			CellInstance cellInstance;

			String content = paragraphInstance.getContent();
			if (content == null || content.toLowerCase().equals("null"))
				content = "";

			Label label = new Label(theSheetParameter.col,
					theSheetParameter.row, content,
					getCellAttribute(new CellInstance()));

			Sheet aSheet = theSheetParameter.sheet;

			theSheetParameter.sheet.addCell(label);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void addTable(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType) throws Exception {
		boolean isEmbed = isEmbedTable(paragraphInstance);
		if (isEmbed) {
			calPhysicalRows(paragraphInstance);
			setCellPhysicalRow(paragraphInstance);
		}
		try {

			String content;
			List<CellInstance> cellList = paragraphInstance.getCellList();
			CellInstance cellInstance;
			int i;
			nowTemplateName = paragraphInstance.getTemplateName();
			float[] widths = paragraphInstance.getWidth();
			if (widths != null)
				theSheetParameter.columnNum = paragraphInstance.getWidth().length;
			else
				theSheetParameter.columnNum = paragraphInstance.getCols();
			if (widths != null)
				for (i = 0; i < widths.length; i++) {
					theSheetParameter.sheet.setColumnView(i, (int) widths[i]);
				}

			if (!isEmbed) {
				for (i = 0; i < cellList.size(); i++) {
					cellInstance = cellList.get(i);
					content = cellInstance.getContent();

					if (cellInstance.getIsFixedHeight() == 0)
						theSheetParameter.sheet
								.setRowView(cellInstance.getRow(),
										Integer.parseInt(cellInstance
												.getHeight()) * 15, false);
					else {
						theSheetParameter.sheet
								.setRowView(cellInstance.getRow(),
										Integer.parseInt(cellInstance
												.getHeight()) * 15, false);
					}

					theSheetParameter.sheet.addCell(getLabel(cellInstance));
					if (cellInstance.getColspan() > 1
							|| cellInstance.getRowspan() > 1) {
						mergeCells(cellInstance);
					}

				}
			} else {
				for (i = 0; i < cellList.size(); i++) {
					cellInstance = cellList.get(i);
					content = cellInstance.getContent();
					if (cellInstance.getIsFixedHeight() == 0)
						theSheetParameter.sheet
								.setRowView(cellInstance.getPhysicalRow(),
										Integer.parseInt(cellInstance
												.getHeight()) * 15, false);
					else
						theSheetParameter.sheet
								.setRowView(cellInstance.getPhysicalRow(),
										Integer.parseInt(cellInstance
												.getHeight()) * 15, false);

					if (cellInstance.getType() != null
							&& cellInstance.getEmbedParagraphInstance() != null) {
						addChildTable(ud,
								cellInstance.getEmbedParagraphInstance());
					} else {
						theSheetParameter.sheet
								.addCell(getEmbedLabel(cellInstance));
						if (cellInstance.getColspan() > 1
								|| cellInstance.getPhysicalRowSpan() > 1) {
							mergeEmbedCells(cellInstance);
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addChildTable(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception {
		try {
			String content;
			List<CellInstance> cellList = paragraphInstance.getCellList();
			CellInstance cellInstance;
			int i;
			nowTemplateName = paragraphInstance.getTemplateName();
			for (i = 0; i < cellList.size(); i++) {
				cellInstance = cellList.get(i);
				content = cellInstance.getContent();
				theSheetParameter.sheet.setRowView(
						cellInstance.getPhysicalRow(),
						Integer.parseInt(cellInstance.getHeight()) * 15, false);
				if (cellInstance.getType() != null
						&& cellInstance.getEmbedParagraphInstance() != null) {
					addChildTable(ud, cellInstance.getEmbedParagraphInstance());

				} else {
					theSheetParameter.sheet
							.addCell(getEmbedLabel(cellInstance));
					if (cellInstance.getColspan() > 1
							|| cellInstance.getRowspan() > 1) {
						this.mergeEmbedCells(cellInstance);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCellPhysicalRow(DocLattice_P paragraphInstance) {
		int i, j;
		List<CellInstance> cellList = paragraphInstance.getCellList();
		int tempPRows = paragraphInstance.getStartPhysicalRow();
		int currentRow = 0;
		int tempSpan = 0;
		for (i = 0; i < cellList.size(); i++) {
			if (currentRow != cellList.get(i).getRow()) {
				tempPRows += paragraphInstance.physicalRows.get(currentRow);
				currentRow = cellList.get(i).getRow();
			}
			cellList.get(i).setPhysicalRow(tempPRows);
			tempSpan = 0;
			for (j = 0; j < cellList.get(i).getRowspan(); j++) {
				tempSpan += paragraphInstance.physicalRows.get(cellList.get(i)
						.getRow() + j);
			}
			cellList.get(i).setPhysicalRowSpan(tempSpan);

			if (cellList.get(i).getEmbedParagraphInstance() != null) {
				cellList.get(i).getEmbedParagraphInstance()
						.setStartPhysicalRow(tempPRows);
				cellList.get(i).getEmbedParagraphInstance()
						.setStartPhysicalCol(cellList.get(i).getCol());
				setCellPhysicalRow(cellList.get(i).getEmbedParagraphInstance());
			} else {
				cellList.get(i).setCol(
						cellList.get(i).getCol()
								+ paragraphInstance.getStartPhysicalCol());
			}
		}
		System.out.println();
	}

	private boolean isEmbedTable(DocLattice_P paragraphInstance) {
		int i;
		List<CellInstance> cellList = paragraphInstance.getCellList();
		for (i = 0; i < cellList.size(); i++) {
			if (cellList.get(i).getEmbedParagraphInstance() != null) {
				return true;
			}
		}
		return false;
	}

	private int calPhysicalRows(DocLattice_P paragraphInstance) {

		int i;
		List<CellInstance> cellList = paragraphInstance.getCellList();
		int currentLogicRow = 0;
		int totalPRowCount = 1;
		int tempPRowCount = 1;
		int paragraphTotalPRow = 0;
		for (i = 0; i < cellList.size(); i++) {
			if (cellList.get(i).getRow() != currentLogicRow) {
				currentLogicRow = cellList.get(i).getRow();
				paragraphInstance.physicalRows.add(totalPRowCount);
				paragraphTotalPRow += totalPRowCount;
				totalPRowCount = 1;
				tempPRowCount = 1;

			}
			if (cellList.get(i).getEmbedParagraphInstance() != null) {
				tempPRowCount = calPhysicalRows(cellList.get(i)
						.getEmbedParagraphInstance());
				if (tempPRowCount > totalPRowCount)
					totalPRowCount = tempPRowCount;
			}

		}
		paragraphInstance.physicalRows.add(totalPRowCount);
		paragraphTotalPRow += totalPRowCount;
		return paragraphTotalPRow;
	}

	// ////////////////////////////////////////////////
	private void mergeCells(CellInstance cellInstance) {
		try {
			int i, j;
			theSheetParameter.sheet.mergeCells(theSheetParameter.col
					+ cellInstance.getCol(), theSheetParameter.row
					+ cellInstance.getRow(), theSheetParameter.col
					+ cellInstance.getCol() + cellInstance.getColspan() - 1,
					theSheetParameter.row + cellInstance.getRow()
							+ cellInstance.getRowspan() - 1);
			for (i = theSheetParameter.col + cellInstance.getCol(); i <= theSheetParameter.col
					+ cellInstance.getCol() + cellInstance.getColspan() - 1; i++) {
				for (j = theSheetParameter.row + cellInstance.getRow(); j <= theSheetParameter.row
						+ cellInstance.getRow() + cellInstance.getRowspan() - 1; j++) {
					if (!(i == theSheetParameter.col + cellInstance.getCol() && j == theSheetParameter.row
							+ cellInstance.getRow()))
						theSheetParameter.sheet.addCell(new Label(i, j, "",
								theSheetParameter.defaultFormat));
				}
			}
		} catch (Exception e) {
		}
	}

	private void mergeEmbedCells(CellInstance cellInstance) {
		try {
			int i, j;
			theSheetParameter.sheet.mergeCells(theSheetParameter.col
					+ cellInstance.getCol(), theSheetParameter.row
					+ cellInstance.getPhysicalRow(), theSheetParameter.col
					+ cellInstance.getCol() + cellInstance.getColspan() - 1,
					theSheetParameter.row + cellInstance.getPhysicalRow()
							+ cellInstance.getPhysicalRowSpan() - 1);
			for (i = theSheetParameter.col + cellInstance.getCol(); i <= theSheetParameter.col
					+ cellInstance.getCol() + cellInstance.getColspan() - 1; i++) {
				for (j = theSheetParameter.row + cellInstance.getPhysicalRow(); j <= theSheetParameter.row
						+ cellInstance.getPhysicalRow()
						+ cellInstance.getPhysicalRowSpan() - 1; j++) {
					if (!(i == theSheetParameter.col + cellInstance.getCol() && j == theSheetParameter.row
							+ cellInstance.getPhysicalRow()))
						theSheetParameter.sheet.addCell(new Label(i, j, "",
								theSheetParameter.defaultFormat));
				}
			}
		} catch (Exception e) {
		}
	}

	private WritableCell getLabel(CellInstance cellInstance) {
		while (!(theSheetParameter.sheet.getCell(theSheetParameter.col
				+ cellInstance.getCol(),
				theSheetParameter.row + cellInstance.getRow()) instanceof EmptyCell)) {
			cellInstance.setCol(cellInstance.getCol() + 1);

		}
		String content = cellInstance.getContent();
		if (content == null || content.toLowerCase().equals("null"))
			content = "";

		if (cellInstance.getIsComplexProcess() == 0) {
			if ("number".equals(cellInstance.getType())) {
				try {
					jxl.write.Number num = null;
					if (content.indexOf('.') >= 0) {
						Double a = Double.parseDouble(content);
						num = new jxl.write.Number(theSheetParameter.col
								+ cellInstance.getCol(), theSheetParameter.row
								+ cellInstance.getRow(), a);

					} else {
						Integer a = Integer.parseInt(content);
						num = new jxl.write.Number(theSheetParameter.col
								+ cellInstance.getCol(), theSheetParameter.row
								+ cellInstance.getRow(), a);

					}
					num.setCellFormat(getCellAttribute(cellInstance));
					return num;
				} catch (Exception e) {
					Label label = new Label(theSheetParameter.col
							+ cellInstance.getCol(), theSheetParameter.row
							+ cellInstance.getRow(), "数值无效",
							getCellAttribute(cellInstance));
					return label;
				}
			} else {
				Label label = new Label(theSheetParameter.col
						+ cellInstance.getCol(), theSheetParameter.row
						+ cellInstance.getRow(), content,
						getCellAttribute(cellInstance));
				return label;
			}
		} else {
			List<ComplexTextPhrase> list = processComplexText(content);
			cellInstance.setComplexTextPhraseList(list);
			int i;
			content = "";
			for (i = 0; i < list.size(); i++) {
				content = content + list.get(i).getContent();
			}

			if ("number".equals(cellInstance.getType())) {
				try {

					jxl.write.Number num = null;
					if (content.indexOf('.') >= 0) {
						Double a = Double.parseDouble(content);
						num = new jxl.write.Number(theSheetParameter.col
								+ cellInstance.getCol(), theSheetParameter.row
								+ cellInstance.getRow(), a);

					} else {
						Integer a = Integer.parseInt(content);
						num = new jxl.write.Number(theSheetParameter.col
								+ cellInstance.getCol(), theSheetParameter.row
								+ cellInstance.getRow(), a);

					}
					num.setCellFormat(getCellAttribute(cellInstance));
					return num;

				} catch (Exception e) {
					Label label = new Label(theSheetParameter.col
							+ cellInstance.getCol(), theSheetParameter.row
							+ cellInstance.getRow(), "数值无效",
							getCellAttribute(cellInstance));
					return label;
				}
			} else {

				Label label = new Label(theSheetParameter.col
						+ cellInstance.getCol(), theSheetParameter.row
						+ cellInstance.getRow(), content,
						getCellAttribute(cellInstance));
				return label;
			}
		}

	}

	// //////////////////////////////////////////////////
	private List<ComplexTextPhrase> processComplexText(String content) {
		int i, j, k;
		ComplexTextPhrase aPhrase = new ComplexTextPhrase();
		aPhrase.setContent(content);
		List<ComplexTextPhrase> contentList = new ArrayList();
		List<ComplexTextPhrase> contentList2 = null;
		contentList.add(aPhrase);
		try {
			contentList2 = processItalicText(contentList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return contentList2;
		}
	}

	private List<ComplexTextPhrase> processItalicText(
			List<ComplexTextPhrase> contentList) throws Exception {
		int i, j, k, e = 0;
		List<ComplexTextPhrase> contentList1 = new ArrayList();
		String str1 = null;
		ComplexTextPhrase aPhrase;
		for (i = 0; i < contentList.size(); i++) {
			e = 0;
			while (e != -1) {
				e = contentList.get(i).getContent().indexOf("[italic]");
				if (e != -1) {
					str1 = contentList.get(i).getContent().substring(0, e);
					aPhrase = new ComplexTextPhrase();
					aPhrase.setContent(str1);
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(e + 8));

					k = contentList.get(i).getContent().indexOf("[~italic]");
					if (k == -1) {
						throw new Exception(
								"文本解析过程中没有\"[italic]\"与\"[~italic]\"匹配");
					}
					str1 = contentList.get(i).getContent().substring(0, k);
					aPhrase = new ComplexTextPhrase();
					aPhrase.setContent(str1);
					aPhrase.setItalic(true);
					contentList1.add(aPhrase);
					contentList.get(i).setContent(
							contentList.get(i).getContent().substring(k + 9));
				} else {
					aPhrase = new ComplexTextPhrase();
					aPhrase.setContent(contentList.get(i).getContent());
					contentList1.add(aPhrase);
				}
			}
		}
		return contentList1;
	}

	// //////////////////////////////////////////////////////////
	private WritableCell getEmbedLabel(CellInstance cellInstance) {

		while (!(theSheetParameter.sheet.getCell(theSheetParameter.col
				+ cellInstance.getCol(),
				theSheetParameter.row + cellInstance.getPhysicalRow()) instanceof EmptyCell)) {
			cellInstance.setCol(cellInstance.getCol() + 1);

		}

		String content = cellInstance.getContent();
		if (content == null || content.toLowerCase().equals("null"))
			content = "";
		if (cellInstance.getIsComplexProcess() == 0) {
			if ("number".equals(cellInstance.getType())) {
				try {
					jxl.write.Number num = null;
					if (content.indexOf('.') >= 0) {
						Double a = Double.parseDouble(content);
						num = new jxl.write.Number(theSheetParameter.col
								+ cellInstance.getCol(), theSheetParameter.row
								+ cellInstance.getRow(), a);

					} else {
						Integer a = Integer.parseInt(content);
						num = new jxl.write.Number(theSheetParameter.col
								+ cellInstance.getCol(), theSheetParameter.row
								+ cellInstance.getRow(), a);

					}
					num.setCellFormat(getCellAttribute(cellInstance));
					return num;
				} catch (Exception e) {
					Label label = new Label(theSheetParameter.col
							+ cellInstance.getCol(), theSheetParameter.row
							+ cellInstance.getPhysicalRow(), "数值无效",
							getCellAttribute(cellInstance));
					return label;
				}
			} else {
				Label label = new Label(theSheetParameter.col
						+ cellInstance.getCol(), theSheetParameter.row
						+ cellInstance.getPhysicalRow(), content,
						getCellAttribute(cellInstance));
				return label;
			}
		} else {
			List<ComplexTextPhrase> list = processComplexText(content);
			cellInstance.setComplexTextPhraseList(list);
			int i;
			content = "";
			for (i = 0; i < list.size(); i++) {
				content = content + list.get(i).getContent();
			}

			if ("number".equals(cellInstance.getType())) {
				try {
					jxl.write.Number num = null;
					if (content.indexOf('.') >= 0) {
						Double a = Double.parseDouble(content);
						num = new jxl.write.Number(theSheetParameter.col
								+ cellInstance.getCol(), theSheetParameter.row
								+ cellInstance.getRow(), a);

					} else {
						Integer a = Integer.parseInt(content);
						num = new jxl.write.Number(theSheetParameter.col
								+ cellInstance.getCol(), theSheetParameter.row
								+ cellInstance.getRow(), a);

					}
					num.setCellFormat(getCellAttribute(cellInstance));
					return num;
				} catch (Exception e) {
					Label label = new Label(theSheetParameter.col
							+ cellInstance.getCol(), theSheetParameter.row
							+ cellInstance.getPhysicalRow(), "数值无效",
							getCellAttribute(cellInstance));
					return label;
				}
			} else {
				Label label = new Label(theSheetParameter.col
						+ cellInstance.getCol(), theSheetParameter.row
						+ cellInstance.getPhysicalRow(), content,
						getCellAttribute(cellInstance));
				return label;
			}

		}

	}

	private WritableImage getImage(CellInstance cellInstance) {
		Sheet aSheet = theSheetParameter.sheet;
		while (!(theSheetParameter.sheet.getCell(theSheetParameter.col
				+ cellInstance.getCol(),
				theSheetParameter.row + cellInstance.getRow()) instanceof EmptyCell)) {

			cellInstance.setCol(cellInstance.getCol() + 1);

		}
		String content = cellInstance.getContent();
		if (content == null || content.toLowerCase().equals("null"))
			content = "";
		WritableImage aImage = null;
		if (cellInstance.getType() != null
				&& cellInstance.getType().equals("image")) {
			aImage = new jxl.write.WritableImage(theSheetParameter.col
					+ cellInstance.getCol(), theSheetParameter.row
					+ cellInstance.getRow(), 1, 1,
					cellInstance.getStreamValue());

		}

		return aImage;
	}

	public void close(UDocument doc) throws Exception {
		// TODO Auto-generated method stub
		if (theSheetParameter != null)
			close();

	}

	public void close() {
		try {
			theSheetParameter.book.write();
			theSheetParameter.book.close();
			theSheetParameter = null;
		} catch (Exception e) {
		}

	}

	public void enforceClose(UDocument doc) {
		try {
			// theSheetParameter.book.write();
			if (theSheetParameter != null) {
				theSheetParameter.book.close();
				theSheetParameter = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// /////////////////////////////////////////////////////////////

	public File getNewTempFile() throws Exception {
		// TODO Auto-generated method stub
		return File.createTempFile("tmp", ".xls");
	}

	public void newPage(UDocument doc) {
		// TODO Auto-generated method stub

	}

	// public UDocument openDocumentFile(File file, UPaperTemplate
	// paperTemplate,
	// UPageNumberTemplate pageNum) throws Exception {
	// TODO Auto-generated method stub
	// FileOutputStream os = new FileOutputStream(file);
	// UDocument ud = null;
	// return null;
	// }

	public List<String[]> getRowList(InputStream inputStream, int sheetNum) {
		if (inputStream == null) {
			return null;
		}
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(inputStream);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (workbook == null) {
			return null;
		}
		Sheet[] sheets = workbook.getSheets();
		List<String[]> rowList = new ArrayList<String[]>();
		Sheet sheet;
		int rows;
		Cell[] row;
		sheet = sheets[sheetNum];
		rows = sheet.getRows();
		String[] contents;
		for (int i = 0; i < rows; i++) {
			row = sheet.getRow(i);
			contents = new String[row.length];
			for (int j = 0; j < contents.length; j++) {
				contents[j] = row[j].getContents();
			}
			rowList.add(contents);
		}
		workbook.close();
		return rowList;
	}

	public void addImage(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void addBlock(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception {
		// TODO Auto-generated method stub

	}

	// ////////////////////////////////////////////////////

	public void openSheetFile(File file) {
		USheetParameter parameter = null;
		try {
			parameter = new USheetParameter();
			parameter.book = Workbook.createWorkbook(file);
			theSheetParameter = parameter;
		} catch (Exception e) {
			e.printStackTrace();
			parameter = null;
		}
	}

	public void addSheet() {
		try {
			if (SHEET_NAME == null) {
				theSheetParameter.sheet = theSheetParameter.book.createSheet(
						Integer.toString(nowSheetId), nowSheetId);
			} else {
				theSheetParameter.sheet = theSheetParameter.book.createSheet(
						(String) SHEET_NAME.get(nowSheetId), nowSheetId);
			}

			if (paper != null)
				if (paper.getDirection().equals("h")) {
					theSheetParameter.sheet.setPageSetup(
							PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4,
							0.0d, 0.0d);
					theSheetParameter.sheet.getSettings().setTopMargin(
							Float.parseFloat(paper.getTop()) / 72);
					theSheetParameter.sheet.getSettings().setLeftMargin(
							Float.parseFloat(paper.getLeft()) / 72);
				}

		} catch (Exception e) {
			e.printStackTrace();
			theSheetParameter = null;
		}

	}

	// /////////////////////////////////////////////////////
	public WritableCellFormat getCellAttribute(CellInstance cellInstance) {
		// WritableFont font1 = new WritableFont(WritableFont
		// .createFont(cellAttribute.font.name), cellAttribute.font.size,
		// WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat();
		try {
			/** 设置字体颜色* */
			// font1.setColour(Colour.BLACK);
			/** 设置水平对齐方式* */
			// if (cellInstance.horizontalAlignment == 0) {
			// format.setAlignment(jxl.format.Alignment.LEFT);
			// } else if (cellAttribute.horizontalAlignment == 1) {
			// format.setAlignment(jxl.format.Alignment.CENTRE);
			// } else if (cellAttribute.horizontalAlignment == 2) {
			// format.setAlignment(jxl.format.Alignment.RIGHT);
			// }
			/** 设置垂直对齐方式* */
			// if (cellAttribute.verticalAlignment == 0) {
			UParagraphTemplate aTemplate = this.modelSessionBase
					.getParagraphTemplateByName(cellInstance.getTemplateName());

			if (aTemplate == null) {
				aTemplate = this.modelSessionBase
						.getParagraphTemplateByName(this.nowTemplateName);
				if (aTemplate == null)
					System.out.println("报表模板中未指定TemplateName");
			}
			boolean isItalic = false;
			if (cellInstance.getComplexTextPhraseList() != null) {
				int i;
				for (i = 0; i < cellInstance.getComplexTextPhraseList().size(); i++) {
					if (cellInstance.getComplexTextPhraseList().get(i)
							.isItalic())
						isItalic = true;
				}
			}

			UFont templateFont = this.modelSessionBase.getFontByName(
					aTemplate.fontName);
			WritableFont wf;
			if (templateFont.stly == 0)
				wf = new WritableFont(WritableFont.createFont("宋体"),
						templateFont.size, WritableFont.NO_BOLD, false);
			else
				wf = new WritableFont(WritableFont.createFont("宋体"),
						templateFont.size, WritableFont.BOLD, false);
			if (isItalic)
				wf.setItalic(true);
			format.setFont(wf);

			if (cellInstance.getHAlign() == 2){
				format.setAlignment(jxl.format.Alignment.LEFT);
			}else if(cellInstance.getHAlign() == 4){
				format.setAlignment(jxl.format.Alignment.RIGHT);
			}else{
				format.setAlignment(jxl.format.Alignment.CENTRE);
			}

			// format.setVerticalAlignment(jxl.format.VerticalAlignment.TOP);
			// } else if (cellAttribute.verticalAlignment == 1) {
			// format
			// .setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			// } else if (cellAttribute.verticalAlignment == 2) {
			// format
			// .setVerticalAlignment(jxl.format.VerticalAlignment.BOTTOM);
			// }

			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

			if (cellInstance.getShowBorder().equals("false"))
				format.setBorder(Border.NONE, BorderLineStyle.NONE);
			else {
				if ("dot".equals(cellInstance.getBorderLineStyle()))
					format.setBorder(Border.ALL, BorderLineStyle.DOTTED);
				else
					format.setBorder(Border.ALL, BorderLineStyle.THIN);
			}
			format.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;

	}

	public void addFormatText(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType) throws Exception {
	}

	// //////////////////////////////////////////////////////////////
	public UDocument openDocumentFile(File file, DocLattice_Paper paperInfo,
			UPageNumberTemplate pageNum) throws Exception {
		UDocument ud = new UDocument();
		ud.name = file.getAbsolutePath();
		this.paper = paperInfo;

		ud.object = file;
		return ud;
	}

	public void addEmbedTable(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType, List paragraphInstances) throws Exception {
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
	public byte[] getFileBytes(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UDocument openDocumentStream(ByteArrayOutputStream stream, UPaperTemplate paperTemplate,
			UPageNumberTemplate pageNum) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
