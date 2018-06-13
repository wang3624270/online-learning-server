package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class UPdfViewDataForm extends UForm{
	private byte[] data;
	private String fileName;
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String toString(){
		return fileName;
	}
}
