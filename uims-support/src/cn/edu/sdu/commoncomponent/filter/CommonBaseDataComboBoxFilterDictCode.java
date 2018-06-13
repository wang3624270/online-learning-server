package cn.edu.sdu.commoncomponent.filter;

import org.sdu.rmi.RmiRequest;

public class CommonBaseDataComboBoxFilterDictCode extends
		CommonBaseDataComboBoxFilter {
	public String getDictCode(){
		return "";
	}
	public void addAddedRequestData(RmiRequest request){
		request.add("dictCode",getDictCode());
	}

	public String getCmd(){
		return "commonBaseDataQueryRequestDictCodeList";
	}

}
