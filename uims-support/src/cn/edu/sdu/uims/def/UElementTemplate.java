package cn.edu.sdu.uims.def;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.flex.FHashMap;
import cn.edu.sdu.uims.util.UimsUtils;

public class UElementTemplate extends UTemplate {
	public int type = 0;
	public String typeString;
	public String xStr = null, yStr = null, wStr = null, hStr = null;
	public int x = 0, y = 0, w = -1, h = -1;
	public String templateName = null;
	public String templateMaker = null;
	public String text, value, enLabel;
	public String borderName = null;
	public int horizontalAlignment = UConstants.ALIGNMENT_LEFT;
	public int verticalAlignment = UConstants.ALIGNMENT_CENTER;
	public String fontName = null;
	public String dataFormMember = null;
	public String mapKey;
	public String colorName = null;
	public String backgroundName = null;
	public int arrangeType = UConstants.TEXTARRANGE_HORIZONTAL;
	public List addedDatas;
	public List event;
	public String menu;
	public int layout = UConstants.ALIGNMENT_CENTER;
	public String filter = null, filter1 = null;
	public String filterParameter = null, filterParameter1 = null;
	public boolean editable, visible = true, enable = true;
	public FHashMap parameters;
	public String uiStyleName = null;
	public int left,right,top, bottom;
	public String dictionary;
	public String beanName;
	public int returnPanel ; /* 0 --不返还 1--正确返回  -1 取消返回 */
	public boolean addSelectItem = false;
	public boolean addAllItem = false;
	public boolean addEmptyItem = false;
	public boolean setFirstItem = true;
	public int gridx=0, gridy=0, gridwidth=1, gridheight=1;
	public double weightx = 1.0, weighty =1.0;
	public int fill = UConstants.GRID_BAG_FILL_NONE;
	public int anchor = UConstants.GRID_BAG_ANCHOR_CENTER;
	public String validatorClassName =null;
	public String validatorName =null;
	public String validatorMsg = null;
	public String validatorParas = null;
	public int colNum =1;
	public int rowNum =1;
	public int lineNo = 0;
	public boolean multiple = false;
	public boolean dlgSelect = false;
	public String iconName;
	public String queryName;
	public int groupNo;
	
	public String getTemplateMaker() {
		return templateMaker;
	}

	public void setTemplateMaker(String templateMaker) {
		this.templateMaker = templateMaker;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public String getDictionary() {
		return dictionary;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getxStr() {
		return xStr;
	}

	public void setxStr(String xStr) {
		this.xStr = xStr;
	}

	public String getyStr() {
		return yStr;
	}

	public void setyStr(String yStr) {
		this.yStr = yStr;
	}

	public String getwStr() {
		return wStr;
	}

	public void setwStr(String wStr) {
		this.wStr = wStr;
	}

	public String gethStr() {
		return hStr;
	}

	public void sethStr(String hStr) {
		this.hStr = hStr;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}



	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String getEnLabel() {
		return enLabel;
	}

	public void setEnLabel(String enLabel) {
		this.enLabel = enLabel;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBorderName() {
		return borderName;
	}

	public void setBorderName(String borderName) {
		this.borderName = borderName;
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

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public String getDataFormMember() {
		return dataFormMember;
	}

	public void setDataFormMember(String dataFormMember) {
		this.dataFormMember = dataFormMember;
	}

	public String getMapKey() {
		return mapKey;
	}

	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}


	public int getArrangeType() {
		return arrangeType;
	}

	public void setArrangeType(int arrangeType) {
		this.arrangeType = arrangeType;
	}

	public List getAddedDatas() {
		return addedDatas;
	}

	public void setAddedDatas(List addedDatas) {
		this.addedDatas = addedDatas;
	}

	public List getEvent() {
		return event;
	}

	public void setEvent(List event) {
		this.event = event;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getLayout() {
		return layout;
	}

	public void setLayout(int layout) {
		this.layout = layout;
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public FHashMap getParameters() {
		return parameters;
	}

	public void setParameters(FHashMap parameters) {
		this.parameters = parameters;
	}

	public String getUiStyleName() {
		return uiStyleName;
	}

	public void setUiStyleName(String uiStyleName) {
		this.uiStyleName = uiStyleName;
	}


	public void getSelfAttribute(Element e1) {
	}

	public void getIntValueFromString() {
		if (hStr != null && hStr.length() >0)
			h = Integer.parseInt(hStr);
		if (wStr != null && wStr.length() >0)
			w = Integer.parseInt(wStr);
		if (xStr != null && xStr.length() >0)
			x = Integer.parseInt(xStr);
		if (yStr != null && yStr.length() >0 )
			y = Integer.parseInt(yStr);
	}

	public void getStringValueFromInt() {
		hStr = "" + h;
		wStr = "" + w;
		xStr = "" + x;
		yStr = "" + y;
	}
	public void setExtendAttributeByParas(String paras){
		if(paras == null)
			return;
		HashMap<String,String> map = UimsUtils.stringToHashMap(paras);
		if(map == null)
			return;
		setExtendAttributeByParasMap(map);
	}
	public void setExtendAttributeByParasMap(HashMap<String, String>map){
		String str = map.get("addSelectItem");
		if(str != null && str.equals("true"))
			addSelectItem = true;
		str = map.get("addEmptyItem");
		if(str != null && str.equals("true"))
			addEmptyItem = true;
	}
	
}
