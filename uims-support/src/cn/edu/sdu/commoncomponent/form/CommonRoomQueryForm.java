package cn.edu.sdu.commoncomponent.form;

import java.util.HashMap;


public class CommonRoomQueryForm extends CommonQueryForm {
	private Integer buildingId;
	private Integer floorId;
	private String buildingType;
	private String locationCode;
	private String roomName;
	private String roomType;
	private String roomSecondType;
	private String floorListType;
	private String roomStatus;

	private String buildingTypes[];
	private Integer buildingIds[];
	private Integer floorIds[];
	private String roomTypes[];
	private String roomSecondTypes[];
	private String [] roomStatuss;
	
	public String getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public Integer getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomSecondType() {
		return roomSecondType;
	}
	public void setRoomSecondType(String roomSecondType) {
		this.roomSecondType = roomSecondType;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Integer getFloorId() {
		return floorId;
	}
	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}
	public String getFloorListType() {
		return floorListType;
	}
	public void setFloorListType(String floorListType) {
		this.floorListType = floorListType;
	}
	public HashMap<String,String> getSaveHashMapOfAttribute(){
		HashMap<String,String> map = super.getSaveHashMapOfAttribute();
		if(roomType!= null && roomType.length()!=0 && !roomType.equals("-1")) {
			map.put("roomType",roomType);
		}
		if(roomSecondType!= null && roomSecondType.length()!=0 && !roomSecondType.equals("-1")) {
			map.put("roomSecondType",roomSecondType);
		}
		if(buildingId!= null  && !buildingId.equals(-1)) {
			map.put("buildingId",buildingId+"");
		}
		if(floorId!= null  && !floorId.equals(-1)) {
			map.put("floorId",+floorId + "");
		}
		return map;	
	}
	public void  setSaveHashMapOfAttribute(HashMap<String,String> map){
		if(map == null)
			return;
		super.setSaveHashMapOfAttribute(map);
		String str;
		str = map.get("roomType");
		if(str != null) {
			roomType = str;
		}
		str = map.get("roomSecondType");
		if(str != null) {
			roomSecondType = str;
		}
		str = map.get("buildingId");
		if(str != null) {
			buildingId = new Integer(str);
		}
		str = map.get("floorId");
		if(str != null) {
			floorId = new Integer(str);
		}
	}
	public Integer[] getBuildingIds() {
		return buildingIds;
	}
	public void setBuildingIds(Integer[] buildingIds) {
		this.buildingIds = buildingIds;
	}
	public Integer[] getFloorIds() {
		return floorIds;
	}
	public void setFloorIds(Integer[] floorIds) {
		this.floorIds = floorIds;
	}
	public String[] getRoomTypes() {
		return roomTypes;
	}
	public void setRoomTypes(String[] roomTypes) {
		this.roomTypes = roomTypes;
	}
	public String[] getRoomSecondTypes() {
		return roomSecondTypes;
	}
	public void setRoomSecondTypes(String[] roomSecondTypes) {
		this.roomSecondTypes = roomSecondTypes;
	}
	public String getRoomStatus() {
		return roomStatus;
	}
	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}
	public String[] getRoomStatuss() {
		return roomStatuss;
	}
	public void setRoomStatuss(String[] roomStatuss) {
		this.roomStatuss = roomStatuss;
	}
	public String[] getBuildingTypes() {
		return buildingTypes;
	}
	public void setBuildingTypes(String[] buildingTypes) {
		this.buildingTypes = buildingTypes;
	}

}
