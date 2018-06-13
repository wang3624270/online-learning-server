package org.octopus.reportdog.module;

import java.util.ArrayList;
import java.util.List;

import org.octopus.reportdog.state.BlockState;
 
public class DocBrick_P {

	public String name;

	public String type;

	public String content;

	public String templateName;

	public float tableBorderWidth = 0.5f;// 表格边框宽度
	public float tableWidthPercentage = 80f;// 表格所占宽度百分比
	public String rowCount = null;// 表行数
	public String colCount = null;// 表列数
	public String widths;// 列宽度
	public String heights = "17";// 行高度
	public String onlyShowInLastPage = "false";// 只在末页显示
	public int rowArrangement = 0;
	public Integer textHeight = 20;// 文本块高度
	public List<BlockState> blockStateList = new ArrayList<BlockState>();

	public List<RowConfig> rowList = new ArrayList<RowConfig>();
	public String newPage = "false";
	public String newPageAction = "";
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

	public void addRowConfig(RowConfig rowConfig) {
		rowList.add(rowConfig);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<RowConfig> getRowList() {
		return rowList;
	}

	public void setRowList(List<RowConfig> rowList) {
		this.rowList = rowList;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void addBlockState(BlockState blockState) {
		this.blockStateList.add(blockState);
	}

	public List<BlockState> getBlockStateList() {
		return blockStateList;
	}

	public void setBlockStateList(List<BlockState> blockStateList) {
		this.blockStateList = blockStateList;
	}

	// //////////////////////////////////////////
	public String getRowCount() {
		return this.rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public String getColCount() {
		return this.colCount;
	}

	public void setColCount(String colCount) {
		this.colCount = colCount;
	}

	// ///////////////////////////////////////////////
	public String getWidths() {
		return this.widths;
	}

	public void setWidths(String widths) {
		this.widths = widths;
	}

	public String getHeights() {
		return this.heights;
	}

	public void setHeights(String heights) {
		this.heights = heights;
	}

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

	public void setRowArrangement(int rowArrangement) {
		this.rowArrangement = rowArrangement;
	}

	public int getRowArrangement() {
		return rowArrangement;
	}

	public void setOnlyShowInLastPage(String onlyShowInLastPage) {
		this.onlyShowInLastPage = onlyShowInLastPage;
	}

	public String getOnlyShowInLastPage() {
		return onlyShowInLastPage;
	}

	public void setNewPage(String newPage) {
		this.newPage = newPage;
	}

	public String getNewPage() {
		return newPage;
	}
}
