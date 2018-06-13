package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UTimeInputTemplate extends UElementTemplate {
	public int hStart=0, hEnd=23, hStep=1;
	public int mStart=0, mEnd=59, mStep=1;
	public String hListStr, mListStr;
	public void getSelfAttribute(Element e1){
		String str;
		str = e1.attributeValue("hStart");
		if(str != null && str.length() > 0)
			hStart = Integer.parseInt(str);
		else
			hStart = 0;
		str = e1.attributeValue("hEnd");
		if(str != null && str.length() > 0)
			hEnd = Integer.parseInt(str);
		else
			hEnd = 23;
		str = e1.attributeValue("hStep");
		if(str != null && str.length() > 0)
			hStep = Integer.parseInt(str);
		else
			hStep = 1;
		str = e1.attributeValue("mStart");
		if(str != null && str.length() > 0)
			mStart = Integer.parseInt(str);
		else
			mStart = 0;
		str = e1.attributeValue("mEnd");
		if(str != null && str.length() > 0)
			mEnd = Integer.parseInt(str);
		else
			mEnd = 59;
		str = e1.attributeValue("mStep");
		if(str != null && str.length() > 0)
			mStep = Integer.parseInt(str);
		else
			mStep = 1;
		hListStr = e1.attributeValue("hListStr");
		mListStr = e1.attributeValue("mListStr");
	}
}
