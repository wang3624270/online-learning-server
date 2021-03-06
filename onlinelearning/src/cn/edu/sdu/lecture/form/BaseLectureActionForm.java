package cn.edu.sdu.lecture.form;

import java.util.Date;
import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class BaseLectureActionForm extends UForm{

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
    private Date startTime;
    private Date endTime;
    private String examTime;
    private String termNum;
    private String termType;
    private Integer isVisible;
    private String startDate;
    private String endDate;
    private String taskName;
    private Integer accId;
    private Integer id;//资源Id
	private String accName;//资源名称
	private String accUrl;//资源路径
	private String accType;
	private Integer accUploader;
	private Date uploadTime;
	private String fileType;//文件类型
	private Long fileSize;//文件长度
	private String uploader;
	private String uploadDate;
	private Integer sectionId;// 节次ID
	private String sectionName;//节次名称
	private Integer orderNum;//顺序
	private Integer personId;//创建人
	private String matchType;
	private String folderMap;
	private String sectionMap;
	private Integer newsId;
	private String titile;//新闻标题
	private String content;//新闻内容
	private Integer newsNum;
	private String createTimeStr;
	private String introduction;//介绍
	private Double courseScore;
	private String courseScoreStr;
	private Integer number;
	private String perName;
	private String perNum;
	private Integer questionNum;
	private Integer answerNum;
	private Integer unanswerNum;
	private Integer questionId;
	private String state;
	private Integer coverImgAcc;
	private String coverImg;
	private String comment;
	private Integer commentId;
	private String startTimeStr;
	private String endTimeStr;
	private String label;
	private String value;
	private Integer videoAcc;
	private Integer audioAcc;
	private Integer pptAcc;
	private Integer videoId;
	private Integer audioId;
	private Integer pptId;
	private Integer lectureId;
	private String theme;
	private Integer creator;
	private Integer modifier;
	private String place;
	private String lectureType;
	private Integer maxNumber;
	private String registStart;
	private String registEnd;
	private String speaker;
	private String introduce;
	private Integer entryId;
	private String cancelReason;
	private String refuseReason;
	private List applyList;
	private List checkedList;
	private List auditedList;
	private String mobilePhone;
	private String attendance;
	
	
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public List getAuditedList() {
		return auditedList;
	}
	public void setAuditedList(List auditedList) {
		this.auditedList = auditedList;
	}
	public List getApplyList() {
		return applyList;
	}
	public void setApplyList(List applyList) {
		this.applyList = applyList;
	}
	public List getCheckedList() {
		return checkedList;
	}
	public void setCheckedList(List checkedList) {
		this.checkedList = checkedList;
	}
	public Integer getEntryId() {
		return entryId;
	}
	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public Integer getLectureId() {
		return lectureId;
	}
	public void setLectureId(Integer lectureId) {
		this.lectureId = lectureId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Integer getModifier() {
		return modifier;
	}
	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getLectureType() {
		return lectureType;
	}
	public void setLectureType(String lectureType) {
		this.lectureType = lectureType;
	}
	public Integer getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}
	public String getRegistStart() {
		return registStart;
	}
	public void setRegistStart(String registStart) {
		this.registStart = registStart;
	}
	public String getRegistEnd() {
		return registEnd;
	}
	public void setRegistEnd(String registEnd) {
		this.registEnd = registEnd;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
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
	public Integer getAccId() {
		return accId;
	}
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccUrl() {
		return accUrl;
	}
	public void setAccUrl(String accUrl) {
		this.accUrl = accUrl;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public Integer getAccUploader() {
		return accUploader;
	}
	public void setAccUploader(Integer accUploader) {
		this.accUploader = accUploader;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getFolderMap() {
		return folderMap;
	}
	public void setFolderMap(String folderMap) {
		this.folderMap = folderMap;
	}
	public String getSectionMap() {
		return sectionMap;
	}
	public void setSectionMap(String sectionMap) {
		this.sectionMap = sectionMap;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getNewsNum() {
		return newsNum;
	}
	public void setNewsNum(Integer newsNum) {
		this.newsNum = newsNum;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Double getCourseScore() {
		return courseScore;
	}
	public void setCourseScore(Double courseScore) {
		this.courseScore = courseScore;
	}
	public String getCourseScoreStr() {
		return courseScoreStr;
	}
	public void setCourseScoreStr(String courseScoreStr) {
		this.courseScoreStr = courseScoreStr;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getPerName() {
		return perName;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}
	public String getPerNum() {
		return perNum;
	}
	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}
	public Integer getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
	public Integer getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}
	public Integer getUnanswerNum() {
		return unanswerNum;
	}
	public void setUnanswerNum(Integer unanswerNum) {
		this.unanswerNum = unanswerNum;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getCoverImgAcc() {
		return coverImgAcc;
	}
	public void setCoverImgAcc(Integer coverImgAcc) {
		this.coverImgAcc = coverImgAcc;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getVideoAcc() {
		return videoAcc;
	}
	public void setVideoAcc(Integer videoAcc) {
		this.videoAcc = videoAcc;
	}
	public Integer getAudioAcc() {
		return audioAcc;
	}
	public void setAudioAcc(Integer audioAcc) {
		this.audioAcc = audioAcc;
	}
	public Integer getPptAcc() {
		return pptAcc;
	}
	public void setPptAcc(Integer pptAcc) {
		this.pptAcc = pptAcc;
	}
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public Integer getAudioId() {
		return audioId;
	}
	public void setAudioId(Integer audioId) {
		this.audioId = audioId;
	}
	public Integer getPptId() {
		return pptId;
	}
	public void setPptId(Integer pptId) {
		this.pptId = pptId;
	}
	
	
}
