package cn.edu.sdu.uims.component.list;

import java.util.ArrayList;

import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.uims.UimsFactory;

public class UListDict extends UList {
	public void initContents() {
		// TODO Auto-generated method stub
		if(elementTemplate == null || elementTemplate.dictionary == null)
			return;
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if(util == null)
			return ;
		ArrayList list;
		list = (ArrayList) util.getComboxListByCode(elementTemplate.dictionary);
		if(list == null)
			this.jList.setListData(new ArrayList().toArray());
		else {
			this.jList.setListData(list.toArray());
		}
		this.updateUI();
		
	}

}
