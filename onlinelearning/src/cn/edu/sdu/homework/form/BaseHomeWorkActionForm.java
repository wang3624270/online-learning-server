package cn.edu.sdu.homework.form;

import java.util.Date;

public class BaseHomeWorkActionForm {
	private Integer courseId;// 课程ID
	private String courseNum;// 课程号
	private String courseName;// 课程名
	private String courseEngName;// 英文课程名
	private String courseType;// 2016新课程类别  NKCSXM  1 公共课  0 非公共课
	private String teachingWayCode;// 教学方式 数据字典JXFSM
	private String examType;// 考核方式 数据字典KHFSM  考核类型
	private String teachLanguage;// 授课语言 数据字典SKYYLXM  考核类型
	private Double credit;// 学分
	private Integer classHour;// 学时
	private Integer termCount;// 学时（学期）
	private Integer teachingHour;// 上课学时
	private Integer experimentHour;// 实验学时
	private Integer onComputerHour;// 上机学时
	private Integer collegeId;// 开课院系
	private Integer collegeId1;// 开课院系
	private String teachGroup;// 教师组
	private String book;// 教材
	private String reference;// 参考书
	private String briefIntroduction;// 课程简介  
	private String englishBriefIntroduction;
	private String remark;// 备注
	private Date createTime;
	private Date modifyTime;
	private Integer modifyerId;
	private String subSpec;// 课程说明
	private String collegeName;
	private String teacherName;
	private String process;
	private Integer taskId;
	private Integer termId;
    private String termName;
    private String termEngName;
    private String startTime;
    private String endTime;
    private String examTime;
    private String termNum;
    private String termType;
    private Integer isVisible;
    private String startDate;
    private String endDate;
    private String taskName;
    private Integer homeworkId;
	private String chapterName;
	private Date checkTime;
	private String content;
	private Integer attachId;
	private Integer personId;
	private String name;
	private String checkDate;
	private String attachName;
	private String uploadState;
	private Integer submitNum;
	private Integer checkNum;
	private String stuNum;
    private String perName;
    private String subState;
    private Double score;
    private String checkState;
    private String state;
    private Integer total;
    private Integer submit;
    private String loginName;
    private String createTimeStr;
    private String checkTimeStr;
    private String answerContent;
    private Integer answerId;
	private Integer activityId;
	private String goal;
	private String activityName;
	private Integer scoreId;
	private Integer stuId;
	private Integer checkerId;
	private Integer grade;
	private String place;
    
    
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Integer getScoreId() {
		return scoreId;
	}
	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}
	public Integer getStuId() {
		return stuId;
	}
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
	public Integer getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(Integer checkerId) {
		this.checkerId = checkerId;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getCheckTimeStr() {
		return checkTimeStr;
	}
	public void setCheckTimeStr(String checkTimeStr) {
		this.checkTimeStr = checkTimeStr;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getSubmit() {
		return submit;
	}
	public void setSubmit(Integer submit) {
		this.submit = submit;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getSubState() {
		return subState;
	}
	public void setSubState(String subState) {
		this.subState = subState;
	}
	public String getStuNum() {
		return stuNum;
	}
	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}
	public String getPerName() {
		return perName;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}
	public Integer getSubmitNum() {
		return submitNum;
	}
	public void setSubmitNum(Integer submitNum) {
		this.submitNum = submitNum;
	}
	public Integer getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(Integer checkNum) {
		this.checkNum = checkNum;
	}
	public String getUploadState() {
		return uploadState;
	}
	public void setUploadState(String uploadState) {
		this.uploadState = uploadState;
	}
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public Integer getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
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
	public String getCourseEngName() {
		return courseEngName;
	}
	public void setCourseEngName(String courseEngName) {
		this.courseEngName = courseEngName;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getTeachingWayCode() {
		return teachingWayCode;
	}
	public void setTeachingWayCode(String teachingWayCode) {
		this.teachingWayCode = teachingWayCode;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getTeachLanguage() {
		return teachLanguage;
	}
	public void setTeachLanguage(String teachLanguage) {
		this.teachLanguage = teachLanguage;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Integer getClassHour() {
		return classHour;
	}
	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}
	public Integer getTermCount() {
		return termCount;
	}
	public void setTermCount(Integer termCount) {
		this.termCount = termCount;
	}
	public Integer getTeachingHour() {
		return teachingHour;
	}
	public void setTeachingHour(Integer teachingHour) {
		this.teachingHour = teachingHour;
	}
	public Integer getExperimentHour() {
		return experimentHour;
	}
	public void setExperimentHour(Integer experimentHour) {
		this.experimentHour = experimentHour;
	}
	public Integer getOnComputerHour() {
		return onComputerHour;
	}
	public void setOnComputerHour(Integer onComputerHour) {
		this.onComputerHour = onComputerHour;
	}
	public Integer getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	public Integer getCollegeId1() {
		return collegeId1;
	}
	public void setCollegeId1(Integer collegeId1) {
		this.collegeId1 = collegeId1;
	}
	public String getTeachGroup() {
		return teachGroup;
	}
	public void setTeachGroup(String teachGroup) {
		this.teachGroup = teachGroup;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public String getEnglishBriefIntroduction() {
		return englishBriefIntroduction;
	}
	public void setEnglishBriefIntroduction(String englishBriefIntroduction) {
		this.englishBriefIntroduction = englishBriefIntroduction;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getModifyerId() {
		return modifyerId;
	}
	public void setModifyerId(Integer modifyerId) {
		this.modifyerId = modifyerId;
	}
	public String getSubSpec() {
		return subSpec;
	}
	public void setSubSpec(String subSpec) {
		this.subSpec = subSpec;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getTermId() {
		return termId;
	}
	public void setTermId(Integer termId) {
		this.termId = termId;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public String getTermEngName() {
		return termEngName;
	}
	public void setTermEngName(String termEngName) {
		this.termEngName = termEngName;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public String getTermNum() {
		return termNum;
	}
	public void setTermNum(String termNum) {
		this.termNum = termNum;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public Integer getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
    
    
}
