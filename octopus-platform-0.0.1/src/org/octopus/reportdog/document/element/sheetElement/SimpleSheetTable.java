package org.octopus.reportdog.document.element.sheetElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.document.ExportObjectOperator;
import org.octopus.reportdog.document.element.ReportElement;
import org.octopus.reportdog.document.impl.StreamSheetUtil;

import cn.edu.sdu.common.reportdog.USheetParameter;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WriteException;


public class SimpleSheetTable implements ReportElement {

	public List<String> columnName = new ArrayList<String>();
	public List<Integer> columnWidth = new ArrayList<Integer>();
	public String[][] data = null;

	@Override
	public void renderElement(ExportObjectOperator ud) {

		StreamSheetUtil util = (StreamSheetUtil) ud.paraMap.get("documentUtil");
		int i, j;
		try {

			Label label = null;
			String content;

			for (i = 0; i < columnName.size(); i++) {
				util.theSheetParameter.sheet.setColumnView(i, columnWidth
						.get(i));

			}
			WritableCellFormat format = getCellAttribute();
			for (i = 0; i < columnName.size(); i++) {
				util.theSheetParameter.sheet
						.addCell(getLabel(util.theSheetParameter, columnName
								.get(i), format, i, 0));

			}
			int nowX = 0;
			int nowY = 1;
			for (i = 0; i < data.length; i++) {
				for (j = 0; j < data[i].length; j++) {
					util.theSheetParameter.sheet.addCell(getLabel(
							util.theSheetParameter, data[i][j], format, j,
							i + 1));

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Label getLabel(USheetParameter theSheetParameter, String content,
			WritableCellFormat format, int x, int y) {
		Sheet aSheet = theSheetParameter.sheet;
		// String content = data[x][y];
		// try {
		// content = data[x][y];
		// } catch (Exception e) {
		// content = "";

		// }
		if (content == null || content.toLowerCase().equals("null"))
			content = "";

		Label label = new Label(x, y, content, format);
		return label;
	}

	public WritableCellFormat getCellAttribute() {

		WritableCellFormat format = new WritableCellFormat();
		try {

			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			// format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN);
			format.setWrap(true);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;

	}

	@Override
	public void injectContent(Object ob, HashMap paraMap) {
		// TODO Auto-generated method stub

	}
}