package org.octopus.reportdog.module;

import com.itextpdf.text.Image;

public class ComplexTextPhraseForRtf {
	boolean isUnderline = false;// 文本块是否有下划线
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

}