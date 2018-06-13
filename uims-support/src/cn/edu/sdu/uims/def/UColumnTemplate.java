package cn.edu.sdu.uims.def;

import java.util.HashMap;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.constant.UConstants;

public class UColumnTemplate extends UTemplate{
	public int width = 30;
	public int  maxLength = 0;
	public String filter=null,filter1=null;
	public String itemFormMember;
	public Integer itemIndex;
	public String itemPar;
	public int horizontalAlignment = UConstants.ALIGNMENT_CENTER;
	public int verticalAlignment = UConstants.ALIGNMENT_CENTER;
	public String borderName;
	public String fontName;
	public String colorName;
	public boolean editable;
//	public int comType = UConstants.COMPONENT_TEXTFIELD;
	public ListOptionInfo[] addedDatas;
	public String dictionary;
	//liuyang added
	public HashMap rowspanMap;
	//public int fixHeight;
	
	//zxn added
	public String beanFormMember;
	public String type;
	public String href;
	public String resource;
	public String renderClassName;	
	public boolean canSum = false;
	public String format;
	public boolean exlout = true;
	public boolean visible = true;
	public String comparator =null;
	public String panelTemplateName;
	public String popupMode = "panel"; 
	public boolean popupable = false;
	public String paras;
	public String templateName;
	public String validatorName =null;
	public String validatorClassName =null;
	public String validatorMsg = null;
	public String validatorParas = null;
	
	public boolean singleChoice = false;
	public boolean isDynamicVisible = false;
	public boolean isDimension = false;
	
	public String getPanelTemplateName() {
		return panelTemplateName;
	}
	public void setPanelTemplateName(String panelTemplateName) {
		this.panelTemplateName = panelTemplateName;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getFilter1() {
		return filter1;
	}
	public void setFilter1(String filter1) {
		this.filter1 = filter1;
	}
	public String getItemFormMember() {
		return itemFormMember;
	}
	public void setItemFormMember(String itemFormMember) {
		this.itemFormMember = itemFormMember;
	}
	public int getHorizontalAlignment() {
		return horizontalAlignment;
	}
	public void setHorizontalAlignment(int horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}
	public int getVerticalAlignment() {
		return verticalAlignment;
	}
	public void setVerticalAlignment(int verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}
	public String getBorderName() {
		return borderName;
	}
	public void setBorderName(String borderName) {
		this.borderName = borderName;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
//	public int getComType() {
//		return comType;
//	}
//	public void setComType(int comType) {
//		this.comType = comType;
//	}
	public ListOptionInfo[] getAddedDatas() {
		return addedDatas;
	}
	public void setAddedDatas(ListOptionInfo[] addedDatas) {
		this.addedDatas = addedDatas;
	}
	public HashMap getRowspanMap() {
		return rowspanMap;
	}
	public void setRowspanMap(HashMap rowspanMap) {
		this.rowspanMap = rowspanMap;
	}
	public String getBeanFormMember() {
		return beanFormMember;
	}
	public void setBeanFormMember(String beanFormMember) {
		this.beanFormMember = beanFormMember;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
}
