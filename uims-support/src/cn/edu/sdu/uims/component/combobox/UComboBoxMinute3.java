package cn.edu.sdu.uims.component.combobox;

import java.util.ArrayList;
import java.util.List;

public class UComboBoxMinute3 extends UComboBoxDataList {
	public List<String> getCreatList(){
		List<String> sList = new ArrayList<String>();
		sList.add("00");
		sList.add("10");
		sList.add("20");
		sList.add("30");
		sList.add("40");
		sList.add("50");
		return sList;
	}
}
