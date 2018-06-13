package cn.edu.sdu.commoncomponent.cs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.commoncomponent.util.UCommonQueryUtils;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.combobox.UComboBoxI;
import cn.edu.sdu.uims.component.complex.UComplexPanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.util.UimsUtils;

public class UComboBoxCollege extends UComplexPanel {

	private HashMap parameters;
	private String collegeType;
	private Integer collegeId = null;
	protected UComboBoxI collegeTypeComboBox;
	protected UComboBoxI collegeComboBox;
	private boolean actionEventCanSend = false;

	public void setParameters(HashMap map) {
		parameters = map;
	}

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		collegeType = this.getCollegeType(parameters);
		this.setLayout(new FlowLayout());
		if (collegeType == null) {
			collegeTypeComboBox = new UComboBox();
			collegeTypeComboBox.setMaximumRowCount(20);
			collegeTypeComboBox.setPreferredSize(new Dimension(150, 20));
			collegeTypeComboBox.addActionListener(this);
			add(collegeTypeComboBox.getAWTComponent());
			collegeTypeComboBox.setAddedDatas(getCollegeTypeList());
		}
		collegeComboBox = new UComboBox();
		collegeComboBox.setMaximumRowCount(20);
		collegeComboBox.setPreferredSize(new Dimension(150, 20));
		collegeComboBox.addActionListener(this);
		add(collegeComboBox.getAWTComponent());
		if (collegeType != null) {
			collegeComboBox.setAddedDatas(getCollegeList());
		}
		setSelectCollegeId();
	}

	public List getCollegeTypeList() {
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if (util == null)
			return null;
		List list = (ArrayList) util.getComboxListByCode("EJDWLXM");
		return list;
	}

	public List getCollegeList() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("collegeType", collegeType);
		request.add("collegeFilterMark", isCollegeFilterMark(parameters));
		request.setCmd("commonBaseDataQueryRequestCollegeList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (e.getSource() == collegeTypeComboBox) {
			collegeType = UCommonQueryUtils
					.getSelectItemOfComboBox(collegeTypeComboBox);
			collegeComboBox.setAddedDatas(getCollegeList());
			this.setSelectCollegeId();
		}
		if (actionEventCanSend && this.getUParent() != null && cmd != null) {
			ActionEvent e1 = new ActionEvent(this, e.getID(), cmd);
			this.getUParent().getEventAdaptor().actionPerformed(e1);
		}
	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventCanSend = true;
			}
		}
	}

	public String getCollegeType(HashMap parameters) {
		if (parameters == null)
			return null;
		if (parameters.get("collegeType") != null) {
			return (String) parameters.get("collegeType");
		} else
			return null;
	}

	public Boolean isCollegeFilterMark(HashMap parameters) {
		if (parameters == null)
			return false;
		if (parameters.get("collegeFilterMark") != null) {
			return new Boolean((String) parameters.get("collegeFilterMark"));
		} else
			return false;
	}

	public Object getData() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(collegeComboBox));
	}

	public void setData(Object o) {
		if (o == null) {
			collegeId = null;
			collegeType = null;
			if (collegeTypeComboBox != null)
				collegeTypeComboBox.setSelectedIndex(-1);
			else
				collegeComboBox.setSelectedIndex(-1);
		} else {
			collegeId = (Integer) o;
			if (collegeTypeComboBox != null) {
				RmiRequest request = new RmiRequest();
				RmiResponse respond = null;
				request.add("collegeId", collegeId);
				request.setCmd("commonBaseDataQueryRequestCollegeTypeById");
				respond = UimsUtils.requestProcesser(request);
				if (respond.getErrorMsg() != null)
					return;
				collegeType = (String) respond.get("collegeType");
				this.setSelectCollegeType();
			} else {
				this.setSelectCollegeId();
			}
		}
	}

	public void setSelectCollegeType() {
		if (collegeType == null) {
			collegeTypeComboBox.setSelectedIndex(-1);
			return;
		}
		int count = collegeTypeComboBox.getItemCount();
		if (count > 0) {
			ListOptionInfo info;
			for (int i = 0; i < count; i++) {
				info = (ListOptionInfo) collegeTypeComboBox.getItemAt(i);
				if (info.getValue().equals(collegeType)) {
					collegeTypeComboBox.setSelectedIndex(i);
					return;
				}
			}
		}
		collegeTypeComboBox.setSelectedIndex(-1);
	}

	public void setSelectCollegeId() {
		if (collegeComboBox == null)
			return;
		if (collegeId == null) {
			collegeComboBox.setSelectedIndex(-1);
			return;
		}
		String collegeIdStr = collegeId.toString();
		int count = collegeComboBox.getItemCount();
		if (count > 0) {
			ListOptionInfo info;
			for (int i = 0; i < count; i++) {
				info = (ListOptionInfo) collegeComboBox.getItemAt(i);
				if (info.getValue().equals(collegeIdStr)) {
					collegeComboBox.setSelectedIndex(i);
					return;
				}
			}
		}
		collegeId = null;
		collegeComboBox.setSelectedIndex(-1);
	}
}
