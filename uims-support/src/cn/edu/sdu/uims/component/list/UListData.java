package cn.edu.sdu.uims.component.list;

import java.util.ArrayList;
import java.util.List;

public class UListData extends UList {

	private List dataList;
	public Object getData() {
		return dataList;
	}

	public void setData(Object obj) {
		if(obj instanceof List) {
			dataList = (List)obj;
			if(dataList == null || dataList.size()==0)
				jList.setListData(new ArrayList().toArray());
			else
				jList.setListData(dataList.toArray());
			this.updateUI();
		}
	}

}
