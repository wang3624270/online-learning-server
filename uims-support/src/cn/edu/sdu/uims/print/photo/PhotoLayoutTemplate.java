package cn.edu.sdu.uims.print.photo;

import org.dom4j.Element;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UListSectionTemplat;
import cn.edu.sdu.uims.service.UFactory;

public class PhotoLayoutTemplate extends UListSectionTemplat {
	public int colNum = 1, rowNum = 1;
	public int imageWidth  = 10;
	public int imageHeight = 10 ;
	public int titleLayout = UConstants.ALIGNMENT_BOTTOM; 
	public int left = 5;
	public int right = 5;
	public int bottom = 5;
	public int top = 5;
	public int titleHeight = 10;
	public int pageNumHeight =5;
	int getPaperOrientation(){
		return 0;
	}
	public void getAttribute(Element e) {
		String str;
		super.getAttribute(e);
		str = e.attributeValue("layout");
		if (str != null)
			titleLayout = UFactory.getModelSession().getTypeValueByString(str, "alignmentType");		
		colNum = geIntValueFromXml(e, "colNum", colNum);
		rowNum = geIntValueFromXml(e, "rowNum", rowNum);
		imageWidth = geIntValueFromXml(e, "imageWidth", imageWidth);
		imageHeight = geIntValueFromXml(e, "IntervalH", imageHeight);
		left = geIntValueFromXml(e, "left", left);
		right = geIntValueFromXml(e, "right", right);
		right = geIntValueFromXml(e, "right", right);
		bottom = geIntValueFromXml(e, "bottom", bottom);
		top = geIntValueFromXml(e, "top", top);
		titleHeight = geIntValueFromXml(e, "titleHeight", titleHeight);
		pageNumHeight = geIntValueFromXml(e, "pageNumHeight", pageNumHeight);
	}
}
