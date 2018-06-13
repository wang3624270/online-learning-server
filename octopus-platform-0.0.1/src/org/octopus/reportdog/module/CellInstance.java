package org.octopus.reportdog.module;

import java.io.Serializable;
import java.util.List;

public class CellInstance implements Serializable {

	private String height = "17";

	private String content;
	private String contentVariableStr;
	private String width;

	private int row;
	private int physicalRow;
	private int col;
	private int physicalCol;
	private int rowspan = 1;
	private int physicalRowSpan = 1;
	private int colspan = 1;
	private int physicalColSpan = 1;
	private int underline = 0;
	public float x;
	public float y;
	public float h = 0;
	public float w = 0;
	private int hAlign;
	private float lineLeading = 1;

	private int isFixedHeight = 1;// 单元格高度是否固定（1固定，0不固定）
	private int isComplexProcess = 0;// 是否对文本进行复杂分析
	private int isUnicodeProcess = 0;// 是否分析文本中的unicode码
	private int isHtmlProcess = 0;// 是否以HTML方式解析文本（目前只支持较简单的文本）
	private int vAlign;
	private String type = "";// 表格内容类型
	private DocLattice_P embedParagraphInstance = null;
	private String templateName = null;
	private byte[] streamValue;// 存储流信息,(如图片信息)
	private String showBorder = "true";
	private String newPage = "false";
	private List<ComplexTextPhrase> ComplexTextPhraseList;// 对于加下划线、加粗等复杂文本来讲，用一个ComplexTextPhrase列表的形式表示
	private String borderLineStyle = "";// 单元格的线型（实线、虚线），多用于excel
	private String delayEvaluate = null;

	public String getNewPage() {
		return newPage;
	}

	public void setNewPage(String newPage) {
		this.newPage = newPage;
	}

	public float getLineLeading() {
		return this.lineLeading;
	}

	public void setLineLeading(float lineLeading) {
		this.lineLeading = lineLeading;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	// ////////////////////////////////////////////////
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	// ////////////////////////////////////////////

	public byte[] getStreamValue() {
		return this.streamValue;
	}

	public void setStreamValue(byte[] streamValue) {
		this.streamValue = streamValue;
	}

	// ////////////////////////////////////////////
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// ///////////////////////////////////////////////
	public void setHAlign(int hAlign) {
		this.hAlign = hAlign;
	}

	public int getHAlign() {
		return hAlign;
	}

	public void setVAlign(int vAlign) {
		this.vAlign = vAlign;
	}

	public int getVAlign() {
		return vAlign;
	}

	// //////////////////////////////
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public int getUnderline() {
		return this.underline;
	}

	public void setUnderline(int underline) {
		this.underline = underline;
	}

	public void setIsFixedHeight(int isFixedHeight) {
		this.isFixedHeight = isFixedHeight;
	}

	public int getIsFixedHeight() {
		return isFixedHeight;
	}

	public void setIsComplexProcess(int isComplexProcess) {
		this.isComplexProcess = isComplexProcess;
	}

	public int getIsComplexProcess() {
		return isComplexProcess;
	}

	public void setIsUnicodeProcess(int isUnicodeProcess) {
		this.isUnicodeProcess = isUnicodeProcess;
	}

	public int getIsUnicodeProcess() {
		return isUnicodeProcess;
	}

	public void setIsHtmlProcess(int isHtmlProcess) {
		this.isHtmlProcess = isHtmlProcess;
	}

	public int getIsHtmlProcess() {
		return isHtmlProcess;
	}

	public void setEmbedParagraphInstance(DocLattice_P embedParagraphInstance) {
		this.embedParagraphInstance = embedParagraphInstance;
	}

	public DocLattice_P getEmbedParagraphInstance() {
		return embedParagraphInstance;
	}

	public void setShowBorder(String showBorder) {
		this.showBorder = showBorder;
	}

	public String getShowBorder() {
		return showBorder;
	}

	public int getPhysicalRow() {
		return physicalRow;
	}

	public void setPhysicalRow(int physicalRow) {
		this.physicalRow = physicalRow;
	}

	public int getPhysicalRowSpan() {
		return physicalRowSpan;
	}

	public void setPhysicalRowSpan(int physicalRowSpan) {
		this.physicalRowSpan = physicalRowSpan;
	}

	public int getPhysicalCol() {
		return physicalCol;
	}

	public void setPhysicalCol(int physicalCol) {
		this.physicalCol = physicalCol;
	}

	public int getPhysicalColSpan() {
		return physicalColSpan;
	}

	public void setPhysicalColSpan(int physicalColSpan) {
		this.physicalColSpan = physicalColSpan;
	}

	public List<ComplexTextPhrase> getComplexTextPhraseList() {
		return ComplexTextPhraseList;
	}

	public void setComplexTextPhraseList(
			List<ComplexTextPhrase> complexTextPhraseList) {
		ComplexTextPhraseList = complexTextPhraseList;
	}

	public String getBorderLineStyle() {
		return borderLineStyle;
	}

	public void setBorderLineStyle(String borderLineStyle) {
		this.borderLineStyle = borderLineStyle;
	}

	public String getContentVariableStr() {
		return contentVariableStr;
	}

	public void setContentVariableStr(String contentVariableStr) {
		this.contentVariableStr = contentVariableStr;
	}

	public String getDelayEvaluate() {
		return delayEvaluate;
	}

	public void setDelayEvaluate(String delayEvaluate) {
		this.delayEvaluate = delayEvaluate;
	}

}
