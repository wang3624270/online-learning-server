package cn.edu.sdu.uims.component.complex.form;

import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class UColumnInfoForm extends UForm {
	private String type;
	private String title;
	private String enTitle;
	private String moreHref;
	private List<UColumnInfoItemForm> dataList;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<UColumnInfoItemForm> getDataList() {
		return dataList;
	}
	public void setDataList(List<UColumnInfoItemForm> dataList) {
		this.dataList = dataList;
	}
	public String getMoreHref() {
		return moreHref;
	}
	public void setMoreHref(String moreHref) {
		this.moreHref = moreHref;
	}
	public String getEnTitle() {
		return enTitle;
	}
	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}
	
}
