package cn.edu.sdu.uims.component.combobox;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.common.util.DateTimeTool;

public class UComboBoxYear extends UComboBoxDataList {
	public List<String> getCreatList(){
		List list = new ArrayList();
		int currentYear = DateTimeTool.getYear();
		for (int year = currentYear+1; year >= 2005; year--) {
			list.add("" + year);
		}
		return list;
	}
}
