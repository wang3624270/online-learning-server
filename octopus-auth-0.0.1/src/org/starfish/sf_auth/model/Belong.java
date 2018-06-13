package org.starfish.sf_auth.model;
 
import org.starfish.configure_model.SFModel;

public class Belong extends SFModel {
	private String dim;
	private String value;

	public Belong(SFModel parent) {
		super(parent);
	}

	public void parse() {
		dim = this.currentElement.attributeValue("dim");
		value = this.currentElement.attributeValue("value");
	}

	public String getDim() {
		return dim;
	}

	public void setDim(String dim) {
		this.dim = dim;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}