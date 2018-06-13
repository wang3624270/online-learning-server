package cn.edu.sdu.uims.component.complex.form;

import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class RoomPlaneDataForm extends UForm{
	private Integer bedNums;
	private Integer perNums;
	private List<RoomBuildingDataForm> buildingList;
	
	public Integer getBedNums() {
		return bedNums;
	}
	public void setBedNums(Integer bedNums) {
		this.bedNums = bedNums;
	}
	public Integer getPerNums() {
		return perNums;
	}
	public void setPerNums(Integer perNums) {
		this.perNums = perNums;
	}
	
	public int getCellRowNum(int column){
		int rowNum = 0;
		if(buildingList == null || buildingList.size() == 0)
			return 0;
		RoomBuildingDataForm f;
		List list;
		int len;
		for(int i = 0; i < buildingList.size();i++) {
			list = buildingList.get(i).getRoomList();
			if(list == null || list.size() == 0)
				continue;
			len = list.size();
			rowNum += len /column ;
			if(len % column != 0)
				rowNum += 1;
		}
		return rowNum;
	}
	public List<RoomBuildingDataForm> getBuildingList() {
		return buildingList;
	}
	public void setBuildingList(List<RoomBuildingDataForm> buildingList) {
		this.buildingList = buildingList;
	}
}
