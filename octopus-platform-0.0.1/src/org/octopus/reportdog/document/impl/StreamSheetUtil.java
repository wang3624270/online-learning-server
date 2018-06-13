package org.octopus.reportdog.document.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.octopus.reportdog.document.BaseStreamDocumentUtil;
import org.octopus.reportdog.document.ExportObjectOperator;
import org.octopus.reportdog.module.CellInstance;
import org.octopus.reportdog.module.DocLattice_P;

import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.USheetParameter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableImage;
import jxl.write.WriteException;


public class StreamSheetUtil extends BaseStreamDocumentUtil {

	// public static USheetUtil instance;

	public int nowSheetId = 0;

	public USheetParameter theSheetParameter = null;

	public static StreamSheetUtil getInstance() {
		return new StreamSheetUtil();

	}

	public ExportObjectOperator openDocumentStream(ByteArrayOutputStream aStream,
			UPaperTemplate paperTemplate, UPageNumberTemplate pageNum)
			throws Exception {
		return null;
	}

	public void addParagraph(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception {
		String fileName = ((File) ud.object).getName();
		// if (theSheetParameter == null)
		// theSheetParameter = openSheetFile((File) ud.object);

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
		// TODO Auto-generated method stub
		String fileName = ((File) ud.object).getName();
		// if (theSheetParameter == null)
		// theSheetParameter = openSheetFile((File) ud.object);
		try {

			Label label = null;
			String content;
			List<CellInstance> cellList = paragraphInstance.getCellList();
			CellInstance cellInstance;
			int i;
			float[] widths = paragraphInstance.getWidth();
			if (widths != null)
				for (i = 0; i < widths.length; i++) {
					theSheetParameter.sheet.setColumnView(i, (int) widths[i]);

				}
			for (i = 0; i < cellList.size(); i++) {
				/** 设置缩进符* */

				cellInstance = cellList.get(i);
				content = cellInstance.getContent();

				theSheetParameter.sheet.setRowView(cellInstance.getCol(),
						Integer.parseInt(cellInstance.getHeight()) * 15);
				// label = new Label(
				// theSheetParameter.col + cellInstance.getCol(),
				// theSheetParameter.row + cellInstance.getRow(), content,
				// getCellAttribute(cellInstance));
				// 将定义好的单元格添加到工作表中

				// Sheet aSheet = theSheetParameter.sheet;
				if (cellInstance.getType() != null
						&& cellInstance.getType().equals("image")) {
					WritableImage image = getImage(cellInstance);
					if (image != null)
						theSheetParameter.sheet
								.addImage(getImage(cellInstance));
					else
						theSheetParameter.sheet.addCell(getLabel(cellInstance));

				} else
					theSheetParameter.sheet.addCell(getLabel(cellInstance));

				// theSheetParameter.sheet.addImage(arg0);
				if (cellInstance.getColspan() > 1
						|| cellInstance.getRowspan() > 1) {
					mergeCells(cellInstance);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}

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
								getCellAttribute(cellInstance)));
				}

			}

		} catch (Exception e) {
		}

	}

	private Label getLabel(CellInstance cellInstance) {
		Sheet aSheet = theSheetParameter.sheet;
		while (!(theSheetParameter.sheet.getCell(theSheetParameter.col
				+ cellInstance.getCol(), theSheetParameter.row
				+ cellInstance.getRow()) instanceof EmptyCell)) {

			cellInstance.setCol(cellInstance.getCol() + 1);

		}
		String content = cellInstance.getContent();
		if (content == null || content.toLowerCase().equals("null"))
			content = "";

		Label label = new Label(theSheetParameter.col + cellInstance.getCol(),
				theSheetParameter.row + cellInstance.getRow(), content,
				getCellAttribute(cellInstance));
		return label;
	}

	private WritableImage getImage(CellInstance cellInstance) {
		Sheet aSheet = theSheetParameter.sheet;
		while (!(theSheetParameter.sheet.getCell(theSheetParameter.col
				+ cellInstance.getCol(), theSheetParameter.row
				+ cellInstance.getRow()) instanceof EmptyCell)) {

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
					+ cellInstance.getRow(), 1, 1, cellInstance
					.getStreamValue());

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
			e.printStackTrace();
		}

	}

	public void enforceClose(UDocument doc) {
		try {
			// theSheetParameter.book.write();
			theSheetParameter.book.close();
			theSheetParameter = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// /////////////////////////////////////////////////////////////

	public File getNewTempFile() throws Exception {
		// TODO Auto-generated method stub
		return File.createTempFile("tmp", ".xls");
	}

	public void newPage(UDocument doc) throws Exception {
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

			theSheetParameter.sheet = theSheetParameter.book.createSheet(
					Integer.toString(nowSheetId), nowSheetId);

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
			format.setVerticalAlignment(jxl.format.VerticalAlignment.TOP);
			// } else if (cellAttribute.verticalAlignment == 1) {
			// format
			// .setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			// } else if (cellAttribute.verticalAlignment == 2) {
			// format
			// .setVerticalAlignment(jxl.format.VerticalAlignment.BOTTOM);
			// }
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			format.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;

	}

	public void addFormatText(UDocument ud,
			DocLattice_P paragraphInstance, int arrageType)
			throws Exception {
	}

	// //////////////////////////////////////////////////////////////


	public void addEmbedTable(UDocument ud,
			DocLattice_P paragraphInstance, int arrageType,
			List paragraphInstances) throws Exception {
	}
}
