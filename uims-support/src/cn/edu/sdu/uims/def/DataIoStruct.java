package cn.edu.sdu.uims.def;

import java.io.Serializable;

public class DataIoStruct implements Serializable {
	private String modelName;
	private String label;
	private String ioType;
	private String suffix;
	private String fileName;
	private String paras;
	
	public String getParas() {
		return paras;
	}
	public void setParas(String paras) {
		this.paras = paras;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getIoType() {
		return ioType;
	}
	public void setIoType(String ioType) {
		this.ioType = ioType;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String toString(){
		return label;
	}
}
