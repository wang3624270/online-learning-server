package cn.edu.sdu.uims.form.impl;

import java.util.HashMap;

import cn.edu.sdu.common.form.UForm;

public class FileUploadProcessForm extends UForm {
	private String dispLayInfo;
	private String dirName;
	private HashMap userMap;
	private String comName;
	private String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public String getDispLayInfo() {
		return dispLayInfo;
	}
	public void setDispLayInfo(String dispLayInfo) {
		this.dispLayInfo = dispLayInfo;
	}
	public HashMap getUserMap() {
		return userMap;
	}
	public void setUserMap(HashMap userMap) {
		this.userMap = userMap;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}

}
