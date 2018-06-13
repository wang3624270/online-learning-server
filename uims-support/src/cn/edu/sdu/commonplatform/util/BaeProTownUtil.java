package cn.edu.sdu.commonplatform.util;

import java.util.ArrayList;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.util.UimsUtils;

public class BaeProTownUtil {
	public static List provinceOptionList = null;
	public static List getProvinceOptionList() {
		if (provinceOptionList == null) {
		RmiRequest request = new RmiRequest();
		request.setCmd("commonBusinessTownRequestProvinceList");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			provinceOptionList = (List) response.get(RmiKeyConstants.KEY_FORMLIST);
		}
		if(provinceOptionList == null)
			provinceOptionList = new ArrayList();
		}
		return provinceOptionList;
	}
	
	public static List getCityOptionList(String provinceName) {
		RmiRequest request = new RmiRequest();
		request.add("provinceName",provinceName);
		request.setCmd("commonBusinessTownRequestCityListByProvince");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			return  (List) response.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	
	public static List getTownOptionList(String provinceName, String cityName) {
		RmiRequest request = new RmiRequest();
		request.add("provinceName",provinceName);
		request.add("cityName", cityName);
		request.setCmd("commonBusinessTownRequestTownListByProvinceAndCity");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			return (List) response.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
}
