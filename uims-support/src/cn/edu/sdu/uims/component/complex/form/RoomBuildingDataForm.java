package cn.edu.sdu.uims.component.complex.form;

import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class RoomBuildingDataForm extends UForm{
	private String buildingName;
	private Integer buildingId;
	private String buildingNum;  
	private String buildingCampus;
	private List<RoomRoomDataForm> roomList;
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public List<RoomRoomDataForm> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<RoomRoomDataForm> roomList) {
		this.roomList = roomList;
	}
	public Integer getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
	public String toString(){
		return buildingName;
	}
	public String getBuildingCampus() {
		return buildingCampus;
	}
	public void setBuildingCampus(String buildingCampus) {
		this.buildingCampus = buildingCampus;
	}
}
