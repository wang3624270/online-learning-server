package org.octopus.reportdog.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.octopus.reportdog.state.BlockState;

public class DocLattice_P extends BaseDocLatticeElement implements Serializable {

	private String name;
	private String type;
	private String content;
	private int rows = 0;
	private int cols = 0;
	public Integer textHeight = 20;// 文本块高度
	private String templateName;
	private int startPhysicalRow = 0;
	private int startPhysicalCol = 0;
	public List<Integer> physicalRows = new ArrayList<Integer>();// 每个tr实际使用的行数，用于excel(试验方案)

	public float tableBorderWidth = 0.5f;// 表格边框宽度
	public float tableWidthPercentage = 80f;// 表格所占宽度百分比

	public String newPageAction = "";// 换页时，这个段落的行为。
	public Integer headerRowCount = 0;
	public boolean splitRows = true;

	public boolean isSplitRows() {
		return splitRows;
	}

	public void setSplitRows(boolean splitRows) {
		this.splitRows = splitRows;
	}

	public Integer getHeaderRowCount() {
		return headerRowCount;
	}

	public void setHeaderRowCount(Integer headerRowCount) {
		this.headerRowCount = headerRowCount;
	}

	public String getNewPageAction() {
		return newPageAction;
	}

	public void setNewPageAction(String newPageAction) {
		this.newPageAction = newPageAction;
	}

	private float width[];
	private float height[];

	public List<BlockState> blockStateList = new ArrayList<BlockState>();

	private List<CellInstance> cellList = new ArrayList<CellInstance>();
	private String newPage = "false";

	public boolean isContainsChildPara() {
		if (this.getType().equals("table")
				|| this.getType().equals("childTable")) {
			int i;
			for (i = 0; i < cellList.size(); i++) {
				if (cellList.get(i).getEmbedParagraphInstance() != null)
					return true;
			}

		}
		return false;
	}

	public float getTableWidthPercentage() {
		return this.tableWidthPercentage;
	}

	public void setTableWidthPercentage(float tableWidthPercentage) {
		this.tableWidthPercentage = tableWidthPercentage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<CellInstance> getCellList() {
		return cellList;
	}

	public void setCellList(List<CellInstance> cellList) {
		this.cellList = cellList;
	}

	public void addCellInstance(CellInstance cellInstance) {
		// TODO Auto-generated method stub
		cellList.add(cellInstance);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	// //////////////////////////////////////////////////////
	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	// //////////////////////////////////////////////////////
	public float[] getWidth() {
		return width;
	}

	public void setWidth(float[] width) {
		this.width = width;
	}

	public float[] getHeight() {
		return height;
	}

	public void setHeight(float[] height) {
		this.height = height;
	}

	public List<BlockState> getBlockStateList() {
		return blockStateList;
	}

	public void setBlockStateList(List<BlockState> blockStateList) {
		this.blockStateList = blockStateList;
	}

	// /////////////////////////////////////////

	public float getTableBorderWidth() {
		return this.tableBorderWidth;
	}

	public void setTableBorderWidth(float tableBorderWidth) {
		this.tableBorderWidth = tableBorderWidth;
	}

	public Integer getTextHeight() {
		return this.textHeight;
	}

	public void setTextHeight(Integer textHeight) {
		this.textHeight = textHeight;
	}

	public void setNewPage(String newPage) {
		this.newPage = newPage;
	}

	public String getNewPage() {
		return newPage;
	}

	public int getStartPhysicalRow() {
		return startPhysicalRow;
	}

	public void setStartPhysicalRow(int startPhysicalRow) {
		this.startPhysicalRow = startPhysicalRow;
	}

	public int getStartPhysicalCol() {
		return startPhysicalCol;
	}

	public void setStartPhysicalCol(int startPhysicalCol) {
		this.startPhysicalCol = startPhysicalCol;
	}

}
