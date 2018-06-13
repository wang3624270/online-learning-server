package cn.edu.sdu.commoncomponent.form;

import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class BaseCampusIncludeBuildingForm extends UForm {
	private String campusNum;
	private String campusName;
	private String campusAddress;
	private String campusRegion;
	private List buildingList;
	public String getCampusNum() {
		return campusNum;
	}
	public void setCampusNum(String campusNum) {
		this.campusNum = campusNum;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getCampusAddress() {
		return campusAddress;
	}
	public void setCampusAddress(String campusAddress) {
		this.campusAddress = campusAddress;
	}
	public String getCampusRegion() {
		return campusRegion;
	}
	public void setCampusRegion(String campusRegion) {
		this.campusRegion = campusRegion;
	}
	public List getBuildingList() {
		return buildingList;
	}
	public void setBuildingList(List buildingList) {
		this.buildingList = buildingList;
	}
	public String toString(){
		return campusName;
	}
}
