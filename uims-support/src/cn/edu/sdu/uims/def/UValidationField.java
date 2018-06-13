package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.List;

public class UValidationField {

	protected String property;
	protected String validate;
	protected List<String>validateList;
	public UValidationField(){
		this.validateList = new ArrayList<String>();
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
}
