package cn.edu.sdu.commoncomponent.cs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.commoncomponent.form.CommonQueryForm;
import cn.edu.sdu.commoncomponent.form.CommonRoomQueryForm;
import cn.edu.sdu.commoncomponent.util.UCommonQueryUtils;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.combobox.UComboBoxI;
import cn.edu.sdu.uims.component.textfield.UNumberFieldWithAddMinus;
import cn.edu.sdu.uims.util.UimsUtils;

public class UCommonRoomQueryPanel extends UQueryComplexPanel {

	public static String GFLXM = "GFLXM";
	public static String GFZTLXM = "GFZTLXM";
	private UComboBoxI buildingTypeComboBox;
	private UComboBoxI buildingIdComboBox;
	private UComboBoxI floorIdComboBox;
	private UComboBoxI roomTypeComboBox;
	private UComboBoxI roomSecondTypeComboBox;
	private UComboBoxI roomStatusComboBox;
	private JTextField locationCodeTextField;
	private JTextField roomNameTextField;
	private UNumberFieldWithAddMinus leftCapacityTextField;

	public CommonQueryForm getDefautDataForm() {
		return new CommonRoomQueryForm();
	}

	public void setDefaultFormData(){
		super.setDefaultFormData();
		CommonRoomQueryForm f = getRoomForm();
		UCommonQueryUtils.setDefaultRoomFormData(parameters, f);

	}

	public CommonRoomQueryForm getRoomForm() {
		return (CommonRoomQueryForm) dataForm;
	}

