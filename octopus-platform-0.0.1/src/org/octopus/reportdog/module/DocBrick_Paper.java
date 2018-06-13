package org.octopus.reportdog.module;

public class DocBrick_Paper extends BaseDocBrickElement {
	// <paper type="A4" left="15.0" right="15.0" top="40.0" bottom="20.0" />
	private String type = "A4";
	private String direction = "v"; // v or h
	private String left = "15.0";
	private String right = "15.0";
	private String top = "40.0";
	private String bottom = "20.0";

	public void parse() {
		parseProperites();
	}

	@Override
	public DocLattice_Paper transToBasicLatticeElement() {

		DocLattice_Paper lPaper = new DocLattice_Paper();

		lPaper.setType(this.type);
		lPaper.setDirection(this.direction);
		lPaper.setLeft(this.left);
		lPaper.setRight(this.right);
		lPaper.setTop(this.top);
		lPaper.setBottom(this.bottom);

		return lPaper;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getBottom() {
		return bottom;
	}

	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}