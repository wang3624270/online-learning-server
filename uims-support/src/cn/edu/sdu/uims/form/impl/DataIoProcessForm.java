package cn.edu.sdu.uims.form.impl;

import java.util.HashMap;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.def.DataIoStruct;

public class DataIoProcessForm extends UForm {
	private String dispLayInfo;
	private String fileName;
	private HashMap userMap;
	private String comName;
	
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	private DataIoStruct dataIoStruct;

	public HashMap getUserMap() {
		return userMap;
	}
	public void setUserMap(HashMap userMap) {
		this.userMap = userMap;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDispLayInfo() {
		return dispLayInfo;
	}
	public void setDispLayInfo(String dispLayInfo) {
		this.dispLayInfo = dispLayInfo;
	}
	public DataIoStruct getDataIoStruct() {
		return dataIoStruct;
	}
	public void setDataIoStruct(DataIoStruct dataIoStruct) {
		this.dataIoStruct = dataIoStruct;
	}
	
}
