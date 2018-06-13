package org.octopus.reportdog.module;

public class DocBrick_PageFooter extends BaseDocBrickElement {
	private String value;
	private String style;
	private String delayEvaluate;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void parse() {
		super.parse();

	}

	@Override
	public DocLattice_PageFooter transToBasicLatticeElement() {

		DocLattice_PageFooter lpageFooter = new DocLattice_PageFooter();

		lpageFooter.setStyle(style);
		lpageFooter.setValue(value);

		return lpageFooter;
	}

	public String getDelayEvaluate() {
		return delayEvaluate;
	}

	public void setDelayEvaluate(String delayEvaluate) {
		this.delayEvaluate = delayEvaluate;
	}
}