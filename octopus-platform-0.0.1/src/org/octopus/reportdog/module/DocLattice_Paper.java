package org.octopus.reportdog.module;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public class DocLattice_Paper extends BaseDocLatticeElement {
	// <paper type="A4" left="15.0" right="15.0" top="40.0" bottom="20.0" />
	private String type = "A4";
	private String direction = "v"; // v or h
	private String left;
	private String right;
	private String top;
	private String bottom;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type != null)
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

	public float getPaperWidth() {
		Rectangle re = PageSize.A4;
		if (direction.equals("v")) {
			return re.getRight();
		} else {
			return re.getTop();
		}

	}

	// Compatible for UIMS. Remove these code in future. It is not recommended
	// to use old pattern.
	public void getOldPaperInfo() {
	}

}