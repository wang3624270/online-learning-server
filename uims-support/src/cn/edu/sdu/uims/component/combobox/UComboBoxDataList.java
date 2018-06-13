package cn.edu.sdu.uims.component.combobox;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.base.CheckObject;

public class UComboBoxDataList extends UComboBoxType {
	public List<String> getCreatList(){
		return null;
	}
	public List getCompnetDefautAddedDatasList() {
		List list = new ArrayList();
		List<String> sList = getCreatList();
		list.add(new ListOptionInfo("-1","请选择.."));
		for (int i = 0; i< sList.size();i++) {
			list.add(new ListOptionInfo(sList.get(i), sList.get(i)));
		}
		return list;
	}

	public void setAddedDatas(Object[] o) {
		if (elementTemplate.multiple) {
			removeAllItems();
			if (o != null) {
				ListOptionInfo info;
				for (int i = 0; i < o.length; i++) {
					info = (ListOptionInfo) o[i];
					addItem(new CheckObject(false, info));
				}
			}
			List<String> sList = getCreatList();
			for (int i = 0; i< sList.size();i++) {
				addItem(new CheckObject(false, new ListOptionInfo(sList.get(i),sList.get(i))));
			}
		} else {
			removeAllItems();
			int i;
			if (o != null) {
				Object a[] = (Object[]) o;
				for (i = 0; i < a.length; i++) {
					addItem(a[i]);
				}
			}
			List<String> sList = getCreatList();
			for (i = 0; i< sList.size();i++) {
				addItem(new ListOptionInfo(sList.get(i), sList.get(i)));
			}
		}
	}

}
