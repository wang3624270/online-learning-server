package cn.edu.sdu.commoncomponent.filter;

import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.filter.UFilter;
import cn.edu.sdu.uims.util.UimsUtils;

public class CommonBaseDataComboBoxFilter extends UFilter{

	public void CommonBaseDataComboBoxFilter(){
		
	}
	public String getCmd(){
		return null;
	}
	public String getPerTypeCode(){
		return null;
	}
	public void addAddedRequestData(RmiRequest request){
	}
	public void init(String parameter) {
		List list = null;
		RmiRequest request = new RmiRequest();
		addAddedRequestData(request);
		request.add("bType", parameter);
		request.add("perTypeCode", getPerTypeCode());
		request.setCmd(getCmd());
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			list= (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		if(list != null)
			arrayObject = list.toArray();
	}
}
