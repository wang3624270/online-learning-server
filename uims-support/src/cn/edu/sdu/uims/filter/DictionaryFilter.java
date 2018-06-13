package cn.edu.sdu.uims.filter;

import java.util.ArrayList;

import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.uims.UimsFactory;



public class DictionaryFilter extends UFilter implements CompletionFilterI {


	public DictionaryFilter() {
		// this(null);
	}

	public void init(String parameter) {
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if(util == null)
			return ;
		ArrayList list;
		list = (ArrayList) util.getComboxListByCode(parameter);
		arrayObject = list.toArray();
	}

	public ArrayList filter(String text) {
		return null;
	}

}
