package cn.edu.sdu.commoncomponent.cs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.commoncomponent.form.CommonBaseDataQueryForm;
import cn.edu.sdu.commoncomponent.form.CommonRegionQueryForm;
import cn.edu.sdu.commoncomponent.util.UCommonQueryUtils;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.combobox.UComboBoxI;
import cn.edu.sdu.uims.component.complex.UComplexPanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.util.UimsUtils;

public class UCommonRegionQueryPanel extends UComplexPanel {
	private CommonRegionQueryForm dataForm = null;

	private UComboBoxI provinceComboBox;
	private UComboBoxI cityComboBox;
	private UComboBoxI townComboBox;
	private JTextField addrTexField;

	private HashMap parameters;

	public void setParameters(HashMap map) {
		parameters = map;
	}
	
	private boolean actionEventCanSend = false;

	@Override
	public void initContents() {
		super.initContents();
		initDataForm();
		initContentComponent();
		initContentData();
	}

	public void initDataForm() {
		dataForm = new CommonRegionQueryForm();
	}

	// TODO Auto-generated method stub
	public void initContentComponent() {
		int h = 25;
		String label;
		this.setLayout(new FlowLayout());
		label = elementTemplate.text;
		if (label != null) {
			add(new JLabel(label));
		}
		provinceComboBox = new UComboBox();
		provinceComboBox.setMaximumRowCount(10);
		provinceComboBox.setPreferredSize(new Dimension(80, h));
		provinceComboBox.addActionListener(this);
		add(provinceComboBox.getAWTComponent());
		cityComboBox = new UComboBox();
		cityComboBox.setMaximumRowCount(10);
		cityComboBox.setPreferredSize(new Dimension(80, h));
		cityComboBox.addActionListener(this);
		add(cityComboBox.getAWTComponent());
		townComboBox = new UComboBox();
		townComboBox.setMaximumRowCount(10);
		townComboBox.setPreferredSize(new Dimension(80, h));
		townComboBox.addActionListener(this);
		add(townComboBox.getAWTComponent());
		if(UCommonQueryUtils.getAddrIsVisible(parameters)) {
			addrTexField = new JTextField();
			addrTexField.setColumns(12);
			addrTexField.setPreferredSize(new Dimension(100, h));
			addrTexField.addActionListener(this);
			add(addrTexField);
		}
	}

	public void initContentData() {
		provinceComboBox.setAddedDatas(getProvinceList());
		cityComboBox.setAddedDatas(getCityList());
		townComboBox.setAddedDatas(getTownList());
	}

	public List getProvinceList() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.setCmd("commonRegionQueryRequestProvinceList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	public List getCityList() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("provinceName", dataForm.getProvince());
		request.setCmd("commonRegionQueryRequestCityList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	public List getTownList() {
		RmiRequest request = new RmiRequest();
		request.add("provinceName", dataForm.getProvince());
		request.add("cityName", dataForm.getCity());
		request.setCmd("commonRegionQueryRequestTownList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventCanSend = true;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String cmd = e.getActionCommand();
		if (e.getSource() == provinceComboBox) {
			provinceChanged();
		} else if (e.getSource() == cityComboBox) {
			cityChanged();
		}
		if (actionEventCanSend && this.getUParent() != null && cmd != null) {
			ActionEvent e1 = new ActionEvent(this, e.getID(), cmd);
			this.getUParent().getEventAdaptor().actionPerformed(e1);
		}
	}

	public String getSelectItemOfComboBox(UComboBoxI box) {
		if (box == null)
			return null;
		ListOptionInfo info = (ListOptionInfo) box.getSelectedItem();
		if (info == null)
			return null;
		else
			return info.getValue();
	}

	public void setSelectItemOfComboBox(UComboBoxI box, Object str) {
		if (box == null || str == null)
			return;
		int count = box.getItemCount();
		if (count == 0)
			return;
		ListOptionInfo info;
		for (int i = 0; i < count; i++) {
			info = (ListOptionInfo) box.getItemAt(i);
			if (info.getValue().equals(str.toString())) {
				box.setSelectedIndex(i);
				return;
			}
		}
	}


	public String getProvinceByComboBox() {
		return getSelectItemOfComboBox(provinceComboBox);
	}

	public void setProvinceToComboBox(String str) {
		setSelectItemOfComboBox(provinceComboBox, str);
	}

	public String getCityByComboBox() {
		return getSelectItemOfComboBox(cityComboBox);
	}

	public void setCityToComboBox(String str) {
		setSelectItemOfComboBox(cityComboBox, str);
	}

	public String getTownByComboBox() {
		return getSelectItemOfComboBox(townComboBox);
	}

	public void setTownToComboBox(String str) {
		setSelectItemOfComboBox(townComboBox, str);
	}

	public String getAddrByTextField() {
		if(addrTexField == null)
			return null;
		return addrTexField.getText();
	}

	public void setAddrToTextField(String str) {
		if(addrTexField == null)
			return ;
		if(str == null)
			addrTexField.setText("");
		else
			addrTexField.setText(str);
	}
	public void componentToForm() {
		dataForm.setProvince(this.getProvinceByComboBox());
		dataForm.setCity(this.getCityByComboBox());
		dataForm.setTown(this.getTownByComboBox());
		dataForm.setAddr(this.getAddrByTextField());
	}

	public void formToComponent() {
		setProvinceToComboBox(dataForm.getProvince());
		setCityToComboBox(dataForm.getCity());
		setTownToComboBox(dataForm.getTown());
		setAddrToTextField(dataForm.getAddr());
	}

	public void updataCityComboxBoxListData() {
		cityComboBox.setAddedDatas(getCityList());
	}

	public void updataTownComboxBoxListData() {
		townComboBox.setAddedDatas(getTownList());
	}

	public void provinceChanged() {
		dataForm.setProvince(this.getProvinceByComboBox());
		updataCityComboxBoxListData();
	}

	public void cityChanged() {
		dataForm.setCity(getCityByComboBox());
		updataTownComboxBoxListData();
	}

	public void setData(Object obj) {
		if (obj != null && obj instanceof CommonBaseDataQueryForm) {
			formToComponent();
		}
	}

	public Object getData() {
		componentToForm();
		return dataForm;
	}

}
