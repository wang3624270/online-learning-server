package cn.edu.sdu.uims.form.impl;

import java.awt.image.BufferedImage;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.handler.UImageAttachOwnerHanderI;

public class ImageAttachViewForm extends UForm {
	private Integer attachId;
	private BufferedImage imageData;
	private String fileName;
	private String cmd;
	private UImageAttachOwnerHanderI handler;
	private boolean isModify;
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public boolean isModify() {
		return isModify;
	}
	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}
	public UImageAttachOwnerHanderI getHandler() {
		return handler;
	}
	public void setHandler(UImageAttachOwnerHanderI handler) {
		this.handler = handler;
	}
	public BufferedImage getImageData() {
		return imageData;
	}
	public void setImageData(BufferedImage imageData) {
		this.imageData = imageData;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
