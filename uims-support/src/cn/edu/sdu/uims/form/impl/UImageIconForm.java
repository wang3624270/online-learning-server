package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class UImageIconForm extends UForm {
	private int x, y;
	private String imageId;
	private String imageTitle;
	private String note;
	private Object imageData;
	
	
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
	public Object getImageData() {
		return imageData;
	}
	public void setImageData(Object imageData) {
		this.imageData = imageData;
	}
	public String getFileName(){
		if(imageId == null){
			return null;
		}else {
			int index = imageId.lastIndexOf("-");
			if(index >= 0)
				return imageId.substring(index+1,imageId.length());
			else
				return null;
		}
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
