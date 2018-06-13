package org.octopus.common_business.attachment.util;

import java.io.Serializable;

public class FileProcessObject implements Serializable{
	private int no;
	private byte[] data;
	private int pos = 0;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public FileProcessObject() {
		this(0);
	}
	public FileProcessObject( int no) {
		super();
		this.no = no;
	}
	public String getKey() {
		return ""+ no;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

}
