package cn.edu.sdu.commoncomponent.cs;

import java.util.HashMap;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.commoncomponent.util.UCommonQueryUtils;
import cn.edu.sdu.uims.component.combobox.UComboBoxKeyFilter;
import cn.edu.sdu.uims.util.UimsUtils;

public class UComboBoxCollegeFilter extends UComboBoxKeyFilter {
	
	private HashMap parameters;
	
	public void initContents() {
		// TODO Auto-generated method stub
		this.setMaximumRowCount(20);
		getInitData();
	}

	public void getInitData(){
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("collegeType", UCommonQueryUtils.getCollegeType(parameters));
		request.add("collegeFilterMark", false);
		request.setCmd("commonBaseDataQueryRequestCollegeList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		} 
		if (list == null ||  list.size() == 0) {
			removeAllItems();
			this.setEnabled(false);
		}
		else {
			if(list.size() == 1) {
				addItem(list.get(0));				
				this.setEnabled(false);
			}else {
				addItem(UimsUtils.getPleaseSelectInfo());
				for (int i = 0; i < list.size(); i++) {
					addItem(list.get(i));
				}
				this.setEnabled(true);
			}
		}
	}
	
	public void setParameters(HashMap map){
		parameters = map;
	}
}
