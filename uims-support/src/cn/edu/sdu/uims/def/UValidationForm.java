package cn.edu.sdu.uims.def;

import java.util.HashMap;

public class UValidationForm {

	protected String page;
	protected String path;
	protected HashMap<String,UValidationField>fieldsMap;
	public UValidationForm(){
		this.fieldsMap = new HashMap<String,UValidationField>();
	}
	public void addFieldConfig(UValidationField field){
		if(fieldsMap.get(field.getProperty()) == null){
			fieldsMap.put(field.getProperty(), field);
		}
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public HashMap<String, UValidationField> getFieldsMap() {
		return fieldsMap;
	}
	public void setFieldsMap(HashMap<String, UValidationField> fieldsMap) {
		this.fieldsMap = fieldsMap;
	}
}
