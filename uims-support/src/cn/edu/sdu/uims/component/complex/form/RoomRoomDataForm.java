package cn.edu.sdu.uims.component.complex.form;

import cn.edu.sdu.common.form.UForm;

public class RoomRoomDataForm extends UForm implements Comparable{
	private Integer roomId;
	private String roomName;
	private String bedNums[];
	private Integer leftBedAmount = 0;
	private Integer roomBedAmount = 0;
	private Integer bedNum;
	public Integer getBedNum() {
		return bedNum;
	}
	public void setBedNum(Integer bedNum) {
		this.bedNum = bedNum;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public String[] getBedNums() {
		return bedNums;
	}
	public void setBedNums(String[] bedNums) {
		this.bedNums = bedNums;
	}
	public Integer getLeftBedAmount() {
		return leftBedAmount;
	}
	public void setLeftBedAmount(Integer leftBedAmount) {
		this.leftBedAmount = leftBedAmount;
	}
	public Integer getRoomBedAmount() {
		return roomBedAmount;
	}
	public void setRoomBedAmount(Integer roomBedAmount) {
		this.roomBedAmount = roomBedAmount;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return roomName.compareTo(((RoomRoomDataForm)o).getRoomName());
	}

}
