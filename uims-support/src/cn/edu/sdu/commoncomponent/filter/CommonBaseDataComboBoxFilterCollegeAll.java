package cn.edu.sdu.commoncomponent.filter;

import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.util.UimsUtils;

public class CommonBaseDataComboBoxFilterCollegeAll extends
		CommonBaseDataComboBoxFilter {

	public void init(String parameter) {
		List list = null;
		RmiRequest request = new RmiRequest();
		request.setCmd("commonBaseDataQueryRequestCollegeAllList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			list= (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		if(list != null)
			arrayObject = list.toArray();
	}

}
