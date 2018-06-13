package cn.edu.sdu.uims.attribute;

import java.io.Serializable;

public class UFileDataAttribute implements Serializable {
	private int id;
	private String path;
	private String fileName;
	private String title;
	private byte[]data;

	public byte[] getData() {
		return data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String toString(){
		if(title != null)
			return title;
		else
			return fileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
