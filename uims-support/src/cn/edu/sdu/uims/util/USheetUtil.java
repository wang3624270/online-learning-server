package cn.edu.sdu.uims.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.USheetParameter;
import cn.edu.sdu.uims.service.UFactory;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class USheetUtil {
	private static int fileNo = 0;
	private static USheetUtil instance = new USheetUtil();

	public static USheetUtil getInstance() {
		return instance;
	}

	public String getNewTempFileName() {
		return UFileUtil.getNewTempFileName(fileNo, "xls");
	}

	public USheetParameter openSheetFile(String fileName) {
		USheetParameter parameter = null;
		try {
			parameter = new USheetParameter();
			parameter.book = Workbook.createWorkbook(new File(fileName));
			// 生成名为"第一页"的工作表，参数0表示这是第一页
			parameter.sheet = parameter.book.createSheet("第一页", 0);

		} catch (Exception e) {
			e.printStackTrace();
			parameter = null;
		}
		return parameter;
	}

	public byte[] getSheetFileBytes(String fileName) {
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

	/**
	 * 传一个输出流，主要用于bs端的下载
	 * 
	 * @param os
	 * @return
	 */
	public USheetParameter openSheetFile(OutputStream os) {
		USheetParameter parameter = null;
		try {
			parameter = new USheetParameter();
			parameter.book = Workbook.createWorkbook(os);
			// 生成名为"第一页"的工作表，参数0表示这是第一页
			parameter.sheet = parameter.book.createSheet("第一页", 0);

		} catch (Exception e) {
			e.printStackTrace();
			parameter = null;
		}
		return parameter;
	}

	/**
	 * 生成excel
	 * 
	 * @param os
	 */
	public void addCells(USheetParameter par, UCellAttribute[] datas) {
		try {

			Label label = null;
			for (int i = 0; i < datas.length; i++) {
				/** 设置缩进符* */
				String content = (datas[i]).content;
				label = new Label(par.col + datas[i].col, par.row
						+ datas[i].row, content, getCellAttribute((datas[i])));
				// 将定义好的单元格添加到工作表中
				par.sheet.addCell(label);
				if (datas[i].colSpan > 1 || datas[i].rowSpan > 1) {
					par.sheet.mergeCells(par.col + datas[i].col, par.row
							+ datas[i].row, par.col + datas[i].col
							+ datas[i].colSpan - 1, par.row + datas[i].row
							+ datas[i].rowSpan - 1);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void close(USheetParameter par) {
		try {
			par.book.write();
			par.book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public WritableCellFormat getCellAttribute(UCellAttribute cellAttribute) {
		UFont uFont = UFactory.getModelSession().getFontByName(
				cellAttribute.fontName);
		WritableFont font1 = new WritableFont(WritableFont
				.createFont(uFont.fontName), 10, WritableFont.NO_BOLD);
		try {
			if (uFont.stly != 0)
				font1.setBoldStyle(WritableFont.BOLD);
		} catch (Exception e) {
		}
		WritableCellFormat format = new WritableCellFormat(font1);
		try {
			/** 设置字体颜色* */
			font1.setColour(Colour.BLACK);
			/** 设置水平对齐方式* */
			if (cellAttribute.horizontalAlignment == 0) {
				format.setAlignment(jxl.format.Alignment.LEFT);
			} else if (cellAttribute.horizontalAlignment == 1) {
				format.setAlignment(jxl.format.Alignment.CENTRE);
			} else if (cellAttribute.horizontalAlignment == 2) {
				format.setAlignment(jxl.format.Alignment.RIGHT);
			}
			/** 设置垂直对齐方式* */
			if (cellAttribute.verticalAlignment == 0) {
				format.setVerticalAlignment(jxl.format.VerticalAlignment.TOP);
			} else if (cellAttribute.verticalAlignment == 1) {
				format
						.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			} else if (cellAttribute.verticalAlignment == 2) {
				format
						.setVerticalAlignment(jxl.format.VerticalAlignment.BOTTOM);
			}
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			format.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}
}
