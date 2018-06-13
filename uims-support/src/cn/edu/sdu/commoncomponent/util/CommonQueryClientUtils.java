package cn.edu.sdu.commoncomponent.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.comparator.ComparatorListOptionInfoPinyin;
import cn.edu.sdu.uims.util.UimsUtils;

public class CommonQueryClientUtils {
	private List stuTypeCodeList = null;
	private List campusOptionList = null;
	private List facultyOptionList = null;
	private HashMap<String , List> collegeOptionMap = null;
	private List termOptionList = null;
	private List processOptionList = null;
	private List gradeOptionList = null;
	private List nationOptionList = null;
	private List peopleOptionList = null;
	private List provinceOptionList = null;
	private List postgraduateMajorOptionList = null;
	private List undergraduateMajorOptionList = null;
	private List cultivateUnitOptionList = null;

	private static CommonQueryClientUtils instance = null;
	public static final String DEFAULT_BUSINESS_TYPE = "general";

	private CommonQueryClientUtils(){
		collegeOptionMap = new  HashMap<String, List > ();
	}
	public static CommonQueryClientUtils getInstance(){
		if(instance == null) 
			instance = new CommonQueryClientUtils();
		return instance;
	}
	public List requestOptionInfoList(String cmd) {
		RmiRequest request = new RmiRequest();
		request.add("bType", DEFAULT_BUSINESS_TYPE);
		request.setCmd(cmd);
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return new ArrayList();
	}

