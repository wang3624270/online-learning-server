package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UFileUploadTemplate extends UElementTemplate {
	public String beanName;
	public String typeLabel;
	public String fileType;
	public boolean singleFile;
	public boolean needProcess = false;
	public int setpLength = 1000000;
	public void getSelfAttribute(Element e1){
		beanName = e1.attributeValue("beanName");
		fileType = e1.attributeValue("fileType");
		typeLabel = e1.attributeValue("typeLabel");
		String str = e1.attributeValue("singleFile");
		if(str != null && str.equals("true"))
			singleFile = true;
		else
			singleFile = false;
		str = e1.attributeValue("needProcess");
		if(str != null && str.equals("true"))
			needProcess = true;
		else
			needProcess = false;
		str = e1.attributeValue("setpLength");
		if(str != null )
			setpLength= Integer.parseInt(str);
	}	

}
