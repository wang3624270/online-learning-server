package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.form.UHtmlItemFormI;

public class HtmlContentItemEditForm extends UForm {

	private UHtmlItemFormI  formData;
	private String itemName;
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public UHtmlItemFormI getFormData() {
		return formData;
	}
	public void setFormData(UHtmlItemFormI formData) {
		this.formData = formData;
	}
	
}
