package cn.edu.sdu.uims.component.combobox;

import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.uims.UimsFactory;

public class UComboBoxDict extends UComboBoxType {
	public void setAddedDatas(String dictionary) {
		removeAllItems();
		if(dictionary == null)
			return ;
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if(util == null)
			return ;
		List list;
		list =  util.getComboxListByCode(dictionary);
		if(list == null)
			return ;
//		if(elementTemplate.addSelectItem) {
//			addItem(UiBaseUtils.getPleaseSelectInfo());
//		}
		for (int i = 0; i < list.size(); i++) {
			addItem(list.get(i));
		}
		if(elementTemplate.addEmptyItem) {
			addItem(new ListOptionInfo("", ""));
		}
	}
}
