package cn.edu.sdu.uims.component.combobox;

import java.util.ArrayList;
import java.util.List;

public class UComboBoxMinute2 extends UComboBoxDataList {
	public List<String> getCreatList(){
		List<String> sList = new ArrayList<String>();
		sList.add("00");
		sList.add("15");
		sList.add("30");
		sList.add("45");
		return sList;
	}
}
