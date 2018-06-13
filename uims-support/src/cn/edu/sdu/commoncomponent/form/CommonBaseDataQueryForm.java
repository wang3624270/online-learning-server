package cn.edu.sdu.commoncomponent.form;

import java.util.HashMap;
import java.util.List;

public class CommonBaseDataQueryForm extends CommonQueryForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5366061168983082374l;
	private String topicTitle;
	private String secondTypeCode;
	private String secondTypeLevel1;
	private String secondTypeLevel2;
	private String secondTypeLevel3;
	private String processType;
	private Integer majorId;
	private String grade;
	private String grade1;
	private Integer classId;
	private Integer termId;
	private String perIdCard;
	private String mobilePhone;
	private Integer groupId;
	private String sqlReslut;
	private String perTypeCode = "1";
	private String perEngName;
	private String perEnglishGivenName;
	private String perEnglishFamilyName;
	private String dataSource;
	private String courseNum;
	private String courseName;
	private String courseNo;
	private String teachTaskType;
	private String majorType = "1";
	private String statusType;
	private String statusString;
	private String loanStatus;
	private Integer yearNum;
	private String loanStatusName;
	private String majorName;
	private String majorNum;
	private String examNo;
	private Integer studyState = 2;
	private String processState;
	private String reviewMode;
	private Integer leaveCause;
	private String scholarType;
	private String studyStateSql;
	private String stuTypeCodeSql;

	public Integer[] processStates;
	public String[] reviewModes;
	public Integer[] studyStates;
	public Integer[] leaveCauses;

	private String[] secondTypeCodes;
	private String[] secondTypeLevel1s;
	private String[] secondTypeLevel2s;
	private String[] secondTypeLevel3s;
	private Integer[] majorIds;
	private String[] grades;
	private String[] grade1s;
	private Integer[] classIds;
	private Integer[] termIds;
	private Integer[] groupIds;
	private String[] dataSources;
	private String[] loanStatuss;
	private String[] loanStatusNames;
	private String[] courseNos;
	private String[] teachTaskTypes;
	private String[] scholarTypes;
	private Integer tutorId;
	private boolean isFilterGrade = false;
	private List collegeList;
	private List gradeList;
	private List majorList;
	private List stuTypeCodeList;
	private List studyStateList;
	private String controlState;
	private Integer isAppeal;
	private Integer checkIds[];
	private String graDegreeDate;
	private String[] graDegreeDates;

	public Integer getIsAppeal() {
		return isAppeal;
	}

	public void setIsAppeal(Integer isAppeal) {
		this.isAppeal = isAppeal;
	}

	public boolean isFilterGrade() {
		return isFilterGrade;
	}

	public void setFilterGrade(boolean isFilterGrade) {
		this.isFilterGrade = isFilterGrade;
	}

	public String getLoanStatusName() {
		return loanStatusName;
	}

	public void setLoanStatusName(String loanStatusName) {
		this.loanStatusName = loanStatusName;
	}

	public Integer getYearNum() {
		return yearNum;
	}

	public void setYearNum(Integer yearNum) {
		this.yearNum = yearNum;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getTeachTaskType() {
		return teachTaskType;
	}

	public void setTeachTaskType(String teachTaskType) {
		this.teachTaskType = teachTaskType;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getSqlReslut() {
		return sqlReslut;
	}

	public void setSqlReslut(String sqlReslut) {
		this.sqlReslut = sqlReslut;
	}

	public Integer getTermId() {
		return termId;
	}

	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	public Integer getMajorId() {
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getPerTypeCode() {
		return perTypeCode;
	}

	public void setPerTypeCode(String perTypeCode) {
		this.perTypeCode = perTypeCode;
	}

	public String getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getPerIdCard() {
		return perIdCard;
	}

	public void setPerIdCard(String perIdCard) {
		this.perIdCard = perIdCard;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getSecondTypeCode() {
		return secondTypeCode;
	}

	public void setSecondTypeCode(String stuTypeCode) {
		this.secondTypeCode = stuTypeCode;
	}

	public String[] getSecondTypeCodes() {
		return secondTypeCodes;
	}

	public void setSecondTypeCodes(String[] stuTypeCodes) {
		this.secondTypeCodes = stuTypeCodes;
	}

	public String getSecondTypeLevel1() {
		return secondTypeLevel1;
	}

	public void setSecondTypeLevel1(String stuTypeLevel1) {
		this.secondTypeLevel1 = stuTypeLevel1;
	}

	public String getSecondTypeLevel2() {
		return secondTypeLevel2;
	}

	public void setSecondTypeLevel2(String stuTypeLevel2) {
		this.secondTypeLevel2 = stuTypeLevel2;
	}

	public String getSecondTypeLevel3() {
		return secondTypeLevel3;
	}

	public void setSecondTypeLevel3(String stuTypeLevel3) {
		this.secondTypeLevel3 = stuTypeLevel3;
	}

	public String getSecondType() {
		if (secondTypeLevel1 == null || secondTypeLevel2 == null
				|| secondTypeLevel3 == null)
			return secondTypeCode;
		else {
			if (secondTypeLevel1 == null || secondTypeLevel1.equals("")
					|| secondTypeLevel1.equals("-1")
					|| secondTypeLevel2 == null || secondTypeLevel2.equals("")
					|| secondTypeLevel2.equals("-1")
					|| secondTypeLevel3 == null || secondTypeLevel3.equals("")
					|| secondTypeLevel3.equals("-1"))
				return "-1";
			else
				return secondTypeLevel1 + secondTypeLevel2 + secondTypeLevel3;
		}
	}

	public String[] getSecondTypes() {
		if (secondTypeLevel1s == null || secondTypeLevel2s == null
				|| secondTypeLevel3s == null)
			return secondTypeCodes;
		else {
			if (secondTypeLevel1 == null || secondTypeLevel1.equals("")
					|| secondTypeLevel1.equals("-1")
					|| secondTypeLevel2 == null || secondTypeLevel2.equals("")
					|| secondTypeLevel2.equals("-1")
					|| secondTypeLevel3 == null || secondTypeLevel3.equals("")
					|| secondTypeLevel3.equals("-1"))
				return new String[] { "-1" };
			else
				return new String[] { secondTypeLevel1 + secondTypeLevel2
						+ secondTypeLevel3 };
		}
	}

	public String getStuTypeCode() {
		return secondTypeCode;
	}

	public void setStuTypeCode(String stuTypeCode) {
		this.secondTypeCode = stuTypeCode;
	}

	public String getStuTypeLevel1() {
		return secondTypeLevel1;
	}

	public void setStuTypeLevel1(String stuTypeLevel1) {
		this.secondTypeLevel1 = stuTypeLevel1;
	}

	public String getStuTypeLevel2() {
		return secondTypeLevel2;
	}

	public void setStuTypeLevel2(String stuTypeLevel2) {
		this.secondTypeLevel2 = stuTypeLevel2;
	}

	public String getStuTypeLevel3() {
		return secondTypeLevel3;
	}

	public void setStuTypeLevel3(String stuTypeLevel3) {
		this.secondTypeLevel3 = stuTypeLevel3;
	}

	public String getStuType() {
		return getSecondType();
	}

	public String getTeaTypeCode() {
		return secondTypeCode;
	}

	public void setTeaTypeCode(String stuTypeCode) {
		this.secondTypeCode = stuTypeCode;
	}

	public String getTeaTypeLevel1() {
		return secondTypeLevel1;
	}

	public void setTeaTypeLevel1(String stuTypeLevel1) {
		this.secondTypeLevel1 = stuTypeLevel1;
	}

	public String getTeaTypeLevel2() {
		return secondTypeLevel2;
	}

	public void setTeaTypeLevel2(String stuTypeLevel2) {
		this.secondTypeLevel2 = stuTypeLevel2;
	}

	public String getTeaTypeLevel3() {
		return secondTypeLevel3;
	}

	public void setTeaTypeLevel3(String stuTypeLevel3) {
		this.secondTypeLevel3 = stuTypeLevel3;
	}

	public String getTeaType() {
		return getSecondType();
	}

	public String getMajorType() {
		return majorType;
	}

	public void setMajorType(String majorType) {
		this.majorType = majorType;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public HashMap<String, String> getSaveHashMapOfAttribute() {
		HashMap<String, String> map = super.getSaveHashMapOfAttribute();
		if (secondTypeCode != null && secondTypeCode.length() != 0
				&& !secondTypeCode.equals("-1")) {
			map.put("secondTypeCode", secondTypeCode);
		}
		if (grade != null && grade.length() != 0 && !grade.equals("-1")) {
			map.put("grade", grade);
		}
		if (majorId != null && !majorId.equals(-1)) {
			map.put("majorId", majorId + "");
		}
		return map;
	}

	public void setSaveHashMapOfAttribute(HashMap<String, String> map) {
		if (map == null)
			return;
		super.setSaveHashMapOfAttribute(map);
		String str;
		str = map.get("secondTypeCode");
		if (str != null) {
			secondTypeCode = str;
		}
		str = map.get("grade");
		if (str != null) {
			grade = str;
		}
		str = map.get("majorId");
		if (str != null) {
			majorId = new Integer(str);
		}
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getMajorNum() {
		return majorNum;
	}

	public void setMajorNum(String majorNum) {
		this.majorNum = majorNum;
	}

	public String getPerEngName() {
		return perEngName;
	}

	public void setPerEngName(String perEngName) {
		this.perEngName = perEngName;
	}

	public Integer getStudyState() {
		return studyState;
	}

	public void setStudyState(Integer studyState) {
		this.studyState = studyState;
	}

	public Integer[] getMajorIds() {
		return majorIds;
	}

	public void setMajorIds(Integer[] majorIds) {
		this.majorIds = majorIds;
	}

	public String[] getGrades() {
		return grades;
	}

	public void setGrades(String[] grades) {
		this.grades = grades;
	}

	public Integer[] getClassIds() {
		return classIds;
	}

	public void setClassIds(Integer[] classIds) {
		this.classIds = classIds;
	}

	public Integer[] getTermIds() {
		return termIds;
	}

	public void setTermIds(Integer[] termIds) {
		this.termIds = termIds;
	}

	public Integer[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(Integer[] groupIds) {
		this.groupIds = groupIds;
	}

	public String[] getDataSources() {
		return dataSources;
	}

	public void setDataSources(String[] dataSources) {
		this.dataSources = dataSources;
	}

	public String[] getLoanStatuss() {
		return loanStatuss;
	}

	public void setLoanStatuss(String[] loanStatuss) {
		this.loanStatuss = loanStatuss;
	}

	public String[] getSecondTypeLevel1s() {
		return secondTypeLevel1s;
	}

	public void setSecondTypeLevel1s(String[] secondTypeLevel1s) {
		this.secondTypeLevel1s = secondTypeLevel1s;
	}

	public String[] getSecondTypeLevel2s() {
		return secondTypeLevel2s;
	}

	public void setSecondTypeLevel2s(String[] secondTypeLevel2s) {
		this.secondTypeLevel2s = secondTypeLevel2s;
	}

	public String[] getSecondTypeLevel3s() {
		return secondTypeLevel3s;
	}

	public void setSecondTypeLevel3s(String[] secondTypeLevel3s) {
		this.secondTypeLevel3s = secondTypeLevel3s;
	}

	public String[] getLoanStatusNames() {
		return loanStatusNames;
	}

	public void setLoanStatusNames(String[] loanStatusNames) {
		this.loanStatusNames = loanStatusNames;
	}

	public String[] getCourseNos() {
		return courseNos;
	}

	public void setCourseNos(String[] courseNos) {
		this.courseNos = courseNos;
	}

	public String[] getTeachTaskTypes() {
		return teachTaskTypes;
	}

	public void setTeachTaskTypes(String[] teachTaskTypes) {
		this.teachTaskTypes = teachTaskTypes;
	}

	public Integer getTutorId() {
		return tutorId;
	}

	public void setTutorId(Integer tutorId) {
		this.tutorId = tutorId;
	}

	public String getStudyStateSql() {
		return studyStateSql;
	}

	public void setStudyStateSql(String studyStateSql) {
		this.studyStateSql = studyStateSql;
	}

	public String getStuTypeCodeSql() {
		return stuTypeCodeSql;
	}

	public void setStuTypeCodeSql(String stuTypeCodeSql) {
		this.stuTypeCodeSql = stuTypeCodeSql;
	}

	public String getPerEnglishGivenName() {
		return perEnglishGivenName;
	}

	public void setPerEnglishGivenName(String perEnglishGivenName) {
		this.perEnglishGivenName = perEnglishGivenName;
	}

	public String getPerEnglishFamilyName() {
		return perEnglishFamilyName;
	}

	public void setPerEnglishFamilyName(String perEnglishFamilyName) {
		this.perEnglishFamilyName = perEnglishFamilyName;
	}

	public Integer[] getStudyStates() {
		return studyStates;
	}

	public void setStudyStates(Integer[] studyStates) {
		this.studyStates = studyStates;
	}

	public Integer getLeaveCause() {
		return leaveCause;
	}

	public void setLeaveCause(Integer leaveCause) {
		this.leaveCause = leaveCause;
	}

	public Integer[] getLeaveCauses() {
		return leaveCauses;
	}

	public void setLeaveCauses(Integer[] leaveCauses) {
		this.leaveCauses = leaveCauses;
	}

	public String getExamNo() {
		return examNo;
	}

	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}

	public List getCollegeList() {
		return collegeList;
	}

	public void setCollegeList(List collegeList) {
		this.collegeList = collegeList;
	}

	public List getGradeList() {
		return gradeList;
	}

	public void setGradeList(List gradeList) {
		this.gradeList = gradeList;
	}

	public List getMajorList() {
		return majorList;
	}

	public void setMajorList(List majorList) {
		this.majorList = majorList;
	}

	public List getStuTypeCodeList() {
		return stuTypeCodeList;
	}

	public void setStuTypeCodeList(List stuTypeCodeList) {
		this.stuTypeCodeList = stuTypeCodeList;
	}

	public String getControlState() {
		return controlState;
	}

	public void setControlState(String controlState) {
		this.controlState = controlState;
	}

	public List getStudyStateList() {
		return studyStateList;
	}

	public void setStudyStateList(List studyStateList) {
		this.studyStateList = studyStateList;
	}

	public String getGrade1() {
		return grade1;
	}

	public void setGrade1(String grade1) {
		this.grade1 = grade1;
	}

	public String[] getGrade1s() {
		return grade1s;
	}

	public void setGrade1s(String[] grade1s) {
		this.grade1s = grade1s;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public String getReviewMode() {
		return reviewMode;
	}

	public void setReviewMode(String reviewMode) {
		this.reviewMode = reviewMode;
	}

	public Integer[] getProcessStates() {
		return processStates;
	}

	public void setProcessStates(Integer[] processStates) {
		this.processStates = processStates;
	}

	public String[] getReviewModes() {
		return reviewModes;
	}

	public void setReviewModes(String[] reviewModes) {
		this.reviewModes = reviewModes;
	}

	public String getScholarType() {
		return scholarType;
	}

	public void setScholarType(String scholarType) {
		this.scholarType = scholarType;
	}

	public String[] getScholarTypes() {
		return scholarTypes;
	}

	public void setScholarTypes(String[] scholarTypes) {
		this.scholarTypes = scholarTypes;
	}

	public Integer[] getCheckIds() {
		return checkIds;
	}

	public void setCheckIds(Integer[] checkIds) {
		this.checkIds = checkIds;
	}

	public String getGraDegreeDate() {
		return graDegreeDate;
	}

	public void setGraDegreeDate(String graDegreeDate) {
		this.graDegreeDate = graDegreeDate;
	}

	public String[] getGraDegreeDates() {
		return graDegreeDates;
	}

	public void setGraDegreeDates(String[] graDegreeDates) {
		this.graDegreeDates = graDegreeDates;
	}
}