	public List getStuTypeCodeList(){
		if(stuTypeCodeList == null) {
			RmiRequest request = new RmiRequest();
			request.add("bType", DEFAULT_BUSINESS_TYPE);
			request.add("perTypeCode", "1");
			request.setCmd("commonBaseDataQueryRequestSecondTypeCodeList");
			RmiResponse respond = UimsUtils.requestProcesser(
					request);
			if (respond.getErrorMsg() == null) {
				stuTypeCodeList = (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
			}else 
				stuTypeCodeList= new ArrayList();
		}
		return stuTypeCodeList;
	}
	public List getCampusOptionList(){
		if(campusOptionList == null)
			campusOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestCampusList");
		return campusOptionList;
	}
	public List getFacultyOptionList(){
		if(facultyOptionList == null ) {
			facultyOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestFacultyList");
		}
		return facultyOptionList;
	}
	public List getCollegeOptionList(){
		return getCollegeOptionList("-1");
	}
	public List getCollegeOptionList(String collegeType){
		List list = collegeOptionMap.get(collegeType) ;
		if(list == null) {			
			RmiRequest request = new RmiRequest();
			request.add("bType", DEFAULT_BUSINESS_TYPE);
			request.add("collegeType", collegeType);
			request.setCmd("commonBaseDataQueryRequestCollegeList");
			RmiResponse respond = UimsUtils.requestProcesser(
					request);
			if (respond.getErrorMsg() == null) {
				list =  (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
			}else
				list =  new ArrayList();
			collegeOptionMap.put(collegeType,list);
		}
		return list;
	}
	public List getTermOptionList(){
		if(termOptionList == null)
			termOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestTermList");
		return termOptionList;
	}
	public List getProcessOptionList(){
		if(processOptionList == null)
			processOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestProcessList");
		return processOptionList;
	}
	public List getGradeOptionList(){
		if(gradeOptionList == null)
			gradeOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestGradeList");
		return gradeOptionList;
	}

	public List getNationOptionList(){
		if(nationOptionList == null) {
			RmiRequest request = new RmiRequest();
			request.setCmd("getNationList");
			RmiResponse respond = UimsUtils.requestProcesser(
					request);
			nationOptionList = (List) respond.get(RmiKeyConstants.KEY_ARRAYLIST);
			if(nationOptionList!=null && nationOptionList.size()>0){
				ComparatorListOptionInfoPinyin order = new ComparatorListOptionInfoPinyin(
						"label");
				Collections.sort(nationOptionList, order);
			}
			if(nationOptionList == null)
				nationOptionList = new ArrayList();
		}
		return nationOptionList;
	}
	public List getPeopleOptionList(){
		if(peopleOptionList == null ) {
			peopleOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestPeopleCodeList");
		}
		return peopleOptionList;
	}

	public List getMajorList(Integer collegeId, String secondType) {
		RmiRequest request = new RmiRequest();
		request.add("collegeId", collegeId);
		request.add("secondType", secondType);
		request.setCmd("commonBaseDataQueryRequestMajorList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	public List getProvinceOptionList() {
		if (provinceOptionList == null) {
		RmiRequest request = new RmiRequest();
		request.setCmd("commonBaseDataQueryRequestProvinceList");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			// 获取响应数据
			provinceOptionList = (List) response.get(RmiKeyConstants.KEY_FORMLIST);
		}
		if(provinceOptionList == null)
			provinceOptionList = new ArrayList();
		}
		return provinceOptionList;
	}
	
	public List getCityOptionList(String provinceName) {
		RmiRequest request = new RmiRequest();
		request.add("provinceName",provinceName);
		request.setCmd("commonBaseDataQueryRequestCityListByProvince");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			return  (List) response.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	
	public List getTownOptionList(String provinceName, String cityName) {
		RmiRequest request = new RmiRequest();
		request.add("provinceName",provinceName);
		request.add("cityName", cityName);
		request.setCmd("commonBaseDataQueryRequestTownListByProvinceAndCity");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			return (List) response.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}

	public List getPostgraduateMajorOptionList(){
		if(postgraduateMajorOptionList == null ) {
			postgraduateMajorOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestPostgraduateMajorOptionList");
		}
		return postgraduateMajorOptionList;
	}
	public List getUndergraduateMajorOptionList(){
		if(undergraduateMajorOptionList == null ) {
			undergraduateMajorOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestUndergraduateMajorOptionList");
		}
		return undergraduateMajorOptionList;
	}
	public List getCultivateUnitOptionList(){
		if(cultivateUnitOptionList == null ) {
			cultivateUnitOptionList = this.requestOptionInfoList("commonBaseDataQueryRequestCultivateUnitOptionList");
		}
		return cultivateUnitOptionList;
	}
	public List getSecondCollegeList(Integer collegeId ) {
		if(collegeId == null || collegeId.equals(-1))
			return null;
		List list = null;
		if(collegeId.equals(44)) {
			list = getCollegeOptionList("11");
		}else if(collegeId.equals(54)) {
			list = getCollegeOptionList("12");			
		}else if(collegeId.equals(143)) {
			list = getCollegeOptionList("21");
		}
		return list;
	}
		public List getMajorList(String stuTypeCode, Integer collegeId, Integer collegeId1, Integer collegeId2) {
//			if(collegeId2 != null)
//				collegeId = collegeId2;
//			else 
			if(collegeId1 != null)
				collegeId = collegeId1;
			return getMajorList(collegeId, stuTypeCode);
		}
		public List getGroupRoleList(){
/*			
			ListOptionInfo info;
			List list = new ArrayList();
			list.add(new ListOptionInfo("13","全日制硕士"));
			list.add(new ListOptionInfo("15","全日制博士"));
			list.add(new ListOptionInfo("109","qrzss"));
			list.add(new ListOptionInfo("110","qrzbs"));
			list.add(new ListOptionInfo("127","本科生"));
			*/
			RmiRequest request = new RmiRequest();
			request.setCmd("commonBaseDataQueryRequestGroupRoleList");
			RmiResponse response = UimsUtils.requestProcesser(request);
			if (response.getErrorMsg() == null) {
				return (List) response.get(RmiKeyConstants.KEY_FORMLIST);
			}
			return null;
		}
		
	public List getBuildingOptionList(String campusNum, Integer collegeId, Integer collegeId1) {
			if (campusNum == null || campusNum.length() == 0
					|| campusNum.equals("-1"))
				return null;
			RmiRequest request = new RmiRequest();
			request.add("campusNum", campusNum);
			request.add("collegeId", collegeId);
			request.add("collegeId1", collegeId1);
			request.setCmd("coursenewGetBuildingInfoByCampusCollege");
			RmiResponse respond = UimsUtils.requestProcesser(
					request);
			List list = null;
			if (respond.getErrorMsg() == null) {
				list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
			}
			return list;
		}


	public void refreshBufferDataClient(String refreshCmd){	
	}
	public void refreshBufferData(String refreshCmd) {
		boolean b1 = refreshBufferData("gradms", refreshCmd);
		boolean b2 = refreshBufferData("gradmsweb", refreshCmd);
		refreshBufferDataClient(refreshCmd);
		if(b1 && b2)
			UimsUtils.messageBoxInfo("刷新成功！");
	}

	public boolean  refreshBufferData(String appName, String refreshCmd) {
		RmiRequest request = new RmiRequest();
		request.add("refreshCmd", refreshCmd);
		request.setCmd("commonDateQueryRequestRefreshBufferData");
		RmiResponse respond = UimsUtils.requestProcesser(appName,
				request);
		if(respond == null) {
			UimsUtils.messageBoxInfo(appName+"服务器链接失败！");
			return false;
		}else {
			if (respond.getErrorMsg() != null) {
				UimsUtils.messageBoxInfo(respond.getErrorMsg());
				return false;
			}
		}
		return true;
	}
}
