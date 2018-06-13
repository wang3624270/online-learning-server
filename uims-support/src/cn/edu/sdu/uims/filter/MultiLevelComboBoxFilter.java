package cn.edu.sdu.uims.filter;

import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.util.UimsUtils;

public class MultiLevelComboBoxFilter extends UFilter implements MultiLevelFilterI {
	private String cmd;
	
	public void init(String parameter) {
		// TODO Auto-generated method stub
		cmd = parameter;
	}
	@Override
	public List getAddedDataList(Object[] codes, Integer index) {
		// TODO Auto-generated method stub
		List list = null;
		RmiRequest request = new RmiRequest();
		request.add("codes", codes);
		request.add("index", index);
		request.setCmd(cmd);
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() == null) {
			list= (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

}
