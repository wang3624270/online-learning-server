package cn.edu.sdu.uims.component.combobox;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.common.util.DateTimeTool;

public class UComboBoxRecentYear extends UComboBoxDataList{
	public List<String> getCreatList(){
		List list = new ArrayList();
		int currentYear = DateTimeTool.getYear();
		for (int year = currentYear+1; year >= 2017; year--) {
			list.add("" + year);
		}
		return list;
	}

}
