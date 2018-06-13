package cn.edu.sdu.commoncomponent.form;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.form.UPopupIoDataI;
import cn.edu.sdu.uims.form.impl.UTableForm;

public class CommonModifyForm extends UTableForm implements UPopupIoDataI{
	private ListOptionInfo data;

	public ListOptionInfo getData() {
		return data;
	}

	public void setData(ListOptionInfo data) {
		this.data = data;
	}

	@Override
	public Object getIoData() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public void setIoData(Object obj) {
		// TODO Auto-generated method stub
		data = (ListOptionInfo)obj;
	}

}
