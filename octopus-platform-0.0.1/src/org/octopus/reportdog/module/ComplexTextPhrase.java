package org.octopus.reportdog.module;

import java.util.HashMap;

import com.itextpdf.text.Image;

public class ComplexTextPhrase {
	private boolean isUnderline = false;// 文本块是否有下划线
	//private int underLineMinLength_cjk = -1;
	private HashMap<String,Object> underlineParaMap=new HashMap<String,Object>();

	private boolean isItalic = false;

	String content;// 文本内容

	private Image image = null;

	public boolean getIsUnderline() {
		return this.isUnderline;
	}

	public void setIsUnderline(boolean isUnderline) {
		this.isUnderline = isUnderline;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public boolean isItalic() {
		return isItalic;
	}

	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}

	public HashMap<String,Object> getUnderlineParaMap() {
		return underlineParaMap;
	}

	public void setUnderlineParaMap(HashMap<String,Object> underlineParaMap) {
		this.underlineParaMap = underlineParaMap;
	}

 
}