package org.octopus.reportdog.document.impl;

import java.util.List;

import org.octopus.reportdog.module.CellInstance;
import org.octopus.reportdog.module.DocLattice_P;
import org.topwellsoft.docbricks.html.model.B_table;
import org.topwellsoft.docbricks.html.model.B_td;
import org.topwellsoft.docbricks.html.model.B_tr;





public class HtmlUtil {

	public static StringBuffer genHtmlFormatDoc(List<DocLattice_P> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");

		int i;
		for (i = 0; i < list.size(); i++) {
			if (list.get(i).getType().equals("table")) {
				B_table table = processTable(list.get(i));

			}
		}
		sb.append("</html>");
		return sb;
	}

	public static B_table processTable(DocLattice_P p) {
		B_table table = new B_table();
		List<CellInstance> list = p.getCellList();
		int i;
		int currentRow = 0;
		B_tr tr = new B_tr();
		if (p.getCellList().size() == 0)
			return table;
		table.addTr(tr);

		for (i = 0; i < list.size(); i++) {

			CellInstance cell = list.get(i);
			if (cell.getRow() != currentRow) {
				currentRow++;
				tr = new B_tr();
				table.addTr(tr);
			}
			tr.getChildBrick().add(processTd(cell));
		}
		return table;
	}

	public static B_td processTd(CellInstance cell) {
		B_td td = new B_td();
		if (cell.getEmbedParagraphInstance() != null) {
			if (cell.getEmbedParagraphInstance().getType().equals("table")) {
				td.getChildBrick().add(
						processTable(cell.getEmbedParagraphInstance()));
			}

		} else {
			td.setBodyText(cell.getContent());
		}
		td.setRowspan(Integer.toString(cell.getRowspan()));
		td.setColspan(Integer.toString(cell.getColspan()));

		return td;

	}
}