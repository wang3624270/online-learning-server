package cn.edu.sdu.uims.component.complex.form;

import cn.edu.sdu.common.form.UForm;

public class UColumnInfoItemForm extends UForm {
	private Integer id;
	private String label;
	private String alt;
	private String enLabel;
	private String href;
	private String style;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String toString(){
		return label;
	}
	public String getEnLabel() {
		return enLabel;
	}
	public void setEnLabel(String enLabel) {
		this.enLabel = enLabel;
	}
	
}
