package cn.edu.sdu.common.reportdog;

import java.io.Serializable;

import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class USheetParameter implements Serializable {
	public WritableWorkbook book;
	public WritableSheet sheet;
	public int columnNum = 0;
	public int row, col = 0;
	public WritableCellFormat defaultFormat = new WritableCellFormat();
	public boolean isEmbedTable = false;
}