	public boolean addSelfComponent(String comName, int h) {
		String label;
		JLabel l = null;
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		if (comName.equals("buildingType")) {
			label = UCommonQueryUtils.getBuildingTypeLabel(b, parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			buildingTypeComboBox = getComboBox("buildingType",getBuildingTypeList(),30,120,h);
			addComponentToContainer(this, l,buildingTypeComboBox.getAWTComponent());
			return true;
		}else if (comName.equals("building")) {
			label = UCommonQueryUtils.getBuildingLabel(b, parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			buildingIdComboBox = getComboBox("building",getBuildingIdList(),30,120,h);
			addComponentToContainer(this, l,buildingIdComboBox.getAWTComponent());
			return true;
		}else	if (comName.equals("floor")) {
			label = UCommonQueryUtils.getFloorLabel(b, parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			floorIdComboBox = getComboBox("floor",getFloorIdList(),20,80,h);
			addComponentToContainer(this, l,floorIdComboBox.getAWTComponent());
			return true;
		}else if (comName.equals("roomType")) {
			label = UCommonQueryUtils.getRoomTypeLabel(b,parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			roomTypeComboBox = getComboBox("roomType",getRoomTypeList(),20,80,h);
			addComponentToContainer(this, l,roomTypeComboBox.getAWTComponent());
			return true;
		}else if (comName.equals("roomSecondType")) {
			label = UCommonQueryUtils.getRoomSecondTypeLabel(b,parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			roomSecondTypeComboBox = getComboBox("roomSecondType",getRoomSecondTypeList(),20,80,h);
			addComponentToContainer(this, l,roomSecondTypeComboBox.getAWTComponent());
			return true;
		}else if (comName.equals("roomStatus")) {
			label = UCommonQueryUtils.getRoomStatusLabel(b, parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			roomStatusComboBox = getComboBox("roomStatus",getRoomStatusList(),30,120,h);
			addComponentToContainer(this, l,roomStatusComboBox.getAWTComponent());
			return true;
		} else if (comName.equals("roomName")) {
			label = UCommonQueryUtils.getRoomNameLabel(b,parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			roomNameTextField = new JTextField();
			roomNameTextField.setColumns(8);
			roomNameTextField.setPreferredSize(new Dimension(60, h));
			roomNameTextField.addActionListener(this);
			addComponentToContainer(this, l,roomNameTextField);
			setComStatusAttribute(roomNameTextField,"roomName");
			return true;
		}else if (comName.equals("locationCode")) {
			label = UCommonQueryUtils.getlocationCodeLabel(b,parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			locationCodeTextField = new JTextField();
			locationCodeTextField.setColumns(10);
			locationCodeTextField.setPreferredSize(new Dimension(100, h));
			locationCodeTextField.addActionListener(this);
			addComponentToContainer(this, l,locationCodeTextField);
			setComStatusAttribute(locationCodeTextField,"locationCode");
			return true;
		}else if (comName.equals("leftCapacity")) {
			label = UCommonQueryUtils.getLeftCapacityLabel(b,parameters);
			if (label != null) {
				l = new JLabel(label);
			}
			leftCapacityTextField = new UNumberFieldWithAddMinus();
			leftCapacityTextField.setPreferredSize(new Dimension(100, h));
			addComponentToContainer(this, l,leftCapacityTextField);
			setComStatusAttribute(leftCapacityTextField.getAWTComponent(),"leftCapacity");
			return true;
		}
		return false;
	}

	// TODO Auto-generated method stub
	public boolean processComponentChange(ActionEvent e) {
		if (e.getSource() == campusComboBox) {
			campusChanged();
			return true;
		}
		
		if (e.getSource() == buildingTypeComboBox) {
			buildingTypeChanged();
			return true;
		}	
		if (e.getSource() == buildingIdComboBox) {
			buildingIdChanged();
			return true;
		}	
		if (e.getSource() == floorIdComboBox) {
			floorIdChanged();
			return true;
		}	
		if (e.getSource() == roomTypeComboBox) {
			roomTypeChanged();
			return true;
		}	
		if (e.getSource() == roomSecondTypeComboBox) {
			roomSecondTypeChanged();
			return true;
		}
		if (e.getSource() == roomStatusComboBox) {
			roomStatusChanged();
			return true;
		}
		return false;
	}
	public void formToComponent() {
		super.formToComponent();
		CommonRoomQueryForm dForm = getRoomForm(); 
		setBuildingTypeToComboBox(dForm.getBuildingType());
		setBuildingIdToComboBox(dForm.getBuildingId());
		setFloorIdToComboBox(dForm.getFloorId());
		setRoomTypeToComboBox(dForm.getRoomType());
		setRoomSecondTypeToComboBox(dForm.getRoomSecondType());
		setRoomStatusToComboBox(dForm.getRoomStatus());
		setBuildingTypesToComboBox(dForm.getBuildingTypes());
		setBuildingIdsToComboBox(dForm.getBuildingIds());
		setFloorIdsToComboBox(dForm.getFloorIds());
		setRoomTypesToComboBox(dForm.getRoomTypes());
		setRoomSecondTypesToComboBox(dForm.getRoomSecondTypes());
		setRoomStatussToComboBox(dForm.getRoomStatuss());
		setRoomNameToTextField(dForm.getRoomName());
		setLocationCodeToTextField(dForm.getLocationCode());
	}
	public void componentToForm() {
		super.componentToForm();
		CommonRoomQueryForm dForm = getRoomForm(); 
		dForm.setBuildingType(this.getBuildingTypeByComboBox());
		dForm.setBuildingId(this.getBuildingIdByComboBox());
		dForm.setFloorId(this.getFloorIdByComboBox());
		dForm.setRoomType(this.getRoomTypeByComboBox());
		dForm.setRoomSecondType(this.getRoomSecondTypeByComboBox());
		dForm.setRoomStatus(this.getRoomStatusByComboBox());
		dForm.setBuildingTypes(this.getBuildingTypesByComboBox());
		dForm.setBuildingIds(this.getBuildingIdsByComboBox());
		dForm.setFloorIds(this.getFloorIdsByComboBox());
		dForm.setRoomTypes(this.getRoomTypesByComboBox());
		dForm.setRoomSecondTypes(this.getRoomSecondTypesByComboBox());
		dForm.setRoomStatuss(this.getRoomStatussByComboBox());

		dForm.setRoomName(this.getRoomNameByTextField());
		dForm.setLocationCode(this.getLocationCodeByTextField()); 
	}
	
	public void setLocationCodeToTextField(String str) {
		if (locationCodeTextField == null)
			return;
		if (str == null)
			locationCodeTextField.setText("");
		else
			locationCodeTextField.setText(str);
	}

	public String getLocationCodeByTextField() {
		if (locationCodeTextField == null)
			return null;
		return locationCodeTextField.getText();
	}
	
	public void setRoomNameToTextField(String str) {
		if (roomNameTextField == null)
			return;
		if (str == null)
			roomNameTextField.setText("");
		else
			roomNameTextField.setText(str);
	}

	public String getRoomNameByTextField() {
		if (roomNameTextField == null)
			return null;
		return roomNameTextField.getText();
	}

	public List getBuildingTypeList() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("campusCode", dataForm.getCampusNum());
		request.add("buildingType",
				null);
		request.add("systemMark", UCommonQueryUtils.getSystemMark(parameters));
		request.setCmd("commonRoomQueryRequestBuildingIdList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}
	
	public List getBuildingIdList() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("campusCode", dataForm.getCampusNum());
		request.add("buildingType",
				null);
		request.add("systemMark", UCommonQueryUtils.getSystemMark(parameters));
		request.setCmd("commonRoomQueryRequestBuildingIdList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}
	public List getFloorIdList() {
		CommonRoomQueryForm rForm = (CommonRoomQueryForm)dataForm;
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("buildingId", rForm.getBuildingId());
		request.add("floorListType", rForm.getFloorListType());
		request.setCmd("commonRoomQueryRequestFloorIdList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	public List getRoomTypeList() {
		return getDictionaryTypeCodeList(GFLXM);
	}
	public List getRoomSecondTypeList() {
		String code = getRoomForm().getRoomType();
		if(code == null || code.equals("-1")) 
			return null;
		return getDictionaryTypeCodeList(GFLXM,code);
	}

	public List getRoomStatusList() {
		return getDictionaryTypeCodeList(GFZTLXM);
	}
	public List getStatusList() {
		return getDictionaryTypeCodeList(GFZTLXM);
	}
	
	public String getBuildingTypeByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(buildingTypeComboBox);
	}

	public void setBuildingTypeToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(buildingTypeComboBox, str);
	}

	public String[] getBuildingTypesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(buildingTypeComboBox);
	}

	public void setBuildingTypesToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(buildingTypeComboBox, str);
	}

	public Integer getBuildingIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils.getSelectItemOfComboBox(buildingIdComboBox));
	}

	public void setBuildingIdToComboBox(Integer str) {
		UCommonQueryUtils.setSelectItemOfComboBox(buildingIdComboBox, str);
	}

	public Integer[] getBuildingIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils.getSelectItemsOfComboBox(buildingIdComboBox));
	}

	public void setBuildingIdsToComboBox(Integer[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(buildingIdComboBox, str);
	}
	
	public Integer getFloorIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils.getSelectItemOfComboBox(floorIdComboBox));
	}
	public void setFloorIdToComboBox(Integer str) {
		UCommonQueryUtils.setSelectItemOfComboBox(floorIdComboBox, str);
	}
	public Integer[] getFloorIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils.getSelectItemsOfComboBox(floorIdComboBox));
	}
	public void setFloorIdsToComboBox(Integer[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(floorIdComboBox, str);
	}

	public String getRoomTypeByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(roomTypeComboBox);
	}
	public void setRoomTypeToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(roomTypeComboBox, str);
	}
	public String[] getRoomTypesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(roomTypeComboBox);
	}
	
	public void setRoomTypesToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(roomTypeComboBox, str);
	}

	public String getRoomSecondTypeByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(roomSecondTypeComboBox);
	}

	public void setRoomSecondTypeToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(roomSecondTypeComboBox, str);
	}
	public String[] getRoomSecondTypesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(roomSecondTypeComboBox);
	}

	public void setRoomSecondTypesToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(roomSecondTypeComboBox, str);
	}

	public String getRoomStatusByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(roomStatusComboBox);
	}

	public void setRoomStatusToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(roomStatusComboBox, str);
	}
	public String[] getRoomStatussByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(roomStatusComboBox);
	}

	public void setRoomStatussToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(roomStatusComboBox, str);
	}
	
	public void updateBuildingTypeComboxBoxListData() {
		buildingTypeComboBox.setAddedDatas(getBuildingTypeList());
	}
	public void updateBuildingIdComboxBoxListData() {
		buildingIdComboBox.setAddedDatas(getBuildingIdList());
	}
	public void updateFloorIdComboxBoxListData() {
		floorIdComboBox.setAddedDatas(getFloorIdList());
	}

	public void updateRoomTypeComboxBoxListData() {
		roomTypeComboBox.setAddedDatas(getRoomTypeList());
	}
	public void updateRoomSecondTypeComboxBoxListData() {
		roomSecondTypeComboBox.setAddedDatas(getRoomSecondTypeList());
	}
	public void updateRoomStatusComboxBoxListData() {
		roomStatusComboBox.setAddedDatas(getRoomStatusList());
	}

	public void campusChanged() {
		super.campusChanged();
		getRoomForm().setCampusNum(getCampusNumByComboBox());
		updateBuildingIdComboxBoxListData();
	}

	public void buildingTypeChanged() {
	}
	public void buildingIdChanged() {
	}
	public void floorIdChanged() {
	}
	
	public void roomTypeChanged(){
		getRoomForm().setRoomType(getRoomTypeByComboBox());
		updateRoomSecondTypeComboxBoxListData();		
	}
	
	public void roomSecondTypeChanged(){		
	}
	public void roomStatusChanged(){		
	}

	public Component getSelfComponent(String comName) {
		String label;
		if (comName.equals("buildingType")) {
			return buildingTypeComboBox.getAWTComponent();
		}else if (comName.equals("building")) {
			return buildingIdComboBox.getAWTComponent();
		}else	if (comName.equals("floor")) {
			return floorIdComboBox.getAWTComponent();
		}else if (comName.equals("roomType")) {
			return roomTypeComboBox.getAWTComponent();
		}else if (comName.equals("roomSecondType")) {
			return roomSecondTypeComboBox.getAWTComponent();
		}else	if (comName.equals("roomStatus")) {
			return roomStatusComboBox.getAWTComponent();
		} else if (comName.equals("roomName")) {
			return roomNameTextField;
		}else if (comName.equals("locationCode")) {
			return locationCodeTextField;
		}else if (comName.equals("leftCapacity")) {
			return leftCapacityTextField;
		}
		return null;
	}

}
