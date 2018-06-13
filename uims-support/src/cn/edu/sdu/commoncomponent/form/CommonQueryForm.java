package cn.edu.sdu.commoncomponent.form;

import java.util.Date;
import java.util.HashMap;

import cn.edu.sdu.uims.form.impl.CommonQueryBaseForm;

public class CommonQueryForm extends CommonQueryBaseForm {
	
	/**
	 * 
	 */
	private String areaNum = "01";
	private String campusNum;
	private Integer facultyId;
	private String collegeType;
	private Integer collegeId;
	private Integer collegeId1;
	private Integer collegeId2;
	private String year;
	private String month;
	private String day;
	private String hour;
	private String status;
	private String checkStatus;
	private String bType;
	private Date queryDate;
	private Date startDate;
	private Date endDate;
	private Integer customId;
	private Integer personId;
	private String perNum;
	private String perName;
	private String collegeName;
	private boolean canAll = false;

	private String[] areaNums;
	private String[] campusNums;
	private Integer [] facultyIds;
	private Integer[] collegeIds;
	private Integer[] collegeId1s;
	private Integer[] collegeId2s;
	private String[] years;
	private String [] months;
	private String [] days;
	private String [] hours;
	private String []statuss;
	private Integer []customIds;
	private String []collegeTypes;
	private String [] checkStatuss;
	private String collegeMapKey[];
	private String stuTypeCollegeKey;
	private String stuTypeMajorKey;
	private Integer classId;//班级查询
	private String inputNum0;
	private String inputNum1;
	private Boolean select;
	private Integer processId;
	private Integer processIds [];
	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getAreaNum() {
		return areaNum;
	}

	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}

	public String getCampusNum() {
		return campusNum;
	}
	public void setCampusNum(String campusNum) {
		this.campusNum = campusNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	public String getBType() {
		return bType;
	}
	public void setBType(String bType) {
		this.bType = bType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getbType() {
		return bType;
	}
	public void setbType(String bType) {
		this.bType = bType;
	}

	public Integer getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Integer facultyId) {
		this.facultyId = facultyId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCollegeType() {
		return collegeType;
	}

	public void setCollegeType(String collegeType) {
		this.collegeType = collegeType;
	}

	public Integer getCustomId() {
		return customId;
	}

	public void setCustomId(Integer customId) {
		this.customId = customId;
	}	
	public String getSecondTypeCode(){
		return null;
	}
	public String[] getSecondTypeCodes(){
		return null;
	}
	public HashMap<String,String> getSaveHashMapOfAttribute(){
		HashMap<String,String> map = new HashMap<String,String>();
		if(areaNum!= null && areaNum.length()!=0 && !areaNum.equals("-1")) {
			map.put("areaNum",areaNum);
		}
		if(campusNum!= null && campusNum.length()!=0 && !campusNum.equals("-1")) {
			map.put("campusNum",campusNum);
		}
		if(facultyId!= null  && !facultyId.equals(-1)) {
			map.put("facultyId",facultyId+"");
		}
		if(collegeId!= null  && !collegeId.equals(-1)) {
			map.put("collegeId",+collegeId + "");
		}
		return map;	
	}
	public void  setSaveHashMapOfAttribute(HashMap<String,String> map){
		if(map == null)
			return;
		String str;
		str = map.get("areaNum");
		if(str != null) {
			areaNum = str;
		}
		str = map.get("campusNum");
		if(str != null) {
			campusNum = str;
		}
		str = map.get("facultyId");
		if(str != null) {
			facultyId = new Integer(str);
		}
		str = map.get("collegeId");
		if(str != null) {
			collegeId = new Integer(str);
		}
	}

	public boolean isCanAll() {
		return canAll;
	}

	public void setCanAll(boolean canAll) {
		this.canAll = canAll;
	}

	public String[] getAreaNums() {
		return areaNums;
	}

	public void setAreaNums(String[] areaNums) {
		this.areaNums = areaNums;
	}

	public String[] getCampusNums() {
		return campusNums;
	}

	public void setCampusNums(String[] campusNums) {
		this.campusNums = campusNums;
	}

	public Integer[] getFacultyIds() {
		return facultyIds;
	}

	public void setFacultyIds(Integer[] facultyIds) {
		this.facultyIds = facultyIds;
	}

	public Integer[] getCollegeIds() {
		return collegeIds;
	}

	public void setCollegeIds(Integer[] collegeIds) {
		this.collegeIds = collegeIds;
	}

	public String[] getYears() {
		return years;
	}

	public void setYears(String[] years) {
		this.years = years;
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}

	public String[] getStatuss() {
		return statuss;
	}

	public void setStatuss(String[] statuss) {
		this.statuss = statuss;
	}

	public Integer[] getCustomIds() {
		return customIds;
	}

	public void setCustomIds(Integer[] customIds) {
		this.customIds = customIds;
	}

	public String[] getCollegeMapKey() {
		return collegeMapKey;
	}

	public void setCollegeMapKey(String[] collegeMapKey) {
		this.collegeMapKey = collegeMapKey;
	}

	public String getStuTypeCollegeKey() {
		return stuTypeCollegeKey;
	}

	public void setStuTypeCollegeKey(String stuTypeCollegeKey) {
		this.stuTypeCollegeKey = stuTypeCollegeKey;
	}

	public String getStuTypeMajorKey() {
		return stuTypeMajorKey;
	}

	public void setStuTypeMajorKey(String stuTypeMajorKey) {
		this.stuTypeMajorKey = stuTypeMajorKey;
	}


	public String[] getCollegeTypes() {
		return collegeTypes;
	}

	public void setCollegeTypes(String[] collegeTypes) {
		this.collegeTypes = collegeTypes;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getPerNum() {
		return perNum;
	}

	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String[] getCheckStatuss() {
		return checkStatuss;
	}

	public void setCheckStatuss(String[] checkStatuss) {
		this.checkStatuss = checkStatuss;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

	public String[] getHours() {
		return hours;
	}

	public void setHours(String[] hours) {
		this.hours = hours;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public String getInputNum0() {
		return inputNum0;
	}

	public void setInputNum0(String inputNum0) {
		this.inputNum0 = inputNum0;
	}

	public String getInputNum1() {
		return inputNum1;
	}

	public void setInputNum1(String inputNum1) {
		this.inputNum1 = inputNum1;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer[] getProcessIds() {
		return processIds;
	}

	public void setProcessIds(Integer[] processIds) {
		this.processIds = processIds;
	}

	public Integer getCollegeId1() {
		return collegeId1;
	}

	public void setCollegeId1(Integer collegeId1) {
		this.collegeId1 = collegeId1;
	}

	public Integer getCollegeId2() {
		return collegeId2;
	}

	public void setCollegeId2(Integer collegeId2) {
		this.collegeId2 = collegeId2;
	}

	public Integer[] getCollegeId1s() {
		return collegeId1s;
	}

	public void setCollegeId1s(Integer[] collegeId1s) {
		this.collegeId1s = collegeId1s;
	}

	public Integer[] getCollegeId2s() {
		return collegeId2s;
	}

	public void setCollegeId2s(Integer[] collegeId2s) {
		this.collegeId2s = collegeId2s;
	}
	public  Integer getQueryCollegeId(){
		if(collegeId2 != null && !collegeId2.equals(-1)) {
			return  collegeId2;
		}else if(collegeId1 != null && !collegeId1.equals(-1)) {
			return collegeId1;
		}
		return collegeId;
	}

}
