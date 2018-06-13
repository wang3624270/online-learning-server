package org.octopus.reportdog.module;

public class CellConfig {

	private String content;

	private String width;
	private String height = "-1";
	private String rowspan;

	private String colspan;
	private String align;
	private String halign;
	private String valign;
	private float lineLeading = 1;
	private int underline = 0;
	private int isFixedHeight = 1;// 单元格高度是否固定（1固定，0不固定）
	private int isComplexProcess = 0;// 是否对文本进行复杂分析
	private int isUnicodeProcess = 0;// 是否分析文本中的unicode码
	private int isHtmlProcess = 0;// 是否以HTML方式解析文本（目前只支持较简单的文本）
	private String type = "";// 单元格类型
	private int isChildTableTemplate = 0;// 该单元格是否为子表模板
	private String templateName = null;
	private String showBorder = "true";
	private String omitEmpty = "false";
	private String newPage = "false";
	private String borderLineStyle = "";// 单元格的线型（实线、虚线），多用于excel
	private String delayEvaluate = "";

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

	public String getRowspan() {
		return rowspan;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getAlign() {
		return this.align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	// //////////////////////////////////////////////////////////
	public String getHalign() {
		return this.halign;
	}

	public void setHalign(String halign) {
		this.halign = halign;
	}

	public String getValign() {
		return this.valign;
	}

	public void setValign(String valign) {
		this.valign = valign;
	}

	// ///////////////////////////////////////////////
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

	// ///////////////////////////////////////////////

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

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHeight() {
		return height;
	}

	public void setIsHtmlProcess(int isHtmlProcess) {
		this.isHtmlProcess = isHtmlProcess;
	}

	public int getIsHtmlProcess() {
		return isHtmlProcess;
	}

	public void setIsChildTableTemplate(int isChildTableTemplate) {
		this.isChildTableTemplate = isChildTableTemplate;
	}

	public int getIsChildTableTemplate() {
		return isChildTableTemplate;
	}

	public void setShowBorder(String showBorder) {
		this.showBorder = showBorder;
	}

	public String getShowBorder() {
		return showBorder;
	}

	public void setOmitEmpty(String omitEmpty) {
		this.omitEmpty = omitEmpty;
	}

	public String getOmitEmpty() {
		return omitEmpty;
	}

	public String getBorderLineStyle() {
		return borderLineStyle;
	}

	public void setBorderLineStyle(String borderLineStyle) {
		this.borderLineStyle = borderLineStyle;
	}

	public String getDelayEvaluate() {
		return delayEvaluate;
	}

	public void setDelayEvaluate(String delayEvaluate) {
		this.delayEvaluate = delayEvaluate;
	}

 

}
