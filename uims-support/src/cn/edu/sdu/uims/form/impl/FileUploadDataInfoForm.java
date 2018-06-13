package cn.edu.sdu.uims.form.impl;

import java.util.HashMap;

import cn.edu.sdu.common.form.UForm;

public class FileUploadDataInfoForm extends UForm {
	private HashMap paraMap;
	private String fileName;
	private byte[] fileData;

	public HashMap getParaMap() {
		return paraMap;
	}

	public void setParaMap(HashMap paraMap) {
		this.paraMap = paraMap;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
}
