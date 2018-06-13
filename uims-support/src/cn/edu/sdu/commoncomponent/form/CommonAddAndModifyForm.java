package cn.edu.sdu.commoncomponent.form;

import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.form.UPopupIoDataI;
import cn.edu.sdu.uims.form.impl.UTableForm;

public class CommonAddAndModifyForm extends UTableForm implements UPopupIoDataI{
	private List<ListOptionInfo> dataList;

	public List<ListOptionInfo> getDataList() {
		return dataList;
	}

	public void setDataList(List<ListOptionInfo> dataList) {
		this.dataList = dataList;
	}

	@Override
	public Object getIoData() {
		// TODO Auto-generated method stub
		return dataList;
	}

	@Override
	public void setIoData(Object obj) {
		// TODO Auto-generated method stub
		dataList = (List<ListOptionInfo>)obj;
	}
}
