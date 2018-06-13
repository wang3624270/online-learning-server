package cn.edu.sdu.uims.filter;

import java.util.List;

import cn.edu.sdu.uims.util.UimsUtils;

public class UComboBoxFilter extends UFilter {
	protected boolean addSelectItem = true;
	protected boolean addAllItem = false;

	public void init(String parameter){
		if(parameter != null && parameter.length() != 0) {
			addSelectItem = false;
			addAllItem = false;
			int index = parameter.indexOf(";");
			if(index < 0) {
				if(parameter.equals("select"))
					addSelectItem = true;
				else
					addAllItem = true;
			}else {
				addSelectItem = true;
				addAllItem = true;
			}
		}
	}
	public void addAddedItem(){
		if(arrayObject == null || arrayObject.length <= 1)
			return;
		int count = 0;
		if(addSelectItem)
			count ++;
		if(addAllItem)
			count ++;
		if(count == 0)
			return;
		Object []p =  arrayObject;
		arrayObject = new Object[p.length+count];
		count=0;
		if(addSelectItem){
			arrayObject[count++] = UimsUtils.getPleaseSelectInfo();
		}
		if (addAllItem){
			arrayObject[count++] =UimsUtils.getSelectAllInfo("-2");
		}
		for(int i = 0; i < p.length;i++) {
			arrayObject[count+i]= p[i];
		}
	}

	public void setAddedData(Object[] a) {
		arrayObject = a;
		addAddedItem();
	}

	public void setAddedData(List a) {
		if (a != null) {
			arrayObject = a.toArray();
		} else {
			arrayObject = null;
		}
		addAddedItem();
	}
	
}
