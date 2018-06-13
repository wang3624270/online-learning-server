package cn.edu.sdu.uims.component.combobox;

import java.util.ArrayList;
import java.util.List;

public class UComboBoxMinute extends UComboBoxDataList {
	public List<String> getCreatList(){
		List<String> sList = new ArrayList<String>();
		for(int i=0; i< 60;i++) {
			if(i < 10)
				sList.add("0" + i);
			else
				sList.add(""+i);
		}
		return sList;
	}
}
