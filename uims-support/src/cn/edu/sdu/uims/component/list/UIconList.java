package cn.edu.sdu.uims.component.list;

import java.util.ArrayList;



public class UIconList extends UList{

	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		if (filter == null)
			return;
		Object o = filter.getAddedData();
		if (o == null) {
			this.jList.setListData(new ArrayList().toArray());
			this.jList.setCellRenderer(new CellRenderer());
		} else {
			Object a[] = (Object[]) o;
			this.jList.setListData(a);
			this.jList.setCellRenderer(new CellRenderer());
		}
		this.updateUI();
	}
}
