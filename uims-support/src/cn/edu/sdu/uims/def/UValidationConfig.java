package cn.edu.sdu.uims.def;

import java.util.HashMap;

public class UValidationConfig {

	protected String prefix;
	protected HashMap<String,UValidationForm>formMap;
	public UValidationConfig(String prefix){
		this.prefix = prefix;
		formMap = new HashMap<String,UValidationForm>();
	}
	public void addFormConfig(UValidationForm form){
		if(formMap.get(form.getPath()) == null){
			formMap.put(form.getPath(), form);
		}
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public HashMap<String, UValidationForm> getFormMap() {
		return formMap;
	}
	public void setFormMap(HashMap<String, UValidationForm> formMap) {
		this.formMap = formMap;
	}
}
