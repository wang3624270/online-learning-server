package cn.edu.sdu.uims.form;

import cn.edu.sdu.common.form.UForm;

public class UTestItemForm extends UForm {
	private String dateTime;
	private String dateSection;
	private String roomList;
	
	public String getRoomList() {
		return roomList;
	}
	public void setRoomList(String roomList) {
		this.roomList = roomList;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDateSection() {
		return dateSection;
	}
	public void setDateSection(String dateSection) {
		this.dateSection = dateSection;
	}
}
